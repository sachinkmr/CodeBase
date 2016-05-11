package com.sapient.unilever;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author JARVIS
 */
public class SpiderConfig {

    public List<WebURL> links;
    private int socketTimeout = 20000;
    private int connectionTimeout = 30000;
    private int ConnectionRequestTimeout = 30000;
    private boolean followRedirects = false;
    private boolean authenticate = false;
    private String username = null;
    private String password = null;
    private String userAgentString = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.82 Safari/537.36";
    private String userAgent;    
    private int totalSpiders = 7;
    public int tracker = 0;
    public static SpiderConfig config=null;
    
    public static SpiderConfig getInstance(){
        if(config==null){
            config=new SpiderConfig();
        }
        return config;
    }
    
    private SpiderConfig() {
        links = Collections.synchronizedList(new ArrayList<WebURL>());
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getConnectionRequestTimeout() {
        return ConnectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int ConnectionRequestTimeout) {
        this.ConnectionRequestTimeout = ConnectionRequestTimeout;
    }

    public boolean isAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(boolean authenticate) {
        this.authenticate = authenticate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public int getTotalSpiders() {
        return totalSpiders;
    }

    public void setTotalSpiders(int totalSpiders) {
        this.totalSpiders = totalSpiders;
    }

    public void validateConfig() throws Exception {        
        if (authenticate) {
            if (username == null || username.isEmpty()) {
                throw new Exception("Username cannot be empty");
            }
            if (password == null || password.isEmpty()) {
                throw new Exception("Password cannot be empty");
            }
        }
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
