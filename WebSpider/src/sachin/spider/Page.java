/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.spider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author JARVIS
 */
public class Page {
    
    private Header[] headers;
    private final Document document;
    private boolean proccessed;
    private final int docid;
    private int resposneTime;
    private final String address;
    private int statusCode;
    private String statusMessage;
    private List<String> outgoingLinks;
    private List<WebURL> parents;

    /**
     * Constructor of the Page Class
     *
     * @param address URL for the webPage
     * @param document document of the webPage
     */
    Page(String address, Document document) {
        this.address = address;
        this.document = document;
        this.docid = address.hashCode();
        parents = new ArrayList<>();
    }

    /**
     * This function is called to get the all the headers of the page.
     *
     * @return array of all the headers
     */
    public Header[] getHeaders() {
        return headers;
    }

    /**
     * This function is called to set headers
     *
     * @param headers array of headers
     */
    void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    /**
     * This function is called to get the document of the page. This document is
     * the object of Jsoup
     *
     * @return document object of the page
     */
    public Document getDocument() {
        return document;
    }

    /**
     * This function is called to check if the page has been processed.
     *
     * @return boolean value
     */
    boolean isProccessed() {
        return proccessed;
    }
    
    void setProccessed(boolean proccessed) {
        this.proccessed = proccessed;
    }

    /**
     * This function is called to get the unique pageId
     *
     * @return an Integer value for the page
     */
    public int getDocid() {
        return docid;
    }

    /**
     * This function is called to get the page response time
     *
     * @return an Integer value for the time taken to get response.
     */
    public int getResposneTime() {
        return resposneTime;
    }
    
    void setResposneTime(int resposneTime) {
        this.resposneTime = resposneTime;
    }

    /**
     * This function is called to get the page url
     *
     * @return url of the page as String
     */
    public String getAddress() {
        return address;
    }

    /**
     * This function is called to get status code of the page
     *
     * @return an Integer value for status code
     */
    public int getStatusCode() {
        return statusCode;
    }
    
    void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * This function is called to get status message of the page
     *
     * @return string value for status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }
    
    void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * This function is called to get the list of outgoing links from the page
     *
     * @return List of all outgoing links
     */
    public List<String> getOutgoingLinks() {
        outgoingLinks = new ArrayList<String>();
        Elements alinks = document.getElementsByAttribute("href");
        Elements slinks = document.getElementsByAttribute("src");
        for (Element alink : alinks) {
            String link = alink.absUrl("href");
            if (!link.isEmpty() && !outgoingLinks.contains(link)) {
                outgoingLinks.add(link);
            }
        }
        for (Element slink : slinks) {
            String link = slink.absUrl("src");
            if (!link.isEmpty() && !outgoingLinks.contains(link)) {
                outgoingLinks.add(link);
            }
        }
        return outgoingLinks;
    }

    /**
     * This function is called to get the list of external outgoing links from
     * the page
     *
     * @return List of all external outgoing links
     */
    public List<String> getExternalLinks() {
        List<String> externalLinks = new ArrayList<String>();
        if (outgoingLinks == null) {
            outgoingLinks = getOutgoingLinks();
        }
        try {
            URL url = new URL(address);
            String host = url.getHost();
            String baseHref = address.substring(0, address.indexOf(host, 0) + host.length());
            for (String link : outgoingLinks) {
                if (!link.startsWith(baseHref)) {
                    externalLinks.add(link);
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
        }
        return externalLinks;
    }

    /**
     * This function is called to get the list of the source of the images on
     * the page
     *
     * @return List of all images
     */
    public List<String> getImageLinks() {
        List<String> imageLinks = new ArrayList<String>();
        Elements slinks = document.select("img[src]");
        for (Element slink : slinks) {
            String link = slink.absUrl("src");
            if (!link.isEmpty() && !imageLinks.contains(link)) {
                imageLinks.add(link);
            }
        }
        return imageLinks;
    }

    /**
     * This function is called to get the list of all the anchor tag links.
     *
     * @return List of all anchor tags
     */
    public List<String> getHyperLinks() {
        List<String> aLinks = new ArrayList<String>();
        Elements slinks = document.select("a[href]");
        for (Element slink : slinks) {
            String link = slink.absUrl("href");
            if (!link.isEmpty() && !aLinks.contains(link) && !link.equals("#")) {
                aLinks.add(link);
            }
        }
        return aLinks;
    }

    /**
     * This function is called to get all the meta tags.
     *
     * @return Map of all meta tags on the page
     */
    public Map<String, String> getAllMetaTags() {
        Map<String, String> metaTags = new HashMap<String, String>();
        Elements tags = document.select("meta");
        metaTags.put(tags.attr("name"), tags.attr("content"));
        return metaTags;
    }
}
