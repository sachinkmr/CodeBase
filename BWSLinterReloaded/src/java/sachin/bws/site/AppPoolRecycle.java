/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.site;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class AppPoolRecycle {

    private String url;
    private WebDriver driver;
    WebDriverBuilder builder;
    private String username;
    private String password;

    public AppPoolRecycle() {
        try {
            url = Data.getDATA_CONFIG().getString("appPoolUrl");
            username = Data.getDATA_CONFIG().getString("appPoolUsername");
            password = Data.getDATA_CONFIG().getString("appPoolPassword");
            authenticate();
        } catch (Exception ex) {
            Logger.getLogger(AppPoolRecycle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void authenticate() throws Exception {
        builder = new WebDriverBuilder();
        driver = builder.getFirefoxDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.findElement(By.name("j_username")).sendKeys(username);
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.name("login")).submit();
        driver.findElement(By.partialLinkText("Build with Parameters")).click();
    }

    public void recycle(String env, String siteHost) {
        try {
            Select select = new Select(driver.findElement(By.name("parameter")).findElement(By.name("value")));
            select.selectByValue(env);
            driver.findElement(By.className("setting-input")).sendKeys(siteHost);
            driver.findElement(By.className("setting-input")).submit();
        } catch (Exception ex) {
            Logger.getLogger(AppPoolRecycle.class.getName()).log(Level.SEVERE, null, ex);
        }
        builder.destroy();
    }
}
