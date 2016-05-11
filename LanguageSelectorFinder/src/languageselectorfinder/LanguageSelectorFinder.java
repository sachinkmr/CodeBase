/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselectorfinder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class LanguageSelectorFinder {

    /**
     * @param args the command line arguments
     */
    static List<Site> sites = new ArrayList<>();
    static Set<String> all=new HashSet<>();
    public static void main(String[] args) {
        try {
            LanguageSelectorFinder finder = new LanguageSelectorFinder();
            FileReader fw = new FileReader("D://all1.txt");
            List<String> list = IOUtils.readLines(fw);
            all.addAll(list);
            fw.close();
            for (String siteName : list) {
                if (!siteName.startsWith("http")) {
                    siteName = "http://" + siteName;
                }
                System.out.println(siteName);
                Site site = new SiteBuilder(siteName)
                        //                            .setUserAgent("")
                        .setUsername("wlnonproduser")
                        .setPassword("Pass@word11")
                        .build();
                if (site.getSiteType().equals("Non BWS Site")) {
                    site.comments = "Non BWS Site";
                }
                try {
                    finder.go(site);
                } catch (Exception ex) {
                    Logger.getLogger(LanguageSelectorFinder.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            JSONObject ob = new JSONObject();
            for (Site ss : sites) {
                try {
                    JSONObject siteJson = new JSONObject();
                    siteJson.put("languageSelector", new JSONArray(ss.getLanguageSelector()));
                    siteJson.put("countrySelector", new JSONArray(ss.getCountrySelector()));
                    siteJson.put("statusCode", ss.statusCode);
                    siteJson.put("statusMsg", ss.statusMsg);
                    siteJson.put("comments", ss.comments);
                    siteJson.put("siteType", ss.getSiteType());
                    siteJson.put("hasCountrySelector", ss.getCountrySelector().size() > 0 ? true : false);
                    siteJson.put("hasLanguageSelector", ss.getLanguageSelector().size() > 0 ? true : false);
//                    siteJson.put("", ss);
//                    siteJson.put("", ss);
//                    siteJson.put("", ss);
//                    siteJson.put("", ss);
                    ob.put(ss.getUrl(), siteJson);
                } catch (JSONException ex) {
                    Logger.getLogger(LanguageSelectorFinder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            FileWriter fr = new FileWriter("D://all.json");
            ob.write(fr);
            fr.close();
            System.out.println(all.removeAll(list));
            System.out.println(all);
            System.out.println("all done");
        } catch (IOException | JSONException ex) {
            Logger.getLogger(LanguageSelectorFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void go(Site site) throws Exception {
        Language c = new Language(site);
        c.getLanguageList();
        Country co = new Country(site);
        co.getCountryList();
        sites.add(site);
        all.addAll(site.getCountrySelector());
        all.addAll(site.getLanguageSelector());
    }

}
