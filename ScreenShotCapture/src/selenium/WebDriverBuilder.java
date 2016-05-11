/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import site.Site;

/**
 *
 * @author sku202
 */
public class WebDriverBuilder {

    private EventFiringWebDriver driver;
    private BrowserMobProxy proxy;
    private boolean proxyCheck;
    private WebDriver webDriver;

    public EventFiringWebDriver getHeadLessDriver(Site site) {
        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());

        //User Agent Settings          
        caps.setCapability("phantomjs.page.settings.userAgent", site.getUserAgent());

        // User Name & Password Settings
        if (site.hasAuthentication()) {
            caps.setCapability("phantomjs.page.settings.userName", site.getUsername());
            caps.setCapability("phantomjs.page.settings.password", site.getPassword());
        }
        webDriver = new PhantomJSDriver(caps);
        if (site.getViewPortHeight() > 0 && site.getViewPortWidth() > 0) {
            Dimension s = new Dimension(site.getViewPortWidth(), site.getViewPortHeight());
            webDriver.manage().window().setSize(s);
        } else {
            webDriver.manage().window().maximize();
        }
        webDriver.manage().timeouts().implicitlyWait(site.getTimeout(), TimeUnit.MILLISECONDS);
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public EventFiringWebDriver getHeadLessDriver() {
        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());
        webDriver = new PhantomJSDriver(caps);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public EventFiringWebDriver getHeadLessDriver(boolean auth, String username, String password) {

        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());
        webDriver = new PhantomJSDriver(caps);
        if (auth) {
            caps.setCapability("phantomjs.page.settings.userName", username);
            caps.setCapability("phantomjs.page.settings.password", password);
        }
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public EventFiringWebDriver getHeadLessDriverWithProxy(Site site) {
        try {
            proxyCheck = true;
            proxy = new BrowserMobProxyServer();
            proxy.setMitmDisabled(false);
            proxy.newHar("NewHAR");
            proxy.start(0);
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            seleniumProxy.setSslProxy("trustAllSSLCertificates");
            DesiredCapabilities caps = DesiredCapabilities.phantomjs();
            caps.setJavascriptEnabled(true);
            caps.setCapability("takesScreenshot", true);
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());  //Command line args             
            caps.setCapability("phantomjs.page.settings.userAgent", site.getUserAgent()); //User Agent Settings     
            caps.setCapability(CapabilityType.PROXY, seleniumProxy);
            // User Name & Password Settings
            if (site.hasAuthentication()) {
                caps.setCapability("phantomjs.page.settings.userName", site.getUsername());
                caps.setCapability("phantomjs.page.settings.password", site.getPassword());
            }
            webDriver = new PhantomJSDriver(caps);
            Dimension s = new Dimension(site.getViewPortWidth(), site.getViewPortHeight());
            webDriver.manage().window().setSize(s);
            webDriver.manage().timeouts().implicitlyWait(site.getTimeout(), TimeUnit.MILLISECONDS);
//            webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public EventFiringWebDriver getHeadLessDriverWithProxy() {
        Random rand = new Random();
        try {
            proxyCheck = true;
            proxy = new BrowserMobProxyServer();
//            proxy.setConnectTimeout(20, TimeUnit.SECONDS);
//            proxy.setIdleConnectionTimeout(20, TimeUnit.SECONDS);
//            proxy.setRequestTimeout(20, TimeUnit.SECONDS);
            proxy.setHostNameResolver(ClientUtil.createDnsJavaResolver());
            proxy.setHostNameResolver(ClientUtil.createNativeCacheManipulatingResolver());
            proxy.newHar(Integer.toString(rand.nextInt(100000)));
            proxy.start(0);
            proxy.newHar();
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            DesiredCapabilities caps = DesiredCapabilities.phantomjs();
            caps.setJavascriptEnabled(true);
            caps.setCapability("takesScreenshot", true);
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());  //Command line args  
            caps.setCapability(CapabilityType.PROXY, seleniumProxy);
            webDriver = new PhantomJSDriver(caps);
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public EventFiringWebDriver getFirefoxDriverWithProxy(Site site) {
        try {
            proxyCheck = true;
            proxy = new BrowserMobProxyServer();
//            proxy.setConnectTimeout(20, TimeUnit.SECONDS);
//            proxy.setIdleConnectionTimeout(20, TimeUnit.SECONDS);
//            proxy.setRequestTimeout(20, TimeUnit.SECONDS);
            proxy.setMitmDisabled(false);
            proxy.newHar("newHAR");
            proxy.start(0);
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            DesiredCapabilities caps = DesiredCapabilities.firefox();
            caps.setJavascriptEnabled(true);
            caps.setCapability("takesScreenshot", true);
            caps.setCapability(CapabilityType.PROXY, seleniumProxy);
            webDriver = new FirefoxDriver(caps);
            Dimension s = new Dimension(site.getViewPortWidth(), site.getViewPortHeight());
            webDriver.manage().window().setSize(s);
            webDriver.manage().timeouts().implicitlyWait(site.getTimeout(), TimeUnit.MILLISECONDS);
//            webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
            if (site.hasAuthentication()) {
                webDriver.get(site.getUrlWithAuth());
            }
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public EventFiringWebDriver getFirefoxDriver(Site site) {
        FirefoxProfile ffp = new FirefoxProfile();
        ffp.setPreference("general.useragent.override", site.getUserAgent());
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setJavascriptEnabled(true);
        caps.setCapability(FirefoxDriver.PROFILE, ffp);
        caps.setCapability("takesScreenshot", true);
        // User Name & Password Settings        
        webDriver = new FirefoxDriver(caps);
        if (site.getViewPortHeight() > 0 && site.getViewPortWidth() > 0) {
            Dimension s = new Dimension(site.getViewPortWidth(), site.getViewPortHeight());
            webDriver.manage().window().setSize(s);
        } else {
            webDriver.manage().window().maximize();
        }
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public EventFiringWebDriver getFirefoxDriver() {
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        // User Name & Password Settings        
        webDriver = new FirefoxDriver(caps);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver = new EventFiringWebDriver(webDriver);
        EventHandler handler = new EventHandler();
        driver.register(handler);
        return driver;
    }

    public BrowserMobProxy getProxy() throws Exception {
        if (proxyCheck) {
            return proxy;
        } else {
            throw new Exception("Please call getHeadLessDriverWithProxy() method to get a proxy");
        }
    }

    private ArrayList<String> addCommandLineArguments() {
        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--ignore-ssl-errors=yes"); // parameter to access https page
//        cliArgsCap.add("--proxy-type=https"); 

        return cliArgsCap;
    }

    public void destroy() {
        try {
            if (proxy != null) {
                proxy.abort();
            }
            if (webDriver != null) {
                webDriver.quit();
            }
            killPhantomJS();
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void killPhantomJS() {
        String serviceName = "phtantomjs.exe";
        try {
            if (ProcessKiller.isProcessRunning(serviceName)) {
                ProcessKiller.killProcess(serviceName);
            }
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Har getHar(WebDriver driver, BrowserMobProxy proxy, String url) {
        Har har = null;
        try {
            har = proxy.getHar();
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return har;
    }

    public Har getHar(WebDriver driver, BrowserMobProxy proxy, Site site) {
        Har har = null;
        try {
            har = proxy.getHar();
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return har;
    }

    public Har getHar(WebDriver driver, BrowserMobProxy proxy) {
        Har har = null;
        try {
            har = proxy.getHar();
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return har;
    }

}
