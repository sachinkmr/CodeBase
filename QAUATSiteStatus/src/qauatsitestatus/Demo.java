/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qauatsitestatus;

/**
 *
 * @author sku202
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import sachin.spider.SpiderConfig;
import sachin.spider.WebSpider;
import sachin.spider.WebURL;

public class Demo extends WebSpider {

    public final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|ico|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf|txt" + "|doc|xls|java|cs|cpp|xml|ppt" + "|rm|smil|wmv|swf|wma|zip|rar|gz))", Pattern.CASE_INSENSITIVE);
    final String site;
    SpiderConfig config;
    String host;
    JSONObject data=new JSONObject();
    File file ;
    public Demo(String site) {
        this.site = site;
        try {
            host = new URL(site).getHost().replaceAll("www.", "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        file = new File("D:/sites1/" + host);
        file.mkdirs();
    }

    @Override
    public boolean shouldVisit(String url) {
        return (url != null && url.startsWith(config.getModifiedSiteName()) && (!FILTERS.matcher(url).find()));
    }

    @Override
    public void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusDescription) {
        try { 
            data.put(webUrl.getUrl(), statusCode);
        } catch (JSONException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(webUrl.getUrl());
    }

    void go() {
        config = new SpiderConfig(site.trim());
        config.setConnectionRequestTimeout(1000000);
        config.setConnectionTimeout(1000000);
        config.setSocketTimeout(1000000);
        config.setTotalSpiders(10);
        config.setAuthenticate(true);
        config.setUsername("wlnonproduser");
        config.setPassword("Pass@word11");
        try {
            
            config.start(this, config);
            Thread.sleep(2000);
            BufferedWriter br=new BufferedWriter(new FileWriter(new File(file,"json.json")));
            data.write(br);
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
