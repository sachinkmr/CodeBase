/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sachin.seo;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author Sachin
 */
public class SetAuthentication {
    public static void authenticate(final String site,final String username, final String password) {
        Authenticator.setDefault(
                new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                username, password.toCharArray());
                    }
                }
        );
        if (site.contains("https")) {
            System.setProperty("https.proxyUser", username);
            System.setProperty("https.proxyPassword", password);
        } else {
            System.setProperty("http.proxyUser", username);
            System.setProperty("http.proxyPassword", password);
        }
    }

}
