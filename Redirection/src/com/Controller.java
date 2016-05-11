/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapient.unilever;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Sachin
 */
public class Controller implements Runnable {

    ExecutorService executor;

    public static Controller control;
    private int threadValue, timeoutValue;
    private CountDownLatch latch;
    private boolean authEnable;
    private String user, pass;
    private File filePath;
    private XSSFWorkbook workbook;
    private FileInputStream file;
    private XSSFSheet sheet, agentSheet;
    public List<WebURL> allLinks;

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public File getFilePath() {
        return filePath;
    }

    public static Controller getInstance(int threadValue, int timeoutValue, boolean authEnable, String user, String pass, File filePath, CountDownLatch latch) {
        if (control == null) {
            control = new Controller(threadValue, timeoutValue, authEnable, user, pass, filePath, latch);
        }
        return control;
    }

    private Controller(int threadValue, int timeoutValue, boolean authEnable, String user, String pass, File filePath, CountDownLatch latch) {
        this.threadValue = threadValue;
        this.timeoutValue = timeoutValue * 1000;
        this.authEnable = authEnable;
        this.user = user;
        this.pass = pass;
        this.filePath = filePath;
        this.latch = latch;
        try {
            file = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet("Results");
            agentSheet = workbook.getSheet("UserAgents");
            UserAgents.getInstance(agentSheet);
            file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.WARN, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.WARN, null, ex);
        }
    }

    public synchronized void writeExcel(ArrayList<WebURL> urls) {

    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public List<WebURL> readData() {
        List<WebURL> list = new ArrayList<WebURL>();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() != 0) {//To filter column headings
                WebURL url = new WebURL();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 0) {// To match column index
                        String address = cell.getStringCellValue();
                        if (address.trim().isEmpty()) {
                            return list;
                        }

                        if (!address.startsWith("http")) {
                            address = "http://" + address;
                        }
                        url.setUrl(address);
                    }
                    if (cell.getColumnIndex() == 2) {// To match column index
                        url.setExpectedAddress(cell.getStringCellValue());
                    }
                    if (cell.getColumnIndex() == 1) {// To match column index
                        url.setUserAgent(cell.getStringCellValue());
                    }
                }
                list.add(url);
            }
        }

        return list;
    }

    @Override
    public void run() {
        SpiderConfig config = SpiderConfig.getInstance();
        config.links = readData();
        config.setConnectionRequestTimeout(timeoutValue);
        config.setConnectionTimeout(timeoutValue);
        config.setSocketTimeout(timeoutValue);
        config.setAuthenticate(authEnable);
        config.setUsername(user);
        config.setPassword(pass);
        config.setTotalSpiders(threadValue);
        executor = Executors.newFixedThreadPool(threadValue);
        config.tracker = 0;
        CountDownLatch latch1 = new CountDownLatch(threadValue);
        for (int j = 0; j < threadValue; j++) {
            Crawler c = new Crawler(latch1);
            executor.execute(c);
        }
        try {
            latch1.await();
            executor.shutdown();
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.WARN, null, ex);
        } finally {
        }
        System.out.println("Generating Output...\nPlease wait...");
        try {
            new ExcelManager().createData();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.WARN, null, ex);
        }
        sendMail();
        control = null;
        latch.countDown();
    }

    private void sendMail() {
        String host = "smtp.mail.yahoo.com";
        String port = "465";
        String mailFrom = "sachinkmr3112@yahoo.com";
        String password = "9639181220";
 
        // message info
        String mailTo = "skumar213@sapient.com,sachinkmr3112@gmail.com,sachinkmr3112@yahoomail.com";
        String subject = "Redirection Validator";
        String message = "Hi, your report has been generated. Please find attached file";
 
        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = filePath.getAbsolutePath();
        
 
        try {
            EmailSender.sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }

    public FileInputStream getFile() {
        return file;
    }

}
