/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import sachin.bws.site.Data;
import sachin.bws.site.HelperClass;

/**
 *
 * @author sku202
 */
public class AddNewSiteServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                session = request.getSession();
            }
            String url = null;
            Map map = request.getParameterMap();
            if (session.getAttribute("Admin") != null) {
                JSONObject site = new JSONObject();
                JSONObject json = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json");
                if (map.containsKey("sites") && map.containsKey("insert")) {
                    url = request.getParameter("url11");
                    site.put("brand", request.getParameter("brand1"));
                    site.put("culture", request.getParameter("culture1"));
                    site.put("environment", request.getParameter("environment1"));
                    site.put("sitePubId", request.getParameter("sitepubid1"));
                    site.put("appPoolEnv", request.getParameter("appPoolEnv1"));
                    site.put("appPoolSite", request.getParameter("appPoolSite1"));
                    site.put("siteSearch", request.getParameter("siteSearch1"));
                    site.put("storeLocatorZIP", request.getParameter("storeLocatorZIP1"));
                    site.put("recipeSearch", request.getParameter("recipeSearch1"));
                    site.put("virtualAgent", request.getParameter("virtualAgent1"));
                    String ua = null;
                    if (request.getParameterMap().containsKey("setAuthentication")) {
                        site.put("username", request.getParameter("username1"));
                        site.put("password", request.getParameter("password1"));
                    } else {
                        site.put("username", "");
                        site.put("password", "");
                    }
                    switch (request.getParameter("ua1")) {
                        case "desktop":
                            ua = Data.UA_DESKTOP;
                            break;
                        case "tablet":
                            ua = Data.UA_TABLET;
                            break;
                        case "mobile":
                            ua = Data.UA_MOBILE;
                            break;
                        default:
                            ua = Data.UA_DESKTOP;
                    }
                    site.put("userAgent", ua);
                    Writer write = new BufferedWriter(new FileWriter(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json"));
                    json.put(url, site);
                    json.write(write);
                    write.close();
//                IOUtils.write(json.toString(), new FileWriter(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json", false));
                    response.sendRedirect("admin");
                }
                if (map.containsKey("sites") && map.containsKey("update")) {
                    url = request.getParameter("url1");
                    site.put("brand", request.getParameter("brand"));
                    site.put("culture", request.getParameter("culture"));
                    site.put("environment", request.getParameter("environment"));
                    site.put("sitePubId", request.getParameter("sitepubid"));
                    site.put("appPoolEnv", request.getParameter("appPoolEnv"));
                    site.put("appPoolSite", request.getParameter("appPoolSite"));                    
                    site.put("siteSearch", request.getParameter("siteSearch"));
                    site.put("storeLocatorZIP", request.getParameter("storeLocatorZIP"));
                    site.put("recipeSearch", request.getParameter("recipeSearch"));
                    site.put("virtualAgent", request.getParameter("virtualAgent"));
                    String ua = null;
                    if (request.getParameterMap().containsKey("setAuthentication")) {
                        site.put("username", request.getParameter("username"));
                        site.put("password", request.getParameter("password"));
                    } else {
                        site.put("username", "");
                        site.put("password", "");
                    }
                    switch (request.getParameter("ua")) {
                        case "desktop":
                            ua = Data.UA_DESKTOP;
                            break;
                        case "tablet":
                            ua = Data.UA_TABLET;
                            break;
                        case "mobile":
                            ua = Data.UA_MOBILE;
                            break;
                        default:
                            ua = Data.UA_DESKTOP;
                    }
                    site.put("userAgent", ua);
                    Writer write = new BufferedWriter(new FileWriter(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json"));
                    json.put(url, site);
                    json.write(write);
                    write.close();
//                IOUtils.write(json.toString(), new FileWriter(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json", false));
                    response.sendRedirect("admin");
                }
                if (map.containsKey("mail")) {
                    if (map.containsKey("update")) {
                        json = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "mail.json");
                        json.put("to", request.getParameter("email"));
                        json.put("subject", request.getParameter("subject"));
                        json.put("message", request.getParameter("mailbody"));
                        Writer write = new BufferedWriter(new FileWriter(HelperClass.getDataFolderPath() + File.separator + "mail.json"));
                        json.write(write);
                        write.close();
                        response.sendRedirect("admin");
                    }
                }
                if (map.containsKey("template")) {
                    if (map.containsKey("update")) {
                        String data[] = request.getParameter("templateName").replaceAll("\\u005b", "").replaceAll("\\u005d", "").split(",");
                        List<String> list = new ArrayList<String>();
                        for (String a : data) {
                            a = a.trim();
                            if (a.length() > 0) {
                                list.add(a);
                            }
                        }
                        Writer write = new BufferedWriter(new FileWriter(HelperClass.getDataFolderPath() + File.separator + "templates.txt"));
                        IOUtils.writeLines(list, "\n", write);
                        write.close();
                        response.sendRedirect("admin");
                    }
                }
                if (map.containsKey("linter")) {
                    if (map.containsKey("update")) {
                        json = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "config.json");
                        Set<String> set = map.keySet();
                        for (String key : set) {
                            if (!key.equalsIgnoreCase("linter") && !key.equals("update") && !key.equalsIgnoreCase("Time") && !request.getParameter(key).trim().isEmpty()) {
                                json.put(key, request.getParameter(key));
                            }
                            if (key.equalsIgnoreCase("Time")) {
                                String data[]=request.getParameter(key).trim().split(":");
                                json.put("runningHoursIn24fromat", data[0].trim());
                                json.put("runningMinIn24fromat", data[1].trim());
                            }
                        }
                        Writer write = new BufferedWriter(new FileWriter(HelperClass.getDataFolderPath() + File.separator + "config.json"));
                        json.write(write);
                        write.close();
                        response.sendRedirect("admin");
                    }
                }
            } else {
                response.sendRedirect("login");
            }
        } catch (Exception ex) {
            Logger.getLogger(AddNewSiteServlet.class.getName()).log(Level.SEVERE, null, ex);
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
