/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.templateFinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class HelperClass {

    public static String getCrawledDataLocation() {        
        return System.getProperty("appPath") + File.separator + "Data" + File.separator + "crawledData";
    }

    public static JSONObject readCrawledData(String host) {
        host = host.replaceAll(".com", "");
        File file = new File(getCrawledDataLocation(), host);
        return readJsonFromFile(file.getAbsolutePath());

    }

    /**
     * Method to read JSON from a file.
     *
     * @param file JSON file path(Absolute path)
     * @return JSON data in file
     */
    public  static JSONObject readJsonFromFile(String file) {
        String data = null;
        JSONObject json = null;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(new FileInputStream(new File(file)));
            Map<String, String> maps=(Map<String, String> )is.readObject();
            json = new JSONObject(maps);       
            is.close();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
