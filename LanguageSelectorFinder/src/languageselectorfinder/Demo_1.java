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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author sku202
 */
public class Demo_1 {

    public static void main(String... s) {
////        FileReader fw = null;
//        JSONObject json = new JSONObject();
//        String ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
//        try {
//            FileReader fw = new FileReader("D://all1.txt");
//            List<String> list = IOUtils.readLines(fw);
//            fw.close();
//            for (String siteName : list) {
//                JSONObject json1 = new JSONObject();
//                if (!siteName.startsWith("http")) {
//                    siteName = "http://" + siteName;
//                }
//                if (siteName.contains("://m.") || siteName.contains("://m-")) {
//                    ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
//                }else{
//                    ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
//                }
//                
//                Site site = new SiteBuilder(siteName)
//                        .setUserAgent(ua)
//                        .build();
//                Document doc=Jsoup.parse(site.getSiteHTML());
//                Elements el=doc.select("div.fps-main-wrapper");
//                System.out.println(el.size());
//                site=null;
//                json1=null;
//            }
////            new ExcelReader(new File("D://myExcel.xlsx")).writeData(json);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println(System.currentTimeMillis());
    }
}
