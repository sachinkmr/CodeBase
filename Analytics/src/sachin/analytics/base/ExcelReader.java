/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class ExcelReader {

    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private CreationHelper createHelper;
    private BufferedOutputStream output;
    private File filePath;
    DataFormatter df;

    public ExcelReader(File filePath) {
        this.filePath = filePath;
        FileInputStream f = null;
        df = new DataFormatter();
        try {
            f = new FileInputStream(filePath);
            this.workbook = new XSSFWorkbook(f);
            createHelper = workbook.getCreationHelper();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }

    public List<AnalyticsData> readEventData() {
        this.sheet = workbook.getSheet("Events");
        Iterator<Row> rows = sheet.iterator();
        Map<String, AnalyticsData> map = new HashMap<>();
        List<AnalyticsData> list = new ArrayList<>();
        while (rows.hasNext()) {
            Row row = rows.next();
            if (row.getRowNum() != 0) {
                String templateName = df.formatCellValue(row.getCell(0));
                AnalyticsData data = null;
                if (map.containsKey(templateName)) {
                    data = map.get(templateName);
                } else {
                    data = new AnalyticsData(templateName);
                    list.add(data);
                }
                Component component = new Component(df.formatCellValue(row.getCell(1)));
                component.setExpectedParams(df.formatCellValue(row.getCell(2)));
                component.setEventPath(df.formatCellValue(row.getCell(7)));
                component.setEventType(df.formatCellValue(row.getCell(8)));
                component.setTestCaseNumber(Integer.toString(row.getRowNum()));
                data.getComponent().add(component);
                map.put(templateName, data);
            }
        }
        return list;
    }

    public JSONObject readSiteData() {
        JSONObject sites = new JSONObject();
        this.sheet = workbook.getSheet("Sites");
        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            try {
                Row row = rowIterator.next();
                if (row.getRowNum() != 0) {
                    JSONObject json = new JSONObject();
                    json.put("site", df.formatCellValue(row.getCell(0)));
                    json.put("brand", df.formatCellValue(row.getCell(1)));
                    json.put("environment", df.formatCellValue(row.getCell(2)));
                    json.put("culture", df.formatCellValue(row.getCell(3)));
                    json.put("username", df.formatCellValue(row.getCell(4)));
                    json.put("password", df.formatCellValue(row.getCell(5)));
                    json.put("userAgent", df.formatCellValue(row.getCell(6)));
                    sites.put(row.getCell(0).getStringCellValue(), json);
                }
            } catch (JSONException ex) {
                Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            workbook.close();
        } catch (IOException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sites;
    }

    public JSONObject initializeUserAgents() {
        JSONObject UA = new JSONObject();
        this.sheet = workbook.getSheet("UserAgents");
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            try {
                Row row = rows.next();
                JSONObject prop = new JSONObject();
                prop.put("name", df.formatCellValue(row.getCell(0)));
                prop.put("string", df.formatCellValue(row.getCell(1)));
                prop.put("width", df.formatCellValue(row.getCell(2)));
                prop.put("height", df.formatCellValue(row.getCell(3)));
                UA.put(df.formatCellValue(row.getCell(0)), prop);
            } catch (JSONException ex) {
                Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(HelperClass.getDataFolderPath(), "UserAgent.json")));
            UA.write(br);
            br.close();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UA;
    }

    public JSONObject initializeSocial() {
        JSONObject UA = new JSONObject();
        this.sheet = workbook.getSheet("SocialMediaDetail");
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            try {
                Row row = rows.next();
                JSONObject prop2 = new JSONObject();
                prop2.put("username", df.formatCellValue(row.getCell(1)));
                prop2.put("password", df.formatCellValue(row.getCell(2)));                              
                UA.put(df.formatCellValue(row.getCell(0)), prop2);
            } catch (JSONException ex) {
                Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(HelperClass.getDataFolderPath(), "SocialMediaDetail.json")));
            UA.write(br);
            br.close();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(HelperClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UA;
    }
}
