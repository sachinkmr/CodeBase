/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.site;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sachin
 */
public final class Data {

    public int threadValue, timeoutValue;
    public String user, pass, site, agentName, crawlingType, address, releaseVer, date, time;
    public boolean redirect, ldap, isAnalytics, isResponsive, crawling = true, completed,stay=false,running;
    static public int urlCharLimit = 115;
    static public int h1CharLimit = 70;
    static public int h2CharLimit = 70;
    static public int titleCharLimit = 65;
    static public int descriptionCharLimit = 156;
    static public int canonicalCharLimit = 115;
    static public int altTextCharLimit = 100;
    static public String outputLocation, outputAddress;
    public Map<String, String> duplicateH1, duplicateH2, duplicateDescription, duplicateTitle, canonicalURLs, urlInfo, statusCode;
    public String loc, host,webinf;
    public String siteName;

    public Data() {
        duplicateH1 = Collections.synchronizedMap(new HashMap<String, String>());
        duplicateH2 = Collections.synchronizedMap(new HashMap<String, String>());
        duplicateDescription = Collections.synchronizedMap(new HashMap<String, String>());
        canonicalURLs = Collections.synchronizedMap(new HashMap<String, String>());
        urlInfo = Collections.synchronizedMap(new HashMap<String, String>());
        statusCode = Collections.synchronizedMap(new HashMap<String, String>());
        duplicateTitle = Collections.synchronizedMap(new HashMap<String, String>());
    }

    public void clean() {
        duplicateH1.clear();
        duplicateH2.clear();
        duplicateDescription.clear();
        canonicalURLs.clear();
        urlInfo.clear();
        statusCode.clear();
        duplicateTitle.clear();

    }
}
