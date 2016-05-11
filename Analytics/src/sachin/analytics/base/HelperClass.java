/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 *
 * @author sku202
 */
public class HelperClass {

    /**
     * Method to read JSON from a file.
     *
     * @param file JSON file path(Absolute path)
     * @return JSON data in file
     */
    public synchronized static JSONObject readJsonFromFile(String file) {
        String data = null;
        JSONObject json = null;
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            data = IOUtils.toString(reader);
            json = new JSONObject(data);
            reader.close();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    public static JSONObject getUserAgentDetails(String UserAgentName) {
        JSONObject ob = null;
        File file = new File(HelperClass.getDataFolderPath(), "UserAgent.json");
        try {
            ob = HelperClass.readJsonFromFile(file.getAbsolutePath()).getJSONObject(UserAgentName);
        } catch (JSONException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ob;
    }

    public static JSONObject getSocialMediaDetails(String name) {
        JSONObject ob = null;
        File file = new File(HelperClass.getDataFolderPath(), "SocialMediaDetail.json");
        try {
            ob = HelperClass.readJsonFromFile(file.getAbsolutePath()).getJSONObject(name);
        } catch (JSONException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ob;
    }

    /**
     * Method to get modified host name. return host does not contain .com, www.
     * and http://, or https://
     *
     * @param url site address as string
     * @return String of host name
     */
    public static String getModifiedHostName(String url) {
        try {
            url = new URL(url).getHost().toLowerCase();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        url = url.contains("www.") ? url.replaceAll("www.", "") : url;
        url = url.contains(".com") ? url.replaceAll(".com", "") : url;
        return url;
    }

    /**
     * Method to get modified host name. return host does not contain .com, www.
     * and http://, or https://
     *
     * @param address URL object of site address
     * @return String of host name
     */
    public static String getModifiedHostName(URL address) {
        String url = address.getHost().toLowerCase();
        url = url.contains("www.") ? url.replaceAll("www.", "") : url;
        url = url.contains(".com") ? url.replaceAll(".com", "") : url;
        return url;
    }

    /**
     * Method returns the unique name for png image based on time stamp
     *
     *
     * @return name of the unique png image
     */
    public static String generateUniqueName() {
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        DateFormat df1 = new SimpleDateFormat("hh-mm-ss-SSaa");
        Calendar calobj = Calendar.getInstance();
        String time = df1.format(calobj.getTime());
        String date = df.format(calobj.getTime());
        return date + " " + time + ".png";
    }

    /**
     * Method returns the unique string based on time stamp
     *
     *
     * @return unique string
     */
    public static String generateUniqueString() {
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        DateFormat df1 = new SimpleDateFormat("hh-mm-ss-SSaa");
        Calendar calobj = Calendar.getInstance();
        String time = df1.format(calobj.getTime());
        String date = df.format(calobj.getTime());
        return date + " " + time;
    }

    /**
     * Method returns the directory name of the excel files for generated
     * reports
     *
     *
     * @return name of the excel file directory of reports
     */
    public static String getExcelRepository() {

        return getOutputPath() + File.separator + "dataRepository" + File.separator + "ExcelFiles" + File.separator + generateUniqueName().replaceAll(".png", "");
    }

    /**
     * Method returns the file name of the screen shot of the site
     *
     * @param host host name of the site
     * @return name of the crawler data file for a site
     */
    public static String getCrawledDataFilename(String host) {
        return host.contains(".com") ? host.substring(0, host.indexOf(".com")) : host;
    }

    /**
     * Method returns the directory of the screen shot of the site
     *
     * @param host host name of the site
     * @return Absolute path of the screen shot directory for a site
     */
    public static String getScreenshotRepository(String host) {
        return getOutputPath() + File.separator + "dataRepository" + File.separator + host + File.separator + "screenshots";
    }

    /**
     * Method returns the directory of the data folder in for output for a host
     *
     * @param host host name of the site
     * @return Absolute path of the crawler data directory for a site
     */
    public static String getDataRepository(String host) {
        File file = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + "dataRepository");
        file.mkdirs();
        return file.getAbsolutePath() + File.separator + host;
    }

    /**
     * Method returns the directory of the saved crawled data for a site
     *
     * @param host host name of the site
     * @return Absolute path of the crawler data directory for a site
     */
    public static String getCrawledDataRepository(String host) {
        File f = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + "crawledData");
        f.mkdirs();
        return f.getAbsolutePath();
    }

    /**
     * To save crawled data to a JSON file for a site. Site host name is
     * provided as input
     *
     * @param links List of UrlLink of the site after crawling
     * @param fileName name of host of the site.
     */
    public static synchronized void saveCrawlingData(List<UrlLink> links, String fileName) {
        try {
            File f = new File(getCrawledDataRepository(fileName), fileName);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
                oos.writeObject(links);
                oos.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method is used to read crawled data from a JSON file for a site. Site
     * host name is provided as input
     *
     * @param fileName name of host of the site.
     * @return list of UrlLinks from JSON file of host
     */
    public synchronized static List<UrlLink> readCrawlingData(String fileName) {
        List<UrlLink> links = null;
        try {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(HelperClass.getCrawledDataRepository(fileName), fileName)))) {
                links = (List<UrlLink>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return links;
    }

    /**
     * Method is used to write latest JSON file to a path(absolute) of urls with
     * their respective template name for a site
     *
     *
     * @param map JSON Object containing data of
     * @param host host name of the site url
     * @return path as string
     */
    public synchronized static String writeJSONoutputWithTemplateToFile(JSONObject map, String host) {
        File file = new File(HelperClass.getDataRepository(host) + File.separator + "JSONoutputWithTemplate");
        file.mkdirs();
        file = new File(file, "report.json");
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            map.write(br);
            br.close();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file.getAbsolutePath();
    }

    /**
     * Method is used to get latest JSON file path(absolute) of site-host with
     * their respective template name
     *
     *
     * @param host host name of the site url
     * @return path as string
     */
    public synchronized static String getLatestJSONoutputWithTemplate(String host) {
        File file = new File(HelperClass.getDataRepository(host) + File.separator + "JSONoutputWithTemplate");
        file = new File(file, "report.json");
        return file.getAbsolutePath();
    }

    /**
     * Method is used to get absolute path of webApp
     *
     *
     * @return path as string
     */
    public String getPath() {
        String path = this.getClass().getClassLoader().getResource("config.json").getPath();
        String fullPath = null;
        try {
            fullPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        String paths = fullPath.split("config.json")[0];
        return paths.substring(1, paths.length());
    }

    /**
     * Method is used to get absolute path of web app
     *
     *
     * @return path as string
     */
    public static String getAppPath() {
        System.setProperty("AnalyticsAppPath", new HelperClass().getPath());
        return System.getProperty("AnalyticsAppPath");
    }

    /**
     * Method is used to get absolute path of data folder in WEB-INF
     *
     *
     * @return path as string
     */
    public static String getDataFolderPath() {
        return getAppPath() + File.separator + "data" + File.separator;
    }

    /**
     *
     * This methods returns the modified mobile site name. it replaces 'm.' with
     * 'm-'
     *
     * @param site
     * @return site name as String
     */
    public static String getModifiedMobileSiteName(String site) {
        try {
            site = new URL(site).getHost();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        site = site.contains("m.") ? site.replace("m.", "m-") : site;
        site = site.substring(0, site.indexOf("."));
        return site;
    }

    /**
     * Method returns all list element to lower case.
     *
     *
     * @param list This method take a list object
     * @return it returns the list object whose elements are in lower case
     */
    public static List<String> getListElementToLowerCase(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).toLowerCase());
        }
        return list;
    }

    public static String getStatusDirectory() {
        File file = new File(getOutputPath() + File.separator + "status");
        file.mkdirs();
        return file.getAbsolutePath();
    }

    public static String getOutputPath() {
        return new File(System.getProperty("user.dir") + File.separator + "output").getAbsolutePath();
    }

    public static void takeScreenshot(EventFiringWebDriver driver, WebElement element, String testCaseNumber,String host) {        
        testCaseNumber="TestCase_"+String.format("%03d", Integer.parseInt(testCaseNumber))+".png";
        File file = new File(HelperClass.getDataRepository(host) + File.separator + "outputScreenshots");
        file.mkdirs();
        String id = element.getAttribute("id");
        boolean clss = false;
        if (id == null || id.trim().isEmpty()) {
            id = element.getAttribute("class");
            clss = true;
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (clss) {
            js.executeScript("document.getElementsByClassName('"+id+"')[0].style.outline='3px solid blue'", element);
        }else{
            js.executeScript("document.getElementById('"+id+"').style.outline='3px solid blue'", element);
        }
        File saveTo = new File(file, testCaseNumber );
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, saveTo);
        } catch (IOException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
