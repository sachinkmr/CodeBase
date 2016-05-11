/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapient.unilever;

import static com.sapient.unilever.SpiderConfig.config;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author sku202
 */
public class Crawler implements Runnable {

    public CountDownLatch latch;

    public Crawler(CountDownLatch latch) {
        this.latch = latch;
    }

    public void processURL(WebURL curUrl) {
        curUrl.setProccessed(true);
        String uaString = UserAgents.getInstance().agentData.get(curUrl.getUserAgent());
        curUrl.setUserAgentString(uaString);
        String url = curUrl.getUrl();
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setRedirectsEnabled(true)
                .setCircularRedirectsAllowed(true)
                .setRelativeRedirectsAllowed(true)
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                .setSocketTimeout(config.getSocketTimeout())
                .setConnectTimeout(config.getConnectionTimeout())
                .build();
        httpget.setConfig(requestConfig);
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setUserAgent(uaString);
        if (config.isAuthenticate()) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(config.getUsername(), config.getPassword()));
            builder.setDefaultCredentialsProvider(credentialsProvider);
        }
        CloseableHttpClient httpclient = builder.build();
        try {
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
            try {
                if (redirectLocations != null) {
                    for (URI s : redirectLocations) {
                        curUrl.redirectPath.add(s.toASCIIString());
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.WARN, null, ex);
            }
            curUrl.setRedirectTo(location.toString());
            curUrl.setStatusCode(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 401) {
                curUrl.setErrorMsg(response.getStatusLine().toString());
                curUrl.setComment("Please provide valid credentials.");
                curUrl.setRedirectTo("");
            }
            EntityUtils.consume(response.getEntity());
        } catch (IOException | URISyntaxException ex) {
            curUrl.setErrorMsg(ex.toString());
            Logger.getLogger(Crawler.class.getName()).log(Level.WARN, null, ex);
        } catch (Exception ex) {
            curUrl.setErrorMsg(ex.toString());
            Logger.getLogger(Crawler.class.getName()).log(Level.WARN, null, ex);
        } finally {

        }
    }

    @Override
    public void run() {
        WebURL url = null;
        while (getTracker() < config.links.size()) {
            try {
                url = config.links.get(getTracker());
                if (!url.isProccessed()) {
                    processURL(url);
                    System.out.println("Processing: " + url.getUrl());
                    new Thread(new Validator(url)).start();
                }
            } catch (Exception ex) {
                url.setErrorMsg(ex.toString());
                Logger.getLogger(Crawler.class.getName()).log(Level.WARN, null, ex);
            } finally {
                updateTracker();
            }
        }
        if (latch.getCount() == 1) {
            verifyUnProcssedUrl();
        }
        latch.countDown();
    }

    public synchronized int getTracker() {
        return config.tracker;
    }

    public synchronized void updateTracker() {
        config.tracker++;
    }

    private void verifyUnProcssedUrl() {
        for (WebURL url : config.links) {
            if (!url.isProccessed()) {
                processURL(url);
            }
        }
    }
}
