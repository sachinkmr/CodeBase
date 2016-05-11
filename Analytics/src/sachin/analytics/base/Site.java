/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import sachin.analytics.selenium.WebDriverBuilder;


/**
 *
 * @author sku202
 */
public abstract class Site {

//    private Featurable feature;
    protected String url;
    protected String username = "", password = "", urlWithAuth, statusMsg, host;
    protected String userAgent;
    protected List<String> errors;
    protected boolean authenticate = false, running, crawling;
    protected String brandName = "", culture = "", environment = "";
    protected int statusCode;
    protected String siteHTML;
//    List<UrlLink> urlLinks;
    private boolean doCrawling = true;
    protected int viewPortHeight, viewPortWidth;
    protected EventFiringWebDriver driver;
    protected WebDriverBuilder builder;

   
    public abstract WebDriverBuilder getWebDriverBuilder() ;

    public abstract EventFiringWebDriver getDriver() ;

    public void setWebDriver(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public void setWebDriverBuilder(WebDriverBuilder builder) {
        this.builder = builder;
    }

    protected Site(String url) {        
        this.url = url;
    }

    /**
     * to get branch versionof the site
     *
     * @return branch version as string
     */
    public String getBranchVersion() {
        if (!this.isRunning()) {
            return "";
        }
        String branchversion = "unable to detect branchversion";
        String login = getUsername() + ":" + getPassword();
        String base64login = new String(Base64.encodeBase64(login.getBytes()));
        try {
            Response res = this.hasAuthentication() ? Jsoup.connect(URLCanonicalizer.getCanonicalURL(url) + "branchversion.txt").header("Authorization", "Basic " + base64login).followRedirects(true).timeout(180000).execute() : Jsoup.connect(URLCanonicalizer.getCanonicalURL(url) + "branchversion.txt").followRedirects(true).timeout(180000).execute();
           
            if (res == null || res.statusCode() != 200) {
                if (getSiteHTML().contains("brand.css")) {
                    branchversion = getSiteHTML().substring(getSiteHTML().toString().indexOf("brand.css?v="), getSiteHTML().toString().indexOf("brand.css?v=") + 15).substring(12);
                }
            } else {
                branchversion = res.parse().text();
            }
        } catch (IOException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
            errors.add(ex.getMessage());
        }
        return branchversion;
    }

    /**
     * to know if site is MOS or BWS-R or BWS-NR
     *
     * @return branch version as string
     */
    public String getSiteType() {
        try {
            if (!this.isRunning()) {
                return "";
            }

            if (url.contains("m.") || url.contains("m-")) {
                return "MOS site";
            }
            Document doc = Jsoup.parse(getSiteHTML());
            Elements elements = doc.getElementsByTag("meta");
            for (Element e : elements) {
                String name = e.attr("name");
                if (name.equalsIgnoreCase("viewport") && getSiteHTML().contains("<!DOCTYPE html>") && doc.body().hasAttr("data-layout")) {
                    return "BWS Responsive Site";
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "BWS non-responsive site";
    }

  

    public abstract boolean hasAuthentication();

    public abstract String getHost();

    public abstract int getViewPortHeight();

    public abstract int getViewPortWidth();

    public abstract String getUrl();

    public abstract String getUsername();

    public abstract String getUrlWithAuth();

    public abstract String getEnvironment();

    public abstract String getCulture();

    public abstract String getBrandName();

    public abstract String getUserAgent();

    public abstract boolean isCrawling();

    /**
     * to get site running status
     *
     * @return true if site is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * to get site information as JSON object it contains
     * <li>BranchVersion</li>
     * <li>BrandName</li>
     * <li>Culture</li>
     * <li>Environment</li>
     * <li>SiteType</li>
     * <li>RunningStatus</li>
     * <li>StatusCode</li>
     * <li>HostName</li>
     * <li>StatusMsg</li>
     * <li>sitePubId</li>
     * <li>Screenshot</li>
     *
     * @return JSON object
     */
    public JSONObject getJsonSiteInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("BranchVersion", this.getBranchVersion());
        map.put("BrandName", this.getBrandName());
        map.put("Culture", this.getCulture());
        map.put("Environment", this.getEnvironment());
        map.put("SiteType", this.getSiteType());
        map.put("SiteUrl", this.getUrl());
        map.put("hostName", this.getHost());
        map.put("RunningStatus", isRunning() == true ? "Running" : "Not Running");
        map.put("StatusCode", Integer.toString(getStatusCode()));
        map.put("StatusMsg", getStatusMsg());        
        return new JSONObject(map);
    }


    /**
     * method to get status message of the site response
     *
     * @return status message of site as string
     */
    public abstract String getStatusMsg();

    /**
     * method to get status code of the site response
     *
     * @return status code of site as integer
     */
    public abstract int getStatusCode();

    /**
     * method to get all links of the site after crawling
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getAllCrawledLinks() {
        List<UrlLink> list = null;
        if (!(new File(HelperClass.getCrawledDataRepository(this.getHost()), HelperClass.getCrawledDataFilename(this.getHost())).exists())) {
            crawling = true;
        }

        if (isCrawling() && doCrawling) {
            if (!this.isRunning()) {
                return Collections.synchronizedList(new ArrayList<>());
            }
            list = new Crawling(this).getUrlLinks();
            HelperClass.saveCrawlingData(list, HelperClass.getCrawledDataFilename(this.getHost()));
            doCrawling = false;
        } else {
            list = HelperClass.readCrawlingData(HelperClass.getCrawledDataFilename(this.getHost()));
        }
        return list;
    }

    /**
     * method to get all links of the site after crawling which gives 200 as
     * status code
     *
     * @return List of UrlLink of the site which gives 200 as status code
     */
    public List<UrlLink> getCrawledLinks() {
        List<UrlLink> urlLinks = new ArrayList<UrlLink>();
        List<UrlLink> list = getAllCrawledLinks();
        for (UrlLink link : list) {
            if (link.getStatusCode() == 200) {
                urlLinks.add(link);
            }
        }
        return urlLinks;
    }

    /**
     * method to get all links of the site after crawling from a saved location
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getSavedCrawledLinksFromFile() {
        return HelperClass.readCrawlingData(HelperClass.getCrawledDataFilename(this.getHost()));
    }

    /**
     * method to get all product detail pages links of the site
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getProductDetailPages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("productdetail")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all product pages links of the site
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getProductPages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("product")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all article detail pages links of the site
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getArticlePages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("article")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all recipe detail pages links of the site
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getRecipePages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("recipe")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all misc pages links of the site
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getMiscPages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (!link.getTemplateName().toLowerCase().contains("product") && !link.getTemplateName().toLowerCase().contains("article") && !link.getTemplateName().toLowerCase().contains("recipe")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all misc pages with their template name of the site
     *
     * @return JSON Object
     */
    public JSONObject getMiscTemplates() {
        Map<String, String> map = new HashMap<String, String>();
        List<UrlLink> links = getMiscPages();
        for (UrlLink link : links) {
            if (map.containsKey(link.getTemplateName())) {
                String urls = map.get(link.getTemplateName()) + "," + link.getUrl();
                map.put(link.getTemplateName(), urls);
            } else {
                map.put(link.getTemplateName(), link.getUrl());
            }
        }
        return new JSONObject(map);
    }

    /**
     * method to get all recipe pages with their template name of the site
     *
     * @return JSON Object
     */
    public JSONObject getRecipeTemplates() {
        Map<String, String> map = new HashMap<String, String>();
        List<UrlLink> links = getRecipePages();
        for (UrlLink link : links) {
            if (map.containsKey(link.getTemplateName())) {
                String urls = map.get(link.getTemplateName()) + "," + link.getUrl();
                map.put(link.getTemplateName(), urls);
            } else {
                map.put(link.getTemplateName(), link.getUrl());
            }
        }
        return new JSONObject(map);
    }

    /**
     * method to get all product pages with their template name of the site
     *
     * @return JSON Object
     */
    public JSONObject getProductTemplates() {
        Map<String, String> map = new HashMap<String, String>();
        List<UrlLink> links = getProductPages();
        for (UrlLink link : links) {
            if (map.containsKey(link.getTemplateName())) {
                String urls = map.get(link.getTemplateName()).concat("," + link.getUrl());
                map.put(link.getTemplateName(), urls);
            } else {
                map.put(link.getTemplateName(), link.getUrl());
            }
        }
        return new JSONObject(map);
    }

    /**
     * method to get all pages with their template name of the site
     *
     * @return JSON Object
     */
    public JSONObject getAllTemplates() {
        List<UrlLink> urlLinks = getCrawledLinks();
        Map<String, String> map = new HashMap<String, String>();
        for (UrlLink link : urlLinks) {
            if (map.containsKey(link.getTemplateName())) {
                String urls = map.get(link.getTemplateName()) + "," + link.getUrl();
                map.put(link.getTemplateName(), urls);
            } else {
                map.put(link.getTemplateName(), link.getUrl());
            }
        }
        return new JSONObject(map);
    }

    /**
     * method to get all article pages with their template name of the site
     *
     * @return JSON Object
     */
    public JSONObject getArticleTemplates() {
        Map<String, String> map = new HashMap<String, String>();
        List<UrlLink> links = getArticlePages();
        for (UrlLink link : links) {
            if (map.containsKey(link.getTemplateName())) {
                String urls = map.get(link.getTemplateName()) + "," + link.getUrl();
                map.put(link.getTemplateName(), urls);
            } else {
                map.put(link.getTemplateName(), link.getUrl());
            }
        }
        return new JSONObject(map);
    }

    /**
     * method to get all article detail pages.
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getArticleDetailPages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("articledetail")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all Recipe detail pages.
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getRecipeDetailPages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("recipedetail") || link.getTemplateName().toLowerCase().contains("recipe-detail")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all article category pages.
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getArticleCategoryPages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("articlecategory")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * method to get all product category pages.
     *
     * @return List of UrlLink of the site
     */
    public List<UrlLink> getProductCategoryPages() {
        List<UrlLink> urlLinks = getCrawledLinks();
        List<UrlLink> links = new ArrayList<UrlLink>();
        for (UrlLink link : urlLinks) {
            if (link.getTemplateName().toLowerCase().contains("productcategory")) {
                links.add(link);
            }
        }
        return links;
    }

    /**
     * site pages with their template names.
     *
     * @return json object
     */
    public JSONObject getOutputWithTemplates() {
        Map<String, JSONObject> map = new HashMap<String, JSONObject>();
        map.put("siteInfo", getJsonSiteInfo());
        try {
            map.put("product", getProductTemplates());
            map.put("article", getArticleTemplates());
            map.put("recipe", getRecipeTemplates());
            map.put("misc", getMiscTemplates());
        } catch (Exception ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject json = new JSONObject(map);
        HelperClass.writeJSONoutputWithTemplateToFile(json, this.getHost());
        return json;
    }

    /**
     *
     *
     * @return if true, site is responsive site
     */
    public boolean isResponsive() {
        return this.getSiteType().equalsIgnoreCase("BWS Responsive Site");
    }

    /**
     *
     *
     * @return if true, site is MOS site
     */
    public boolean isMOS() {
        return this.getSiteType().equalsIgnoreCase("MOS site");
    }

    /**
     *
     *
     * @return if true, site is non-responsive site
     */
    public boolean isNonResponsive() {
        return this.getSiteType().equalsIgnoreCase("BWS non-responsive site");
    }


    /**
     * method to test functionality on site
     *
     * @return HTML of site source
     */
    public abstract String getSiteHTML();

    /**
     * method to test functionality on site
     *
     * @return List of error messages
     */
    public abstract List<String> getErrors();

    public abstract String getPassword();

}
