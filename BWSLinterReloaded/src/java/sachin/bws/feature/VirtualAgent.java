/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import com.google.common.base.Function;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.URLCanonicalizer;
import sachin.bws.site.UrlLink;

/**
 *
 * @author sku202
 */
public class VirtualAgent implements Featurable {
    
    JSONObject result = new JSONObject();
    private final Site site;
    private CookieStore cookieStore;
    private Document doc;
    boolean feature = false;
    String type = null;
    String keyword ="prod";
    
    public VirtualAgent(Site site) {
        this.site = site;
        try {
            this.keyword=site.getParamJSON().getString("virtualAgent");
        } catch (JSONException ex) {
            Logger.getLogger(VirtualAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean hasFeature() {
        try {
            type = site.getConfigAsJSON().getString("HelpCenterService");
            feature = !type.equalsIgnoreCase("disabled");
        } catch (JSONException ex) {
            Logger.getLogger(VirtualAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feature;
    }
    
    @Override
    public boolean isWorking() {
        if (!site.isResponsive()) {
            return false;
        }
        try {
            HttpPost authpost = new HttpPost(URLCanonicalizer.getCanonicalURL(site.getUrl() + "/help/virtualAgent"));
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(site.getUsername(), site.getPassword()));
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .build();
            CloseableHttpResponse httpResponse = httpclient.execute(authpost, context);
            cookieStore = context.getCookieStore();
            if (hasFeature()) {
                authpost = new HttpPost(URLCanonicalizer.getCanonicalURL(site.getUrl() + doc.select("#virtualagentgetanswerurl").val()));
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("question", "tea"));
                params.add(new BasicNameValuePair("sessionId", doc.select("#sessionId").val()));
                authpost.setEntity(new UrlEncodedFormEntity(params));
                httpResponse = httpclient.execute(authpost, context);
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity).contains("bws-faq-virtual-agent-response");
            }
            httpResponse.close();
            httpclient.close();
            return false;
        } catch (IOException ex) {
            Logger.getLogger(VirtualAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public JSONObject getData() {
        JSONObject map = new JSONObject();
        
        JSONObject errors = new JSONObject();
        if (!feature) {
            try {
                result.put("hasErrors", true);
                errors.put("Error", "Feature is not working");
                result.put("errors", errors);
            } catch (JSONException ex) {
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }
        if (!site.isMOS()) {
            try {
                map.put("type", type);
                map.put("working", false);
                String address = null;
                for (UrlLink link : site.getCrawledLinks()) {
                    if (link.getTemplateName().equalsIgnoreCase("virtualAgentPage")) {
                        address = link.getUrl();
                        break;
                    }
                }
                if(address==null){
                    try {
                        result.put("hasErrors", true);
                        errors.put("Error", "Functioanlity is not available on site");
                        result.put("errors", errors);
                    } catch (JSONException ex) {
                        Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     return new JSONObject();
                }
                WebDriver driver = site.getDriver();
                driver.navigate().to(address);
                Wait wait = new FluentWait(driver)
                        .withTimeout(60, TimeUnit.SECONDS)
                        .pollingEvery(2, TimeUnit.SECONDS)
                        .ignoring(NoSuchElementException.class);
                WebElement element = driver.findElement(By.id("question"));
                element.sendKeys(keyword);
                String autoComplete = site.getConfigAsJSON().getString("EnableAutoCompleteRealDialog");
                if (address != null && (type.equalsIgnoreCase("salesForce") || (type.equalsIgnoreCase("realdialog") && site.isResponsive() && autoComplete.equalsIgnoreCase("true")))) {
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-autocomplete-loading")));
                    Thread.sleep(2000);
                    if (driver.findElement(By.id("ui-id-1")).isDisplayed()) {
                        WebElement element1 = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
                            @Override
                            public WebElement apply(WebDriver f) {
                                return ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#ui-id-1 li.ui-menu-item")).apply(driver).get(0);
                            }
                        });
                        element1.click();
                    }
                }
//                if (address != null && type.equalsIgnoreCase("realDialog") && site.isResponsive()) {
//                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ui-autocomplete-loading")));
//                    Thread.sleep(2000);
//                    if (driver.findElement(By.id("ui-id-1")).isDisplayed()) {
//                        WebElement element1 = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
//                            @Override
//                            public WebElement apply(WebDriver f) {
//                                return ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#ui-id-1 li.ui-menu-item")).apply(driver).get(0);
//                            }
//                        });
//                        element1.click();
//                    }
//                }
                element.submit();
                if (driver.findElement(By.id("response-container")).findElements(By.tagName("div")).size() > 0) {
                    result.put("hasErrors", false);
                    map.put("working", true);
                } else {
                    try {
                        result.put("hasErrors", true);
                        errors.put("Error", "Something gone wrong");
                        result.put("errors", errors);
                    } catch (JSONException ex) {
                        Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (Exception ex) {
                try {
                    result.put("hasErrors", true);
                    errors.put("Error", "Something gone wrong");
                    result.put("errors", errors);
                } catch (JSONException ex1) {
                    Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(VirtualAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        try {
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(Analytics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
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
            Logger.getLogger(BazaarVoice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
