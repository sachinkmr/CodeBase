/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sachin.bws.email.EmailSender;
import sachin.bws.feature.Analytics;
import sachin.bws.feature.BVSEO;
import sachin.bws.feature.BazaarVoice;
import sachin.bws.feature.BuyOnline;
import sachin.bws.feature.ContactUs;
import sachin.bws.feature.DataCaptureTraction;
import sachin.bws.feature.Kritique;
import sachin.bws.feature.RecipeRating;
import sachin.bws.feature.RecipeSearch;
import sachin.bws.feature.SignUp;
import sachin.bws.feature.SiteSearch;
import sachin.bws.feature.StoreLocator;
import sachin.bws.feature.UserManagement;
import sachin.bws.feature.VirtualAgent;
import sachin.bws.site.Data;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.URLCanonicalizer;

/**
 *
 * @author sku202
 */
public class GetInfo extends HttpServlet {

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
        JSONObject results = new JSONObject();
        response.setContentType("text/html;charset=UTF-8");
//        File file = new File(HelperClass.getExcelRepository());
//        FileUtils.copyFileToDirectory(new File(HelperClass.getDataFolderPath(), "Report.xlsx"), file, true);
        Map<String, JSONObject> list = new HashMap<String, JSONObject>();
        Map<String, String[]> requestMap = request.getParameterMap();
        String siteName = URLCanonicalizer.getCanonicalURL((String) request.getParameter("url"));
        String ua = "";
        String mailid = "";
        if (request.getParameter("ua").equalsIgnoreCase("desktop")) {
            ua = Data.UA_DESKTOP;
        }
        if (request.getParameter("ua").equalsIgnoreCase("mobile")) {
            ua = Data.UA_MOBILE;
        }
        if (request.getParameter("ua").equalsIgnoreCase("tablet")) {
            ua = Data.UA_TABLET;
        }
        Site site = null;
        try (PrintWriter out = response.getWriter()) {
            JSONObject mail = new JSONObject(FileUtils.readFileToString(new File(HelperClass.getDataFolderPath(), "mail.json"), "UTF-8"));
            EmailSender send = new EmailSender();
            String address = Data.getDATA_CONFIG().getString("outputAddressWithHostMachine");
            String msg = "";
            System.out.println("Running on " + siteName);
//            try {
////                site = new Site(siteName, (String) request.getParameter("brand"), request.getParameter("culture"), (String) request.getParameter("environment"), true, requestMap.containsKey("username") ? (String) request.getParameter("username") : "", requestMap.containsKey("password") ? (String) request.getParameter("password") : "");
//                site.setUserAgent(ua);
//                msg = "Hi,<br/>Your Report has been generated. Please follow the link. <br/><a href='" + address + site.getHost() + "'>" + address + site.getHost() + "<br/>Thanks";
//                if (requestMap.containsKey("crawler")) {
//                    site.setCrawling(true);
//                }
//                JSONObject ob = site.getOutputWithTemplates();
//                list.put(site.getUrl(), ob.getJSONObject("siteInfo").put("outputAddress", address + site.getHost()));
//                HelperClass.deleteAllFunctionality(site.getHost());
//                if (requestMap.containsKey("analytics")) {
//                    site.testFeature(new Analytics(site));
//                }
//                if (requestMap.containsKey("signup")) {
//                    site.testFeature(new SignUp(site));
//                }
//                if (requestMap.containsKey("SiteSearch")) {
//                    site.testFeature(new SiteSearch(site));
//                }
//                if (requestMap.containsKey("BV")) {
//                    site.testFeature(new BazaarVoice(site));
//                }
//                if (requestMap.containsKey("BuyOnline")) {
//                    site.testFeature(new BuyOnline(site));
//                }
//                if (requestMap.containsKey("username")) {
//                    site.testFeature(new ContactUs(site));
//                }
//                if (requestMap.containsKey("BVSEO")) {
//                    site.testFeature(new BVSEO(site));
//                }
//                if (requestMap.containsKey("DataCaptureTraction")) {
//                    site.testFeature(new DataCaptureTraction(site));
//                }
//                if (requestMap.containsKey("Kritique")) {
//                    site.testFeature(new Kritique(site));
//                }
//                if (requestMap.containsKey("RecipeRatings")) {
//                    site.testFeature(new RecipeRating(site));
//                }
//                if (requestMap.containsKey("RecipeSearch")) {
//                    site.testFeature(new RecipeSearch(site));
//                }
//                if (requestMap.containsKey("WTB")) {
//                    site.testFeature(new StoreLocator(site));
//                }
//                if (requestMap.containsKey("UM")) {
//                    site.testFeature(new UserManagement(site));
//                }
//                if (requestMap.containsKey("VirtualAgent")) {
//                    site.testFeature(new VirtualAgent(site));
//                }
//                try (FileWriter writer = new FileWriter(new File(HelperClass.getStatusDirectory(), "BWSstatus.json"), false)) {
//                    JSONObject data = new JSONObject(list);
//                    data.write(writer);
//                }
//                results.put("hasErrors", false);
//                results.put("add", address + site.getHost());
//            } catch (Exception ex) {
//                Logger.getLogger(GetInfo.class.getName()).log(Level.SEVERE, null, ex);
//            }
            results.put("hasErrors", true);
            try {
                send.sendEmail(mail.getString("host"), mail.getString("port"), mail.getString("mailFrom"), "", false, false, false, mailid, "", "", "BWS Linter", mail.getString("subject"), msg);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(GetInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println(results);
        } catch (JSONException ex) {
            Logger.getLogger(GetInfo.class.getName()).log(Level.SEVERE, null, ex);
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

}
