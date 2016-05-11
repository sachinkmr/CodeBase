package sachin.codejam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;

import sachin.codejam.selenium.WebDriverBuilder;

/**
 *
 * @author Sachin
 */
public class SearchFlight {
	WebDriverBuilder builder;
	EventFiringWebDriver driver;

	@BeforeClass
	public void setUp() {
		builder = new WebDriverBuilder();
		driver = builder.getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Config.TIMEOUT, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.get(Config.URL);

	}

	@Test(priority = 0)
	public void flightDetails() {
		// selecting domestic origin
		fillPlaces(By.id("BE_flight_origin_city"), Config.DOMASTIC_ORIGIN, By.cssSelector("div.ac_results"));

		// selecting domestic destination
		fillPlaces(By.id("BE_flight_arrival_city"), Config.DOMASTIC_DESTINATION, By.cssSelector("div.ac_results"));

		// selecting one way trip
		driver.findElement(By.className("type-active")).click();

		// Selecting the departure date as “Current date+1 day”
		calculateDate();

		// Selecting passengers
		driver.findElement(By.id("BE_flight_paxInfoBox")).click();
		WebElement element = driver.findElement(By.cssSelector("div#msdrpdd20_msdd span.ddSpinnerPlus"));
		for (int i = 1; i < Config.ADULTS; i++)
			element.click();
		element = driver.findElement(By.cssSelector("div#msdrpdd21_msdd span.ddSpinnerPlus"));
		for (int i = 0; i < Config.CHILDREN; i++)
			element.click();
		element = driver.findElement(By.cssSelector("div#msdrpdd22_msdd span.ddSpinnerPlus"));
		for (int i = 0; i < Config.INFANTS; i++)
			element.click();
		driver.findElement(By.cssSelector("div#BE_flight_passengerBox div.be-ddn-footer span")).click();
	}

	@Test(priority = 1)
	public void validationCheck1() {
		int total = Config.ADULTS + Config.CHILDREN + Config.INFANTS;
		int totalOnSite = Integer
				.parseInt(driver.findElement(By.cssSelector("div#BE_flight_paxInfoBox span.totalCount")).getText());
		System.out.println("Validation Check 1:");
		System.out.println("Total passengers count as per input: " + total);
		System.out.println("Total passengers count as per site: " + totalOnSite);
		System.out.println("---------------------------------------------------------");
		HelperClass.takeScreenshot(driver, driver.findElement(By.id("BE_flight_form")), "ValidateCheck1_001", "red");
		Assert.assertEquals(total, totalOnSite);

		// search for flights
		driver.findElement(By.id("BE_flight_flsearch_btn")).click();
	}

	@Test(priority = 2)
	public void validationCheck2() {
		// waiting to complete all ajax call for flight search
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("progressbar")));
		int flightCount = getCountinText();
		int flightindetail = driver.findElements(By.cssSelector("article.my-result-list")).size();
		System.out.println("Validation Check 2:");
		System.out.println("Flights count on top of the page: " + flightCount);
		System.out.println("Flights count in results of the page: " + flightindetail);
		System.out.println("---------------------------------------------------------");
		HelperClass.takeScreenshot(driver, driver.findElement(By.xpath(".//*[@id='resultBox']/div[1]/div[2]")),
				"ValidateCheck2_001", "red");
		Assert.assertEquals(flightCount, flightindetail);
	}

	@Test(priority = 3)
	public void validationCheck3() {
		// Sorting in decending order
		WebElement element = driver.findElement(By.xpath(".//*[@id='resultList_0']/div[1]/ul/li[3]/a"));
		element.click();
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver arg0) {
				if (driver.findElement(By.xpath(".//*[@id='resultList_0']/div[1]/ul/li[3]/a")).getAttribute("class")
						.equals("under-link active down"))
					return true;
				return false;
			}
		});
		element = driver.findElement(By.xpath(".//*[@id='resultList_0']/div[1]/ul/li[3]/a"));
		List<WebElement> flights = driver.findElements(By.cssSelector("article.my-result-list"));
		String id = flights.get(0).getAttribute("id");

		int max = Integer.parseInt(driver.findElement(By.id("fare-" + id)).getText().replaceAll("Rs.", "")
				.replaceAll("\\u20B9", "").replaceAll(",", "").trim());
		System.out.println("Validation Check 3:");
		List<Integer> list = new ArrayList<>();
		for (WebElement ele : flights) {
			id = ele.getAttribute("id");
			int rate = Integer.parseInt(driver.findElement(By.id("fare-" + id)).getText().replaceAll("Rs.", "")
					.replaceAll("\\u20B9", "").replaceAll(",", "").trim());
			Assert.assertTrue(rate <= max);
			max = rate;
			list.add(rate);
		}
		System.out.println("Rates in decending orders are: ");
		System.out.println(list);
		System.out.println("---------------------------------------------------------");
		HelperClass.takeScreenshot(driver, element, "ValidateCheck3_001", "red");
	}

	@Test(priority = 4)
	public void validationCheck4() {
		int x = 70;
		WebElement slider = driver
				.findElement(By.xpath(".//*[@id='filterBox']/aside/div/div[3]/div[2]/div/rzslider/span[2]"));
		int width = slider.getSize().getWidth();
		Actions move = new Actions(driver);
		move.moveToElement(slider, ((width * x) / 100), 0).click();
		move.build().perform();
		HelperClass.takeScreenshot(driver, driver
				.findElement(By.xpath(".//*[@id='filterBox']/aside/div/div[3]/div[2]/div/rzslider/span[1]")), "ValidationCheck4_001", "red");
		String str=driver.findElement(By.xpath(".//*[@id='filterBox']/aside/div/div[3]/div[2]/div/rzslider/span[8]")).getText().replaceAll("Rs.", "")
				.replaceAll("\\u20B9", "").replaceAll(",", "").trim();
		int maxbudget=Integer.parseInt(str);
		List<Integer> list = new ArrayList<>();
		List<WebElement> flights = driver.findElements(By.cssSelector("article.my-result-list"));
		List<WebElement> flightsNew=new ArrayList<>();
		for(WebElement el:flights){
			if(!el.getAttribute("class").contains("ng-hide")){
				flightsNew.add(el);
			}
		}
		flights=null;
		System.out.println("Validation Check 4:");
		for (WebElement ele : flightsNew) {
			String id = ele.getAttribute("id");
			int rate = Integer.parseInt(driver.findElement(By.id("fare-" + id)).getText().replaceAll("Rs.", "")
					.replaceAll("\\u20B9", "").replaceAll(",", "").trim());
			Assert.assertTrue(rate <= maxbudget);
			maxbudget = rate;
			list.add(rate);
		}
		System.out.println("Rates in decending orders are: ");
		System.out.println(list);
		System.out.println("---------------------------------------------------------");
	}

	private int getCountinText() {
		String text = driver.findElement(By.xpath(".//*[@id='resultBox']/div[1]/div[2]/p[1]")).getText();
		String texts[] = text.split("\\u0020");
		for (String s : texts) {
			if (isDigit(s)) {
				return Integer.parseInt(s);
			}
		}
		return 0;
	}

	private boolean isDigit(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private void fillPlaces(By place, String placeValue, By locator) {
		driver.findElement(place).sendKeys(placeValue);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		driver.findElement(place).sendKeys(Keys.TAB);
	}

	private void calculateDate() {
		driver.findElement(By.id("BE_flight_depart_date")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#calmain")));
		List<WebElement> elements = element.findElements(By.tagName("td"));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("EEEE, d MMMM yyyy");
		String nextDay = format1.format(cal.getTime());
		for (WebElement el : elements) {
			if (el.getAttribute("title").equals(nextDay)) {
				el.click();
				break;
			}
		}
	}

	@AfterClass
	public void tearDown() {
		 builder.destroy();
	}
}
