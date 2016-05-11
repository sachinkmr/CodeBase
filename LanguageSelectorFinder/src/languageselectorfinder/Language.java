/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselectorfinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author sku202
 */
public class Language {

    Site site;

    public Language(Site site) {
        this.site = site;
    }

    void getLanguageList() {
        String siteName = site.getUrl().replaceFirst("/[a-z|A-Z][a-z|A-Z]-[a-z|A-Z][a-z|A-Z]", "/");
        Document doc1 = Jsoup.parse(site.getSiteHTML(), siteName);
        Elements e1 = doc1.select("select#language option");        
        if (e1 == null || e1.isEmpty()) {
            e1 = doc1.select("div.languageToggle select#languageOptions option");
        }
        if (e1 == null || e1.isEmpty()) {
            return;
        }
        for (Element e : e1) {
            String url = e.attr("value");
            url = url.startsWith("http") ? url : siteName.concat(url);
            url = URLCanonicalizer.getCanonicalURL(url);
            site.getLanguageSelector().add(url);
        }

    }

}
