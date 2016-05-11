/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo;

import com.sachin.site.Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Sachin
 */
public class CreateOutput {

    BufferedWriter fw;
    File f;
    Data data;
    public CreateOutput(Data data) {
        this.data=data;
        f = new File(data.outputLocation);
        f.mkdirs();
    }

    public void createIndexFile() {
        try {           
            String fname = "/index.html";
            fw = new BufferedWriter(new FileWriter(f.getAbsolutePath() + fname));
            Document doc1 = Jsoup.connect(data.address + "/MainOutput").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            fw.write(doc1.toString());
        } catch (IOException ex) {           
            Logger.getLogger(CreateOutput.class.getName()).log(Level.SEVERE, null, ex);            
        } finally {
            try {
                fw.flush();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(CreateOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
