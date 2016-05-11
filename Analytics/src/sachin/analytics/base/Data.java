/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.io.IOUtils;

/**
 *
 * @author sku202
 */
public class Data {

    private static JSONObject DATA_CONFIG;
    public static final String UA_DESKTOP="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.52 Safari/537.36";
    public static final String UA_TABLET="Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";
    public static final String UA_MOBILE="Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";
    public static int timeout;
    public static int threads;
    static {
        try {
            JSONObject json=HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "config.json");
            DATA_CONFIG = new JSONObject(IOUtils.readFully(new FileInputStream(HelperClass.getDataFolderPath()+ "config.json")));
            threads=Integer.parseInt(json.getString("threads"));
            timeout=Integer.parseInt(json.getString("Timeout"))*1000;
            System.setProperty("phantomjs.binary.path", HelperClass.getDataFolderPath()+Data.getDATA_CONFIG().getString("phantomBrowserEXE"));
            
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JSONObject getDATA_CONFIG() {
        return DATA_CONFIG;
    }

}
