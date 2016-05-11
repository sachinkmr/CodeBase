/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.templateFinder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import sachin.spider.SpiderConfig;
import sachin.spider.WebSpider;
import sachin.spider.WebURL;

/**
 *
 * @author Sachin
 */
public class Controller extends WebSpider {

    public final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|ico|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf|txt" + "|doc|xls|java|cs|cpp|xml|ppt" + "|rm|smil|wmv|swf|wma|zip|rar|gz))", Pattern.CASE_INSENSITIVE);
    private int threadValue, timeoutValue;
    private boolean authEnable, hasError, crawl;
    private String user, pass, appPath, siteName, domain, tamplate;
    private Template template;
    private List<WebURL> urls;
    Map<String, String> map = new HashMap<>();

    @Override
    public boolean shouldVisit(String url) {
        boolean check = false;
        try {
            String urlHost = new URL(url).getHost().replaceAll("www.", "");
            check = ((!FILTERS.matcher(url).find()) && domain.equals(urlHost));
        } catch (MalformedURLException ex) {
            hasError = true;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public Controller(org.json.simple.JSONObject map) {
        try {
            JSONObject s = new JSONObject(map.toJSONString());
            this.threadValue = s.getInt("threadValue");
            this.timeoutValue = s.getInt("timeoutValue");
            this.authEnable = s.getBoolean("auth");
            this.user = s.getString("user");
            this.pass = s.getString("pass");
            this.siteName = s.getString("siteName");
            this.appPath = s.getString("appPath");
            this.domain = new URL(siteName).getHost().replaceAll("www.", "");
        } catch (JSONException | MalformedURLException ex) {
            hasError = true;
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        template = Template.getInstance(appPath);
    }

    @Override
    protected void visitPage(Document document, sachin.spider.WebURL webUrl) {
        String temp = template.getTemplate(document);
        if (map.containsKey(temp)) {
            map.put(temp, map.get(temp) + "," + webUrl.getUrl());
        } else {
            map.put(temp, webUrl.getUrl());
        }
    }

    public void go() {
        SpiderConfig config = new SpiderConfig(siteName.trim());
        config.setConnectionRequestTimeout(timeoutValue);
        config.setConnectionTimeout(timeoutValue);
        config.setSocketTimeout(timeoutValue);
        config.setAuthenticate(authEnable);
        config.setUsername(user);
        config.setPassword(pass);
        config.setTotalSpiders(threadValue);
        config.tracker = 0;
        try {
            config.start(this, config);
            urls = config.links;
            writeUrlsWithTemplate();
        } catch (Exception ex) {
            hasError = true;
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public List<WebURL> getUrls() {
        return urls;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    private void writeUrlsWithTemplate() {        
        File file = new File(HelperClass.getCrawledDataLocation());
        file.mkdirs();        
        File file2=new File(file, domain.replaceAll(".com", ""));
        FileUtils.deleteQuietly(file2);
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(file, domain.replaceAll(".com", ""))));
            oos.writeObject(map);
            oos.close();
        } catch (IOException ex) {
            hasError = true;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
