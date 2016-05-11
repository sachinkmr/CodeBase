/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo.parameters;

import com.sachin.site.Data;
import java.util.Objects;

/**
 *
 * @author Sachin
 */
public class URL implements Parameterizable{

    public final String url;
    public boolean hasError = false, isOverChar;
    public final int urlCharLimit;
    Data data;

    public URL(String url, Data data) {
        this.url = url;
        this.data = data;
        urlCharLimit = data.urlCharLimit;
        isOverChar = isOverCharURL();
    }

    public boolean isOverCharURL() {
        if (url.length() > urlCharLimit) {
            data.urlInfo.put(url, Integer.toString(urlCharLimit));
            hasError = true;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.url);
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
        final URL other = (URL) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    public boolean hasError() {
        return hasError;
    }
}
