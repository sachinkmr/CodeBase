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
public class H1 implements Parameterizable{

    public boolean hasError = false, isBlank;
    public boolean isMissing, isMultiple, isOverChar;
    private final String url;
    public final int charLimit;
    private String h1Name = "";
    Elements h1;
//    ArrayList<String> h1s;
    public static int missingH1 = 0, OverCharH1 = 0;
    Data data;

    public H1(String url, Document doc, Data data) {
        this.data = data;
        this.url = url;
        charLimit = data.h1CharLimit;
        this.h1 = doc.getElementsByTag("h1");
        this.isMissing = h1.isEmpty();
        if (!isMissing) {
            this.h1Name = h1.select("h1").text();
            this.isMultiple = h1.size() > 1;
            duplicateH1();
        }
        hasError = hasError();
    }

    public boolean duplicateH1() {
        if (!isMissing && h1.size() > 0) {
            for (Element element : h1) {
                if (element.text().length() > charLimit) {
                    isOverChar = true;
                }
                if (element.text().trim().isEmpty()) {
                    isBlank = true;
                }
                if (data.duplicateH1.containsKey(element.text().trim())) {
                    if (!data.duplicateH1.get(element.text().trim()).contains(url)) {
                        data.duplicateH1.put(element.text().trim(), data.duplicateH1.get(element.text().trim()) + ", " + url);
                    }
                    return true;
                } else {
                    data.duplicateH1.put(element.text().trim(), url);
                    return false;
                }

            }
        }
        return false;
    }

    public ArrayList<String> getH1() {
        ArrayList<String> list = new ArrayList<String>();
        if (!isMissing && h1.size() > 0) {
            for (Element element : h1) {
                list.add(element.text());
            }
        }
        return list;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.h1Name);
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
        final H1 other = (H1) obj;
        if (!Objects.equals(this.h1Name, other.h1Name)) {
            return false;
        }
        return true;
    }

    public boolean hasError() {
        return isMissing || isMultiple || isOverChar || isBlank;
    }

}
