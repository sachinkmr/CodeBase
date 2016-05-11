/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author sku202
 */
public class Template {

    private List<String> templates;
    private static Template template;

    public static Template getInstance() {
        if (template == null) {
            template = new Template();
        }
        return template;
    }

    private Template() {
        try {
            templates = FileUtils.readLines(new File(HelperClass.getDataFolderPath(), "templates.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getTemplate(Document doc) {
        String bodyClass[] = doc.body().attr("class").split("\\u0020");
        List<String> classes = Arrays.asList(bodyClass);
        Collections.reverse(classes);
        for (String t : classes) {
            if (templates.contains(t)) {
                return t;
            }
        }
        return "****";
    }

    public String getTemplate(String url) {
        try {
            Document doc = Jsoup.connect(url).ignoreHttpErrors(true).ignoreContentType(true).timeout(100000).followRedirects(true).get();
            return getTemplate(doc);
        } catch (IOException ex) {
            Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "****";
    }

    public List<String> getTemplates() {
        return templates;
    }
}
