/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.selenium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;

/**
 *
 * @author sku202
 */
public class WebDriverBuilder {

    private WebDriver driver;
    private BrowserMobProxy proxy;
    private boolean proxyCheck;
    

    public WebDriver getHeadLessDriver(Site site) {

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
        driver = new PhantomJSDriver(caps);
        return driver;
    }

    public  WebDriver getSingletonIEDriver() {
        DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
        capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilitiesIE.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        capabilitiesIE.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        String exe = HelperClass.getDataFolderPath() + File.separator + "IEDriver" + File.separator + "IEDriverServer.exe";
        InternetExplorerDriverService.Builder serviceBuilder = new InternetExplorerDriverService.Builder();
        serviceBuilder.usingAnyFreePort(); // This specifies that sever can pick any available free port to start
        serviceBuilder.usingDriverExecutable(new File(exe)); //Tell it where you server exe is
        serviceBuilder.withLogLevel(InternetExplorerDriverLogLevel.TRACE); //Specifies the log level of the server
        serviceBuilder.withLogFile(new File("D:\\logFile.txt")); //Specify the log file. Change it based on your system

        InternetExplorerDriverService service = serviceBuilder.build(); //Create a driver service and pass it to Internet explorer driver instance

        
            driver = new InternetExplorerDriver(service, capabilitiesIE);
        
//        InternetExplorerDriver driver = new InternetExplorerDriver(service);
//        System.setProperty("webdriver.ie.driver", service);
//        WebDriver driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getHeadLessDriver() {
        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());
        driver = new PhantomJSDriver(caps);

        return driver;
    }

    public WebDriver getHeadLessDriver(boolean auth, String username, String password) {
        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());
        driver = new PhantomJSDriver(caps);
        if (auth) {
            caps.setCapability("phantomjs.page.settings.userName", username);
            caps.setCapability("phantomjs.page.settings.password", password);
        }
        return driver;
    }

    public WebDriver getHeadLessDriverWithProxy(Site site) {
        try {
            proxyCheck = true;
            proxy = new BrowserMobProxyServer();           
            proxy.newHar(site.getUrl());
            proxy.start();
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
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
            driver = new PhantomJSDriver(caps);
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return driver;
    }

    public WebDriver getHeadLessDriverWithProxy() {
        Random rand = new Random();
        try {
            proxyCheck = true;
            proxy = new BrowserMobProxyServer();          
            proxy.newHar(Integer.toString(rand.nextInt(100000)));
            proxy.start();
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            DesiredCapabilities caps = DesiredCapabilities.phantomjs();
            caps.setJavascriptEnabled(true);
            caps.setCapability("takesScreenshot", true);
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, addCommandLineArguments());  //Command line args  
            caps.setCapability(CapabilityType.PROXY, seleniumProxy);
            driver = new PhantomJSDriver(caps);
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return driver;
    }

    public WebDriver getFirefoxDriverWithProxy(Site site) {
        try {
            proxyCheck = true;
            proxy = new BrowserMobProxyServer();
            proxy.newHar(site.getUrl());            
            proxy.start();
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            DesiredCapabilities caps = DesiredCapabilities.firefox();
            caps.setJavascriptEnabled(true);
            caps.setCapability("takesScreenshot", true);
            caps.setCapability(CapabilityType.PROXY, seleniumProxy);
            driver = new FirefoxDriver(caps);
            driver.manage().window().maximize();
            if (!(site.getUsername().trim().length() < 1 || site.getUsername().trim().equals(""))) {
                driver.get(getAuthURL(site.getUrl(), site.getUsername(), site.getPassword()));
            }
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return driver;
    }

    public WebDriver getFirefoxDriver(Site site) {
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        // User Name & Password Settings           
        driver = new FirefoxDriver(caps);
        driver.manage().window().maximize();
        if (!(site.getUsername().trim().length() < 1 || site.getUsername().trim().equals(""))) {
            driver.get(getAuthURL(site.getUrl(), site.getUsername(), site.getPassword()));
        }
        return driver;
    }

    public WebDriver getFirefoxDriver() {
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);
        // User Name & Password Settings        
        driver = new FirefoxDriver(caps);
        driver.manage().window().maximize();
        return driver;

    }

    public WebDriver getIEDriver() {
        String exe = HelperClass.getDataFolderPath() + File.separator + "IEDriver" + File.separator + "IEDriverServer.exe";
        InternetExplorerDriverService.Builder serviceBuilder = new InternetExplorerDriverService.Builder();
        serviceBuilder.usingAnyFreePort(); // This specifies that sever can pick any available free port to start
        serviceBuilder.usingDriverExecutable(new File(exe)); //Tell it where you server exe is
        serviceBuilder.withLogLevel(InternetExplorerDriverLogLevel.TRACE); //Specifies the log level of the server
        serviceBuilder.withLogFile(new File("D:\\logFile.txt")); //Specify the log file. Change it based on your system
        InternetExplorerDriverService service = serviceBuilder.build(); //Create a driver service and pass it to Internet explorer driver instance
        WebDriver driver = new InternetExplorerDriver(service);
//        InternetExplorerDriver driver = new InternetExplorerDriver(service);
//        System.setProperty("webdriver.ie.driver", service);
//        WebDriver driver = new InternetExplorerDriver();
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
        return cliArgsCap;
    }

    public void destroy() {
        try {
            if (proxy != null&&!proxy.isStarted()) {
                proxy.abort();
            }
            if (driver != null) {
                driver.quit();
            }
            killPhantomJS();
            killIEService();
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

    public static void killIEService() {
        String serviceName = "IEDriverServer.exe";
        try {
            if (ProcessKiller.isProcessRunning(serviceName)) {
                ProcessKiller.killProcess(serviceName);
            }
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Har getHar(WebDriver driver, BrowserMobProxy proxy, String url) {
        Har har = null;
        try {
            har = proxy.getHar();
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return har;
    }

    public static Har getHar(WebDriver driver, BrowserMobProxy proxy, Site site) {
        Har har = null;
        try {
            har = proxy.getHar();
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return har;
    }

    public static Har getHar(WebDriver driver, BrowserMobProxy proxy) {
        Har har = null;
        try {
            har = proxy.getHar();
        } catch (Exception ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return har;
    }

    public static String getAuthURL(String url, String username, String password) {
        URL address = null;
        try {
            address = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        String protocol = address.getProtocol() + "://";
        String path = url.replaceFirst(protocol, "");
        return protocol + username + ":" + password + "@" + path;
    }

    public static void killWebDriverServices() {
        killPhantomJS();
        killIEService();
    }
}
