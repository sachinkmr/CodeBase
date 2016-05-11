/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.UrlLink;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class Kritique implements Featurable {

    JSONObject result = new JSONObject();
    private final Site site;
    private WebDriverBuilder builder;
    private WebDriver driver;

    public Kritique(Site site) {
        this.site = site;
    }
//KritiqueEnabled RecipeService

    @Override
    public boolean hasFeature() {
        try {
            return site.getConfigAsJSON().getString("KritiqueEnabled").equalsIgnoreCase("true") && site.getConfigAsJSON().getString("ProductRatingsProvider").equalsIgnoreCase("Kritique");
        } catch (JSONException ex) {
            Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
//        JSONObject map = new JSONObject();
        JSONObject errors = new JSONObject();
        if (!hasFeature()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Feature is not working");
                result.put("errors", errors);
            } catch (JSONException ex) {
                Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }

        int i = 0;
        List<UrlLink> links = site.getProductDetailPages();
        builder = site.getWebDriverBuilder();
        driver = site.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Map<String, JSONObject> mapOuter = new HashMap<>();
        boolean error = false;
        WebElement e = null;
        Set<String> hash = new HashSet<>();
        for (UrlLink link : links) {
            if (!hash.contains(link.getTemplateName())) {
                System.out.println(link.getTemplateName() + " : " + link.getUrl());
                // driver.manage().deleteAllCookies();
                JSONObject map = new JSONObject();
                boolean check = true;
                try {
                    driver.navigate().to(link.getUrl());
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("wRtng")));
                    driver.findElement(By.className("wRtng")).click();
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("rr-modalWidowcontent")));
                    List<WebElement> elements=driver.findElement(By.className("starRatingsContainer")).findElements(By.tagName("li"));
                    WebElement element = elements.get(elements.size()-1);
                    element.click();
                    while (check && element.isDisplayed()) {
                        submitForm(driver);
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("bv-submission-thankyou")));

                        try {
                            e = driver.findElement(By.className("bv-submission-thankyou"));
                            hash.add(link.getTemplateName());
                        } catch (Exception ex) {
                            Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
                    try {
                        errors.put("Error", ex.toString());
                    } catch (JSONException ex1) {
                        Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                if (e != null) {
                    try {
                        map.put("working", "yes");
                        map.put("template", link.getTemplateName());
                        map.put("url",link.getUrl());
                        check = false;
                    } catch (JSONException ex) {
                        Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        map.put("working", "no");
                        map.put("template", link.getTemplateName());
                        map.put("url",link.getUrl());
                        errors.put("FormError" + i++, "unable to submit BV form");
                    } catch (JSONException ex1) {
                        Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                mapOuter.put(link.getUrl(), map);
            }
        }
        //builder.destroy();
        JSONObject data = new JSONObject(mapOuter);
        try {
            result.put("hasErrors", error);
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(Kritique.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;        
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
    
    private void submitForm(WebDriver driver) {
        String source = driver.getPageSource();
        if (source.contains("bv-radio-rating-5")) {
            driver.findElement(By.id("bv-radio-rating-5")).click();
        }
        if (source.contains("bv-text-field-title")) {
            driver.findElement(By.id("bv-text-field-title")).sendKeys("Review Test Title");
        }
        if (source.contains("bv-textarea-field-reviewtext")) {
            driver.findElement(By.id("bv-textarea-field-reviewtext")).sendKeys("This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. This is review text. ");
        }
        Random rand = new Random();
        if (source.contains("bv-text-field-usernickname")) {
            driver.findElement(By.id("bv-text-field-usernickname")).sendKeys("user" + Integer.toString(rand.nextInt(100000)) + "unie" + Integer.toString(rand.nextInt(100000)));
        }
        if (source.contains("bv-email-field-hostedauthentication_authenticationemail")) {
            driver.findElement(By.name("hostedauthentication_authenticationemail")).sendKeys("xyx@xxx.xxax");
        }
        if (source.contains("bv-select-field-contextdatavalue_Gender")) {
            Select select = new Select(driver.findElement(By.id("bv-select-field-contextdatavalue_Gender")));
            select.selectByValue("Male");
        }
        driver.findElement(By.id("bv-checkbox-agreedtotermsandconditions")).click();
        driver.findElement(By.id("bv-checkbox-agreedtotermsandconditions")).submit();
    }
}
