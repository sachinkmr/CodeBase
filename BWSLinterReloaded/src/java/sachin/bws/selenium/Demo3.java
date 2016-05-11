/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import sachin.bws.email.EmailSender;
import sachin.bws.feature.Analytics;
import sachin.bws.feature.BazaarVoice;
import sachin.bws.feature.BuyOnline;
import sachin.bws.feature.ContactUs;
import sachin.bws.feature.Kritique;
import sachin.bws.feature.RecipeRating;
import sachin.bws.feature.RecipeSearch;
import sachin.bws.feature.SignUp;
import sachin.bws.feature.SiteSearch;
import sachin.bws.feature.UserManagement;
import sachin.bws.feature.VirtualAgent;
import sachin.bws.site.Data;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.SiteBuilder;
import sachin.bws.web.ExcelManager;
import sachin.bws.web.InstantRun;
import sachin.bws.web.ScheduledJob;

/**
 *
 * @author sku202
 */
public class Demo3 {

    public void go() {
        try {
//            File file = new File(HelperClass.getExcelRepository());
//            FileUtils.copyFileToDirectory(new File(HelperClass.getDataFolderPath(), "Report.xlsx"), file, true);
            Map<String, JSONObject> list = new HashMap<String, JSONObject>();
            String address = Data.getDATA_CONFIG().getString("outputAddressWithHostMachine");
            File uploadedExcelFile = new File("D:\\Sites.xlsx");
            JSONObject sites = new ExcelManager(uploadedExcelFile).readUploadedExcel();
            JSONArray array1 = sites.names();
            WebDriverBuilder builder = new WebDriverBuilder();
            for (int i = 0; i < array1.length(); i++) {
                String siteName = (String) array1.getString(i);
                System.out.println("Running on " + siteName);
                try {
                    JSONObject json = sites.getJSONObject(siteName);
                    Site site = new SiteBuilder(siteName)
                            .setParamJSON(json)
                            .setBrandName(json.getString("brand"))
                            .setCulture(json.getString("culture"))
                            .setEnvironment(json.getString("environment"))
                            .setUserAgent(json.getString("userAgent"))
                            .setPassword(json.getString("password"))
                            .setUsername(json.getString("username"))
//                            .setCrawling(true)
                            .build();
                    WebDriver driver = builder.getHeadLessDriver(site);
                    site.setWebDriverBuilder(builder);
                    site.setWebDriver(driver);
                     JSONObject ob = site.getOutputWithTemplates();
                    list.put(site.getUrl(), ob.getJSONObject("siteInfo").put("outputAddress", address + site.getHost()));
                    HelperClass.deleteAllFunctionality(site.getHost());
                    if (site.isRunning()) {
                        JSONArray array = new JSONArray();
                        array.put(site.testFeature(new Analytics(site)));
                        array.put(site.testFeature(new SignUp(site)));
                        array.put(site.testFeature(new SiteSearch(site)));
                        array.put(site.testFeature(new BazaarVoice(site)));
                        array.put(site.testFeature(new BuyOnline(site)));
                        array.put(site.testFeature(new ContactUs(site)));
//                        array.put(site.testFeature(new BVSEO(site)));
//                        array.put(site.testFeature(new DataCaptureTraction(site)));
                        array.put(site.testFeature(new Kritique(site)));
                        array.put(site.testFeature(new RecipeRating(site)));
                        array.put(site.testFeature(new RecipeSearch(site)));
//                        array.put(site.testFeature(new StoreLocator(site)));
                        array.put(site.testFeature(new UserManagement(site)));
                        array.put(site.testFeature(new VirtualAgent(site)));
                        JSONObject ob1 = new JSONObject();
                        ob1.put("results", array);
                        
                        HelperClass.writeAllResults(site.getHost(), ob1);
                        System.out.println(ob1);
                    }
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
                }
//                break;
            }
            builder.destroy();
//            file = new File(file.getAbsolutePath(), "Report.xlsx");
//            writeExcelRow(file, list);
            FileWriter writer = new FileWriter(new File(HelperClass.getStatusDirectory(), "BWSstatus.json"), false);
            JSONObject data = new JSONObject(list);
            data.write(writer);
            writer.close();
            JSONObject mail = new JSONObject(FileUtils.readFileToString(new File(HelperClass.getDataFolderPath(), "mail.json"), "UTF-8"));
            EmailSender send = new EmailSender();
            try {
                send.sendEmailWithAttachments(mail.getString("host"), mail.getString("port"), mail.getString("mailFrom"), "", false, false, false, mail.getString("to"), "", "", "BWS Linter", mail.getString("subject"), mail.getString("message"), new String[]{});
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InstantRun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Demo3.class.getName()).log(Level.SEVERE, null, ex);
        }
        HelperClass.cleanUp();
        System.out.println("Completed");

    }

    private void writeExcelRow(File file, Map<String, JSONObject> list) {
        new ExcelManager(file).createData(list);
    }

    public static void main(String[] args) {
        new Demo3().go();
    }
}
