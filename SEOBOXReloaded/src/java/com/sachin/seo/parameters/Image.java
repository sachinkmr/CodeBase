/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo.parameters;

import com.sachin.site.Data;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Sachin
 */
public class Image implements Parameterizable{

    public boolean hasMissingAltText, hasOverText;
    public boolean hasError = false;
    public int altTextCharLimit;
    public static int missingAlt = 0, OverCharAlt = 0;
    Elements img;
    private HashMap<String, String> OverTextMap1;
    Data data;

    public Image(String url, Document doc, Data data) {
        this.data = data;
        this.altTextCharLimit = data.altTextCharLimit;
        this.img = doc.select("img");
        if (img != null) {
            hasOverText = hasOverText(altTextCharLimit);
        }
    }

    public ArrayList<String> getMissingAlts() {
        ArrayList<String> list = new ArrayList<String>();
        if (img != null && img.size() > 0) {
            for (Element link : img) {
                if (link.attr("alt").isEmpty()) {
                    list.add(link.attr("abs:src"));
                    hasMissingAltText = true;
                }
            }
        }
        return list;
    }

    public boolean hasOverText(int i) {
        OverTextMap1 = new HashMap<String, String>();
        boolean a = false;
        if (img != null && img.size() > 0) {
            for (Element link : img) {
                if (link.attr("alt").length() > i) {
                    OverTextMap1.put(link.attr("abs:src"), link.attr("alt"));
                    a = true;
                }
            }
        }
        return a;
    }

    public HashMap<String, String> getOverTextList() {
        return OverTextMap1;
    }

    public boolean hasError() {
        return hasMissingAltText || hasOverText;
    }
}
