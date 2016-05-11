/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import sachin.bws.site.ConfigDev;
import sachin.bws.site.HelperClass;
import sachin.bws.site.Site;
import sachin.bws.site.URLCanonicalizer;

/**
 *
 * @author sku202
 */
public class GetSiteInfoForPage extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ConfigDev devConfig = ConfigDev.getInstance();
        Site site = null;
        JSONObject map = new JSONObject();
        try {
            if (request.getParameter("siteInfo").equalsIgnoreCase("yes")) {
                out.println(getSiteJson(devConfig));
            }
            if (request.getParameter("siteInfo").equalsIgnoreCase("no")) {
                String[] ar = null;
                if (request.getParameter("address") != null && !request.getParameter("address").isEmpty()) {
                    String address = URLCanonicalizer.getCanonicalURL((String) request.getParameter("address"));
                    try {
                        ar = HelperClass.findConfigDevProperties(devConfig, address);
                    } catch (Exception ex) {
                        map.put("hasError", "Yes");
                        map.put("errors", ex.getMessage());
                        Logger.getLogger(GetSiteInfoForPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    map.put("brands", ar[0]);
                    map.put("env", ar[2]);
                    map.put("cultures", ar[1]);
                    map.put("hasError", "true".equals(ar[3]) ? "Yes" : "No");
                } else {
                    map.put("hasError", "Yes");
                    map.put("errors", "");
                }
                out.println(map);
            }
        } catch (Exception ex) {
            Logger.getLogger(GetSiteInfoForPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private JSONObject getSiteJson(ConfigDev devConfig) {
        Map<String, JSONArray> map = new HashMap<String, JSONArray>();
        List<String> list=null;
        list = devConfig.getBrands();
        Collections.sort(list);
        map.put("brands", new JSONArray(list));
        list = devConfig.getEnvironments();
        Collections.sort(list);
        map.put("env", new JSONArray(list));
        list = devConfig.getCultures();
        Collections.sort(list);
        map.put("cultures", new JSONArray(list));
        return new JSONObject(map);
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
