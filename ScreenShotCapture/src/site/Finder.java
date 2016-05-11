/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package site;

import capture.Capture;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import sachin.spider.SpiderConfig;
import sachin.spider.WebSpider;
import sachin.spider.WebURL;

/**
 *
 * @author sku202
 */
public class Finder extends WebSpider {

    /**
     * @param args the command line arguments
     */
    final Site site;
    SpiderConfig config;
    private JSONObject json;
    private String name = "1";
    private int i = 1;
    private String type;

    public Finder(Site site) {
        this.site = site;
        json = new JSONObject();
    }

    @Override
    public boolean shouldVisit(String url) {
        String urlHost = null;
        if (null != url) {
            try {
                urlHost = new URL(url).getHost().replaceAll("www.", "");
            } catch (MalformedURLException ex) {
                Logger.getLogger(Finder.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error in URL: " + url);
            }
        }
        return (url != null && !url.contains("?") && (!FILTERS.matcher(url).find()) && urlHost.contains(site.getHost().replaceAll("www.", "")));
    }

    @Override
    public void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusDescription) {
        if (statusCode == 200) {
            JSONObject url = new JSONObject();  
            name = Integer.toString(i++);
            System.out.println(name+" : "+webUrl.getUrl());
            
            try {
                url.put("url", webUrl.getUrl());
                url.put("statusCode", statusCode);
                url.put("SN", name);
                json.put(name, url);
            } catch (JSONException ex) {
                Logger.getLogger(Finder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    @Override
//    public void visitPage(Document doc,WebURL weburl) {
//        System.out.println(doc);
//        System.out.println(weburl);
//    }
  public  void go(String type) {
        this.type = type;
        config = new SpiderConfig(site.getUrl().trim());
        config.setConnectionRequestTimeout(site.getTimeout());
        config.setConnectionTimeout(site.getTimeout());
        config.setSocketTimeout(site.getTimeout());
        config.setTotalSpiders(5);
        config.setAuthenticate(site.hasAuthentication());
        config.setUsername(site.getUsername());
        config.setPassword(site.getPassword());
        config.setUserAgentString(site.getUserAgent());
        try {
            String unique = HelperClass.generateUniqueString();
            json.put("unique", unique);
            config.start(this, config);
            Thread.sleep(20000);
            System.out.println(json.length());
            String dir = System.getProperty("user.dir") + File.separator + "output" + File.separator + config.getHostName() + File.separator + type + File.separator;
            String dir2 = System.getProperty("user.dir") + File.separator + "output" + File.separator + config.getHostName() + File.separator + type + File.separator + unique + File.separator + "screenshots" + File.separator;
            File f = new File(dir);
            f.mkdirs();
            json.put("dir", dir);
            json.put("screenshotDir", dir2);
            FileWriter fw = new FileWriter(new File(f, unique + ".json"));
            json.write(fw);
            fw.close();
            config = null;
            capture(dir2);
        } catch (Exception ex) {
            Logger.getLogger(Finder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void capture(String dir) {
        new Capture(dir, site).takeScreen(json);
    }

    static {
        System.setProperty("phantomjs.binary.path", System.getProperty("user.dir") + File.separator + "Resources" + File.separator + "phantomjs.exe");
    }
}
