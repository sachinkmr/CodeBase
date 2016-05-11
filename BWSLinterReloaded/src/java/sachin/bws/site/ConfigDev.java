/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.site;

import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfKeyValueOfintstring.KeyValueOfintstring;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfKeyValueOfstringstring.KeyValueOfstringstring;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.tempuri.ConfigService;
import sachin.bws.selenium.WebDriverBuilder;

/**
 *
 * @author sku202
 */
public class ConfigDev extends ConfigService {

    private String url;
    private String username;
    private String password;
    private static ConfigDev selfService;

    //Singlton object
    public static synchronized ConfigDev getInstance() {
        if (selfService == null) {
            selfService = new ConfigDev();
        }
        return selfService;
    }

    private ConfigDev() {
        try {
            url = Data.getDATA_CONFIG().getString("ConfigDevUrl");
            username = Data.getDATA_CONFIG().getString("ConfigDevUsername");
            password = Data.getDATA_CONFIG().getString("ConfigDevPassword");
        } catch (Exception ex) {
            Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This function is called to get the list of BWS brands.
     *
     * @return list of all BWS brands
     */
    public List<String> getBrands() {
        List<String> list = new ArrayList<>();
        for (KeyValueOfintstring k : getInstance().getBasicHttpBindingIService().getBrandNames().getKeyValueOfintstring()) {
            list.add(k.getValue());
        }
        list = new ArrayList<>(new HashSet<>(list));
        list.remove("");
        return list;
    }

    /**
     * This function is called to get the list of BWS brands in sort order.
     *
     * @return list of all BWS brands in sorted order.
     */
    public List<String> getSortedBrands() {
        List<String> list = getBrands();
        Collections.sort(list);
        return list;
    }

    /**
     * This function is called to get the list of BWS example brands.
     *
     * @return list of all BWS example brands
     */
    public List<String> getExampleBrand() {
        List<String> list = new ArrayList<String>();
        for (String e : getBrands()) {
            if (e.toLowerCase().contains("example")) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * This function is called to get the list of BWS cultures.
     *
     * @return list of all BWS cultures.
     */
    public List<String> getCultures() {
        List<String> list = new ArrayList<String>();
        for (KeyValueOfintstring k : getInstance().getBasicHttpBindingIService().getCultureNames().getKeyValueOfintstring()) {
            list.add(k.getValue());
        }
        list.remove("");
        return list;
    }

    /**
     * This function is called to get the list of BWS Environments.
     *
     * @return list of all BWS Environments.
     */
    public List<String> getEnvironments() {
        List<String> list = new ArrayList<String>();
        for (KeyValueOfintstring k : getInstance().getBasicHttpBindingIService().getEnvNames().getKeyValueOfintstring()) {
            list.add(k.getValue());
        }
        list.remove("");
        return list;
    }

    public class SiteConfig implements Serializable {

        private final String siteURL;
        private final String brandName;
        private final String envName;
        private final String cultureName;

        /**
         * This function is called to get the config of BWS Site.
         *
         * @param siteURL URL of the site
         * @param brandName brand name ex: Ragu or examplefoods
         * @param envName environment for the site ex: QA, UAT,Dev
         * @param cultureName culture of the site
         *
         *
         */
        public SiteConfig(String siteURL, String brandName, String envName, String cultureName) {
            this.siteURL = siteURL;
            this.cultureName = cultureName;
            this.brandName = brandName;
            this.envName = envName;
            try {
                if (envName.equalsIgnoreCase("PROD")||envName.equalsIgnoreCase("QA_Product")) {
                    if (!HelperClass.getLatestSiteConfigFile(siteURL).exists()) {
                        getConfigJSONProd();
                    }
                } else {
                    getConfigJSON();
                }
            } catch (JSONException | IOException ex) {
                Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * This function is called to get BWS Site config as Json object.
         *
         *
         *
         * @throws org.json.JSONException
         * @throws java.io.IOException
         */
        private void getConfigJSON() throws JSONException, IOException {
            List<KeyValueOfstringstring> list = getInstance().getBasicHttpBindingIService().getAppCultureConfig("WLBrandSite", brandName, envName, cultureName).getKeyValueOfstringstring();
            JSONObject map = new JSONObject();
            for (KeyValueOfstringstring key : list) {
                map.put(key.getKey(), key.getValue());
            }
            FileWriter fw = new FileWriter(HelperClass.getLatestSiteConfigFile(siteURL), false);
            map.write(fw);
            fw.close();
        }

        /**
         * This function is called to get BWS Prod Site config as Json object.
         *
         *
         *
         */
        private void getConfigJSONProd() {
            WebDriverBuilder builder = new WebDriverBuilder();
            WebDriver driver=builder.getSingletonIEDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
            driver.get(url);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
//                Robot rb = new Robot();
//
//                //Enter user name by ctrl-v
//                StringSelection username1 = new StringSelection(username);
//                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username1, null);
//                rb.keyPress(KeyEvent.VK_CONTROL);
//                rb.keyPress(KeyEvent.VK_A);
//                rb.keyRelease(KeyEvent.VK_A);                
//                rb.keyPress(KeyEvent.VK_V);
//                rb.keyRelease(KeyEvent.VK_V);
//                rb.keyRelease(KeyEvent.VK_CONTROL);
//
//                //tab to password entry field
//                rb.keyPress(KeyEvent.VK_TAB);
//                rb.keyRelease(KeyEvent.VK_TAB);
//                Thread.sleep(2000);
//
//                //Enter password by ctrl-v
//                StringSelection pwd = new StringSelection(password);
//                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
//                rb.keyPress(KeyEvent.VK_CONTROL);
//                rb.keyPress(KeyEvent.VK_A);
//                rb.keyRelease(KeyEvent.VK_A);  
//                rb.keyPress(KeyEvent.VK_V);
//                rb.keyRelease(KeyEvent.VK_V);
//                rb.keyRelease(KeyEvent.VK_CONTROL);
//
//                //press enter
//                rb.keyPress(KeyEvent.VK_ENTER);
//                rb.keyRelease(KeyEvent.VK_ENTER);
//
//                //wait
                Thread.sleep(5000);
                String command = HelperClass.getDataFolderPath() + File.separator + "IEDriver" + File.separator + "WindowsLoginPopUp.exe " + username + " " + password;
                ProcessBuilder process = new ProcessBuilder("cmd.exe", "/c", command);
                process.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
            }
            handleBrandNames(driver);
            Select brandSelect = new Select(driver.findElement(By.id("MainContent_ddlBrand1")));
            brandSelect.selectByValue(brandName);
            Select cultureSelect = new Select(driver.findElement(By.id("MainContent_ddlCulture1")));
            cultureSelect.selectByValue(cultureName);
            Select envSelect = new Select(driver.findElement(By.id("MainContent_ddlEnv1")));
            envSelect.selectByValue(envName);

            try {
                handleConfigData(driver);
            } catch (Exception ex) {
                Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
            }
            Document doc = Jsoup.parse(driver.getPageSource());
            driver.quit();

            try {
                JSONObject map = new JSONObject();
                Elements rows = doc.select("table#MainContent_gvDetails tr");
                int i = 0;
                for (Element e : rows) {
                    if (i++ != 0) {
                        try {
                            Element key = e.select("td:first-child").first();
                            Element value = e.select("td:first-child+td").first();
                            if (key.text().trim().length() > 0) {
                                map.put(key.text(), value.text().trim());
                            }
                        } catch (JSONException ex) {
                            Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                FileWriter fw = new FileWriter(HelperClass.getLatestSiteConfigFile(siteURL), false);
                map.write(fw);
                fw.close();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | JSONException ex) {
                Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        private void handleBrandNames(WebDriver driver) {
            while (driver.findElements(By.cssSelector("#MainContent_ddlBrand1 option")).size() < 2) {
                try {
                    Select selectSiteType = new Select(driver.findElement(By.id("MainContent_ddlApp")));
                    selectSiteType.selectByIndex(2);
                    Wait wait = new FluentWait(driver)
                        .withTimeout(60, TimeUnit.SECONDS)
                        .pollingEvery(2, TimeUnit.SECONDS)
                        .ignoring(NoSuchElementException.class);
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (driver.findElements(By.cssSelector("#MainContent_ddlBrand1 option")).size() < 2) {
                          selectSiteType = new Select(driver.findElement(By.id("MainContent_ddlApp")));
                        selectSiteType.selectByIndex(1);
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        break;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        private void handleConfigData(WebDriver driver) {
            while (!driver.getPageSource().contains("MainContent_gvDetails")) {
                driver.findElement(By.id("MainContent_btnShow")).click();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConfigDev.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
