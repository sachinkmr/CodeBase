/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class ExcelManager {

    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private CreationHelper createHelper;
    private BufferedOutputStream output;
    private File filePath;

    public ExcelManager(File filePath) {
        this.filePath = filePath;
        FileInputStream f = null;
        try {
            f = new FileInputStream(filePath);
            this.workbook = new XSSFWorkbook(f);
            this.sheet = workbook.getSheetAt(0);
            createHelper = workbook.getCreationHelper();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void createData(Map<String, JSONObject> list) {
        int i = 0;
        Row row = sheet.createRow(i++);
        createHeader(row);
        Set<String> set = list.keySet();
        for (String siteName : set) {
            fillRow(sheet.createRow(i++), list.get(siteName));
        }
    }

    void fillRow(Row row, JSONObject json) {
        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(2);
        Cell cell4 = row.createCell(3);
        Cell cell5 = row.createCell(4);
        Cell cell6 = row.createCell(5);
        Cell cell7 = row.createCell(6);
        Cell cell8 = row.createCell(7);
        Cell cell9 = row.createCell(8);
//        Cell cell10 = row.createCell(9);
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            cell.setCellStyle(cellWrapText()); //Setting wrap text with left align
        }
        try {
            cell1.setCellValue(json.getString("SiteUrl"));
            cell1.setHyperlink(setHyperLink(json.getString("outputAddress")));
            cell1.setCellStyle(wrapWithHyperlink());
            cell2.setCellValue(json.getString("RunningStatus"));
            if (json.getString("RunningStatus").equalsIgnoreCase("Running")) {
                cell3.setCellValue(json.getString("BranchVersion"));
                try {
                    cell4.setCellValue(json.getString("BrandName"));
                    cell5.setCellValue(json.getString("Culture"));
                    cell6.setCellValue(json.getString("Environment"));
                } catch (JSONException ex) {
                    Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                cell7.setCellValue(json.getString("SiteType"));
            } else {
                cell3.setCellValue("");
                cell4.setCellValue("");
                cell5.setCellValue("");
                cell6.setCellValue("");
                cell7.setCellValue("");
            }
            cell8.setCellValue(json.getString("StatusCode"));
            cell9.setCellValue(json.getString("StatusMsg"));

//            cell10.setCellValue("View Screenshot");
//            cell10.setHyperlink(setHyperLink(Data.getDATA_CONFIG().getString("hostMachine")+json.getString("Screenshot")));
//            cell10.setCellStyle(wrapWithHyperlink());
        } catch (JSONException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            output = new BufferedOutputStream(new FileOutputStream(filePath));
            workbook.write(output);
            output.flush();
            output.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createHeader(Row row) {
        String[] data = {"SiteUrl", "RunningStatus", "BranchVersion", "BrandName", "Culture", "Environment", "SiteType", "StatusCode", "StatusMsg"};
        int i = 0;
        for (int j = 0; j < data.length; j++) {
            row.createCell(j);
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            cell.setCellStyle(headerStyle()); //Setting style  
            cell.setCellValue(data[i++]);
        }
    }

    private CellStyle headerStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle cellWrapText() {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        return style;
    }

    private CellStyle wrapWithRedBG() {
        CellStyle style = cellWrapText();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }

    private CellStyle wrapWithGreenBG() {
        CellStyle style = cellWrapText();
        style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }

    private CellStyle wrapWithYellowBG() {
        CellStyle style = cellWrapText();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }

    private CellStyle wrapWithHyperlink() {
        Font hlink_font = workbook.createFont();
        CellStyle style = cellWrapText();
        hlink_font.setUnderline(Font.U_NONE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        style.setFont(hlink_font);
        return style;
    }

    private Hyperlink setHyperLink(String address) {
        Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress(address);
        return link;
    }

    public JSONObject readUploadedExcel() {
        JSONObject sites = new JSONObject();
        Iterator<Row> rowIterator = sheet.rowIterator();
        DataFormatter df = new DataFormatter();
        while (rowIterator.hasNext()) {
            try {
                Row row = rowIterator.next();
                if (row.getRowNum() != 0) {
                    JSONObject json = new JSONObject();
                    json.put("sitePubId", df.formatCellValue(row.getCell(6)));
                    json.put("brand", df.formatCellValue(row.getCell(1)));
                    json.put("culture", df.formatCellValue(row.getCell(3)));
                    json.put("environment", df.formatCellValue(row.getCell(2)));
                    json.put("userAgent", df.formatCellValue(row.getCell(7)));
                    json.put("username", df.formatCellValue(row.getCell(4)));
                    json.put("password", df.formatCellValue(row.getCell(5)));
                    json.put("appPoolEnv", df.formatCellValue(row.getCell(12)));
                    json.put("siteSearch", df.formatCellValue(row.getCell(8)));
                    json.put("storeLocatorZIP", df.formatCellValue(row.getCell(10)));
                    json.put("virtualAgent", df.formatCellValue(row.getCell(11)));
                    json.put("recipeSearch", df.formatCellValue(row.getCell(9)));
                    json.put("appPoolSite", df.formatCellValue(row.getCell(13)));
                    sites.put(row.getCell(0).getStringCellValue(), json);
                }
            } catch (JSONException ex) {
                Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            workbook.close();
        } catch (IOException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sites;
    }

//    public void createData(SpiderConfig config, File reportPath) {
//        int i = 0;
//        Row row = sheet.createRow(0);
//        createHeader(row);
//        while (i < config.links.size()) {
//            WebURL url = config.links.get(i++);
//            row = sheet.createRow(i);
//            Cell cell1 = row.createCell(0);
//            Cell cell2 = row.createCell(1);
//            Cell cell3 = row.createCell(2);
//            Cell cell4 = row.createCell(3);
//            Cell cell5 = row.createCell(4);
//            Cell cell6 = row.createCell(5);
//            Cell cell7 = row.createCell(6);
//            Cell cell8 = row.createCell(7);
//            Cell cell9 = row.createCell(8);
//            Iterator<Cell> cellIterator = row.cellIterator();
//            while (cellIterator.hasNext()) {
//                Cell cell = cellIterator.next();
//                cell.setCellStyle(cellWrapText()); //Setting wrap text with left align
//            }
//
//            cell1.setCellValue(url.getUrl());
//        //    cell1.setHyperlink(setHyperLink(url.getUrl()));
//        //    cell1.setCellStyle(wrapWithHyperlink());            
//            
//            cell3.setCellValue(url.getExpectedAddress());
//        //    cell3.setHyperlink(setHyperLink(url.getUrl()));
//        //    cell3.setCellStyle(wrapWithHyperlink());
//
//            cell4.setCellValue(url.getRedirectTo());
//        //    cell4.setHyperlink(setHyperLink(url.getUrl()));
//        //    cell4.setCellStyle(wrapWithHyperlink());
//           
//            cell5.setCellValue(url.getRedirectPath().toString());
//            cell6.setCellValue(url.getStatus());
//
//            if (url.getStatus().equals("Passed")) {
//                cell6.setCellStyle(wrapWithGreenBG());
//            } else if (url.getStatus().equals("Failed")) {
//                cell6.setCellStyle(wrapWithRedBG());
//            } else {
//                cell6.setCellStyle(wrapWithYellowBG());
//            }
//            cell7.setCellValue(Integer.toString(url.getStatusCode()));
//            cell8.setCellValue(url.getErrorMsg());
//            cell9.setCellValue(url.getComment());
//            
//            cell2.setCellValue(url.getUserAgent());
//        }
//
//        try {
//            output = new BufferedOutputStream(new FileOutputStream(filePath));
//            workbook.write(output);
//            output.flush();
//            output.close();
//        } catch (FileNotFoundException ex) {            
//            Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
//        } catch (IOException ex) {           
//            Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
//        }
//    }
}
