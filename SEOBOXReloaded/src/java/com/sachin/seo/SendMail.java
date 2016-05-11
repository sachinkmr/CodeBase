/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMail extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = getServletContext();
        String To = context.getInitParameter("adminMailID");
        String mailSenderAdminID = context.getInitParameter("mailSenderAdminID");
        String mailSenderAdminPass = context.getInitParameter("mailSenderAdminPass");
        String subject = (String) request.getParameter("subject");
        String body = (String) request.getParameter("msg");
        String from = (String) request.getParameter("mail");
        String name = (String) request.getParameter("fullname");
        String mobile = (String) request.getParameter("mobile");
        try {
            String host = "smtp.gmail.com", user = mailSenderAdminID, pass = mailSenderAdminPass;
            String messageText = "Email regarding SEO Utility<br/>From: " + from + "<br/>Name: " + name + "<br/>Subject: " + subject + "<br/>Mobile: " + mobile + "<br/>Message: " + body;
            boolean sessionDebug = true;
            Properties props = System.getProperties();
            props.put("mail.host", host);
            props.put("mail.transport.protocol.", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(To)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setContent(messageText, "text/html"); // use setText if you want to send text
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            try {
                transport.sendMessage(msg, msg.getAllRecipients());
            } catch (Exception ex) {
                out.println("<div class='alert alert-danger alert-dismissible' role='alert'> <button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>  <strong>Error! </strong> Technical error.<br/>" + ex + "</div>");
                Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
            }
            transport.close();
            out.println("<div class='alert alert-success alert-dismissible' role='alert'> <button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>  <strong>Success! </strong> Mail sent Successfully, Thank you for contacting us. We will revert back to you soon</div>");

        } catch (Exception ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
            out.println("<div class='alert alert-danger alert-dismissible' role='alert'> <button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>  <strong>Error! </strong> Technical error.<br/>" + ex + "</div>");
        }

    }
}
