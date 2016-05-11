/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import sachin.analytics.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class SiteBuilder extends Site {

    public SiteBuilder setViewPort(int viewPortWidth, int viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
        this.viewPortWidth = viewPortWidth;
        return this;
    }

    @Override
    public boolean hasAuthentication() {
        return authenticate;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getViewPortHeight() {
        return viewPortHeight;
    }

    @Override
    public int getViewPortWidth() {
        return viewPortWidth;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public SiteBuilder setUsername(String username) {
        if (username != null && !username.trim().equalsIgnoreCase("")) {
            authenticate = true;
            this.username = username;
        } else {
            authenticate = false;
        }
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public SiteBuilder setPassword(String password) {        
        this.password = password;
        return this;
    }

    @Override
    public String getUrlWithAuth() {
        return urlWithAuth;
    }

    public void setUrlWithAuth() {
        if (authenticate) {
            URL address = null;
            try {
                address = new URL(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(SiteBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
            String protocol = address.getProtocol() + "://";
            String path = url.replaceFirst(protocol, "");
            this.urlWithAuth = protocol + username + ":" + password + "@" + path;
        } else {
            this.urlWithAuth = url;
        }
//        System.out.println(urlWithAuth);

    }

    public SiteBuilder setUserAgent(String userAgent) {
        try {
            JSONObject us=HelperClass.getUserAgentDetails(userAgent);
            if(us==null){
                us=  HelperClass.getUserAgentDetails("Desktop(Windows 7) Firefox v36");
            }
            this.userAgent = us.getString("string");
            this.viewPortHeight=us.getInt("height");
            this.viewPortWidth=us.getInt("width");            
        } catch (JSONException ex) {
            Logger.getLogger(SiteBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public SiteBuilder setCrawling(boolean crawling) {
        this.crawling = crawling;
        return this;
    }

    public SiteBuilder setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public SiteBuilder setCulture(String culture) {
        this.culture = culture;
        return this;
    }

    public SiteBuilder setEnvironment(String environment) {
        this.environment = environment;
        return this;
    }

    public SiteBuilder(String url) {
        super(url);
    }

    public Site build() {
        try {
            initialize();
        } catch (Exception ex) {
            Logger.getLogger(SiteBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    private void initialize() throws Exception {
        try {
            host = new URL(url).getHost();
        } catch (MalformedURLException ex) {
            Logger.getLogger(SiteBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            HttpGet authpost = new HttpGet(URLCanonicalizer.getCanonicalURL(getUrl()));
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setRedirectsEnabled(true)
                    .setCircularRedirectsAllowed(true)
                    .setRelativeRedirectsAllowed(true)
                    .setConnectionRequestTimeout(Data.timeout)
                    .setSocketTimeout(Data.timeout)
                    .setConnectTimeout(Data.timeout)
                    .build();
            CloseableHttpClient httpclient = null;
            if (authenticate) {
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(getUsername(), getPassword()));
                httpclient = HttpClients.custom()
                        .setDefaultRequestConfig(requestConfig)
                        .setUserAgent(this.getUserAgent())
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .build();
            } else {
                httpclient = HttpClients.custom()
                        .setDefaultRequestConfig(requestConfig)
                        .setUserAgent(this.getUserAgent())
                        .build();
            }
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse httpResponse = null;
            HttpEntity entity = null;
            try {
                httpResponse = httpclient.execute(authpost, context);
                entity = httpResponse.getEntity();
            } catch (Exception ex) {
                Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                errors.add(ex.getMessage());
            }
            statusCode = httpResponse.getStatusLine().getStatusCode();
            statusMsg = httpResponse.getStatusLine().getReasonPhrase();
            this.running = httpResponse.getStatusLine().getStatusCode() < 400;
            siteHTML = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
            httpResponse.close();
            httpclient.close();
            setUrlWithAuth();
        } catch (IOException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
            errors.add(ex.getMessage());
            return;
        }

    }

    @Override
    public String getEnvironment() {
        return this.environment;
    }

    @Override
    public String getCulture() {
        return this.culture;
    }

    @Override
    public String getBrandName() {
        return this.brandName;
    }

    @Override
    public String getUserAgent() {
        return this.userAgent;
    }

    @Override
    public boolean isCrawling() {
        return this.crawling;
    }

    @Override
    public String getStatusMsg() {
        return this.statusMsg;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getSiteHTML() {
        return this.siteHTML;
    }

    @Override
    public List<String> getErrors() {
        return this.errors;
    }

    @Override
    public EventFiringWebDriver getDriver() {
        return driver;
    }

    @Override
    public WebDriverBuilder getWebDriverBuilder() {
        return builder;
    }

}
