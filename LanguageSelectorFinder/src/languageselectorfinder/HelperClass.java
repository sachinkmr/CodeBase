/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselectorfinder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author sku202
 */
public class HelperClass {

    /**
     * Method to read results.
     *
     * @param host brand name of the BWS file
     * @return JSON data as JSON object
     */
    public synchronized static JSONObject readAllResults(String host) {
        File file = new File(HelperClass.getCrawledDataRepository(host), "AllResults.json");
        return readJsonFromFile(file.getAbsolutePath());
    }

    /**
     * Method to write JSON from a file.
     *
     * @param host brand name of the BWS file
     * @param data JSON data object to write on file
     * @param outputDir
     */
    public synchronized static void writeAllResults(String host, JSONObject data) {        
        File file = new File(HelperClass.getDataRepository(host) + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }        
        file = new File(file, "AllResults.json");        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            data.write(writer);
            writer.close();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to read JSON from a file of a BWS functionality.
     *
     * @param host brand name of the BWS file
     * @param functionalityName as Class name of the functionality
     * @return JSON data as JSON object
     */
    public synchronized static JSONObject readFunctionality(String host, String functionalityName) {
        File file = new File(HelperClass.getDataRepository(host), functionalityName + ".json");
        return readJsonFromFile(file.getAbsolutePath());
    }

    /**
     * Method to delete all JSON of a BWS functionality.
     *
     * @param host brand name of the BWS file
     */
    public synchronized static void deleteAllFunctionality(String host) {
        File files[]=new File(HelperClass.getDataRepository(host)).listFiles();
//        File files[] = new File(HelperClass.getDataRepository(host)).listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                if (pathname.getName().toLowerCase().endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        });

        for (File file : files) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException ex) {
                Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Method to write JSON in a file.
     *
     * @param host brand name of the BWS file
     * @param functionalityName name of the class as functionality
     * @param data JSON data object to write on file
     * @param outputDir
     */
    public synchronized static void writeFunctionality(String host, String functionalityName, JSONObject data) {
        File file = new File(HelperClass.getDataRepository(host) + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(file, functionalityName + ".json");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            data.write(writer);
            writer.close();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    /**
     * Method to get latest modified siteConfig JSON file.
     *
     * @param siteURL Address of site
     * @return JSON of site config for a site as File object
     */
    public static File getLatestSiteConfigFile(String siteURL) {
        File file = new File(HelperClass.getDataFolderPath() + File.separator + HelperClass.getModifiedHostName(siteURL));
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(file, "siteConfig.json");
        return file;
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
        return getAppPath() + File.separator + "output" + File.separator + "dataRepository" + File.separator + "ExcelFiles" + File.separator + generateUniqueName().replaceAll(".png", "");
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
        File file=new File(getAppPath() + File.separator + "output" + File.separator + "dataRepository" + File.separator + host + File.separator + "screenshots" );
        file.mkdirs();
        return file.getAbsolutePath();
    }

    /**
     * Method returns the directory of the data folder in for output for a host
     *
     * @param host host name of the site
     * @return Absolute path of the crawler data directory for a site
     */
    public static String getDataRepository(String host) {
        return getAppPath() + File.separator + "output" + File.separator + "dataRepository" + File.separator + host + File.separator;
    }

    /**
     * Method returns the directory of the saved crawled data for a site
     *
     * @param host host name of the site
     * @return Absolute path of the crawler data directory for a site
     */
    public static String getCrawledDataRepository(String host) {
        host=HelperClass.getCrawledDataFilename(host);
        File f = new File(HelperClass.getAppPath() + File.separator + "output" + File.separator + "crawledData" + File.separator + host);
        f.mkdirs();
        return f.getAbsolutePath();
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
        return getRelativePathToWebApp(file.getAbsolutePath());
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
        return getRelativePathToWebApp(file.getAbsolutePath());
    }

    /**
     * Method is used to get relative path to webApp
     *
     *
     * @param path Absolute path of the resource
     * @return path as string
     */
    public static String getRelativePathToWebApp(String path) {
        return path.substring(getAppPath().length() + 1, path.length());
    }

    public static void cleanUp() {
        WebDriverBuilder.killWebDriverServices();
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
        String paths = fullPath.split("/WEB-INF/classes/")[0];
        return paths.substring(1, paths.length());
    }

    /**
     * Method is used to get absolute path of web app
     *
     *
     * @return path as string
     */
    public static String getAppPath() {
        return new HelperClass().getPath();
    }

    /**
     * Method is used to get absolute path of data folder in WEB-INF
     *
     *
     * @return path as string
     */
    public static String getDataFolderPath() {
        return getAppPath() + File.separator + "WEB-INF" + File.separator + "data" + File.separator;
    }

    /**
     * Method is used to read site names from file in data folder.
     *
     * @return Liste of site names
     */
    public static List<String> readSiteNames() {
        List<String> list = new ArrayList<>();
        try {
            JSONArray array = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json").names();
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        } catch (JSONException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
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

    public static int getStatusCode(String url) {
        try {
            return Request.Get(url).execute().returnResponse().getStatusLine()
                    .getStatusCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static int getStatusCode(String url,String username,String password) {
        String login = username + ":" + password;
        String base64login = new String(Base64.encodeBase64(login.getBytes()));
        try {
            return Request.Get(url).addHeader("Authorization", "Basic " + base64login).execute().returnResponse().getStatusLine()
                    .getStatusCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAbsolutePath(String relativePath) {
        return getAppPath() + relativePath.substring(relativePath.toLowerCase().indexOf("bwslinter") + 10);
    }

    public static String getStatusDirectory() {
        File file = new File(getAppPath() + File.separator + "output" + File.separator + "status");
        file.mkdirs();
        return file.getAbsolutePath();
    }

    
}