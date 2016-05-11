package sachin.bws.selenium;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import sachin.bws.feature.BuyOnline;
import sachin.bws.site.Data;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.SiteBuilder;
import sachin.bws.web.ExcelManager;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        String siteName = "http://Knorr.com/";

        Site site = new SiteBuilder(siteName)
                .setBrandName("Knorr")
                .setCulture("en-US")
                .setEnvironment("PROD")
//                .setCrawling(true)
                .setUserAgent(Data.UA_DESKTOP)
                                        .setPassword("")
                                        .setUsername("")                        
                .build();
        WebDriverBuilder builder = new WebDriverBuilder();
        WebDriver driver = builder.getFirefoxDriverWithProxy(site);
        site.setWebDriverBuilder(builder);
        site.setWebDriver(driver);
        
//        driver.navigate().to("http://google.com");
//        Thread.sleep(10000);
//        builder.destroy();
        try {
//            System.out.println(site.getSiteHTML());
//            System.out.println(site.getSiteType());
//            File file = new File(HelperClass.getExcelRepository());
//            FileUtils.copyFileToDirectory(new File(HelperClass.getDataFolderPath(), "Report.xlsx"), file, true);
//            Map<String, JSONObject> list = new HashMap<String, JSONObject>();
//            String address = Data.getDATA_CONFIG().getString("outputAddressWithHostMachine");
//            JSONArray array1 = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json").names();
//            for (int i = 0; i < array1.length(); i++) {
//                String siteName = (String) array1.getString(i);

            System.out.println("Running on " + siteName);
            try {
//                site.getOutputWithTemplates();
                
//                    list.put(site.getUrl(), ob.getJSONObject("siteInfo").put("outputAddress", address + site.getHost()));
                JSONArray array = new JSONArray();
//                array.put(site.testFeature(new Analytics(site)));
////                array.put(site.testFeature(new SignUp(site)));
////                array.put(site.testFeature(new SiteSearch(site)));
////                array.put(site.testFeature(new BazaarVoice(site)));
                array.put(site.testFeature(new BuyOnline(site)));
////                array.put(site.testFeature(new ContactUs(site)));
////                array.put(site.testFeature(new BVSEO(site)));
////                array.put(site.testFeature(new DataCaptureTraction(site)));
////                array.put(site.testFeature(new Kritique(site)));
//                array.put(site.testFeature(new RecipeRating(site)));
//                array.put(site.testFeature(new RecipeSearch(site)));
////                array.put(site.testFeature(new StoreLocator(site)));
////                array.put(site.testFeature(new UserManagement(site)));
//                array.put(site.testFeature(new VirtualAgent(site)));
                JSONObject ob1 = new JSONObject();
                ob1.put("results", array);
                 System.out.println(array);
                HelperClass.writeAllResults(site.getHost(), ob1);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
//                break;
//            }

//            file = new File(file.getAbsolutePath(), "Report.xlsx");
//            writeExcelRow(file, list);
//            FileWriter writer = new FileWriter(new File(HelperClass.getStatusDirectory(), "BWSstatus.json"), false);
//            JSONObject data = new JSONObject(list);
//            data.write(writer);
//            writer.close();
//            JSONObject mail = new JSONObject(FileUtils.readFileToString(new File(HelperClass.getDataFolderPath(), "mail.json"), "UTF-8"));
//            EmailSender send = new EmailSender();
//            try {
//                send.sendEmailWithAttachments(mail.getString("host"), mail.getString("port"), mail.getString("mailFrom"), "", false, false, false, mail.getString("to"), "", "", "BWS Linter", mail.getString("subject"), mail.getString("message"), new String[]{file.getAbsolutePath()});
//
//            } catch (Exception ex) {
//                java.util.logging.Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        builder.destroy();
        System.out.println("Completed");
    }

    private static void writeExcelRow(File file, Map<String, JSONObject> list) {
        new ExcelManager(file).createData(list);
    }

}
