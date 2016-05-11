/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo.parameters;

import com.sachin.site.Data;
import java.util.ArrayList;
import java.util.Objects;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Sachin
 */
public class H2 implements Parameterizable{

    public boolean hasError = false, isBlank;
    public boolean isMissing, isMultiple, isOverChar;
    final String url;
    public final int charLimit;
    String h2Name = "";
    Elements h2;
    public static int missingH2 = 0, OverCharH2 = 0;
    Data data;

    public H2(String url, Document doc, Data data) {
        this.data = data;
        charLimit = data.h2CharLimit;
        this.url = url;
        this.h2 = doc.select("h2");
        this.isMissing = h2.isEmpty();
        if (!isMissing) {
            this.h2Name = h2.select("h2").text();
            duplicateH2();
            isBlank = h2Name.length() < 1;
            this.isMultiple = h2.size() > 1;
        }
        hasError = hasError();

    }

    boolean duplicateH2() {
        if (!isMissing && h2.size() > 0) {
            for (Element element : h2) {
                if (element.text().trim().isEmpty()) {
                    isBlank = true;
                }
                if (element.text().trim().length() > charLimit) {
                    isOverChar = true;
                }
                if (data.duplicateH2.containsKey(element.text().trim())) {
                    if (!data.duplicateH2.get(element.text().trim()).contains(url)) {
                        data.duplicateH2.put(element.text().trim(), data.duplicateH2.get(element.text().trim()) + ", " + url);
                    }
                    return true;
                } else {
                    data.duplicateH2.put(element.text().trim(), url);
                    return false;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getH2() {
        ArrayList<String> list = new ArrayList<String>();
        if (!isMissing && h2.size() > 0) {
            for (Element element : h2) {
                list.add(element.text());
            }
        }
        return list;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.h2Name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final H2 other = (H2) obj;
        if (!Objects.equals(this.h2Name, other.h2Name)) {
            return false;
        }
        return true;
    }

    public boolean hasError() {
        return isBlank;
    }

}
