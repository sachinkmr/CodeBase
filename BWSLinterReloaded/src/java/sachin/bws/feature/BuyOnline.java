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
import org.openqa.selenium.support.ui.WebDriverWait;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.UrlLink;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class BuyOnline implements Featurable {

    JSONObject result = new JSONObject();
    private final Site site;
    private String BINProvider;
    private WebDriverBuilder builder;
    private WebDriver driver;
    private WebDriverWait wait;

    public BuyOnline(Site site) {
        this.site = site;

    }
//BINEnabled

    @Override
    public boolean hasFeature() {
        try {
            BINProvider = site.getConfigAsJSON().getString("BINProvider");
            return site.getConfigAsJSON().getString("BINEnabled").equalsIgnoreCase("true");
        } catch (JSONException ex) {
            Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean isWorking() {

        return true;
    }

    @Override
    public JSONObject getData() {
        JSONObject errors = new JSONObject();
        if (!hasFeature()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Feature is not working");
                result.put("errors", errors);
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }
        Set<String> hash = new HashSet<>();
        Map<String, JSONObject> mapOuter = new HashMap<String, JSONObject>();
        List<UrlLink> links = site.getProductDetailPages();
        builder = site.getWebDriverBuilder();
        driver = site.getDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15);
        for (UrlLink link : links) {
            if (!hash.contains(link.getTemplateName())) {
                boolean hasBINButton = true;
                // driver.manage().deleteAllCookies();
                JSONObject map1 = new JSONObject();
                try {
                    result.put("hasErrors", false);
                    driver.navigate().to(link.getUrl());
                    System.out.println(link.getUrl());
                    try {
                        if (!site.isMOS()) {
                            try {
                                if (BINProvider.equalsIgnoreCase("cartwire")) {
                                    driver.findElement(By.id("buy-online")).findElements(By.xpath("./child::*")).get(0).click();
                                } else {
                                    driver.findElement(By.id("buy-online")).findElement(By.tagName("a")).click();
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
                                hasBINButton = false;
                                result.put("hasErrors", true);
                                map1.put("data", "");
                                map1.put("working", "no");
                                map1.put("template", link.getTemplateName());
                                map1.put("BINProvider", BINProvider);
                                map1.put("buttonError", "BIN Button not found");
                                map1.put("url", link.getUrl());
                            }
                        } else {
                            if (driver.getPageSource().contains("buy-online")) {
                                if (driver.findElement(By.id("buy-online")).getTagName().equalsIgnoreCase("a")) {
                                    driver.findElement(By.id("buy-online")).click();
                                }
                            } else {
                                hasBINButton = false;
                                result.put("hasErrors", true);
                                map1.put("data", "");
                                map1.put("working", "no");
                                map1.put("template", link.getTemplateName());
                                map1.put("BINProvider", BINProvider);
                                map1.put("buttonError", "BIN Button not found");
                                map1.put("url", link.getUrl());
                                errors.put("buttonError", "button not found");
                            }
                        }
                    } catch (Exception ex) {
                        hasBINButton = false;
                        result.put("hasErrors", true);
                        map1.put("data", "");
                        map1.put("working", "no");
                        map1.put("template", link.getTemplateName());
                        map1.put("BINProvider", BINProvider);
                        map1.put("buttonError", "BIN Button not found");
                        map1.put("url", link.getUrl());
                        errors.put("buttonError", "button not found");
                        Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (hasBINButton && BINProvider.equalsIgnoreCase("etale")) {
                        String className = null;
                        if (site.isResponsive()) {
                            className = "bws-bin-modal--iframe";
                        } else {
                            className = "brands";
                        }
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
                        String frameSRC = driver.findElement(By.className(className)).getAttribute("src");                      
                        driver.navigate().to(frameSRC);
                        try {
                            if (driver.getPageSource().contains("</select>")) {
                                driver.findElement(By.tagName("a")).click();
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        boolean check = false;
                       List<WebElement> items=driver.findElements(By.className("item"));
                         for(WebElement ele:items){
                           if(ele.getAttribute("data-modelname").trim().length()>1){
                               check = true;
                               break;
                           }
                       }
                        if (check) {
                            result.put("hasErrors", false);
                            map1.put("working", "yes");
                            map1.put("template", link.getTemplateName());
                            map1.put("BINProvider", BINProvider);
                            map1.put("url", link.getUrl());
                            
                        } else {
                            result.put("hasErrors", true);
                             map1.put("working", "no");
                            map1.put("template", link.getTemplateName());
                            map1.put("BINProvider", BINProvider);
                            map1.put("storeError", "Stores not found");
                            map1.put("url", link.getUrl());
                        }
                        hash.add(link.getTemplateName());
                    } else if (hasBINButton && BINProvider.equalsIgnoreCase("cartwire")) {
                        if (site.isResponsive()) {
                            try {
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialog_cw")));
                                if (driver.getPageSource().contains("retlogolist_cw")) {
                                    List<WebElement> el = driver.findElement(By.className("retlogolist_cw")).findElements(By.tagName("li"));;
                                    if (el.size() > 0) {
                                        map1.put("working", "yes");
                                        map1.put("template", link.getTemplateName());
                                        map1.put("BINProvider", BINProvider);
                                        map1.put("url", link.getUrl());
                                    }
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
                                result.put("hasErrors", true);
                                map1.put("working", "no");
                                map1.put("template", link.getTemplateName());
                                map1.put("BINProvider", BINProvider);
                                map1.put("storeError", "Functioality is not working");
                                map1.put("url", link.getUrl());
                            }
                              hash.add(link.getTemplateName());
                        }
                        if (site.isNonResponsive()) {
                            try {
                                result.put("hasErrors", true);
                                errors.put("Error", "not automated yet");
                            } catch (JSONException ex) {
                                Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (site.isMOS()) {
                            try {
                                result.put("hasErrors", true);
                                errors.put("Error", "not automated yet");
                            } catch (JSONException ex) {
                                Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (hasBINButton) {
                    mapOuter.put(link.getUrl(), map1);
                }
//                break;
            }
        }
        //builder.destroy();
        try {
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONObject(mapOuter);
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
            Logger.getLogger(BuyOnline.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
