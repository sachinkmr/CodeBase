/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapient.unilever;

import static com.sapient.unilever.Controller.control;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Sachin
 */
public class ExcelManager {

    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private CreationHelper createHelper;
    private BufferedOutputStream output;

    public ExcelManager() {
        FileInputStream f = null;
        try {
            f = new FileInputStream(new File(control.getFilePath().toString()));
            this.workbook = new XSSFWorkbook(f);
            this.sheet = workbook.getSheet("Results");
            createHelper = workbook.getCreationHelper();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
        } finally {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
            }
        }
    }

    public void createData() {
        int i = 0;
        Row row = sheet.createRow(0);
        createHeader(row);
        while (i < SpiderConfig.config.links.size()) {
            WebURL url = SpiderConfig.config.links.get(i++);
            row = sheet.createRow(i);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            Cell cell3 = row.createCell(2);
            Cell cell4 = row.createCell(3);
            Cell cell5 = row.createCell(4);
            Cell cell6 = row.createCell(5);
            Cell cell7 = row.createCell(6);
            Cell cell8 = row.createCell(7);
            Cell cell9 = row.createCell(8);
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.setCellStyle(cellWrapText()); //Setting wrap text with left align
            }

            cell1.setCellValue(url.getUrl());
            cell1.setHyperlink(setHyperLink(url.getUrl()));
            cell1.setCellStyle(wrapWithHyperlink());            
            
            cell3.setCellValue(url.getExpectedAddress());
            cell3.setHyperlink(setHyperLink(url.getUrl()));
            cell3.setCellStyle(wrapWithHyperlink());

            cell4.setCellValue(url.getRedirectTo());
            cell4.setHyperlink(setHyperLink(url.getUrl()));
            cell4.setCellStyle(wrapWithHyperlink());
           
            cell5.setCellValue(url.getRedirectPath().toString());
            cell6.setCellValue(url.getStatus());

            if (url.getStatus().equals("Passed")) {
                cell6.setCellStyle(wrapWithGreenBG());
            } else if (url.getStatus().equals("Failed")) {
                cell6.setCellStyle(wrapWithRedBG());
            } else {
                cell6.setCellStyle(wrapWithYellowBG());
            }
            cell7.setCellValue(Integer.toString(url.getStatusCode()));
            cell8.setCellValue(url.getErrorMsg());
            cell9.setCellValue(url.getComment());
            
            cell2.setCellValue(url.getUserAgent());
        }

        try {
            output = new BufferedOutputStream(new FileOutputStream(control.getFilePath()));
            workbook.write(output);
            output.flush();
            output.close();
//            JOptionPane.showMessageDialog(null, "Excel written successfully..", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
        }
    }

    private void createHeader(Row row) {
        String[] data = {"URL", "User-Agent", "Expected URL", "Actual Redirected URL", "Redirection Path", "Result Status", "Response Code", "Error Message", "Remarks"};
        int i = 0;
        row.createCell(0);
        row.createCell(1);
        row.createCell(2);
        row.createCell(3);
        row.createCell(4);
        row.createCell(5);
        row.createCell(6);
        row.createCell(7);
        row.createCell(8);
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            cell.setCellStyle(headerStyle()); //Setting style
            if (cell.getColumnIndex() == 0 || cell.getColumnIndex() == 1 || cell.getColumnIndex() == 2) {
                cell.setCellStyle(wrapWithYellowBG());
            }
            cell.setCellValue(data[i++]);
        }
    }

    private CellStyle headerStyle() {
        CellStyle style = workbook.createCellStyle();
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
        style.setAlignment(CellStyle.ALIGN_JUSTIFY);
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
}
