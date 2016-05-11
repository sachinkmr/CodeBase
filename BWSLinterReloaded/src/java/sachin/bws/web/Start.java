/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import sachin.bws.selenium.Demo3;

/**
 *
 * @author sku202
 */
public class Start extends HttpServlet {

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
        new Demo3().go();
//        try {
//            System.out.println("yes");
//            File file = new File(HelperClass.getExcelRepository());
//            FileUtils.copyFileToDirectory(new File(HelperClass.getDataFolderPath(), "Report.xlsx"), file, true);
//            Map<String, JSONObject> list = new HashMap<String, JSONObject>();
//            String address = Data.getDATA_CONFIG().getString("outputAddressWithHostMachine");
//            
//            String siteName = "http://examplefood-qa.unileversolutions.com/";
//            try {
//                JSONObject json = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json").getJSONObject(siteName);
////                Site site = new Site(siteName, json.getString("brand"), json.getString("culture"), json.getString("environment"), true, json.getString("username"), json.getString("password"),json);
////                System.out.println("Running on " + siteName);
////                site.setCrawling(true);
////                JSONObject ob = site.getOutputWithTemplates();
////                HelperClass.writeJSONoutputWithTemplateToFile(ob, site.getHost());
////                list.put(site.getUrl(), ob.getJSONObject("siteInfo").put("outputAddress", address + site.getHost()));
//            } catch (Exception ex) {
//                java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            file = new File(file.getAbsolutePath(), "Report.xlsx");
//            writeExcelRow(file, list);
//            JSONObject mail = new JSONObject(FileUtils.readFileToString(new File(HelperClass.getDataFolderPath(), "mail.json"), "UTF-8"));
//            EmailSender send = new EmailSender();
//            try {
//                send.sendEmailWithAttachments(mail.getString("host"), mail.getString("port"), mail.getString("mailFrom"), "", false, false, false, mail.getString("to"), "", "", "BWS Linter", mail.getString("subject"), mail.getString("message"), new String[]{file.getAbsolutePath()});
//
//            } catch (Exception ex) {
//                java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(ScheduledJob.class.getName()).log(Level.SEVERE, null, ex);
//        }
        out.println("Completed");
    }

    private void writeExcelRow(File file, Map<String, JSONObject> list) {
        new ExcelManager(file).createData(list);
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
