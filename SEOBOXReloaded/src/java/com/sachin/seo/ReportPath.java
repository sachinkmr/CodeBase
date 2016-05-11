/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author sku202
 */
public class ReportPath {

    String returnPath = null;
    ArrayList<String> list1 = new ArrayList<String>();

    public ArrayList<String> displayIt(File node) {
        if (node.isDirectory()) {
            String[] subNode = node.list();
            List<String> list = Arrays.asList(subNode);
            if (!list.contains("pages")) {
                for (String path : list) {
                    displayIt(new File(node, path).getAbsoluteFile());
                }
            } else {
                list1.add(node.getAbsolutePath());
            }
        }
        return list1;
    }

    public void removeEmptyDir(File node) {
        if (node.isDirectory()) {
            String[] subNode = node.list();
            if (subNode.length <= 0) {
                try {
                    FileUtils.deleteDirectory(node);
                } catch (IOException ex) {
                    Logger.getLogger(ReportPath.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                for (String path : subNode) {
                    removeEmptyDir(new File(node, path).getAbsoluteFile());
                }
            }
        }
    }
}
