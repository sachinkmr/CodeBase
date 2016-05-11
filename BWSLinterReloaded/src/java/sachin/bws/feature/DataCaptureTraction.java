/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.CookieStore;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;

/**
 *
 * @author sku202
 */
public class DataCaptureTraction  implements Featurable {
    JSONObject result = new JSONObject();
    private final Site site;


    public DataCaptureTraction(Site site) {
        this.site = site;
    }

    @Override
    public boolean hasFeature() {
      return  false;
    }

    @Override
    public boolean isWorking() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject getData() {
        JSONObject map = new JSONObject();
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
        if (site.isResponsive()) {
            try {
                result.put("hasErrors", true);
                 errors.put("Error", "not automated yet");
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (site.isNonResponsive()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "not automated yet");
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (site.isMOS()) {
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
    
}
