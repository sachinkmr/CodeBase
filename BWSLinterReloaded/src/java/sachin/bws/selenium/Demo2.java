/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.selenium;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author sku202
 */
public class Demo2 {

    public static void main(String[] args) {
        try {
            Document doc=Jsoup.connect("http://www.liptontea.com/").followRedirects(true).get();
            System.out.println(doc.childNode(0).nodeName());
            
//        ConfigDev config = ConfigDev.getInstance();
//        config.new SiteConfig(url, brandName, environment, culture);
//        System.out.println(ConfigDev.getInstance().getBrands().size());
//        ConfigService s=new ConfigService();
//        System.out.println(s.getBasicHttpBindingIService().getApplicationNames().getKeyValueOfintstring().get(1).getValue());
//        try {
//            String siteName = "http://examplefoods-qa.unileversolutions.com/";
//            JSONObject json = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json").getJSONObject(siteName);
//            Site site = new Site(siteName, json.getString("brand"), json.getString("culture"), json.getString("environment"), true, json.getString("username"), json.getString("password"),json);
////            site.setCrawling(true);
//            JSONArray array = new JSONArray();
//            array.put(site.testFeature(new Analytics(site)));
////            array.put(site.testFeature(new SignUp(site)));
////            array.put(site.testFeature(new SiteSearch(site)));
//            array.put(site.testFeature(new BuyOnline(site)));
//            array.put(site.testFeature(new BazaarVoice(site)));
////            array.put(site.testFeature(new RecipeRating(site)));
////            array.put(site.testFeature(new ContactUs(site)));
////            array.put(site.testFeature(new DataCaptureTraction(site)));
////            array.put(site.testFeature(new Kritique(site)));
////            array.put(site.testFeature(new RecipeRating(site)));
////            array.put(site.testFeature(new RecipeSearch(site)));
////            array.put(site.testFeature(new StoreLocator(site)));
////            array.put(site.testFeature(new UserManagement(site)));
////            array.put(site.testFeature(new VirtualAgent(site)));
////            JSONObject ob = new JSONObject();
////            ob.put("results", array);
////            HelperClass.writeAllResults(site.getHost(), ob);
//            System.out.println(array);
//            System.out.println(site.getStatusCode());
//        } catch (JSONException ex) {
//            Logger.getLogger(Demo2.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (IOException ex) {
            Logger.getLogger(Demo2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
