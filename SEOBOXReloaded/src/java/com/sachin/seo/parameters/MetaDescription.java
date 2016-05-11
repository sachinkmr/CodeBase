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
public class MetaDescription implements Parameterizable{

    public boolean isMissing, isOverChar, isMultiple, hasError, isBlank, isDupli;
    Elements desc;
    Document doc;
    private final String url;
    public final int charLimit;
    private String descname="";
    ArrayList<String> md;
    Data data;

   public  MetaDescription(String url, Document doc, Data data) {
        this.url = url;
        this.doc = doc;
        this.data = data;
        charLimit = data.canonicalCharLimit;
        isBlank = false;
        isDupli = false;
        this.desc = doc.select("meta[name=description]");
        isMissing = desc.isEmpty();
        if (!isMissing) {            
            descname = desc.first().attr("content");
            isMultiple = desc.size() > 1;
            md = getDescription();
            isOverChar = OverCharDesc(charLimit);
            duplicateDesc();
        }
    }

    public ArrayList<String> getDescription() {
        ArrayList<String> list = new ArrayList<String>();
        if (!isMissing&&desc.size() > 0) {
            for (Element link : desc) {
                list.add(link.attr("content"));
                if (link.attr("content").length() > charLimit) {
                    isOverChar = true;
                }
            }
        }
        return list;
    }

    public boolean hasError() {
        return isMissing || isOverChar || isMultiple || isBlank;
    }

    public boolean duplicateDesc() {
        if (!isMissing&&desc.size() > 0) {
            for (Element element : desc) {
                if (element.attr("content").trim().isEmpty()) {
                    isBlank = true;
                }
                String descName = element.attr("content").trim();
                if (data.duplicateDescription.containsKey(descName)) {
                    if (!data.duplicateDescription.get(descName).contains(url)) {
                        data.duplicateDescription.put(descName, data.duplicateDescription.get(descName) + ", " + url);
                        return true;
                    }
                } else {
                    data.duplicateDescription.put(descName, url);
                }
            }
        }
        return false;
    }

    public boolean OverCharDesc(int i) {
        if (!isMissing&&desc.size() > 0) {
            for (Element h1Text : desc) {
                if (h1Text.attr("content").trim().length() > i) {
                    return true;
                }
            }
        }
        return false;
    }

    public int missingDesc() {
        return desc.size();
    }

    public int multipleDesc() {
        return desc.size();
    }

}
