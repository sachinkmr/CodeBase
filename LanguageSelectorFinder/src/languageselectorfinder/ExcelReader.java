/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageselectorfinder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class ExcelReader {

    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
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

    public synchronized void writeData(JSONObject json) {
        try {
            sheet = workbook.createSheet(HelperClass.generateUniqueString());
            JSONArray arr = json.names();
            Row row1 = sheet.createRow(0);
            Cell c1 = row1.createCell(0);
            Cell c2 = row1.createCell(1);
            Cell c3 = row1.createCell(2);
            Cell c4 = row1.createCell(3);
            c1.setCellValue("Site Name");
            c2.setCellValue("Site Type");
            c3.setCellValue("Status Code");
            c4.setCellValue("Branch Version");
            for (int i = 0; i < json.length(); i++) {
                Row row = sheet.createRow(i + 1);
                Cell cell0 = row.createCell(0);
                Cell cell1 = row.createCell(1);
                Cell cell2 = row.createCell(2);
                Cell cell3 = row.createCell(3);
                try {
                    JSONObject js = json.getJSONObject(arr.getString(i));
                    cell0.setCellValue(js.getString("name"));
                    cell2.setCellValue(js.getInt("status"));
                    cell1.setCellValue(js.getString("type"));
                    cell3.setCellValue(js.getString("branch"));
                } catch (JSONException ex) {
                    Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<JSONObject> verifyUserData() {
        List<JSONObject> sites = new ArrayList<>();
        this.sheet = workbook.getSheet("VerifyUser");
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            try {
                Row row = rowIterator.next();
                if (row.getRowNum() != 0) {
                    JSONObject json = new JSONObject();
                    json.put("epin", df.formatCellValue(row.getCell(0)));
                    json.put("ccid", df.formatCellValue(row.getCell(1)));
                    json.put("email", df.formatCellValue(row.getCell(2)));
                    json.put("expected", df.formatCellValue(row.getCell(3)));
                    json.put("actual", df.formatCellValue(row.getCell(4)));
                    json.put("status", df.formatCellValue(row.getCell(5)));
                    sites.add(json);
                    json = null;
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

}
