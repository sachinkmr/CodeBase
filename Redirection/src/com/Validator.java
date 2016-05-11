/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapient.unilever;

import java.util.List;

/**
 *
 * @author Sachin
 */
public class Validator implements Runnable {

    List<WebURL> list;
    WebURL url;

    public Validator(List<WebURL> list) {
        this.list = list;
    }

    public Validator(WebURL url) {
        this.url=url;
    }

    public void validate() {
        for (WebURL url : list) {
            if (url.getExpectedAddress().length() > 0 && url.getRedirectTo().startsWith(url.getExpectedAddress())) {
                url.setStatus("Passed");
            } else if (url.getErrorMsg().length() > 0) {
                url.setStatus("Blocked");
                url.setComment("Unable to process URL because something went wrong.");
                if (url.getErrorMsg().contains("org.apache.http.client.ClientProtocolException")) {
                    url.setComment("Internet Connection Error.");
                }
                if (url.getErrorMsg().contains("UnknownHostException")) {
                    url.setComment("Please verify URL.");
                }
            } else {
                url.setStatus("Failed");
            }
        }
    }

    public void validateURL() {
        if (url.getExpectedAddress().length() > 0 && url.getRedirectTo().startsWith(url.getExpectedAddress())) {
            url.setStatus("Passed");
        } else if (url.getErrorMsg().length() > 0) {
            url.setStatus("Blocked");
            url.setComment("Unable to process URL because something went wrong error.");
            if (url.getErrorMsg().contains("org.apache.http.client.ClientProtocolException")) {
                url.setComment("Internet Connection Error.");
            }
        } else {
            url.setStatus("Failed");
        }
    }

    @Override
    public void run() {
         validateURL();
    }
}
