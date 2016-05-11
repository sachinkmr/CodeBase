/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capture;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import selenium.WebDriverBuilder;
import site.Site;

/**
 *
 * @author sku202
 */
public class Capture {

    WebDriverBuilder builder;
    EventFiringWebDriver driver;
    JSONObject json;
    Site site;
    String dir;

    public Capture(String dir, Site site) {
        this.dir = dir;
        this.site = site;
        builder = new WebDriverBuilder();
        driver = builder.getFirefoxDriver(site);
        if (site.hasAuthentication()) {
            driver.get(site.getUrlWithAuth());
        }
    }

    public void takeScreen(JSONObject json) {
        try {
            for (int i = 1; i < json.length(); i++) {
                JSONObject js = json.getJSONObject(Integer.toString(i));
                if (js.getInt("statusCode") == 200) {
                    try {
                        driver.navigate().to(js.getString("url"));

                        driver.getScreenshotAs(OutputType.FILE);
                        System.out.println(js.getString("SN") + " : " + js.getString("url"));
                        File saveTo = new File(dir, js.getString("SN") + ".png");
                        File scrFile = driver.getScreenshotAs(OutputType.FILE);
                        try {
                            FileUtils.copyFile(scrFile, saveTo);
                        } catch (IOException ex) {
                            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        js.put("imageLocation", saveTo.getAbsolutePath());
                    } catch (JSONException ex) {
                        Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            FileWriter fw = new FileWriter(new File(json.getString("dir"), json.getString("unique") + ".json"));
            json.write(fw);
            fw.close();
            builder.destroy();
        } catch (JSONException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
