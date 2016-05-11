/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselectorfinder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author sku202
 */
public abstract class Site {

//    private Featurable feature;
    protected String url, comments;
    protected String username = "", password = "", urlWithAuth, statusMsg, host, startTime;
    protected String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
    protected boolean authenticate = false, running;
    protected int statusCode;
    protected String siteHTML;
    protected WebDriver driver;
    protected WebDriverBuilder builder;
    private Set<String> languageSelector;
    private Set<String> countrySelector;

    public Set<String> getLanguageSelector() {
        return languageSelector;
    }

    public Set<String> getCountrySelector() {
        return countrySelector;
    }

    public WebDriverBuilder getWebDriverBuilder() {
        return builder;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setWebDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setWebDriverBuilder(WebDriverBuilder builder) {
        this.builder = builder;
    }

    protected Site(String url) {
        this.url = url;
        this.languageSelector = new HashSet<>();
        this.countrySelector = new HashSet<>();
    }

    /**
     * to know if site is MOS or BWS-R or BWS-NR
     *
     * @return branch version as string
     */
    public String getSiteType() {
        try {
            if (!this.isRunning()) {
                return "Site is not running";
            }
            Document doc = Jsoup.parse(getSiteHTML());
            if (doc.body().attr("id").equalsIgnoreCase("homepage") && doc.toString().contains("ym-wrapper") && doc.select("div.ym-wrapper") != null && doc.select("div.ym-wrapper").size() > 0) {
                return "MOS site";
            }
            if (doc.body().attr("id").equalsIgnoreCase("homepage") && doc.toString().contains("fps-main-wrapper") && doc.select("div.fps-main-wrapper") != null && doc.select("div.fps-main-wrapper").size() > 0) {
                return "BWS Feature Phone Site";
            }
            Elements elements = doc.getElementsByTag("meta");
            for (Element e : elements) {
                String name = e.attr("name");
                if (name.equalsIgnoreCase("viewport") && getSiteHTML().contains("<!DOCTYPE html>") && doc.body().hasAttr("data-layout") && doc.select("div.bws-globalWrapper") != null && doc.select("div.bws-globalWrapper").size() > 0) {
                    return "BWS Responsive Site";
                }
            }
            if (doc.body().hasClass("HomePage") && doc.body().attr("id").trim().equalsIgnoreCase("homepage") && doc.select("div.page") != null && doc.select("div.page").size() > 0) {
                return "BWS non-responsive site";
            }
        } catch (Exception ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Non BWS Site";
    }

    public abstract boolean hasAuthentication();

    public abstract String getHost();

    public abstract String getUrl();

    public abstract String getUsername();

    public abstract String getUrlWithAuth();

    public abstract String getUserAgent();

    /**
     * to get site running status
     *
     * @return true if site is running
     */
    public boolean isRunning() {
        return running;
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

    public abstract String getSiteHTML();

    public abstract String getPassword();

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

        if (getSiteHTML().contains("brand.css")) {
            branchversion = getSiteHTML().substring(getSiteHTML().toString().indexOf("brand.css?v="), getSiteHTML().toString().indexOf("brand.css?v=") + 15).substring(12);
        }

//        String login = getUsername() + ":" + getPassword();
//        String base64login = new String(Base64.encodeBase64(login.getBytes()));
//        try {
//            Response res = this.hasAuthentication() ? Jsoup.connect(URLCanonicalizer.getCanonicalURL(url) + "branchversion.txt").header("Authorization", "Basic " + base64login).followRedirects(true).timeout(180000).execute() : Jsoup.connect(URLCanonicalizer.getCanonicalURL(url) + "branchversion.txt").followRedirects(true).timeout(180000).execute();
//            if (res != null && res.statusCode() < 400) {
//                branchversion = res.parse().text();
//            } else {
//                if (getSiteHTML().contains("brand.css")) {
//                    branchversion = getSiteHTML().substring(getSiteHTML().toString().indexOf("brand.css?v="), getSiteHTML().toString().indexOf("brand.css?v=") + 15).substring(12);
//                }
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return branchversion;
    }
}
