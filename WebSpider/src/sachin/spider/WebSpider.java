/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.spider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author JARVIS
 */
public class WebSpider implements Runnable {

    public final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|ico|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf|txt" + "|doc|xls|java|cs|cpp|xml|ppt" + "|rm|smil|wmv|swf|wma|zip|rar|gz))", Pattern.CASE_INSENSITIVE);
    private CountDownLatch latch;
    private int trackers = 0;
    private PoolingHttpClientConnectionManager cm;
    private CloseableHttpClient httpclient = null;
    private SpiderConfig config;
    private static boolean flag2 = true;

    public void setValues(SpiderConfig config, CountDownLatch latch) {
        this.config = config;
        this.latch = latch;
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setUserAgent(config.getUserAgentString());
        cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(config.getTotalSpiders()*2);
        cm.setMaxTotal(config.getTotalSpiders() * 2);

        if (config.isAuthenticate()) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(config.getUsername(), config.getPassword()));

            httpclient = HttpClients.custom()
                    .setUserAgent(config.getUserAgentString())
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setConnectionManager(cm)
                    .build();
        } else {
            httpclient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setUserAgent(config.getUserAgentString())
                    .build();
        }

    }

    /**
     * This function is called just before starting the crawling by this spider
     * instance. It can be used for setting up the data structures or
     * initializations needed by this spider instance.
     */
    protected void onStart() {
    }

    /**
     * This function is called just before the termination of the current spider
     * instance. It can be used for other finalization tasks.
     */
    protected void onBeforeExit() {
    }

    /**
     * It can be overridden by sub-classes to perform custom logic for different
     * status codes. For example, 404 pages can be logged, etc.
     *
     * @param webUrl WebUrl containing the statusCode
     * @param response response of the url
     * @param statusCode Html Status Code number
     * @param statusDescription Html Status COde description
     */
    protected void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusDescription) {

    }

    /**
     * This function is called before processing of the page's URL It can be
     * overridden by subclasses for tweaking of the url before processing it.
     * For example, http://abc.com/def?a=123 - http://abc.com/def
     *
     * @param curURL current URL which can be tweaked before processing
     * @return tweaked WebURL
     */
    protected String handleUrlBeforeProcess(String curURL) {
        return curURL;
    }

    /**
     * Classes that extends WebSpider should overwrite this function to tell the
     * spider whether the given url should be crawled or not. The following
     * default implementation indicates that all urls should be included in the
     * crawling process.
     *
     * @param url the url which we are interested to know whether it should be
     * included in the crawl or not.
     *
     * @return if the url should be included in the crawl it returns true,
     * otherwise false is returned.
     */
    protected boolean shouldVisit(String url) {
        // By default allow all urls to be crawled.
        return true;
    }

    /**
     * Classes that extends WebSpider should overwrite this function to process
     * the response of the url if it has OK status message(Status code: 200)
     *
     * @param document document of the page
     * @param webUrl url of the page
     *
     */
    protected void visitPage(Document document, WebURL webUrl) {

    }

    @Override
    public void run() {
        onStart();
        try {
            boolean flag = true;
            WebURL curUrl = null;
            synchronized (this) {
                if (flag2) {
                    curUrl = config.links.get(0);
                    if (!curUrl.isProccessed()) {
                        processURL(curUrl);
                    }
                    updateTracker();
                    flag2 = false;
                }
            }
            while (flag) {
                int track = getTracker();
                if (track < config.links.size()) {
                    curUrl = config.links.get(track);
                    if (!curUrl.isProccessed()) {
                        processURL(curUrl);
                    }
                    updateTracker();
                } else {
                    Thread.sleep(2000);
                    if (track < config.links.size()) {

                    } else {
                        flag = false;
                    }
                }
            }

            if (latch.getCount() == 1) {
                onBeforeExit();
            }
        } catch (Exception ex) {
            Logger.getLogger(WebSpider.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.latch.countDown();
        }
        verifyUnProcessedUrls();
    }

    private void processURL(WebURL curUrl) {
        curUrl.setProccessed(true);
        String url = curUrl.getUrl();
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = getRequestConfigWithRedirectDisabled();
        httpget.setConfig(requestConfig);
        try {
            HttpClientContext context = HttpClientContext.create();
            long startingTime = System.currentTimeMillis();
            try (CloseableHttpResponse response = httpclient.execute(httpget, context)) {
                long endingTime = System.currentTimeMillis();
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                curUrl.setStatusCode(statusCode);
                curUrl.setStatusMessage(EnglishReasonPhraseCatalog.INSTANCE.getReason(statusCode, Locale.ENGLISH));
                curUrl.setResposneTime(((int) (endingTime - startingTime)) / 1000);
                curUrl.setHeaders(response.getAllHeaders());
                curUrl.setBaseHref(context.getTargetHost().toString());
                if (curUrl.getStatusCode() >= 300 && curUrl.getStatusCode() < 400) {
                    handleRedirectedLink(curUrl);
                } else if (statusCode == 200) {
                    try {
                        processPage(response, curUrl);
                    } catch (Exception ex) {
                        Logger.getLogger(WebSpider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                handleLink(curUrl, response, statusCode, EnglishReasonPhraseCatalog.INSTANCE.getReason(statusCode, Locale.ENGLISH));
                EntityUtils.consumeQuietly(response.getEntity());
                HttpClientUtils.closeQuietly(response);
            }
        } catch (Exception ex) {
            System.out.println(curUrl.getUrl());
            curUrl.setErrorMsg(ex.toString());
            Logger.getLogger(WebSpider.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpget.releaseConnection();
        }
    }

    private void processPage(HttpResponse response, WebURL curUrl) {
        long startingTime = System.currentTimeMillis();
        Document doc = Jsoup.parse(getContentAsString(response), curUrl.getBaseHref());
        long endingTime = System.currentTimeMillis();
        Page page = new Page(curUrl.getUrl(), doc);
        page.setHeaders(response.getAllHeaders());
        page.setResposneTime(curUrl.getResposneTime() + ((int) (endingTime - startingTime)) / 1000);
        page.setStatusCode(curUrl.getStatusCode());
        page.setStatusMessage(curUrl.getStatusMessage());
        page.setProccessed(true);
        viewPage(page);
        List<String> list = page.getOutgoingLinks();
        for (String linkUrl : list) {
            linkUrl = URLCanonicalizer.getCanonicalURL(linkUrl);
            linkUrl = handleUrlBeforeProcess(linkUrl);
            WebURL weburl = new WebURL(linkUrl);
            weburl.addParent(curUrl);
            if (shouldVisit(linkUrl)) {
                if (!config.links.contains(weburl) && !linkUrl.contains("#") && !linkUrl.contains("mailto:")) {
                    config.links.add(weburl);
                }
            }
        }
        visitPage(doc, curUrl);
    }

    private void handleRedirectedLink(WebURL curUrl) {
        String url = curUrl.getUrl();
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = getRequestConfigWithRedirectEnabled();
        httpget.setConfig(requestConfig);
        try {
            HttpClientContext context = HttpClientContext.create();
            long startingTime = System.currentTimeMillis();
            try (CloseableHttpResponse response = httpclient.execute(httpget, context)) {
                long endingTime = System.currentTimeMillis();
                HttpHost target = context.getTargetHost();
                List<URI> redirectLocations = context.getRedirectLocations();
                URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
                String redirectUrl = location.toString();
                curUrl.setRedirectTo(redirectUrl);
                curUrl.setResposneTime(((int) (endingTime - startingTime)) / 1000);
                redirectUrl = URLCanonicalizer.getCanonicalURL(redirectUrl);
                WebURL weburl = new WebURL(redirectUrl);
                weburl.addParent(curUrl);
                if (!config.links.contains(weburl)) {
                    config.links.add(weburl);
                }
                try {
                    if (redirectLocations != null) {
                        for (URI s : redirectLocations) {
                            String urls = URLCanonicalizer.getCanonicalURL(s.toString());
                            WebURL url1 = new WebURL(urls);
                            if (!config.links.contains(url1)) {
                                config.links.add(url1);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(WebSpider.class.getName()).log(Level.SEVERE, null, ex);
                }
                EntityUtils.consumeQuietly(response.getEntity());
                HttpClientUtils.closeQuietly(response);
            }
        } catch (IOException | URISyntaxException ex) {
            System.out.println(curUrl.getUrl());
            curUrl.setErrorMsg(ex.toString());
            Logger.getLogger(WebSpider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println(curUrl.getUrl());
            curUrl.setErrorMsg(ex.toString());
            Logger.getLogger(WebSpider.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpget.releaseConnection();
        }
    }

    private RequestConfig getRequestConfigWithRedirectEnabled() {
        return RequestConfig.custom()
                .setRedirectsEnabled(true)
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                .setSocketTimeout(config.getSocketTimeout())
                .setConnectTimeout(config.getConnectionTimeout())
                .build();
    }

    private RequestConfig getRequestConfigWithRedirectDisabled() {
        return RequestConfig.custom()
                .setRedirectsEnabled(config.isFollowRedirects())
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                .setSocketTimeout(config.getSocketTimeout())
                .setConnectTimeout(config.getConnectionTimeout())
                .build();
    }

    private synchronized int getTracker() {
        return trackers;
    }

    private synchronized void updateTracker() {
        trackers++;
    }

    private void verifyUnProcessedUrls() {
        for (WebURL url : config.links) {
            if (!url.isProccessed()) {
                processURL(url);
            }
        }
    }

    private String getContentAsString(HttpResponse response) {
        String str = null;
        try {
            str = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(WebSpider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    protected void viewPage(Page page) {

    }
}
