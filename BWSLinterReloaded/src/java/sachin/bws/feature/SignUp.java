/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.feature;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.Template;
import sachin.bws.site.URLCanonicalizer;
import sachin.bws.selenium.SeleniumHandler;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class SignUp implements Featurable {

    private final Site site;
    private int statusCode;
    private Document doc;
    private WebDriverBuilder builder1;
    private WebDriver driver;
    private final String url;
    JSONObject result = new JSONObject();

    public SignUp(Site site) {
        this.site = site;
        url = URLCanonicalizer.getCanonicalURL(site.getUrl() + "/contactus");
    }

    @Override
    public boolean hasFeature() {
        try {
            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setRedirectsEnabled(true)
                    .setCircularRedirectsAllowed(true)
                    .setRelativeRedirectsAllowed(true)
                    .setConnectionRequestTimeout(site.getStatusCode() * 1000)
                    .setSocketTimeout(site.getStatusCode() * 1000)
                    .setConnectTimeout(site.getStatusCode() * 1000)
                    .build();
            httpget.setConfig(requestConfig);
            HttpClientBuilder builder = HttpClientBuilder.create();
            builder.setUserAgent(site.getUserAgent());
            if (site.hasAuthentication()) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(site.getUsername(), site.getPassword()));
                builder.setDefaultCredentialsProvider(credentialsProvider);
            }
            CloseableHttpClient httpclient = builder.build();
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            doc = Jsoup.parse(IOUtils.toString(response.getEntity().getContent()), site.getUrl());
            EntityUtils.consume(response.getEntity());
            statusCode = response.getStatusLine().getStatusCode();
            response.close();
            httpclient.close();
            String template = Template.getInstance().getTemplate(doc);
            return statusCode < 400 && template.equalsIgnoreCase("ContactUs");
        } catch (IOException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean isWorking() {
        return false;
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
                Logger.getLogger(BVSEO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JSONObject();
        }
        builder1 = site.getWebDriverBuilder();
        driver = site.getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        // driver.manage().deleteAllCookies();
        driver.navigate().to(url);
        if (driver.getPageSource().contains("recaptcha_response_field") || driver.getPageSource().contains("sc2powered")) {
            try {
                result.put("hasErrors", true);
                errors.put("captchaError", "Unable to submit form because of captcha");
            } catch (JSONException ex) {
                Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                result.put("hasErrors", false);
                JSONObject data = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "signUpData.json");
                WebElement element = null;
                if (site.isResponsive()) {
                    try {
                        SeleniumHandler.fillForm(data.getJSONObject("NonResponsive"), driver, wait, driver.getPageSource());
                        driver.findElement(By.id("submit")).submit();
                    } catch (Exception ex) {
                        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                        result.put("hasErrors", true);
                        errors.put("formError", "unable to insert values in form");
                    }
                    try {
                        element = driver.findElement(By.className("SignUpConfirm"));
                        map.put("output", "Form submitted successfully");
                    } catch (Exception ex) {
                        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                        result.put("hasErrors", true);
                        errors.put("formError", "Form is giving error after submitting");
                    }
                }
                if (site.isNonResponsive()) {
                    try {
                        SeleniumHandler.fillForm(data.getJSONObject("NonResponsive"), driver, wait, driver.getPageSource());
                        driver.findElement(By.id("submit")).submit();
                    } catch (Exception ex) {
                        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                        result.put("hasErrors", true);
                        errors.put("formError", "unable to insert values in form");
                    }
                    try {
                        element = driver.findElement(By.className("SignUpConfirm"));
                        map.put("output", "Form submitted successfully");
                    } catch (org.openqa.selenium.NotFoundException ex) {
                        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                        result.put("hasErrors", true);
                        errors.put("formError", "Form is giving error after submitting");
                    }
                }
                if (site.isMOS()) {
                    try {
                        SeleniumHandler.fillForm(data.getJSONObject("NonResponsive"), driver, wait, driver.getPageSource());
                        driver.findElement(By.id("")).submit();
                    } catch (Exception ex) {
                        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                        result.put("hasErrors", true);
                        errors.put("formError", "unable to insert values in form");
                    }
                    try {
                        element = driver.findElement(By.className("SignUpConfirm"));
                        map.put("output", "Form submitted successfully");
                    } catch (org.openqa.selenium.NotFoundException ex) {
                        Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                        result.put("hasErrors", true);
                        errors.put("formError", "Form is giving error after submitting");
                    }
                }
            } catch (Exception ex) {
                try {
                    Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
                    result.put("hasErrors", true);
                    errors.put("formError", "Unable to submit form");
                } catch (JSONException ex1) {
                    Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        try {
            result.put("errors", errors);
        } catch (JSONException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //builder.destroy();
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
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
