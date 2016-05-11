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
public class MetaKeyword implements Parameterizable{

    public boolean isMissing, isOverChar, isMultiple, hasError, isBlank;
    Elements key;
    Document doc;
    private final String url;
//    private final int charLimit;
    private String keywordname="";
    Data data;

  public   MetaKeyword(String url, Document doc, Data data) {
        isBlank = false;
        this.url = url;
        this.data = data;
        this.doc = doc;
        this.key = doc.select("meta[name=keywords]");
        isMissing = key.isEmpty();
        if (!isMissing) {            
            keywordname = key.attr("content");
            isMultiple = key.size() > 1;
            getKeywords();
        }
    }

    public ArrayList<String> getKeywords() {
        ArrayList<String> list = new ArrayList<String>();
        if (!isMissing&&key.size() > 0) {
            for (Element link : key) {
                String mkName = link.attr("content").trim();
                if (mkName.isEmpty()) {
                    isBlank = true;
                }
                list.add(mkName);
            }
        }
        return list;
    }

    public boolean hasError() {
        return isMissing || isMultiple || isBlank;
    }

}
