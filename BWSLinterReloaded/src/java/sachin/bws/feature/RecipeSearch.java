/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.Template;

/**
 *
 * @author sku202
 */
public class RecipeSearch implements Featurable {
//RecipeService

    boolean check = false;
    JSONObject result = new JSONObject();
    private final Site site;
    WebDriver driver;
    private String keyword;

    public RecipeSearch(Site site) {
        this.site = site;
        this.driver = site.getDriver();
        try {
            this.keyword = site.getParamJSON().getString("recipeSearch");
        } catch (JSONException ex) {
            Logger.getLogger(RecipeSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean hasFeature() {

        JSONObject json = site.getConfigAsJSON();
        try {
            if (json.getString("SiteSearchSchemas").toLowerCase().contains("recipe") && !json.getString("RecipeService").toLowerCase().contains("disabled")) {
                check = !site.getConfigAsJSON().getString("SiteSearchSchemas").equalsIgnoreCase("Disabled");
            }
        } catch (JSONException ex) {
            Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RecipeSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    @Override
    public boolean isWorking() {
        if (!site.isResponsive()) {
            return false;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject getData() {
        JSONObject map = new JSONObject();
        JSONObject errors = new JSONObject();
        if (!check) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Feature is not working");
                result.put("errors", errors);
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }
        if (site.isResponsive()) {

            try {
                JSONObject linksWithTemplates = site.getAllTemplates();
                String link = linksWithTemplates.has("RecipeLanding01") ? linksWithTemplates.getString("RecipeLanding01") : null;
                String elementID = "bws-keyword";
                if (link == null) {
                    link = linksWithTemplates.has("RecipeDetail01") ? linksWithTemplates.getString("RecipeDetail01").split(",")[0].trim() : null;
                    elementID = "bws-form-field-search";
                }
                if (link == null) {
                    link = linksWithTemplates.has("RecipeLanding02") ? linksWithTemplates.getString("RecipeLanding02").split(",")[0].trim() : null;
                    elementID = "bws-form-field-search";
                }
                if (link == null) {
                    elementID = "";
                }
                if (link != null) {
                    link=link.split(",")[0].trim();
                    driver.navigate().to(link);
                    if (driver.getPageSource().contains(elementID)) {
                        WebElement e = driver.findElement(By.id(elementID));
                        e.sendKeys(keyword);
                        e.submit();
                        String rTemplate = Template.getInstance().getTemplate(driver.getPageSource());
                        if (rTemplate.equalsIgnoreCase("RecipeSearchResults01")) {
                            result.put("hasErrors", false);
                            String data[] = driver.findElement(By.cssSelector("p.grid-info")).getText().split("\\u0020");
                            int count = 0;
                            if(data!=null){
                               count= getCount(data);
                            }
                            result.put("resultCount", count);
                        }
                    }
                }
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (site.isMOS() || site.isNonResponsive()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "not automated yet");
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONObject();
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

    private int getCount(String[] data) {
        int c = 0;
        boolean assigned = false;
        for (int i = 0; i < data.length; i++) {
            try {
                c = Integer.parseInt(data[i]);
                assigned = true;
                break;
            } catch (Exception e) {
            }
        }
        return assigned ? c : 0;
    }
}
