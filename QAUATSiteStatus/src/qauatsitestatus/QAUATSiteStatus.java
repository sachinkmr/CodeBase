/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qauatsitestatus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author sku202
 */
public class QAUATSiteStatus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            List<String> sites=FileUtils.readLines(new File("D://sites1.txt"), "UTF-8");
            for(String site:sites){
              Demo d=  new Demo(site);
              d.go();
            }
        } catch (IOException ex) {
            Logger.getLogger(QAUATSiteStatus.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
}
