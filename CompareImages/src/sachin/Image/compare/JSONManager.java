/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.Image.compare;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class JSONManager {

    public JSONObject createComparingJSON(JSONObject json1, JSONObject json2) {
        JSONObject json = new JSONObject();
        
        try {
            String diff = json1.getString("dir")+"differnces"+File.separator;
            
            json.put("diff", diff);
            new File(diff).mkdirs();
            if (json1.length() > json2.length()) {
                for (int i = 1; i < json1.length(); i++) {
                    JSONObject j1 = json1.getJSONObject(Integer.toString(i));
                    for (int j = 1; j < json2.length(); j++) {
                        JSONObject j2 = json2.getJSONObject(Integer.toString(j));
                        if (j2.getString("url").equals(j1.getString("url"))) {
                            JSONObject js = new JSONObject();
                            js.put("url", j2.getString("url"));
                            js.put("pic1", j1.getString("imageLocation"));
                            js.put("pic2", j2.getString("imageLocation"));
                            js.put("differences", diff);
                            js.put("matched", false);
                            json.put(Integer.toString(i), js);
                            break;
                        }
                    }
                }
            } 
            else {
                for (int i = 1; i < json2.length(); i++) {
                    JSONObject j2 = json2.getJSONObject(Integer.toString(i));
                    for (int j = 1; j < json1.length(); j++) {
                        JSONObject j1 = json1.getJSONObject(Integer.toString(j));
                        if (j1.getString("url").equals(j2.getString("url"))) {
                            JSONObject js = new JSONObject();
                            js.put("url", j1.getString("url"));
                            js.put("pic1", j2.getString("imageLocation"));
                            js.put("pic2", j1.getString("imageLocation"));
                            js.put("differences", diff);
                            js.put("matched", false);
                            json.put(Integer.toString(i), js);
                            break;
                        }
                    }
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
