/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.modules;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import sachin.analytics.base.Analytics;
import sachin.analytics.base.Component;
import sachin.analytics.base.HelperClass;
import sachin.analytics.selenium.SeleniumHandler;

/**
 *
 * @author sku202
 */
public class SignUpPage extends Analytics {
    private String pageUrl;
    Component comp ;
    public SignUpPage() {
        super();        
    }
@Test
    public String[] brandOptin(String expectedOptinValues) {
        if (featureEnabled()) {
            String ar[] = expectedOptinValues.split(",");
            for (int i = 0; i < ar.length; i++) {
                ar[i] = ar[i].trim();
            }
            return checkOptin("BrandInfo", ar);
        } else {
            return new String[]{"NA", "Feature is not on this"};
        }

    }
    @Test
    public String[] corporateOptin(String expectedOptinValues) {
        if (featureEnabled()) {
            String ar[] = expectedOptinValues.split(",");
            for (int i = 0; i < ar.length; i++) {
                ar[i] = ar[i].trim();
            }
            return checkOptin("GeneralInfo", ar);
        } else {
            return new String[]{"NA", "Feature is Applicable"};
        }

    }

    private String[] checkOptin(String optinID, String[] expectedOptinValues) {
        if (!featureEnabled()) {
            return null;
        }
        String[] check = null;
        try {
            driver.navigate().to(pageUrl);
            WebDriverWait wait = new WebDriverWait(driver, 20);
            JSONObject data = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "signUpData.json");
            SeleniumHandler.fillForm(data.getJSONObject("Responsive"), driver, wait, driver.getPageSource());
            if (driver.findElement(By.id(optinID)) != null && !driver.findElement(By.id(optinID)).isSelected()) {
                WebElement e=driver.findElement(By.id(optinID));
                e.click();
                HelperClass.takeScreenshot(driver, e, comp.getTestCaseNumber(),this.getSite().getHost());
                builder.getProxy().newHar("Acquisition");
                driver.findElement(By.id("submit")).submit();
                check = this.verifyHAR(expectedOptinValues);
                
            }else{
                check=new String[]{"NA", "Feature is Applicable"};
            }

        } catch (Exception ex) {
            Logger.getLogger(SignUpPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    @Override
    public void analyticsResult() {      
        comp = this.getComponent();
        if (comp.getName().equalsIgnoreCase("Brand Opt in")) {
            String ar[] = brandOptin(comp.getExpectedParams());
            comp.setStatus(ar[0]);
            comp.setComment(ar[0]);
        }
        if (comp.getName().equalsIgnoreCase("Corporate Opt in")) {
            String ar[] = corporateOptin(comp.getExpectedParams());
            comp.setStatus(ar[0]);
            comp.setComment(ar[0]);
        }
    }

    @Override
    public boolean featureEnabled() {
        String template = "SignUpPage";
        List<String> list = getURLs(template);
        if (!list.isEmpty()) {
            pageUrl = getURLs(template).get(0);
            return true;
        } else {
            return false;
        }
    }

}
