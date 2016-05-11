/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.site;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
import sachin.spider.SpiderConfig;
import sachin.spider.WebSpider;
import sachin.spider.WebURL;

/**
 *
 * @author sku202
 */
public class Crawling extends WebSpider {

    public SpiderConfig config;
    private final List<UrlLink> urlLinks;
    private Site site;

    public Crawling(Site site) {
        this.site = site;
        urlLinks = Collections.synchronizedList(new ArrayList<UrlLink>());
        String url1 = site.getUrl();
        url1 = site.data.stay ? (url1.charAt(url1.length() - 1) == '/' ? url1 + "?stay=1" : url1 + "/?stay=1") : url1;

        config = new SpiderConfig(url1);
        if (site.hasAuthentication()) {
            config.setAuthenticate(true);
            config.setUsername(site.getUsername());
            config.setPassword(site.getPassword());
        }
        config.setUserAgentString(site.getUserAgent());
        config.setConnectionRequestTimeout(site.getData().timeoutValue * 1000);
        config.setConnectionTimeout(site.getData().timeoutValue * 1000);
        config.setSocketTimeout(site.getData().timeoutValue * 1000);
        config.setTotalSpiders(site.getData().threadValue);
    }

    @Override
    public boolean shouldVisit(String url) {
        String hostURL = null;
        try {
            hostURL = new URL(url).getHost().replaceAll("www.", "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Crawling.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return (null != url && hostURL.contains(site.getHost().replaceAll("www.", "")) && (!FILTERS.matcher(url).find()));
    }

    @Override
    public void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusMsg) {
        UrlLink url = new UrlLink(webUrl.getUrl(), statusCode, statusMsg);
        if (urlLinks.contains(url)) {
            UrlLink url1 = urlLinks.get(urlLinks.indexOf(url));
            url1.setStatusCode(statusCode);
            url1.setStatusMsg(statusMsg);
            url1.setSiteName(site.getUrl());
        } else {
            urlLinks.add(url);
        }
//        if(statusCode>200&&statusCode<400)
//        System.out.println(statusCode+" : "+webUrl.getUrl());
    }

    @Override
    public void visitPage(Document document, WebURL webUrl) {
        urlLinks.add(new UrlLink(webUrl.getUrl(), Template.getInstance().getTemplate(document), document));
    }

    public List<UrlLink> getUrlLinks() {
        try {
            System.out.println("Crawling started...");
            config.start(this, config);
        } catch (Exception ex) {
            Logger.getLogger(Crawling.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        System.out.println("Crawling Completed...");
        return urlLinks;
    }
}
