/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.templateFinder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.simple.JSONObject;

import sachin.spider.URLCanonicalizer;

/**
 *
 * @author Sachin
 */
public class Verify extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        int threadValue = 7, timeoutValue = 20000;
        String user = null, pass = null, siteURL = null, template = null;
        boolean auth = false, crawl = false;
        siteURL = URLCanonicalizer.getCanonicalURL(request.getParameter("siteURL"));
        if (request.getParameter("setAuthentication") != null && request.getParameter("setAuthentication").equalsIgnoreCase("on")) {
            user = request.getParameter("username");
            pass = request.getParameter("password");
            auth = true;
        }
        if (request.getParameter("setPerformance") != null && request.getParameter("setPerformance").equalsIgnoreCase("on")) {
            threadValue = Integer.parseInt(request.getParameter("threads")) * 1000;
            timeoutValue = Integer.parseInt(request.getParameter("timeout")) * 1000;
        }
        if (request.getParameter("crawl") != null && request.getParameter("crawl").equalsIgnoreCase("on")) {
            crawl = true;
        }
        if (request.getParameter("template") != null) {
            template = request.getParameter("template");
        }
        String host = new URL(siteURL).getHost();
        if (!new File(HelperClass.getCrawledDataLocation(),host.replaceAll(".com", "")).exists()) {
            crawl = true;
        }
        JSONObject map = new JSONObject();
        map.put("user", auth == false ? "" : user);
        map.put("appPath", getServletContext().getRealPath("/"));
        map.put("siteName", siteURL);
        map.put("pass", auth == false ? "" : pass);
        map.put("threadValue", threadValue);
        map.put("timeoutValue", timeoutValue);
        map.put("auth", auth);
        org.json.JSONObject json = new org.json.JSONObject();
        if (crawl) {
            Controller control = new Controller(map);
            control.go();
            if (control.isHasError()) {
                response.setStatus(500);
                return;
            }
        }
        if (template != null) {
            try {
                json.put(template, HelperClass.readCrawledData(host).getString(template));
            } catch (JSONException ex) {
                Logger.getLogger(Verify.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            json = HelperClass.readCrawledData(host);
        }
        if (json != null && json.length() > 0) {
            out.println(json);
        } else {
            response.setStatus(500);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        System.setProperty("appPath", getServletContext().getRealPath("/"));
    }
}
