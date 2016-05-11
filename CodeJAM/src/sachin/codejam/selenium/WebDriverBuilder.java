package sachin.codejam.selenium;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import sachin.codejam.Config;

/**
 *
 * @author Sachin
 */
public class WebDriverBuilder {

	private EventFiringWebDriver driver;

	// private WebDriver driver;
	// block to set path of all the driver exes and servers
	static {
		System.setProperty("webdriver.chrome.driver",
				"Resources" + File.separator + "servers" + File.separator + "chromedriver.exe"); // setting chrome driver path
		System.setProperty("webdriver.ie.driver",
				"Resources" + File.separator + "servers" + File.separator + "IEDriverServer.exe"); // setting IE driver path
	}

	/**
	 * This method is used to get a web driver instance of internet
	 * explorer(Event firing).
	 *
	 * @return instance of the IE web driver(Event firing)
	 */
	public EventFiringWebDriver getIEDriver() {
		EventHandler handler = new EventHandler();
		DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
		capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilitiesIE.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		capabilitiesIE.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		capabilitiesIE.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		String exe = "Resources" + File.separator + "servers" + File.separator + "IEDriverServer.exe";
		InternetExplorerDriverService.Builder serviceBuilder = new InternetExplorerDriverService.Builder();
		serviceBuilder.usingAnyFreePort(); // This specifies that sever can pick any available free port to start
		serviceBuilder.usingDriverExecutable(new File(exe)); // Tell it where you server exe is
		serviceBuilder.withLogLevel(InternetExplorerDriverLogLevel.WARN); // Specifies the log level of the server
		serviceBuilder.withLogFile(new File("Logs\\IElogFile.txt")); // Specify the log file.
		InternetExplorerDriverService service = serviceBuilder.build(); // Create a driver service and pass it to Internet explorer driver instance
		driver = new EventFiringWebDriver(new InternetExplorerDriver(service, capabilitiesIE));
		driver.register(handler);
		return driver;
	}

	/**
	 * This method is used to get a web driver instance of Chrome(Event firing).
	 *
	 * @return instance of the chrome driver(Event firing)
	 */
	public EventFiringWebDriver getChromeDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
		EventHandler handler = new EventHandler();
		driver = new EventFiringWebDriver(new ChromeDriver(capabilities));
		driver.register(handler);
		return driver;
	}

	/**
	 * This method is used to get a web driver instance of firefox(Event
	 * firing).
	 *
	 * @return instance of the firefox driver(Event firing)
	 */
	public EventFiringWebDriver getFirefoxDriver() {
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile myprofile = profile.getProfile("default");
		myprofile.setAcceptUntrustedCertificates(true);
		myprofile.setAssumeUntrustedCertificateIssuer(true);
		DesiredCapabilities caps = DesiredCapabilities.firefox();
		caps.setCapability(FirefoxDriver.PROFILE, myprofile);
		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);

		// User Name & Password Settings
		WebDriver webDriver = new FirefoxDriver(caps);
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver = new EventFiringWebDriver(webDriver);
		EventHandler handler = new EventHandler();
		driver.register(handler);
		return driver;
	}

	/**
	 * This method is used to get a web driver instance of firefox(Event
	 * firing).
	 *
	 * @param capabilities   DesiredCapabilities for firefox	 *
	 *
	 * @return instance of the firefox driver(Event firing)
	 */
	public EventFiringWebDriver getFirefoxDriver(DesiredCapabilities capabilities) {
//		ProfilesIni profile = new ProfilesIni();
//		FirefoxProfile myprofile = profile.getProfile("default");
//		myprofile.setAcceptUntrustedCertificates(true);
//		myprofile.setAssumeUntrustedCertificateIssuer(true);
//		capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);
		WebDriver webDriver = new FirefoxDriver(capabilities);
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver = new EventFiringWebDriver(webDriver);
		EventHandler handler = new EventHandler();
		driver.register(handler);
		return driver;
	}

	/**
	 * This method is used to kill all the processes running internet explorer
	 * server if any is running as system processes.
	 *
	 **/
	private void killIEService() {
		String serviceName = "IEDriverServer.exe";
		try {
			if (ProcessKiller.isProcessRunning(serviceName)) {
				ProcessKiller.killProcess(serviceName);
			}
		} catch (Exception ex) {
			Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.WARN, null, ex);
		}
	}

	/**
	 * This method is used to kill all the processes running chrome server if
	 * any is running as system processes.
	 *
	 **/
	private void killChromeService() {
		String serviceName = "chromedriver.exe";
		try {
			if (ProcessKiller.isProcessRunning(serviceName)) {
				ProcessKiller.killProcess(serviceName);
			}
		} catch (Exception ex) {
			Logger.getLogger(WebDriverBuilder.class.getName()).log(Level.WARN, null, ex);
		}
	}

	/**
	 * This method is used to kill all the processes running all web driver
	 * server if any is running as system processes.
	 *
	 **/
	public void destroy() {
		if(null!=driver){
			driver.quit();
		}
		killChromeService();
		killIEService();
	}

	/**
	 * This method is used to get webDriver Instance based on config file
	 *
	 * @return EventFiringWebDriver instance
	 **/
	public EventFiringWebDriver getDriver() {
		if (Config.BROWSER_TYPE.equalsIgnoreCase("Firefox")) {
			return this.getFirefoxDriver();
		} else if (Config.BROWSER_TYPE.equalsIgnoreCase("Chrome")) {
			this.getChromeDriver();
		} else if (Config.BROWSER_TYPE.equalsIgnoreCase("IE")) {
			this.getIEDriver();
		}
		return driver;
	}
}