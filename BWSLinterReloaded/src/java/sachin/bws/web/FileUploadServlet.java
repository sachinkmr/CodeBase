/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import sachin.bws.site.HelperClass;

/**
 *
 * @author Sachin
 */
public class FileUploadServlet extends HttpServlet {

//    private static final long serialVersionUID = 1L;
    private File fileUploadPath;
    HttpSession session;
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     *
     */
    /**
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    @SuppressWarnings("unchecked")

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        }
        fileUploadPath = new File(getServletContext().getRealPath("/upload")+File.separator+HelperClass.generateUniqueString());
        fileUploadPath.mkdirs();
        System.out.println(fileUploadPath.getAbsolutePath());
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(fileUploadPath, item.getName());
                    item.write(file);
                    JSONObject jsono = new JSONObject();
                    jsono.put("name", item.getName());
                    jsono.put("size", item.getSize());
                    jsono.put("url", "upload?getfile=" + item.getName());                  
                    json.put(jsono);
                //    String FileURL = fileUploadPath + "/" + item.getName();
                //    createSiteList(FileURL);
                }
            }
            writer.write(json.toString());
            writer.close();
        //    FileUtils.copyDirectory(fileUploadPath, new File(getServletContext().getRealPath("/upload")));
        } catch (FileUploadException ex) {
             Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
             Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
