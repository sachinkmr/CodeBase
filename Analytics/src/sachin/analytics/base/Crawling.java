/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
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
    private final String host;
    private final List<UrlLink> urlLinks;

    public Crawling(Site site) {
        urlLinks = Collections.synchronizedList(new ArrayList<UrlLink>());
        config = new SpiderConfig(site.getUrl());
        host=config.getHostName();
        if (site.hasAuthentication()|| !site.getUsername().isEmpty()) {
            config.setAuthenticate(site.hasAuthentication());
            config.setUsername(site.getUsername());
            config.setPassword(site.getPassword());
        }
        config.setUserAgentString(site.getUserAgent());
        config.setConnectionRequestTimeout(120000);
        config.setConnectionTimeout(120000);
        config.setSocketTimeout(120000);
        config.setTotalSpiders(10);
        config.setFollowRedirects(true);
    }

    @Override
    public boolean shouldVisit(String url) {
        String urlHost = null;
        try {
            urlHost = new URL(url).getHost().replaceAll("www.","");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Crawling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ((!FILTERS.matcher(url).find()) && urlHost.equalsIgnoreCase(host));
    }

    @Override
    public void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusMsg) {
        UrlLink url = new UrlLink(webUrl.getUrl(), statusCode, statusMsg);
        if (urlLinks.contains(url)) {
            UrlLink url1 = urlLinks.get(urlLinks.indexOf(url));
            url1.setStatusCode(statusCode);
            url1.setStatusMsg(statusMsg);
        } else {
            urlLinks.add(url);
        }
//        System.out.println(url.getUrl());
    }

    @Override
    public void visitPage(Document document, WebURL webUrl) {
        urlLinks.add(new UrlLink(webUrl.getUrl(), Template.getInstance().getTemplate(document), document)); 
    }

    public List<UrlLink> getUrlLinks() {
        System.out.println("Crawling Started");
        try {
            config.start(this, config);
        } catch (Exception ex) {
            Logger.getLogger(Crawling.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        System.out.println("Crawling Finished");
        return urlLinks;
    }
}
