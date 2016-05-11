/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo.parameters;

import com.sachin.site.Data;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Sachin
 */
public class Canonical implements Parameterizable {

    Data data;
    public boolean isMissing, isOverChar, isMultiple, hasError, isBlank;
    transient Elements canonical;
    transient Document doc;
    private final String url;
    public final int charLimit;
    public String canonicalURL = "";

    public Canonical(String url, Document doc, Data data) {
        this.url = url;
        this.data = data;
        charLimit = data.canonicalCharLimit;
        canonical = doc.select("link[rel=canonical]");
        isMissing = canonical.isEmpty();
        if (!isMissing) {
            canonicalURL = canonical.attr("abs:href");
            isMultiple = canonical.size() > 1;
            isBlank = canonicalURL.length() < 1;
            duplicateCanonical();
        }
        hasError = hasError();
    }

    public boolean hasError() {
        return isMissing || isMultiple || isOverChar || isBlank;
    }

    public ArrayList<String> getCanonical() {
        ArrayList<String> list = new ArrayList<String>();
        if (!isMissing && canonical.size() > 0) {
            for (Element element : canonical) {
                list.add(element.attr("abs:href"));
            }
        }
        return list;
    }

    public boolean duplicateCanonical() {
        if (!isMissing && canonical.size() > 0) {
            for (Element element : canonical) {
                if (element.attr("abs:href").trim().isEmpty()) {
                    isBlank = true;
                }
                if (element.attr("abs:href").trim().length() > charLimit) {
                    isOverChar = true;
                }
                if (data.canonicalURLs.containsKey(element.attr("abs:href").trim())) {
                    data.canonicalURLs.put(element.attr("abs:href").trim(), data.canonicalURLs.get(element.attr("abs:href").trim()) + ", " + url);
                    return true;
                } else {
                    data.canonicalURLs.put(element.attr("abs:href").trim(), url);
                    return false;
                }
            }
        }

        return false;
    }

}
