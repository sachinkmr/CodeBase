/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;

/**
 *
 * @author sku202
 */
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.*;
import sachin.bws.email.EmailSender;
import sachin.bws.feature.Analytics;
import sachin.bws.feature.BVSEO;
import sachin.bws.feature.BazaarVoice;
import sachin.bws.feature.BuyOnline;
import sachin.bws.feature.ContactUs;
import sachin.bws.feature.DataCaptureTraction;
import sachin.bws.feature.Kritique;
import sachin.bws.feature.RecipeRating;
import sachin.bws.feature.RecipeSearch;
import sachin.bws.feature.SignUp;
import sachin.bws.feature.SiteSearch;
import sachin.bws.feature.StoreLocator;
import sachin.bws.feature.UserManagement;
import sachin.bws.feature.VirtualAgent;
import sachin.bws.site.Data;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;

public class ScheduledJob implements StatefulJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
//            System.out.println("yes");
            File file = new File(HelperClass.getExcelRepository());
            FileUtils.copyFileToDirectory(new File(HelperClass.getDataFolderPath(), "Report.xlsx"), file, true);
            Map<String, JSONObject> list = new HashMap<String, JSONObject>();
            String address = Data.getDATA_CONFIG().getString("outputAddressWithHostMachine");
            JSONArray array1 = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json").names();
            for (int i = 0; i < array1.length(); i++) {
                String siteName = (String) array1.getString(i);
                System.out.println("Running on " + siteName);
                try {
                    JSONObject json = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json").getJSONObject(siteName);
//                    Site site = new Site(siteName, json.getString("brand"), json.getString("culture"), json.getString("environment"), true, json.getString("username"), json.getString("password"),json);
//                    site.setCrawling(true);
//                    JSONObject ob = site.getOutputWithTemplates();
//                    list.put(site.getUrl(), ob.getJSONObject("siteInfo").put("outputAddress", address + site.getHost()));
//                    HelperClass.deleteAllFunctionality(site.getHost());                    
//                    JSONArray array = new JSONArray();                    
//                    array.put(site.testFeature(new Analytics(site)));
//                    array.put(site.testFeature(new SignUp(site)));
//                    array.put(site.testFeature(new SiteSearch(site)));
//                    array.put(site.testFeature(new BazaarVoice(site)));
//                    array.put(site.testFeature(new BuyOnline(site)));
//                    array.put(site.testFeature(new ContactUs(site)));
//                    array.put(site.testFeature(new BVSEO(site)));
//                    array.put(site.testFeature(new DataCaptureTraction(site)));
//                    array.put(site.testFeature(new Kritique(site)));
//                    array.put(site.testFeature(new RecipeRating(site)));
//                    array.put(site.testFeature(new RecipeSearch(site)));
//                    array.put(site.testFeature(new StoreLocator(site)));
//                    array.put(site.testFeature(new UserManagement(site)));
//                    array.put(site.testFeature(new VirtualAgent(site)));
//                    JSONObject ob1 = new JSONObject();
//                    ob1.put("results", array);
//                    HelperClass.writeAllResults(site.getHost(), ob1);                    
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            file = new File(file.getAbsolutePath(), "Report.xlsx");
            writeExcelRow(file, list);
            FileWriter writer = new FileWriter(new File(HelperClass.getStatusDirectory(), "BWSstatus.json"), false);
            JSONObject data = new JSONObject(list);
            data.write(writer);
            writer.close();
            JSONObject mail = new JSONObject(FileUtils.readFileToString(new File(HelperClass.getDataFolderPath(), "mail.json"), "UTF-8"));
            EmailSender send = new EmailSender();
            try {
                send.sendEmailWithAttachments(mail.getString("host"), mail.getString("port"), mail.getString("mailFrom"), "", false, false, false, mail.getString("to"), "", "", "BWS Linter", mail.getString("subject"), mail.getString("message"), new String[]{file.getAbsolutePath()});
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Completed");
    }

    private void writeExcelRow(File file, Map<String, JSONObject> list) {
        new ExcelManager(file).createData(list);
    }
}
