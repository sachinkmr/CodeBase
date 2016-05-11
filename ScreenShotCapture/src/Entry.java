
import site.Finder;
import site.Site;
import site.SiteBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sku202
 */
public class Entry {
    public static void main(String... ar) {
        String siteName = ar[0];
        if (siteName.toLowerCase().contains("uat")) {
            Site BWSsite = new SiteBuilder(siteName)
                    .setUsername("wlnonproduser")
                    .setPassword("Pass@word11")
                    .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1")
                    .setTimeout(120000)
                    .setViewPortHeight(667)
                    .setViewPortWidth(375)
                    .build();
            new Finder(BWSsite).go("UAT-Mobile");

            BWSsite = new SiteBuilder(siteName)
                    .setUsername("wlnonproduser")
                    .setPassword("Pass@word11")                 
                    .setTimeout(120000)
                    .build();
            new Finder(BWSsite).go("UAT");
        } else {
            Site BWSsite = new SiteBuilder(siteName)
                    .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1")
                    .setTimeout(120000)
                    .setViewPortHeight(667)
                    .setViewPortWidth(375)
                    .build();
            new Finder(BWSsite).go("PROD-Mobile");

            BWSsite = new SiteBuilder(siteName)
                    .setTimeout(120000)
                    .build();
            new Finder(BWSsite).go("PROD");
        }

    }
}
