/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.URLCanonicalizer;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class SiteSearch implements Featurable {

    private WebDriverBuilder builder;
    private WebDriver driver;
    private JSONObject data;
    private CookieStore cookieStore;
    private final Site site;
    JSONObject result = new JSONObject();

    public SiteSearch(Site site) {
        this.site = site;
    }

    @Override
    public boolean hasFeature() {
        String search = null;
        try {
            JSONObject siteConfig = site.getConfigAsJSON();
            search = siteConfig.getString("EnableSiteSearch");
        } catch (JSONException ex) {
            Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (site.isResponsive()) {
            return "true".equals(search.toLowerCase()) && site.getSiteHTML().contains("bws-site-search");
        }
        if (site.isNonResponsive()) {
            return "true".equals(search.toLowerCase()) && site.getSiteHTML().contains("searchForm");
        }
        
        return false;
    }

    @Override
    public boolean isWorking() {
        if (!hasFeature()) {
            return false;
        }
        return getData().has("TotalCount");
    }

    @Override
    public JSONObject getData() {
        JSONObject map = new JSONObject();
        JSONObject errors = new JSONObject();
        if (!hasFeature()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Feature is not working");
                result.put("errors", errors);
            } catch (JSONException ex) {
                Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }
        String keyword = "search";
        try {
            keyword = site.getParamJSON().getString("siteSearch");
        } catch (JSONException ex) {
            Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new JSONObject();
        if (site.isResponsive()) {
            builder = site.getWebDriverBuilder();
            driver = site.getDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.navigate().to(site.getUrl());
            
            
            try {
                result.put("hasErrors", false);
                HttpPost authpost = new HttpPost(URLCanonicalizer.getCanonicalURL(site.getUrl() + "/search/" + keyword));
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(site.getUsername(), site.getPassword()));
                HttpClientContext context = HttpClientContext.create();
                CloseableHttpClient httpclient = HttpClients.custom()
                        .setDefaultCookieStore(cookieStore)
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .build();
                CloseableHttpResponse httpResponse = httpclient.execute(authpost, context);
                HttpEntity entity = httpResponse.getEntity();
                cookieStore = context.getCookieStore();
                authpost = new HttpPost(URLCanonicalizer.getCanonicalURL(site.getUrl() + "/GetSiteSearchCategory"));
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("hdnKeyword", keyword));
                authpost.setEntity(new UrlEncodedFormEntity(params));
                httpResponse = httpclient.execute(authpost, context);
                entity = httpResponse.getEntity();
                cookieStore = context.getCookieStore();
                data = new JSONObject(EntityUtils.toString(entity));
                EntityUtils.consume(entity);
                httpResponse.close();
                httpclient.close();
            } catch (Exception ex) {
                Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    result.put("hasErrors", true);
                    errors.put("siteSearch", "Search is not working");
                } catch (JSONException ex1) {
                    Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        if (site.isNonResponsive()) {
            builder = site.getWebDriverBuilder();
            driver = site.getDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.navigate().to(site.getUrl());
            WebElement element = driver.findElement(By.id("searchForm"));
            String searchURl = element.findElement(By.id("searchUrl")).getAttribute("value");
            String url = URLCanonicalizer.getCanonicalURL(site.getUrl() + searchURl + keyword);
            driver.navigate().to(url);
            if (driver.getPageSource().contains("SearchResultsLanding")) {
                try {
                    Document doc = Jsoup.parse(driver.getPageSource());
                    Elements e = doc.select(".advance-search-results .subcl .search-row");

                    if (e.size() > 0) {
                        result.put("hasErrors", false);
                        data.put("keyword", keyword);
                        for (Element el : e) {
                            String results[] = el.select("h2").first().text().replaceAll("\\u0029", "").replaceAll("\\u0028", "").split(" ");
                            System.out.println(Arrays.toString(results));
                            int x = getIntValue(results);
                            data.put(results[0], x);
                        }
                    }
                } catch (Exception ex) {
                    try {
                        result.put("hasErrors", true);
                        errors.put("Error", "Not working");
                    } catch (JSONException ex1) {
                        Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    result.put("hasErrors", true);
                    errors.put("Error", "search is not working");
                } catch (JSONException ex) {
                    Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (site.isMOS()) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Not supported in MOS");
            } catch (JSONException ex) {
                Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject getJsonResult() {
        try {
            result.put("functionality", this.getClass().getSimpleName());
            result.put("hasFeature", hasFeature());
            result.put("data", getData());
            HelperClass.writeFunctionality(site.getHost(), this.getClass().getSimpleName(), result);
        } catch (JSONException ex) {
            Logger.getLogger(SiteSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private int getIntValue(String[] results) {
        int x = 0;
        for (String s : results) {
            if (!s.trim().isEmpty()) {
                try {
                    x = Integer.parseInt(s);
                    break;
                } catch (Exception e) {
                }
            }
        }
        return x;
    }
}
