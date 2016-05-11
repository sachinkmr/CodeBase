/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapient.unilever;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sku202
 */
public class WebURL {

    private String url="";    
    private boolean proccessed=false;    
    private String redirectTo="";
    public List<String> redirectPath;
    private String ExpectedAddress="";    
    private String status="";
    private String errorMsg="";
    private String comment="";
    private String userAgent="";
    private String userAgentString="";
    private int statusCode;

    public List<String> getRedirectPath() {
        return redirectPath;
    }

    public WebURL() {
        this.redirectPath = new ArrayList<String>();
    }

    public String getUrl() {
        return url;
    }
    

    public synchronized boolean isProccessed() {
        return proccessed;
    }

    public synchronized void setProccessed(boolean proccessed) {
        this.proccessed = proccessed;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public String getExpectedAddress() {
        return ExpectedAddress;
    }

    public void setExpectedAddress(String ExpectedAddress) {
        this.ExpectedAddress = ExpectedAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String error) {
        this.errorMsg = errorMsg.concat(error+"\n");
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
