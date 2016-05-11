/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.site;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sku202
 */
public class UserAgents extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    JSONParser parser;
    JSONObject jsonObject;
    BufferedReader reader;
    BufferedWriter file;    
    ServletContext context;
    public PrintWriter out;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("q");        
        out = response.getWriter();
        if (query.equals("getUserAgents")) {
            out.println(readJsonFile());
        }
        if (query.equals("value")) {
            String name = request.getParameter("name");
            out.println(getValue(name));
        }
        if (query.equals("add")) {
            String name = request.getParameter("id1");
            String value = request.getParameter("uaVal");
            if (addAgent(name, value)) {
                out.println(true);
            } else {
                out.println(false);
            }
        }
        writeData();
    }


    public void writeData() {
        try {
            file = new BufferedWriter(new FileWriter(getServletConfig().getServletContext().getRealPath("/WEB-INF/data/userAgents.json"), false));
            file.write(jsonObject.toJSONString());            
            file.flush();
        } catch (IOException ex) {            
            out.println(ex+"<br/>");
            Logger.getLogger(UserAgents.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {                
                file.close();
            } catch (IOException ex) {
                out.println(ex+"<br/>");
                Logger.getLogger(UserAgents.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void init() throws ServletException {
        context = getServletContext();  
        parser = new JSONParser();
        try {
            reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/WEB-INF/data/userAgents.json")));
            jsonObject = (JSONObject) parser.parse(reader);

        } catch (IOException | ParseException ex) {            
            out.println(ex+"<br/>");
            Logger.getLogger(UserAgents.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                out.println(ex+"<br/>");
                Logger.getLogger(UserAgents.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String readJsonFile() {
//        keys = jsonObject.keySet();
        return jsonObject.toString();
    }

    public boolean addAgent(String name, String value) {
        Integer s1 = jsonObject.size();
        jsonObject.put(name, value);
        return jsonObject.size() != s1;
    }

    public String getValue(String key) {
        return (String) jsonObject.get(key);
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
