/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselectorfinder;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author sku202
 */
public class Country {

    Site site;

    public Country(Site site) {
        this.site = site;
    }

    void getCountryList() throws IOException, JSONException {
        Document doc1 = Jsoup.parse(site.getSiteHTML(), site.getUrl());
        if (doc1.getElementById("countryselectorurl") == null) {
            return;
        }
        String url = doc1.getElementById("countryselectorurl").attr("value");
        if (url == null || url.isEmpty()) {
            return;
        }
        Pattern p = Pattern.compile("/[a-z|A-Z][a-z|A-Z]-[a-z|A-Z][a-z|A-Z]");
        if (p.matcher(site.getUrl()).find() && p.matcher(url).find()) {
            url = url.replaceFirst("/[a-z|A-Z][a-z|A-Z]-[a-z|A-Z][a-z|A-Z]", "");
        }
        url = url.startsWith("http") ? url : site.getUrl().concat(url);
        url = URLCanonicalizer.getCanonicalURL(url);
        String login = site.getUsername() + ":" + site.getPassword();
        String base64login = new String(Base64.encodeBase64(login.getBytes()));
        Response res = site.hasAuthentication() ? Jsoup.connect(url).userAgent(site.getUserAgent()).ignoreContentType(true).header("Authorization", "Basic " + base64login).followRedirects(true).timeout(180000).execute() : Jsoup.connect(url).ignoreContentType(true).followRedirects(true).userAgent(site.getUserAgent()).timeout(180000).execute();
        Document doc = res.parse();
        if (doc.toString().contains("countrySelectorWrapper") && doc.toString().contains("country-dropdown")) {
            Element element = doc.getElementsByClass("country-dropdown").get(0);
            Elements alinks = element.getElementsByAttribute("href");
            for (Element alink : alinks) {
                String link = alink.attr("href");
                if (!link.isEmpty()) {
                    if (link.contains("javascript:SetRedirect")) {
                        link = link.replaceAll("javascript:SetRedirect\\('", "").replaceAll("'\\)", "");
                        site.getCountrySelector().add(link);
                    } else {
                        link = alink.absUrl("href");
                        site.getCountrySelector().add(link);
                    }
                }
            }
        } else {
            JSONObject jssss = new JSONObject(doc.body().text());
            JSONObject json = null;
            if (jssss.has("countrylist")) {
                json = new JSONObject(doc.body().text()).getJSONObject("countrylist");
            }
            if (jssss.has("Countries")) {
                json = new JSONObject(doc.body().text());
            }
            site.getCountrySelector().add(json.getString("DefaultUrl"));
            if (json.has("Countries")) {
                JSONArray j = json.getJSONArray("Countries");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject js = j.getJSONObject(i);
                    site.getCountrySelector().add(js.getString("CountryUrl"));
                    if (js.has("Locales")) {
                        JSONArray jl = js.getJSONArray("Locales");
                        for (int k = 0; k < jl.length(); k++) {
                            JSONObject jsl = jl.getJSONObject(k);
                            site.getCountrySelector().add(jsl.getString("LocaleUrl"));
                        }
                    }
                }
            }
        }
    }
}
