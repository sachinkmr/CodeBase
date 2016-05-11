/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.UrlLink;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class RecipeRating implements Featurable {

    JSONObject result = new JSONObject();
    private final Site site;
    private WebDriverBuilder builder;
    private WebDriver driver;

    public RecipeRating(Site site) {
        this.site = site;
        this.driver = site.getDriver();
    }

    @Override
    public boolean hasFeature() {
        try {
            return site.getConfigAsJSON().getString("EnableRating").trim().equalsIgnoreCase("true");
        } catch (JSONException ex) {
            Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RecipeRating.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean isWorking() {
        if (!site.isResponsive()) {
            return false;
        }
        return true;
    }

    @Override
    public JSONObject getData() {
        JSONObject errors = new JSONObject();
        if (!hasFeature()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Feature is not enabled");
                result.put("errors", errors);
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }
        List<UrlLink> links = site.getRecipeDetailPages();
        String link = null;
        if (!links.isEmpty()) {
            link = links.get(0).getUrl();
        }
//        try {
//            link = site.getAllTemplates().getString("RecipeLanding01");
//        } catch (JSONException ex) {
//            Logger.getLogger(RecipeRating.class.getName()).log(Level.SEVERE, null, ex);
//        }
        JSONObject map = new JSONObject();
        if (link == null || link.isEmpty()) {
            try {
                result.put("hasErrors", true);
                result.put("working", "no");
                errors.put("PageError", "Recipe page not found");
            } catch (JSONException ex1) {
                Logger.getLogger(RecipeRating.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } else {
            driver.navigate().to(link);
            if (driver.getPageSource().contains("bws-rating-wrapper") || driver.getPageSource().contains("bws-rating-stars") || driver.getPageSource().contains("mainRating")) {
                try {
                    result.put("hasErrors", false);
                    result.put("working", "yes");
                    map.put("working", "yes");
                    map.put("url", link);
                } catch (JSONException ex) {
                    Logger.getLogger(RecipeRating.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        //builder.destroy();

        return map;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject getJsonResult() {
        try {
            result.put("functionality", this.getClass().getSimpleName());
            result.put("hasFeature", hasFeature());
            result.put("data", getData());
            HelperClass.writeFunctionality(site.getHost(), this.getClass().getSimpleName(), result);
        } catch (JSONException ex) {
            Logger.getLogger(BazaarVoice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
