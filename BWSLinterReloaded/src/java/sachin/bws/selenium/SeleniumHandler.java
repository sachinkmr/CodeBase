/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.selenium;

import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author sku202
 */
public class SeleniumHandler {
public static void fillForm(JSONObject data, WebDriver driver, WebDriverWait wait, String html) throws JSONException {
        Iterator iterator = data.keys();
        WebElement element = null;
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String id = data.getJSONObject(key).getString("id");
            String name = data.getJSONObject(key).getString("name");
            String value = data.getJSONObject(key).getString("value");
            String type = data.getJSONObject(key).getString("type");
            if (html.contains(name)) {
                try {
                    if (html.contains(name)) {
                        element = driver.findElement(By.name(name));
                    } else if (html.contains(id)) {
                        element = driver.findElement(By.id(id));
                    }
                } catch (Exception ex) {

                }
            }
            if (element != null && element.isEnabled() && element.isDisplayed()) {
//                System.out.println(id);
                if (type.equalsIgnoreCase("select")) {
                    Select select = new Select(element);
                    select.selectByIndex(Integer.parseInt(value));
                }
                if (type.equalsIgnoreCase("text")) {
                    if (value.trim().isEmpty()) {
                        element.click();
                        element.sendKeys("");
                        element.sendKeys(Keys.TAB);
                    } else if (value.equalsIgnoreCase("data-mask")) {
                        element.click();
                        element.sendKeys(element.getAttribute("data-mask").replaceAll("-", ""));
                        element.sendKeys(Keys.TAB);
                    } else if (value.equalsIgnoreCase("datepicker")) {
                        element.click();
                        handleDatePicker(driver, "1");
                        element.sendKeys(Keys.TAB);
                    } else {
                        element.clear();
                        element.click();
                        element.sendKeys(value);
                        element.sendKeys(Keys.TAB);
                    }
                }
                if (type.equalsIgnoreCase("radio")) {
                    element.click();
                }
                if (type.equalsIgnoreCase("checkbox")) {
                    element.click();
                }
                if (type.equalsIgnoreCase("textarea")) {
                    element.clear();
                    element.sendKeys(value);
                }
            }
            element = null;
        }        
    }

    public static void handleDatePicker(WebDriver driver, String date) {
        //Click on textbox so that datepicker will come  
        List<WebElement> el = driver.findElement(By.className("ui-datepicker-calendar")).findElements(By.tagName("a"));
        for (WebElement elem : el) {
            if (elem.getText().equalsIgnoreCase(date)) {
                elem.click();
                break;
            }
        }
    }

    void handleSelect(WebElement element) {
        Select select = new Select(element);
        select.selectByIndex(1);
    }
}
