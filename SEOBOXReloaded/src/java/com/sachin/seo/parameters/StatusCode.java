/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo.parameters;

import java.util.Objects;

/**
 *
 * @author Sachin
 */
public class StatusCode implements Parameterizable{

    public final int code;
    public final String url, msg;
    public boolean hasError;

    public StatusCode(String url, int statusCode,String statusMsg) {
        this.url = url;       
        code = statusCode;
        hasError = false;
        msg = statusMsg;       
        if (code > 303) {
            hasError = true;
        }
    }

    public int getStatusCode() {
        return this.code;
    }

    public String getStatusMsg() {
        return msg;
    }

    public boolean hasError() {
        return hasError;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StatusCode other = (StatusCode) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.url);
        return hash;
    }
}
