package com.sachin.site;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public SiteBuilder setData(Data data) {
        this.data = data;
        return this;
    }

    public SiteBuilder setRedirect(boolean redirect) {
        this.redirect = redirect;
        return this;
    }

    public SiteBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public SiteBuilder setCrawling(boolean crawling) {
        this.crawling = crawling;
        return this;
    }

    public SiteBuilder(String url) {
        super(url);
    }

    public Site build() throws Exception {
        initialize();
        return this;
    }

    private void initialize() throws Exception {
        this.startTime = HelperClass.generateUniqueString();
        host = new URL(url).getHost();
        if (host == null) {
            statusCode = 0;
            statusMsg = "URL not found";
        }
        String url1 = URLCanonicalizer.getCanonicalURL(getUrl());
        url1 = this.data.stay ? (url1.charAt(url1.length() - 1) == '/' ? url1 + "?stay=1" : url1 + "/?stay=1") : url1;
        HttpGet authpost = new HttpGet(url1);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        RequestConfig requestConfig = RequestConfig.custom()
                .setRedirectsEnabled(isRedirect())
                .setCircularRedirectsAllowed(isRedirect())
                .setRelativeRedirectsAllowed(isRedirect())
                .setConnectionRequestTimeout(getTimeout() * 1000)
                .setSocketTimeout(getTimeout() * 1000)
                .setConnectTimeout(getTimeout() * 1000)
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
    public Data getData() {
        return this.data;
    }

    @Override
    public boolean isCrawling() {
        return crawling;
    }

}
