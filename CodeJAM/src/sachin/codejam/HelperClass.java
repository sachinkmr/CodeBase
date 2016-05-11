package sachin.codejam;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class HelperClass {
	  public static void takeScreenshot(EventFiringWebDriver driver, WebElement element, String testCaseNumber,String color) {
	        testCaseNumber=testCaseNumber+".png";
	        File file = new File("Screenshots/");
	        file.mkdirs();
	        String id = element.getAttribute("id");
	        boolean clss = false;
	        if (id == null || id.trim().isEmpty()) {
	            id = element.getAttribute("class");
	            clss = true;
	        }
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        if (clss) {
	            js.executeScript("document.getElementsByClassName('"+id+"')[0].style.outline='3px solid "+color+"'", element);
	        }else{
	            js.executeScript("document.getElementById('"+id+"').style.outline='3px solid "+color+"'", element);
	        }
	        File saveTo = new File(file, testCaseNumber );
	        File scrFile = driver.getScreenshotAs(OutputType.FILE);
	        try {
	            FileUtils.copyFile(scrFile, saveTo);
	        } catch (IOException ex) {
	            Logger.getLogger(HelperClass.class.getName()).log(Level.WARN, null, ex);
	        }

	        if (clss) {
	            js.executeScript("document.getElementsByClassName('"+id+"')[0].style.outline='0px solid white'", element);
	        }else{
	            js.executeScript("document.getElementById('"+id+"').style.outline='0px solid white'", element);
	        }
	    }
}
