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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import sachin.bws.site.HelperClass;

/**
 *
 * @author sku202
 */
public class AdminSite extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            Map map = request.getParameterMap();
            if (map.containsKey("param")) {
                String param = request.getParameter("param");
                String site = request.getParameter("site");
                HttpSession session = request.getSession(false);
                if (session == null) {
                    session = request.getSession();
                }
                if (session.getAttribute("Admin") == null) {
                    response.sendRedirect("login");
                } else {
                    JSONObject object = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json");
                    if (param.equalsIgnoreCase("delete")) {
                        object.remove(site);
						FileWriter fr=new FileWriter(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json", false);
						object.write(fr);
						fr.close();
                    }
                                        response.sendRedirect("admin");
                }
            }
            if (map.containsKey("exampleSite")) {
                String data = request.getParameter("exampleSite");
                JSONObject ob = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json").getJSONObject(data);
                out.println(ob);
            }
        } catch (JSONException ex) {
            Logger.getLogger(AdminSite.class.getName()).log(Level.SEVERE, null, ex);
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
