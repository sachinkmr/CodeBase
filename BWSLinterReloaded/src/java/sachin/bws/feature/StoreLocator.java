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
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;

/**
 *
 * @author sku202
 */
public class StoreLocator implements Featurable {

    JSONObject result = new JSONObject();
    private final Site site;

    public StoreLocator(Site site) {
        this.site = site;
    }
//StoreLocatorService

    @Override
    public boolean hasFeature() {
        if (!site.isResponsive()) {
            return false;
        }
        try {
            return !site.getConfigAsJSON().getString("StoreLocatorService").equalsIgnoreCase("Disabled");
        } catch (JSONException ex) {
            Logger.getLogger(StoreLocator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StoreLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
