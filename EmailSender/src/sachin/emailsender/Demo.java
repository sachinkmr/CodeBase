/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.emailsender;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sku202
 */
public class Demo {

    public static void main(String... s) {
        EmailSender send = new EmailSender();
        try {
            send.sendEmail("inrelaymail.sapient.com", "25", "unileverseo@sapient.com", "", false, false, false, "skumar213@sapient.com", "", "", "Sachin", "Subject", "message");

        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(send.getResponseCode());
        System.out.println(send.getResponseStatus());
    }
}
