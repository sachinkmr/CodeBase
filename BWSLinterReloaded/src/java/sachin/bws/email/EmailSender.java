/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.email;

/**
 *
 * @author Sachin
 */
import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EmailSender {

    private int responseCode;
    private String responseStatus;
    private Exception error;

    public void sendEmailWithAttachments(String host, String port,
            final String userName, final String password, final boolean authen, final boolean ssl, final boolean tsl, String toAddress, String CC_Address, String BCC_Address, String fromName,
            String subject, String message, String[] attachFiles) {
        try {
            Session session;
            // sets SMTP server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", Boolean.toString(authen));
            if (tsl) {
                properties.put("mail.smtp.starttls.enable", "true");
            }
            if (ssl) {
                properties.put("mail.smtp.socketFactory.port", port); //SSL Port
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
                properties.put("mail.smtp.socketFactory.fallback", "false");
            }
            if (authen) {
                properties.put("mail.smtp.user", userName);
                properties.put("mail.smtp.password", password);
                // creates a new session with an authenticator
                Authenticator auth = new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                };
                session = Session.getInstance(properties, auth);
            } else {
                session = Session.getInstance(properties);
            }
            // creates a new e-mail message
            Message msg = new MimeMessage(session);
            InternetAddress iAdd = new InternetAddress(userName);
            if (!(fromName == null && fromName.isEmpty())) {
                try {
                    iAdd.setPersonal(fromName, "utf-8");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            msg.setFrom(iAdd);
            InternetAddress[] toAddresses = getAddresses(toAddress);
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
//
//            // setting CC mail addresses
//            if (!(CC_Address == null && CC_Address.isEmpty())) {
//                InternetAddress[] ccAddresses = getAddresses(CC_Address);
//                msg.setRecipients(Message.RecipientType.CC, ccAddresses);
//            }
//
//            // setting BCC mail addresses
//            if (!(BCC_Address == null && BCC_Address.isEmpty())) {
//                InternetAddress[] bccAddresses = getAddresses(BCC_Address);
//                msg.setRecipients(Message.RecipientType.BCC, bccAddresses);
//            }

            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
            if (attachFiles != null && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    if (new File(filePath).isFile()) {
                        MimeBodyPart attachPart = new MimeBodyPart();
                        try {
                            attachPart.attachFile(filePath);
                        } catch (IOException ex) {
                            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        multipart.addBodyPart(attachPart);
                    }
                }
            }

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(host, userName, password);
            t.sendMessage(msg, msg.getAllRecipients());
            responseCode = t.getLastReturnCode();
            responseStatus = t.getLastServerResponse();
            t.close();
        } catch (Exception ex) {
            error = ex;
            responseStatus = "Error occurred";
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static InternetAddress[] getAddresses(String addresses) {
        String[] address = addresses.split(",");
        InternetAddress[] iAddresses = new InternetAddress[address.length];
        for (int i = 0; i < address.length; i++) {
            try {
                iAddresses[i] = new InternetAddress(address[i].trim());
            } catch (AddressException ex) {
                Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return iAddresses;
    }

    public void sendEmail(String host, String port,
            final String userName, final String password, final boolean authen, final boolean ssl, final boolean tsl, String toAddress, String CC_Address, String BCC_Address, String fromName,
            String subject, String message) {
        try {
            Session session;
            // sets SMTP server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", Boolean.toString(authen));
            if (tsl) {
                properties.put("mail.smtp.starttls.enable", "true");
            }
            if (ssl) {
                properties.put("mail.smtp.socketFactory.port", port); //SSL Port
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
                properties.put("mail.smtp.socketFactory.fallback", "false");
            }
            if (authen) {
                properties.put("mail.smtp.user", userName);
                properties.put("mail.smtp.password", password);
                // creates a new session with an authenticator
                Authenticator auth = new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                };
                session = Session.getInstance(properties, auth);
            } else {
                session = Session.getInstance(properties);
            }
            // creates a new e-mail message
            Message msg = new MimeMessage(session);
            InternetAddress iAdd = new InternetAddress(userName);
            if (!(fromName == null && fromName.isEmpty())) {
                try {
                    iAdd.setPersonal(fromName, "utf-8");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            msg.setFrom(iAdd);
            InternetAddress[] toAddresses = getAddresses(toAddress);
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
//
//            // setting CC mail addresses
//            if (!(CC_Address == null && CC_Address.isEmpty())) {
//                InternetAddress[] ccAddresses = getAddresses(CC_Address);
//                msg.setRecipients(Message.RecipientType.CC, ccAddresses);
//            }
//
//            // setting BCC mail addresses
//            if (!(BCC_Address == null && BCC_Address.isEmpty())) {
//                InternetAddress[] bccAddresses = getAddresses(BCC_Address);
//                msg.setRecipients(Message.RecipientType.BCC, bccAddresses);
//            }

            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(host, userName, password);
            t.sendMessage(msg, msg.getAllRecipients());
            responseCode = t.getLastReturnCode();
            responseStatus = t.getLastServerResponse();
            t.close();
        } catch (Exception ex) {
            error = ex;
            responseStatus = "Error occurred";
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public Exception getError() {
        return error;
    }
}

