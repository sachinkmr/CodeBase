/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.selenium;

/**
 *
 * @author sku202
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import sachin.spider.SpiderConfig;
import sachin.spider.WebSpider;
import sachin.spider.WebURL;

public class Demoss extends WebSpider {

    public final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|ico|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf|txt" + "|doc|xls|java|cs|cpp|xml|ppt" + "|rm|smil|wmv|swf|wma|zip|rar|gz))", Pattern.CASE_INSENSITIVE);
    final String site;
    String host;

    public Demoss(String site) {
        this.site = site;
        try {
            this.host = new URL(site).getHost().replaceAll("www.", "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Demoss.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean shouldVisit(String url) {
        String urlHost = null;
        try {
            urlHost = new URL(url).getHost().replaceAll("www.", "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Demoss.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ((!FILTERS.matcher(url).find()) && urlHost.equalsIgnoreCase(host));
    }

    @Override
    public void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusDescription) {
//        if(statusCode>400)
        System.out.println(statusCode+" : "+webUrl.getUrl());

    }

    void go() {
        SpiderConfig config = new SpiderConfig(site.trim());
        config.setConnectionRequestTimeout(1000000);
        config.setConnectionTimeout(1000000);
        config.setSocketTimeout(1000000);
        config.setTotalSpiders(10);
        config.setUsername("wlnonproduser");
        config.setPassword("Pass@word11");
        config.setAuthenticate(true);
        try {
            config.start(this, config);
        } catch (Exception ex) {
            Logger.getLogger(Demoss.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("all done");
    }

    public static void main(String... str) {
//        List<String> list = new ArrayList<>();
//        list.add("http://knorr-reg.unileversolutions.com/");
//        list.add("http://nexxus-reg.unileversolutions.com");
//        list.add("http://bestfoods-reg.unileversolutions.com");
//        list.add("http://hellmanns-ca-reg.unileversolutions.com");
//        list.add("http://hellmanns-ca-reg.unileversolutions.com/fr-ca");
//        list.add("http://liptontea-reg.unileversolutions.com");
//        list.add("http://knorr-ca-reg.unileversolutions.com/");
//        list.add("http://knorr-ca-reg.unileversolutions.com/fr-ca");
//        list.add("http://clear-cn-reg.unileversolutions.com");
//        list.add("http://tresemme-reg.unileversolutions.com/");
//        list.add("http://neutral-dk-reg.unileversolutions.com");
//        list.add("http://knorr-tw-reg.unileversolutions.com/");
//        list.add("http://m-becel-ca-reg.unileversolutions.com/");
//        list.add("http://m-knorr-ca-reg.unileversolutions.com");
        
//        for(String ss:list)
        new Demoss("http://knorr-reg.unileversolutions.com/").go();
    }
}
