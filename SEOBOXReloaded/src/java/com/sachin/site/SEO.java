/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.site;

import com.sachin.seo.CreateExcel;
import com.sachin.seo.CreateMainOutput;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Sachin
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class SEO extends HttpServlet {

    public static boolean started = false;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
//    public static LinksManager linksMgr;
    public boolean validateIni(HttpServletRequest request, HttpServletResponse response, Data data) {
        data.address = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().lastIndexOf('/'));
        boolean x = true;
        data.threadValue = 5;
        data.timeoutValue = 180;
        try {
            try {
                data.agentName = URLEncoder.encode(request.getParameter("uaName"), "utf-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SEO.class.getName()).log(Level.SEVERE, null, ex);
                x = false;
            }
            if (request.getParameter("setAuthentication") != null && request.getParameter("setAuthentication").equalsIgnoreCase("on")) {
                data.user = request.getParameter("username");
                data.pass = request.getParameter("password");
                data.ldap = true;
            } else {
                data.ldap = false;
            }
            if (request.getParameter("redirect") != null && request.getParameter("redirect").equalsIgnoreCase("on")) {
                data.redirect = true;
            } else {
                data.redirect = false;
            }
            if (request.getParameter("stay") != null && request.getParameter("stay").equalsIgnoreCase("on")) {
                data.stay = true;
            } else {
                data.stay = false;
            }
            data.crawling = true;
            if (request.getParameter("setPerformance") != null && request.getParameter("setPerformance").equalsIgnoreCase("on")) {
                data.threadValue = Integer.parseInt(request.getParameter("threads"));
                data.timeoutValue = Integer.parseInt(request.getParameter("timeout"));
            }
            if (request.getParameter("editSEO") != null && request.getParameter("editSEO").equalsIgnoreCase("on")) {
                data.urlCharLimit = Integer.parseInt(request.getParameter("urlCharLimit"));
                data.h1CharLimit = Integer.parseInt(request.getParameter("h1CharLimit"));
                data.h2CharLimit = Integer.parseInt(request.getParameter("h2CharLimit"));
                data.titleCharLimit = Integer.parseInt(request.getParameter("titleCharLimit"));
                data.descriptionCharLimit = Integer.parseInt(request.getParameter("descriptionCharLimit"));
                data.canonicalCharLimit = Integer.parseInt(request.getParameter("canonicalCharLimit"));
                data.altTextCharLimit = Integer.parseInt(request.getParameter("altTextCharLimit"));
            }
        } catch (Exception ex) {
            Logger.getLogger(SEO.class.getName()).log(Level.SEVERE, null, ex);
            x = false;
        }
        return x;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Data data = null;
        if (started) {
            out.println("Another Process is running....<br/>");
            out.println("Please try after some time....<br/>");
            return;
        }

        try {
            data = new Data();
            data.loc = request.getServletContext().getRealPath("/output");
            data.webinf = request.getServletContext().getRealPath("/WEB-INF");
            if (validateIni(request, response, data)) {
                started = true;
                List<String> list = new ArrayList<>();
                data.crawlingType = request.getParameter("crawlingType");
                if (data.crawlingType.equalsIgnoreCase("singlePage")) {
                    data.threadValue = 1;
                }
                if (data.crawlingType.equalsIgnoreCase("txtFile")) {
                    list = (List<String>) request.getSession().getAttribute("siteName");
                    data.crawlingType = "allPages";
                } else {
                    list.add((String) request.getParameter("siteURL"));
                    data.crawlingType = "allPages";
                }
                getAllSitesCrawled(list, data, out);
                if (data.running) {
                    generateSEO(list, data, out);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            out.println("<br/>Error...<br/>" + ex.toString());
        }
        data = null;
        Constants.webPage = null;
        started = false;
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

    private void getAllSitesCrawled(List<String> list, Data data, PrintWriter out) throws Exception {
        for (String url : list) {
            Site site = new SiteBuilder(URLCanonicalizer.getCanonicalURL(url.trim()))
                    .setData(data)
                    .setCrawling(data.crawling)
                    .setUserAgent(data.agentName)
                    .setUsername(data.user)
                    .setPassword(data.pass)
                    .build();

            data.running = site.isRunning();
            if (data.running) {
                data.isResponsive = site.isResponsive();
                data.releaseVer = site.getBranchVersion();
                site.getAllCrawledLinks();
                data.site = URLCanonicalizer.getCanonicalURL(url.trim());
                data.host = site.getHost();
            } else {
                out.println("Site is giving following response code. Please correct parameters accordingly...<br/>");
                out.println(site.getStatusCode() + " : " + site.getStatusMsg());
            }
        }
    }

    private void generateSEO(List<String> list, Data data, PrintWriter out) throws IOException {
        for (String url : list) {
            generateSEOReport(data, getWebpages(url, data), out);
        }

    }

    private Map<String, WebPage> getWebpages(String url, Data data) {
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        DateFormat df1 = new SimpleDateFormat("hh-mm-ss-SSaa");
        Calendar calobj = Calendar.getInstance();
        String time = df1.format(calobj.getTime());
        String date = df.format(calobj.getTime());
        data.date = date;
        data.time = time;
        Map<String, WebPage> webPages = Collections.synchronizedMap(new HashMap<String, WebPage>());
        try {
            String host = new URL(URLCanonicalizer.getCanonicalURL(url.trim())).getHost();
            data.outputLocation = data.loc + "/" + host + "/" + date + "/" + time + "/";
            data.outputAddress = data.address + "/output/" + host + "/" + date + "/" + time + "/";
            List<UrlLink> urlLinks = HelperClass.readCrawlingData(host);
            HelperClass.deleteCrawlingData(host);
            System.out.println("Crawled Data readed");
            for (UrlLink link : urlLinks) {
                if (!link.getUrl().contains("?")) {
                    WebPage webPage = new WebPage(link.getUrl(), link, data);
                    webPages.put(webPage.urlName, webPage);
                    webPage.run();
                    Constants.webPage = webPage;
                    webPage.writeData();
                    Constants.webPage = null;
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(SEO.class.getName()).log(Level.SEVERE, null, ex);
            data.completed = false;
        }
        return webPages;
    }

    private void generateSEOReport(Data data, Map<String, WebPage> webPages, PrintWriter out) {
        try {
            System.out.println("Generating SEO Report...");
            new CreateMainOutput(data, webPages).createHTML();
            FileUtils.copyFile(new File(data.webinf + "/data/SEOReport.xlsx"), new File(Data.outputLocation + "SEOReport.xlsx"));
            new CreateExcel(data, webPages).updateExcelSEOFile();
            data.completed = true;
            out.println("To View Output <a href='" + data.outputAddress + "index.html' target='_blank'>Click Here</a><br/><br/>");
            out.println("<br/>");
            out.println(" To download report in excel format <a href='" + data.outputAddress + "SEOReport.xlsx'>Click Here</a><br/><br/>");
            out.println("<br/>");
            data.clean();
            System.out.println("SEO Report Generated...");
        } catch (Exception ex) {
            Logger.getLogger(SEO.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex + "<br/>");
            data.completed = false;
        }
        if (!data.completed) {
            try {
                System.out.println("Error Occoured, Cleaning directories....<br/>");
                out.println("Error Occoured, Cleaning directories....<br/>");
                FileUtils.deleteDirectory(new File(data.outputLocation));
            } catch (IOException ex) {
                Logger.getLogger(SEO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
