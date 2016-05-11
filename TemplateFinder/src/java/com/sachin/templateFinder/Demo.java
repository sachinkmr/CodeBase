/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.templateFinder;

import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import sachin.spider.WebSpider;
import org.jsoup.nodes.Document;

/**
 *
 * @author JARVIS
 */
public class Demo extends WebSpider {

    public final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|ico|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf|txt" + "|doc|xls|java|cs|cpp|xml|ppt" + "|rm|smil|wmv|swf|wma|zip|rar|gz))", Pattern.CASE_INSENSITIVE);
    String  site = "http://www.pgtips.co.uk";
//    SpiderConfig config;

    @Override
    public boolean shouldVisit(String url) {
        return ((!FILTERS.matcher(url).find()) && url.contains("pgtips.co.uk"));
    }
    @Override
    protected void visitPage(Document document, sachin.spider.WebURL webUrl) {
        System.out.println(webUrl.getUrl());
//        webUrl.setErrorMsg(template.getTemplate(document));
    }
    
    
//    @Override
//    public void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusDescription) {
////        if(statusCode!=200)
//        System.out.println(statusCode + " : " + " : " + statusDescription + " : " + webUrl.getUrl());
////        System.out.println(webUrl.getUrl());
//    }
    void go(){
        JSONObject map = new JSONObject();
        map.put("user", "");
        map.put("appPath", "C:\\Users\\sku202\\Desktop\\Current\\TemplateFinder\\build\\web");
        map.put("siteName", "http://pgtips.co.uk");
        map.put("pass", "");
        map.put("threadValue", 7);
        map.put("timeoutValue", 20000);
        map.put("auth", false);
        Controller control = new Controller(map);
        control.go();
        if (control.isHasError()) {
            System.out.println("Error occured");
        } else {
            String jsonText = JSONValue.toJSONString(control.getUrls());
            System.out.println(control.getUrls().size());
        }
        
//        SpiderConfig config =new SpiderConfig(site.trim());
//        config.setAuthenticate(true);
//        config.setUsername("wlnonproduser");
//        config.setPassword("Pass@word11");
//        config.setConnectionRequestTimeout(100000);
//        config.setConnectionTimeout(100000);
//        config.setSocketTimeout(100000);
//        config.setTotalSpiders(7);     
//        try {
//            config.start(this,config);
//        } catch (Exception ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("all done");
        
    }
    public static void main(String... str) {
        new Demo().go();
        
//        try {
//            System.out.println(new URL("http://exampleknorrcaresponsive-qa.unileversolutions.com/product/detail/364336/chicken").getHost());
//        } catch (IOException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
