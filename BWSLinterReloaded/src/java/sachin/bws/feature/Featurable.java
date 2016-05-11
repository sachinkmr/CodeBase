/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public interface Featurable extends Runnable {
    
    boolean hasFeature();

    boolean isWorking();

    JSONObject getData();

    JSONObject getJsonResult();
}
