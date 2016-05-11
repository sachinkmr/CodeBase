/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo;

import com.sachin.site.Data;
import com.sachin.site.WebPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Sachin
 */
public class CreateExcel implements Runnable {

    private Data data;
    private Cell cell;
    private Sheet sheet, overViewSheet;
    private Workbook workbook;
    private int row = 0;
    private int h1 = 0, h2 = 0, title = 0, sc = 0, md = 0, mk = 0, img = 0, url = 0, can = 0, pageWithError = 0;
    private File reportFile;
    private Hyperlink hlink;
    private List<WebPage> webPages = new ArrayList<>();
    private Map<String, WebPage> lll;

    public CreateExcel(Data data, Map<String, WebPage> lll) {
        try {
            this.lll = lll;
            this.data = data;
            for (WebPage page : lll.values()) {
                webPages.add(page);
            }
            reportFile = new File(Data.outputLocation + "SEOReport.xlsx");
            
            workbook = WorkbookFactory.create(new FileInputStream(reportFile));
            CreationHelper createHelper = workbook.getCreationHelper();
            hlink = createHelper.createHyperlink(Hyperlink.LINK_URL);
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateExcelSEOFile() {
        try {
            sheet = workbook.getSheet("SEO");
            for (WebPage linkInfo1 : webPages) {
                WebPage web = linkInfo1;
                if (web.sc.code == 200) {
                    if (web.h1.isMissing) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("H1 Tags");
                            r.createCell((short) 1).setCellValue("Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
//                            r.createCell((short) 8).setCellValue(getStatus(web.h1.isMissing));
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.h1.isBlank) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("H1 Tags");
                            r.createCell((short) 1).setCellValue("Content Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
//                            r.createCell((short) 8).setCellValue(getStatus(web.h1.isBlank));
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.h1.isOverChar) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("H1 Tags");
                            r.createCell((short) 1).setCellValue("Over Character");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            r.createCell((short) 4).setCellValue(web.h1.getH1().get(0));
                            r.createCell((short) 5).setCellValue(web.h1.getH1().get(0).length());
//                            r.createCell((short) 8).setCellValue(getStatus(web.h1.isOverChar));
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.h1.isMultiple) {
                        for (String link : web.h1.getH1()) {
                            try {
                                Row r = sheet.createRow(++row);
                                r.createCell((short) 0).setCellValue("H1 Tags");
                                r.createCell((short) 1).setCellValue("Multiple");
                                r.createCell((short) 2).setCellValue(web.template);
                                r.createCell((short) 3).setCellValue(web.urlName);
                                // r.createCell((short) 4).setCellValue(web.sc.code);
                                // r.createCell((short) 5).setCellValue(web.sc.msg);
                                r.createCell((short) 4).setCellValue(web.h1.getH1().toString());
//                                r.createCell((short) 8).setCellValue(getStatus(web.h1.isMultiple));
                                cell = r.getCell((short) 3);
                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                            } catch (Exception ex) {
                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
//                    if (web.h2.isMissing) {
//                        try {
//                            Row r = sheet.createRow(++row);
//                            r.createCell((short) 0).setCellValue("H2 Tags");
//                            r.createCell((short) 1).setCellValue("Missing");
//                            r.createCell((short) 2).setCellValue(web.template);
//                            r.createCell((short) 3).setCellValue(web.urlName);
//                            // r.createCell((short) 4).setCellValue(web.sc.code);
//                            // r.createCell((short) 5).setCellValue(web.sc.msg);
//                            cell = r.getCell((short) 3);
//                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
//                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
//                        } catch (Exception ex) {
//                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    if (web.h2.isBlank) {
//                        try {
//                            Row r = sheet.createRow(++row);
//                            r.createCell((short) 0).setCellValue("H2 Tags");
//                            r.createCell((short) 1).setCellValue("Content Missing");
//                            r.createCell((short) 2).setCellValue(web.template);
//                            r.createCell((short) 3).setCellValue(web.urlName);
//                            // r.createCell((short) 4).setCellValue(web.sc.code);
//                            // r.createCell((short) 5).setCellValue(web.sc.msg);
//                            cell = r.getCell((short) 3);
//                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
//                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
//                        } catch (Exception ex) {
//                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    if (web.h2.isOverChar) {
//                        try {
//                            Row r = sheet.createRow(++row);
//                            r.createCell((short) 0).setCellValue("H2 Tags");
//                            r.createCell((short) 1).setCellValue("Over Character");
//                            r.createCell((short) 2).setCellValue(web.template);
//                            r.createCell((short) 3).setCellValue(web.urlName);
//                            // r.createCell((short) 4).setCellValue(web.sc.code);
//                            // r.createCell((short) 5).setCellValue(web.sc.msg);
//                            r.createCell((short) 4).setCellValue(web.h2.getH2().get(0));
//                            r.createCell((short) 5).setCellValue(web.h2.getH2().get(0).length());
//                            cell = r.getCell((short) 3);
//                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
//                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
//                        } catch (Exception ex) {
//                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    if (web.h2.isMultiple) {
//                        for (String link : web.h2.getH2()) {
//                            try {
//                                Row r = sheet.createRow(++row);
//                                r.createCell((short) 0).setCellValue("H2 Tags");
//                                r.createCell((short) 1).setCellValue("Multiple");
//                                r.createCell((short) 2).setCellValue(web.template);
//                                r.createCell((short) 3).setCellValue(web.urlName);
//                                // r.createCell((short) 4).setCellValue(web.sc.code);
//                                // r.createCell((short) 5).setCellValue(web.sc.msg);
//                                r.createCell((short) 4).setCellValue(link);
//                                cell = r.getCell((short) 3);
//                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
//                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
//                            } catch (Exception ex) {
//                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    }
                    if (web.pageTitle.isMissing) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("Page Title");
                            r.createCell((short) 1).setCellValue("Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.pageTitle.isBlank) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("Page Title");
                            r.createCell((short) 1).setCellValue("Content Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.pageTitle.isOverChar) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("Page Title");
                            r.createCell((short) 1).setCellValue("Over Character");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            r.createCell((short) 4).setCellValue(web.pageTitle.getTitle().get(0));
                            r.createCell((short) 5).setCellValue(web.pageTitle.getTitle().get(0).length());
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.pageTitle.isMultiple) {
                        for (String link : web.pageTitle.getTitle()) {
                            try {
                                Row r = sheet.createRow(++row);
                                r.createCell((short) 0).setCellValue("Page Title");
                                r.createCell((short) 1).setCellValue("Multiple");
                                r.createCell((short) 2).setCellValue(web.template);
                                r.createCell((short) 3).setCellValue(web.urlName);
                                // r.createCell((short) 4).setCellValue(web.sc.code);
                                // r.createCell((short) 5).setCellValue(web.sc.msg);
                                r.createCell((short) 4).setCellValue(web.pageTitle.getTitle().toString());
                                cell = r.getCell((short) 3);
                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                            } catch (Exception ex) {
                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    if (web.md.isMissing) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("Meta Description");
                            r.createCell((short) 1).setCellValue("Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.md.isBlank) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("Meta Description");
                            r.createCell((short) 1).setCellValue("Content Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.md.isOverChar) {
                        try {
                            Row r = sheet.createRow(++row);
                            r.createCell((short) 0).setCellValue("Meta Description");
                            r.createCell((short) 1).setCellValue("Over Character");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            r.createCell((short) 4).setCellValue(web.md.getDescription().get(0));
                            r.createCell((short) 5).setCellValue(web.md.getDescription().get(0).length());
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.md.isMultiple) {
                        for (String link : web.md.getDescription()) {
                            try {
                                Row r = sheet.createRow(++row);
                                r.createCell((short) 0).setCellValue("Meta Description");
                                r.createCell((short) 1).setCellValue("Multiple");
                                r.createCell((short) 2).setCellValue(web.template);
                                r.createCell((short) 3).setCellValue(web.urlName);
                                // r.createCell((short) 4).setCellValue(web.sc.code);
                                // r.createCell((short) 5).setCellValue(web.sc.msg);
                                r.createCell((short) 4).setCellValue(web.md.getDescription().toString());
                                cell = r.getCell((short) 3);
                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                            } catch (Exception ex) {
                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                    if (web.can.isMissing) {
                        try {
                            Row r = sheet.createRow(++row);

                            r.createCell((short) 0).setCellValue("Canonical Link");
                            r.createCell((short) 1).setCellValue("Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);

                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.can.isBlank) {
                        try {
                            Row r = sheet.createRow(++row);

                            r.createCell((short) 0).setCellValue("Canonical Link");
                            r.createCell((short) 1).setCellValue("Content Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);

                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.can.isOverChar) {
                        try {
                            Row r = sheet.createRow(++row);

                            r.createCell((short) 0).setCellValue("Canonical Link");
                            r.createCell((short) 1).setCellValue("Over Character");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            r.createCell((short) 4).setCellValue(web.can.getCanonical().get(0));
                            r.createCell((short) 5).setCellValue(web.can.getCanonical().get(0).length());
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.can.isMultiple) {
                        for (String link : web.can.getCanonical()) {
                            try {
                                Row r = sheet.createRow(++row);

                                r.createCell((short) 0).setCellValue("Canonical Link");
                                r.createCell((short) 1).setCellValue("Multiple");
                                r.createCell((short) 2).setCellValue(web.template);
                                r.createCell((short) 3).setCellValue(web.urlName);

                                // r.createCell((short) 4).setCellValue(web.sc.code);
                                // r.createCell((short) 5).setCellValue(web.sc.msg);
                                r.createCell((short) 4).setCellValue(web.can.getCanonical().toString());
                                cell = r.getCell((short) 3);
                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                            } catch (Exception ex) {
                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    if (web.image.hasMissingAltText) {
                        for (String missingAlt : web.image.getMissingAlts()) {
                            try {
                                Row r = sheet.createRow(++row);

                                r.createCell((short) 0).setCellValue("Image Alt Text");
                                r.createCell((short) 1).setCellValue("Missing Alt Text");
                                r.createCell((short) 2).setCellValue(web.template);
                                r.createCell((short) 3).setCellValue(web.urlName);
                                // r.createCell((short) 4).setCellValue(web.sc.code);
                                // r.createCell((short) 5).setCellValue(web.sc.msg);
                                r.createCell((short) 4).setCellValue(missingAlt);
                                cell = r.getCell((short) 3);
                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                            } catch (Exception ex) {
                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    if (web.image.hasOverText) {
                        for (String overAlt : web.image.getOverTextList().keySet()) {
                            try {
                                Row r = sheet.createRow(++row);
                                r.createCell((short) 0).setCellValue("Image Alt Text");
                                r.createCell((short) 1).setCellValue("Over Character Alt text");
                                r.createCell((short) 2).setCellValue(web.template);
                                r.createCell((short) 3).setCellValue(web.urlName);
                                // r.createCell((short) 4).setCellValue(web.sc.code);
                                // r.createCell((short) 5).setCellValue(web.sc.msg);
                                r.createCell((short) 4).setCellValue(overAlt);
                                r.createCell((short) 5).setCellValue(web.image.getOverTextList().get(overAlt));
                                cell = r.getCell((short) 3);
                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                            } catch (Exception ex) {
                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    if (web.mk.isMissing) {
                        try {
                            Row r = sheet.createRow(++row);

                            r.createCell((short) 0).setCellValue("Meta Keywords");
                            r.createCell((short) 1).setCellValue("Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);

                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.mk.isBlank) {
                        try {
                            Row r = sheet.createRow(++row);

                            r.createCell((short) 0).setCellValue("Meta Keywords");
                            r.createCell((short) 1).setCellValue("Content Missing");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);

                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.mk.isOverChar) {
                        try {
                            Row r = sheet.createRow(++row);

                            r.createCell((short) 0).setCellValue("Meta Keywords");
                            r.createCell((short) 1).setCellValue("Over Character");
                            r.createCell((short) 2).setCellValue(web.template);
                            r.createCell((short) 3).setCellValue(web.urlName);
                            // r.createCell((short) 4).setCellValue(web.sc.code);
                            // r.createCell((short) 5).setCellValue(web.sc.msg);
                            r.createCell((short) 4).setCellValue(web.mk.getKeywords().get(0));
                            r.createCell((short) 5).setCellValue(web.mk.getKeywords().get(0).length());
                            cell = r.getCell((short) 3);
                            hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                            cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                        } catch (Exception ex) {
                            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (web.mk.isMultiple) {
                        for (String link : web.mk.getKeywords()) {
                            try {
                                Row r = sheet.createRow(++row);

                                r.createCell((short) 0).setCellValue("Meta Keywords");
                                r.createCell((short) 1).setCellValue("Multiple");
                                r.createCell((short) 2).setCellValue(web.template);
                                r.createCell((short) 3).setCellValue(web.urlName);
                                // r.createCell((short) 4).setCellValue(web.sc.code);
                                // r.createCell((short) 5).setCellValue(web.sc.msg);
                                r.createCell((short) 4).setCellValue(web.mk.getKeywords().toString());
                                cell = r.getCell((short) 3);
                                hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                                cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                            } catch (Exception ex) {
                                Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    //Getting nos of eerors   
                    if (web.h1.hasError) {
                        h1++;
                    }
                    if (web.h2.hasError) {
                        h2++;
                    }
                    if (web.can.hasError) {
                        can++;
                    }
                    if (web.image.hasError) {
                        img++;
                    }
                    if (web.md.hasError) {
                        md++;
                    }
                    if (web.mk.hasError) {
                        mk++;
                    }
                    if (web.pageTitle.hasError) {
                        title++;
                    }
                    if (web.sc.hasError) {
                        sc++;
                    }

                }

                if (web.url.hasError) {
                    url++;
                }
                if (web.url.isOverChar) {
                    try {
                        Row r = sheet.createRow(++row);
                        r.createCell((short) 0).setCellValue("URL");
                        r.createCell((short) 1).setCellValue("Over Character");
                        r.createCell((short) 2).setCellValue(web.template);
                        r.createCell((short) 3).setCellValue(web.urlName);
                        r.createCell((short) 5).setCellValue(web.url.url.length());
                        // r.createCell((short) 4).setCellValue(web.sc.code);
                        // r.createCell((short) 5).setCellValue(web.sc.msg);
                        r.createCell((short) 4).setCellValue(web.url.url);
                        cell = r.getCell((short) 3);
                        hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                        cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                    } catch (Exception ex) {
                        Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (300 <= web.sc.code && web.sc.code < 400) {
                    try {
                        Row r = sheet.createRow(++row);

                        r.createCell((short) 0).setCellValue("Status Code");
                        r.createCell((short) 1).setCellValue("3XX");
                        r.createCell((short) 2).setCellValue(web.template);
                        r.createCell((short) 3).setCellValue(web.urlName);
                        // r.createCell((short) 4).setCellValue(web.sc.code);
                        // r.createCell((short) 5).setCellValue(web.sc.msg);

                        cell = r.getCell((short) 3);
                        hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                        cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                    } catch (Exception ex) {
                        Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (400 <= web.sc.code && web.sc.code < 500) {
                    try {
                        Row r = sheet.createRow(++row);

                        r.createCell((short) 0).setCellValue("Status Code");
                        r.createCell((short) 1).setCellValue("4XX");
                        r.createCell((short) 2).setCellValue(web.template);
                        r.createCell((short) 3).setCellValue(web.urlName);
                        // r.createCell((short) 4).setCellValue(web.sc.code);
                        // r.createCell((short) 5).setCellValue(web.sc.msg);

                        cell = r.getCell((short) 3);
                        hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                        cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                    } catch (Exception ex) {
                        Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (500 <= web.sc.code && web.sc.code < 600) {
                    try {
                        Row r = sheet.createRow(++row);

                        r.createCell((short) 0).setCellValue("Status Code");
                        r.createCell((short) 1).setCellValue("5XX");
                        r.createCell((short) 2).setCellValue(web.template);
                        r.createCell((short) 3).setCellValue(web.urlName);
                        // r.createCell((short) 4).setCellValue(web.sc.code);
                        // r.createCell((short) 5).setCellValue(web.sc.msg);

                        cell = r.getCell((short) 3);
                        hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                        cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                    } catch (Exception ex) {
                        Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (600 <= web.sc.code) {
                    try {
                        Row r = sheet.createRow(++row);

                        r.createCell((short) 0).setCellValue("Status Code");
                        r.createCell((short) 1).setCellValue("6XX");
                        r.createCell((short) 2).setCellValue(web.template);
                        r.createCell((short) 3).setCellValue(web.urlName);
                        // r.createCell((short) 4).setCellValue(web.sc.code);
                        // r.createCell((short) 5).setCellValue(web.sc.msg);

                        cell = r.getCell((short) 3);
                        hlink.setAddress(data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html");
                        cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                    } catch (Exception ex) {
                        Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (web.hasError()) {
                    pageWithError++;
                }
            }

            Iterator itr = data.duplicateH1.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.duplicateH1.get(key);
                String values[] = value.split(",");
                if (values.length > 1) {
                    WebPage web = lll.get(values[0].trim());
                    Row r = sheet.createRow(++row);
                    r.createCell((short) 0).setCellValue("H1 Tags");
                    r.createCell((short) 1).setCellValue("Duplicate");
//                    r.createCell((short) 2).setCellValue("");
                    r.createCell((short) 3).setCellValue(value);
                    // r.createCell((short) 4).setCellValue(web.sc.code);
                    // r.createCell((short) 5).setCellValue(web.sc.msg);
                    r.createCell((short) 4).setCellValue(key);
                    cell = r.getCell((short) 3);
                    hlink.setAddress(data.outputAddress + "pages/" + web.urlName.trim().hashCode() + ".html");
                    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                }
            }
//            itr = data.duplicateH2.keySet().iterator();
//            while (itr.hasNext()) {
//                String key = (String) itr.next();
//                String value = (String) data.duplicateH2.get(key);
//                String values[] = value.split(",");
            //           if (values.length > 1) {
//                for (String link : values) {
//                   WebPage web = lll.get(link.trim());
//                    Row r = sheet.createRow(++row);
//
//                    r.createCell((short) 0).setCellValue("H2 Tags");
//                    r.createCell((short) 1).setCellValue("Duplicate");
//                    r.createCell((short) 2).setCellValue(web.template);
//                    r.createCell((short) 3).setCellValue(web.urlName);
//                    // r.createCell((short) 4).setCellValue(web.sc.code);
//                    // r.createCell((short) 5).setCellValue(web.sc.msg);
//                    r.createCell((short) 4).setCellValue(link);
//                    cell = r.getCell((short) 3);
//                    hlink.setAddress(data.outputAddress + "pages/" + web.urlName.trim().hashCode() + ".html");
//                    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
//                }
            //           }
//            }
            itr = data.duplicateTitle.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.duplicateTitle.get(key);
                String values[] = value.split(",");
                if (values.length > 1) {
                    WebPage web = lll.get(values[0].trim());
                    Row r = sheet.createRow(++row);
                    r.createCell((short) 0).setCellValue("Page Title");
                    r.createCell((short) 1).setCellValue("Duplicate");
//                    r.createCell((short) 2).setCellValue("");
                    r.createCell((short) 3).setCellValue(value);
                // r.createCell((short) 4).setCellValue(web.sc.code);
                    // r.createCell((short) 5).setCellValue(web.sc.msg);
                    r.createCell((short) 4).setCellValue(key);
                    cell = r.getCell((short) 3);
                    hlink.setAddress(data.outputAddress + "pages/" + web.urlName.trim().hashCode() + ".html");
                    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                }
            }
            itr = data.duplicateDescription.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.duplicateDescription.get(key);
                String values[] = value.split(",");
                if (values.length > 1) {
                    WebPage web = lll.get(values[0].trim());
                    Row r = sheet.createRow(++row);
                    r.createCell((short) 0).setCellValue("Meta Description");
                    r.createCell((short) 1).setCellValue("Duplicate");
//                    r.createCell((short) 2).setCellValue("");
                    r.createCell((short) 3).setCellValue(value);
                // r.createCell((short) 4).setCellValue(web.sc.code);
                    // r.createCell((short) 5).setCellValue(web.sc.msg);
                    cell = r.getCell((short) 3);
                    hlink.setAddress(data.outputAddress + "pages/" + web.urlName.trim().hashCode() + ".html");
                    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
                    r.createCell((short) 4).setCellValue(key);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Modifying Overview Sheet
        modifyOverview();

        //writing excel to disk
        FileOutputStream output_file;
        try {
            output_file = new FileOutputStream(new File(data.outputLocation, reportFile.getName()));
            workbook.write(output_file);
            output_file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CreateExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modifyOverview() {
        int rowNo = 1;
        overViewSheet = workbook.getSheetAt(0);
        Row r = overViewSheet.getRow(rowNo);

        overViewSheet.createRow(rowNo).createCell((short) 1).setCellValue("Brand Site:");
        overViewSheet.getRow(rowNo).createCell((short) 2).setCellValue(data.site);

        if (data.releaseVer.length() > 0) {
            overViewSheet.createRow(++rowNo).createCell((short) 1).setCellValue("Build Release Version:");
            overViewSheet.getRow(rowNo).createCell((short) 2).setCellValue(data.releaseVer);
        }
        overViewSheet.createRow(++rowNo).createCell((short) 1).setCellValue("Report Execution Date:");
        overViewSheet.getRow(rowNo).createCell((short) 2).setCellValue(data.date);
        overViewSheet.createRow(++rowNo).createCell((short) 1).setCellValue("Report Execution Time:");
        overViewSheet.getRow(rowNo).createCell((short) 2).setCellValue(data.time);
        overViewSheet.createRow(++rowNo).createCell((short) 1).setCellValue("Responsive:");
        overViewSheet.getRow(rowNo).createCell((short) 2).setCellValue(data.isResponsive ? "Yes" : "No");
        overViewSheet.createRow(++rowNo).createCell((short) 1).setCellValue("Release Version:");
        overViewSheet.getRow(rowNo).createCell((short) 2).setCellValue(data.releaseVer);
        overViewSheet.createRow(++rowNo).createCell((short) 1).setCellValue("Total Pages Crawled:");
        overViewSheet.getRow(rowNo).createCell((short) 2).setCellValue(webPages.size());

        cell = r.getCell((short) 2);
        hlink.setAddress(data.outputAddress + "index.html");
        cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hlink);
    }

    @Override
    public void run() {
//        updateExcelSEOFile();
    }

    private String getStatus(boolean missing) {
        return missing ? "Failed" : "Passed";
    }
}
