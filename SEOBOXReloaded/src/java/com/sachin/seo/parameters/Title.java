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
public final class Title implements Parameterizable{

    public boolean hasError = false, isBlank;
    public boolean isMissing, isMultiple, isOverChar;
    final String url;
    public final int charLimit;
    public String titleName = "";
    Elements title;
    ArrayList<String> titles;
    public static int missingTitle = 0, OverCharTitle = 0;
    Data data;

    public Title(String url, Document doc, Data data) {
        this.url = url;
        this.data = data;
        charLimit = data.titleCharLimit;
        this.title = doc.select("title");
        this.isMissing = title.isEmpty();
        if (!isMissing) {
            this.titleName = title.select("title").text();
            isMultiple = title.size() > 1;
            titles = getTitle();
            isBlank = titleName.length() < 1;
            isOverChar = OverCharTitle(charLimit);
            duplicateTitle();
        }
        hasError = hasError();
    }

    public synchronized int getMissingTitle() {
        return missingTitle;
    }

    public synchronized void updateMissingTitle() {
        missingTitle++;
    }

    public boolean OverCharTitle(int i) {
        if (!isMissing && title.size() > 0) {
            for (String h1Text : titles) {
                if (h1Text.length() > i) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean duplicateTitle() {
        if (!isMissing && title.size() > 0) {
            for (Element element : title) {
                if (element.text().trim().isEmpty()) {
                    isBlank = true;
                }
                if (data.duplicateTitle.containsKey(element.text().trim())) {
                    if (!data.duplicateTitle.get(element.text().trim()).contains(url)) {
                        data.duplicateTitle.put(element.text().trim(), data.duplicateTitle.get(element.text().trim()) + ", " + url);
                    }
                    return true;
                } else {
                    data.duplicateTitle.put(element.text().trim(), url);
                    return false;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getTitle() {
        ArrayList<String> list = new ArrayList<String>();
        if (!isMissing && title.size() > 0) {
            for (Element element : title) {
                list.add(element.text());
            }
        }
        return list;
    }

    public boolean hasError() {
        return isMissing || isMultiple || isOverChar || isBlank;
    }
}
