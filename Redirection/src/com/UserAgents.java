/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapient.unilever;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author sku202
 */
public class UserAgents {

    static UserAgents UA = null;
    JSONParser parser;
    String path;
    FileReader reader;
    FileWriter file;
    Map<String, String> agentData;
    private XSSFSheet agentSheet;
    
    public static UserAgents getInstance(XSSFSheet agentSheet) {
        if (UA == null) {
            UA = new UserAgents(agentSheet);
        }
        return UA;
    }

    public static UserAgents getInstance() {
        return UA;
    }

    private UserAgents(XSSFSheet agentSheet) {
        this.agentSheet = agentSheet;
        this.agentData = readData();
    }

    public Set<String> getAgentNames() {
        return agentData.keySet();

    }

    public String getValue(String key) {
        String val = "";
        try {
            val = URLEncoder.encode(agentData.get(key), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserAgents.class.getName()).log(Level.WARN, null, ex);
        }
        return val;
    }

    public Map<String, String> readData() {
        Map<String, String> list = new HashMap<String, String>();
        Iterator<Row> rowIterator = agentSheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() > 0 && (!row.getCell(0).getStringCellValue().equals("")) && row.getCell(0).getStringCellValue() != null) {//To filter column headings
                list.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
            }
        }
        return list;
    }
}
