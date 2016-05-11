/*
 // * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import sachin.analytics.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class Base {

    public static void main(String str[]) {
        WebDriverBuilder builder = new WebDriverBuilder();
        File uploadedExcelFile = new File("D:\\BWSAnalytics.xlsx");
        ExcelReader excelReader = new ExcelReader(uploadedExcelFile);
        excelReader.initializeUserAgents();
        List<AnalyticsData> analyticsData = excelReader.readEventData();
        JSONObject sites = excelReader.readSiteData();
        excelReader = null;
        JSONArray array1 = sites.names();
        for (int i = 0; i < array1.length(); i++) {
            try {
                String siteName = (String) array1.getString(i);
                System.out.println("Running on " + siteName);
                JSONObject json = sites.getJSONObject(siteName);
                Site site = new SiteBuilder(siteName)
                        .setBrandName(json.getString("brand"))
                        .setCulture(json.getString("culture"))
                        .setEnvironment(json.getString("environment"))
                        .setUserAgent(json.getString("userAgent"))
                        .setUsername(json.getString("username"))
                        .setPassword(json.getString("password"))
                        //                        .setCrawling(true)
                        .build();
                EventFiringWebDriver driver = builder.getFirefoxDriverWithProxy(site);
                site.setWebDriverBuilder(builder);
                site.setWebDriver(driver);
                for (int j = 0; j < analyticsData.size(); j++) {
                    AnalyticsData aData = analyticsData.get(j);
                    try {
                        Class cls = Class.forName("sachin.analytics.modules." + aData.getTemplate());
                        Analytics a = (Analytics) cls.newInstance();
                        a.setSite(site);
                        a.setAnalyticsData(aData);
                        for (int k = 0; k < aData.getComponent().size(); k++) {
                            Component component = aData.getComponent().get(k);                            
                            a.setComponent(component);
                            a.analyticsResult();
                            System.out.println(component.getName());
                            System.out.println(component.getStatus());
                            System.out.println(component.getActual());
                            System.out.println(component.getExpectedParams());
                            System.out.println(component.getComment());
                            break;
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(Base.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                builder.destroy();
                break;
                
            } catch (JSONException ex) {
                Logger.getLogger(Base.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
