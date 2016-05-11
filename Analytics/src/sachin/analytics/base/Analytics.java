/*
 /*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import sachin.analytics.selenium.WebDriverBuilder;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Test;

/**
 *
 * @author sku202
 */
public abstract class Analytics implements Trackable {

    private Site site;
    protected WebDriverBuilder builder;
    protected EventFiringWebDriver driver;
    protected JSONObject error;
    private Component component;
    private AnalyticsData analyticsData;

    public abstract boolean featureEnabled();
    
    @Test
    protected abstract void analyticsResult();
    
    protected String computeUpperCaseTextValue() {
        return null;
    }

    public void setSite(Site site) {
        this.site = site;
        builder = site.getWebDriverBuilder();
        driver = site.getDriver();
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        error = new JSONObject();
    }

    public Site getSite() {
        return site;
    }

    public List<String> getURLs(String templateName) {
        String urls[];
        List<String> list = new ArrayList<>();
        try {
            urls = getSite().getAllTemplates().getString(templateName).split(",");
            for (int i = 0; i < urls.length; i++) {
                list.add(urls[i]);
            }
        } catch (JSONException ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

  

    public String[] verifyHAR(String[] expectedOptinValues) {
        Har har = null;
        try {
            Thread.sleep(5000);
            har=builder.getProxy().getHar();
        } catch (Exception ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        String arr[] = new String[2];
        String actual[] = null;
        short count = 0;
        boolean matched = false;
        for (HarEntry entry : har.getLog().getEntries()) {
            boolean check = false;
            String url = entry.getRequest().getUrl();
            if (url.contains("www.google-analytics.com") && url.contains("/collect?v=1")) {
//                System.out.println(url);
                String[] test = getParamFromRequest(url);
                check = Arrays.equals(expectedOptinValues, test);
                if (check) {
                    count++;
                    matched = true;
                    actual = test;
                }
                this.getComponent().setActual(Arrays.toString(test));
            }
            if (matched) {
                this.getComponent().setActual(Arrays.toString(actual));
            }
        }
        if (matched) {
            arr[0] = "true";
            arr[1] = "Matched";
        } else if (count > 1) {
            arr[0] = "false";
            arr[1] = "Matched, but event is called " + count + " times";
        } else {
            arr[0] = "false";
            arr[1] = "Not Matched";
        }
        return arr;
    }

    public String[] getParamFromRequest(String requestUrl) {
        String arr[] = new String[3];
        try {
            List<NameValuePair> params = URLEncodedUtils.parse(new URI(requestUrl), "UTF-8");
            for (NameValuePair param : params) {
                if (param.getName().equalsIgnoreCase("ec")) {
                    arr[0] = param.getValue().trim();
                }
                if (param.getName().equalsIgnoreCase("ea")) {
                    arr[1] = param.getValue().trim();
                }
                if (param.getName().equalsIgnoreCase("el")) {
                    arr[2] = param.getValue().trim();
                }
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public AnalyticsData getAnalyticsData() {
        return analyticsData;
    }

    public void setAnalyticsData(AnalyticsData analyticsData) {
        this.analyticsData = analyticsData;
    }

}
