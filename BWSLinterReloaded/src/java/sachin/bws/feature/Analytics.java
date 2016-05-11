/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;


import java.util.logging.Level;
import java.util.logging.Logger;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class Analytics implements Featurable {

    JSONObject result = new JSONObject();
    private final Site site;
    private String EnablePageTagging;

    public Analytics(Site site) {
        this.site = site;
        try {
            this.EnablePageTagging = site.getConfigAsJSON().getString("EnablePageTagging");
        } catch (JSONException ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean hasFeature() {
        try {
            return EnablePageTagging.equalsIgnoreCase("true");
        } catch (Exception ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean isWorking() {
        return false;
    }

    @Override
    public JSONObject getData() {
        JSONObject map = new JSONObject();
        JSONObject errors = new JSONObject();
        if (!hasFeature()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Feature is not Enabled");
                result.put("errors", errors);
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }

        boolean googleAnalytics = false, hasError = true;
        Har har = getHar();
        for (HarEntry entry : har.getLog().getEntries()) {
            try {
                String url = entry.getRequest().getUrl();
                if (url.toLowerCase().contains("www.google-analytics.com") && url.toLowerCase().contains("/collect")) {
                    googleAnalytics = true;
                    hasError = false;
                    break;
                }
            } catch (Exception ex) {
                Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
                hasError = true;
                try {
                    errors.put("error", ex.toString());
                } catch (JSONException ex1) {
                    Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

        try {
            map.put("googleAnalytics", googleAnalytics);
            result.put("hasErrors", hasError);
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Har getHar() {
        Har har = null;
        WebDriverBuilder builder = new WebDriverBuilder();
        WebDriver driver = builder.getHeadLessDriverWithProxy(site);
        try {
            BrowserMobProxy proxy = builder.getProxy();
            proxy.newHar("analytics");
            proxy.newPage("analytics");
            driver.navigate().to(site.getUrl());
            Thread.sleep(5000);
            har = proxy.getHar();            
            proxy.stop();
            driver.quit();
        } catch (Exception ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return har;
    }

}
