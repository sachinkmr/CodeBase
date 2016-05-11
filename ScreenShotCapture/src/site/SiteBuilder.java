/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package site;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.openqa.selenium.WebDriver;
import selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class SiteBuilder extends Site {

    @Override
    public boolean hasAuthentication() {
        return authenticate;
    }

    @Override
    public String getHost() {
        return host;
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
        if (username != null && !username.trim().isEmpty()) {
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

    public void setUrlWithAuth(String urlWithAuth) {
        if (this.hasAuthentication()) {
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
    }

    public SiteBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
        this.startTime = HelperClass.generateUniqueString();
        try {
            host = new URL(url).getHost();
        } catch (MalformedURLException ex) {
            Logger.getLogger(SiteBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (host == null) {
            statusCode = 0;
            statusMsg = "URL not found";
        }
        try {
            HttpGet authpost = new HttpGet(URLCanonicalizer.getCanonicalURL(getUrl()));
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setRedirectsEnabled(true)
                    .setCircularRedirectsAllowed(true)
                    .setRelativeRedirectsAllowed(true)
                    .setConnectionRequestTimeout(1000000)
                    .setSocketTimeout(1000000)
                    .setConnectTimeout(1000000)
                    .build();
            CloseableHttpClient httpclient = null;
            if (this.hasAuthentication()) {
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
            }
            statusCode = httpResponse.getStatusLine().getStatusCode();
            statusMsg = httpResponse.getStatusLine().getReasonPhrase();
            running = httpResponse.getStatusLine().getStatusCode() < 400;
            siteHTML = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
            httpResponse.close();
            httpclient.close();
        } catch (IOException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getUserAgent() {
        return this.userAgent;
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
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public WebDriverBuilder getWebDriverBuilder() {
        return builder;
    }

    public SiteBuilder setViewPortHeight(int viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
        return this;
    }

   public  SiteBuilder setViewPortWidth(int viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
        return this;
    }

    /**
     * set time out for the url.
     *
     * @param timeout time in miliseconds
     * @return same sitebulder object
     */
    public SiteBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

}
