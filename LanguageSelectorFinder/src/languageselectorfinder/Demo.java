/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselectorfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class Demo {

    public static void main(String... s) {
//        FileReader fw = null;
        JSONObject json = new JSONObject();
        String ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
        try {
            FileReader fw = new FileReader("D://all.txt");
            List<String> list = IOUtils.readLines(fw);
            fw.close();
            for (String siteName : list) {
                JSONObject json1 = new JSONObject();
                if (!siteName.startsWith("http")) {
                    siteName = "http://" + siteName;
                }
                if (siteName.contains("://m.") || siteName.contains("://m-")) {
                    ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
                }else{
                    ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
                }
                
                Site site = new SiteBuilder(siteName)
                        .setUserAgent(ua)
                        .build();
                json1.put("type", site.getSiteType());
                json1.put("name", site.getUrl());
                json1.put("status", site.getStatusCode());
                json1.put("branch", site.getBranchVersion());
                json.put(siteName, json1);
                System.out.println( site.getSiteType()+" : "+siteName);
                site=null;
                json1=null;
//                break;
            }
            new ExcelReader(new File("D://SiteType.xlsx")).writeData(json);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
