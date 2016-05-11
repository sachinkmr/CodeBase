/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.seo;

import com.sachin.site.Data;
import com.sachin.site.WebPage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Sachin
 */
public class CreateMainOutput {

    List<WebPage> webPages = new ArrayList<>();
    Data data;
    int duplicateH1Count = 0, duplicateSCCount = 0, duplicateH2Count = 0, duplicateTitleCount = 0, duplicateCanCount = 0, duplicateDescCount = 0;
    int pageErrorAll = 0, pageNoErrorAll = 0;
    int H1Error = 0, H2Error, titleErrors = 0, canErrors = 0, scErrors = 0, URLErrors = 0, altErrors = 0, DescErrors = 0, mkErrors = 0;
    String clss = "";
    BufferedWriter output;
    String time[];

    public CreateMainOutput(Data data, Map<String, WebPage> lll) {
        this.data = data;
        for (WebPage page : lll.values()) {
            webPages.add(page);
        }
        time = data.outputAddress.substring(0, data.outputAddress.length() - 1).split("/");
    }

    public String prepareData() throws IOException {
        int i = 1;
        Document docs = Jsoup.connect(data.address + "/MainOutput").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
        int a = 1, b = 1, c = 1, d = 1, e = 1, f = 1, g = 1, h = 1, j = 1, z = 1;;
        for (WebPage linkInfo1 : webPages) {
            if (linkInfo1.link.getStatusCode() == 200) {
                if (linkInfo1.link.getTemplateName().toLowerCase().contains("Article".toLowerCase())) {
                    if (linkInfo1.link.getTemplateName().toLowerCase().contains("ArticleCategory".toLowerCase())) {
                        docs.getElementById("Article-Category").append("<tr><td>" + a++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    } else if (linkInfo1.link.getTemplateName().toLowerCase().contains("ArticleLanding".toLowerCase())) {
                        docs.getElementById("Article-Landing").append("<tr><td>" + b++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    } else if (linkInfo1.link.getTemplateName().toLowerCase().contains("ArticleDetail".toLowerCase())) {
                        docs.getElementById("Article-Detail").append("<tr><td>" + c++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    } else {
                        docs.getElementById("Other").append("<tr><td>" + d++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    }

                } else if (linkInfo1.link.getTemplateName().toLowerCase().contains("Product".toLowerCase())) {
                    if (linkInfo1.link.getTemplateName().toLowerCase().contains("ProductLanding".toLowerCase())) {
                        docs.getElementById("Product-Landing").append("<tr><td>" + e++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    } else if (linkInfo1.link.getTemplateName().toLowerCase().contains("ProductCategory".toLowerCase())) {
                        docs.getElementById("Product-Category").append("<tr><td>" + f++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    } else if (linkInfo1.link.getTemplateName().toLowerCase().contains("ProductDetail".toLowerCase())) {
                        docs.getElementById("Product-Detail").append("<tr><td>" + g++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    }

                } else if (linkInfo1.link.getTemplateName().toLowerCase().contains("Recipe".toLowerCase())) {
                    if (linkInfo1.link.getTemplateName().toLowerCase().contains("RecipeLanding".toLowerCase())) {
                        docs.getElementById("Recipe-Landing").append("<tr><td>" + h++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    } else if (linkInfo1.link.getTemplateName().toLowerCase().contains("RecipeDetail".toLowerCase())) {
                        docs.getElementById("Recipe-Detail").append("<tr><td>" + i++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                    }

                } else {
                    docs.getElementById("Other-Templates").append("<tr><td>" + j++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                }
                if (linkInfo1.hasError()) {
                    docs.getElementById("errorPages").append("<tr><td>" + ++pageErrorAll + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");

                } else {
                    docs.getElementById("all-success").append("<tr><td>" + ++pageNoErrorAll + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
                }
                docs.getElementById("all").append("<tr><td>" + z++ + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.link.getStatusCode() + "</td><td>" + linkInfo1.link.getStatusMsg() + "</td><td class='check-error'>" + (linkInfo1.hasError() ? "Yes" : "No") + "</td></tr>");
            }
        }
        docs.getElementsMatchingOwnText("Pages With Errors").first().text("Page With Errors (" + pageErrorAll + ")");
        docs.getElementsMatchingOwnText("Pages Without Errors").first().text("Page Without Errors (" + (webPages.size() - (pageErrorAll)) + ")");
        docs.getElementsMatchingOwnText("All Pages").first().text("All Pages (" + webPages.size() + ")");

        docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>H1 Errors</em><span>" + (duplicateH1Count + H1Error) + "</span> </li> <li><em>H2 Errors</em><span>" + (duplicateH2Count + H2Error) + "</span> </li><li><em>Title Errors</em><span>" + (duplicateTitleCount + titleErrors) + "</span> </li><li><em>Meta Description Errors</em><span>" + (duplicateDescCount + DescErrors) + "</span> </li><li><em>Meta Keyword Errors</em><span>" + mkErrors + "</span> </li><li><em>Canonical URL Errors</em><span>" + (duplicateCanCount + canErrors) + "</span> </li><li><em>URL Errors</em><span>" + URLErrors + "</span></li><li><em>Links Error</em><span>" + scErrors + "</span> </li><li><em>Image Alt Text Error</em><span>" + altErrors + "</span></li> </ul>");
        docs.getElementById("site-info").append(getSiteInfo());
        if (pageErrorAll > 0) {
            clss = "danger";
        } else {
            clss = "success";
        }
        docs.getElementById("site-info").append("<tr id='site-seo-status' class=" + clss + "><td>Pages with Errors:</td><td id='site-status-data'>" + pageErrorAll + "</td></tr>");
        return docs.toString();
    }

    public synchronized void createHTML() {
        try {
            createH1File();
            createH2File();
            createTitleFile();
            createCanonicalFile();
            createStatusCodeFile();
            createURLFile();
            createAltTextFile();
            createDescriptionFile();
            createKeywordsFile();
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/index.html")));
            output.write(prepareData());
            output.flush();
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createJSON() throws IOException {
        BufferedWriter h1 = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/json/h1.json")));
        BufferedWriter h2 = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/json/h2.json")));
        BufferedWriter title = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/json/title.json")));
        BufferedWriter CanonicalUrl = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/json/canonical.json")));
        BufferedWriter description = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/json/description.json")));
        BufferedWriter statusCodeJson = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/json/statusCode.json")));
        BufferedWriter urlInfoJson = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/json/urlInfo.json")));

        JSONObject jsonObject;
        jsonObject = new JSONObject(data.duplicateH1);
        h1.write(jsonObject.toJSONString());
        h1.flush();
        h1.close();

        jsonObject = new JSONObject(data.duplicateH2);
        h2.write(jsonObject.toJSONString());
        h2.flush();
        h2.close();

        jsonObject = new JSONObject(data.duplicateTitle);
        title.write(jsonObject.toJSONString());
        title.flush();
        title.close();

        jsonObject = new JSONObject(data.canonicalURLs);
        CanonicalUrl.write(jsonObject.toJSONString());
        CanonicalUrl.flush();
        CanonicalUrl.close();

        jsonObject = new JSONObject(data.duplicateDescription);
        description.write(jsonObject.toJSONString());
        description.flush();
        description.close();

        jsonObject = new JSONObject(data.urlInfo);
        urlInfoJson.write(jsonObject.toJSONString());
        urlInfoJson.flush();
        urlInfoJson.close();

        jsonObject = new JSONObject(data.statusCode);
        statusCodeJson.write(jsonObject.toJSONString());
        statusCodeJson.flush();
        statusCodeJson.close();
    }

    public void createDescriptionFile() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/description.html")));
            Document docs = Jsoup.connect(data.address + "/MetaDescription").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int missingC = 0, multiC = 0, blankC = 0, duplicateC = 0, overcharC = 0, pageError = 0, pageNoError = 0;
            Iterator itr = data.duplicateDescription.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.duplicateDescription.get(key);
                String values[] = value.split(",");
                if (key.length() > data.descriptionCharLimit) {
                    Element overCharH1 = docs.getElementById("overCharH1");
                    overCharH1.append("<tr><td>" + ++overcharC + "</td><td>" + key + "</td><td>" + key.length() + "</td><td>" + getURLLi(values) + "</td></tr>");
                }

                if (values.length > 1) {
                    Element DuplicateH1 = docs.getElementById("DuplicateH1");
                    DuplicateH1.append("<tr><td>" + ++duplicateC + "</td><td>" + key + "</td><td>" + getURLLi(values) + "</td></tr>");
                }
                if (key.trim().isEmpty()) {
                    Element blankH1 = docs.getElementById("blankH1");
                    for (String val : values) {
                        blankH1.append("<tr><td>" + ++blankC + "</td><td><a href='pages/" + val.trim().hashCode() + ".html'>" + val + "</a></td></tr>");
                    }
                }
            }
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.link.getStatusCode() == 200) {
                    if (linkInfo1.md.isMissing) {
                        Element missingH1 = docs.getElementById("missingH1");
                        missingH1.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    if (linkInfo1.md.isMultiple) {
                        Element multipleH1 = docs.getElementById("multipleH1");
                        multipleH1.append("<tr><td>" + ++multiC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + getStringLi(linkInfo1.md.getDescription().toArray()) + "</td></tr>");
                    }
                    if (linkInfo1.md.hasError()) {
                        docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                        DescErrors++;
                    } else {
                        docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                }
            }
            duplicateDescCount = duplicateC;
            docs.getElementsMatchingOwnText("Duplicate").first().text("Duplicate (" + duplicateC + ")");
            docs.getElementsMatchingOwnText("OverCharacter").first().text("OverCharacter (" + overcharC + ")");
            docs.getElementsMatchingOwnText("Without Text").first().text("Without Text (" + blankC + ")");
            docs.getElementsMatchingOwnText("Multiple Description").first().text("Multiple Description Tag (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing Description").first().text("Missing Description Tag (" + missingC + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With Error (" + DescErrors + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without Error (" + (webPages.size() - (DescErrors)) + ")");

            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Duplicate Description</em><span>" + duplicateC + "</span> </li> <li><em>Missing Description</em><span>" + missingC + "</span> </li> <li><em>Multiple Description</em><span>" + multiC + "</span> </li> <li><em>Blank Description</em><span>" + blankC + "</span> </li> <li><em>Over Char Description</em><span>" + overcharC + "</span> </li><li><em>Page With Error</em><span>" + (DescErrors) + "</span> </li> <li><em>Page Without Error</em><span>" + (webPages.size() - (DescErrors)) + "</span> </li></ul>");
            output.write(docs.toString());

        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {

                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createKeywordsFile() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/keywords.html")));
            Document docs = Jsoup.connect(data.address + "/Keywords").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int missingC = 0, multiC = 0, blankC = 0, pageError = 0, pageNoError = 0;
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.link.getStatusCode() == 200) {
                    if (linkInfo1.mk.isMissing) {
                        Element missingH1 = docs.getElementById("missingH1");
                        missingH1.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    if (linkInfo1.mk.isMultiple) {
                        Element multipleH1 = docs.getElementById("multipleH1");
                        multipleH1.append("<tr><td>" + ++multiC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + getStringLi(linkInfo1.mk.getKeywords().toArray()) + "</td></tr>");
                    }
                    if (linkInfo1.mk.isBlank) {
                        Element blankH1 = docs.getElementById("blankH1");
                        blankH1.append("<tr><td>" + ++blankC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + getStringLi(linkInfo1.mk.getKeywords().toArray()) + "</td></tr>");
                    }
                    if (linkInfo1.mk.hasError()) {
                        docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                        mkErrors++;
                    } else {
                        docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                }
            }
            docs.getElementsMatchingOwnText("Multiple Keyword Tag").first().text("Multiple Keyword Tag (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing Keyword Tag").first().text("Missing Keyword Tag (" + missingC + ")");
            docs.getElementsMatchingOwnText("Without Text").first().text("Without Text (" + blankC + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With  Error (" + mkErrors + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without  Error (" + (webPages.size() - (mkErrors)) + ")");

            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Missing </em><span>" + missingC + "</span> </li> <li><em>Without Text </em><span>" + blankC + "</span> </li> <li><em>Multiple</em><span>" + multiC + "</span> </li> <li><em>Page With Error</em><span>" + (mkErrors) + "</span> </li> <li><em>Page Without Error</em><span>" + (webPages.size() - (mkErrors)) + "</span> </li></ul>");
            output.write(docs.toString());

        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {

                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createURLFile() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/url.html")));
            int i = 1;
            Document docs = Jsoup.connect(data.address + "/URL").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int overcharC = 0, pageError = 0, pageNoError = 0;
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.url.isOverCharURL()) {
                    Element missingH1 = docs.getElementById("overCharH1");
                    missingH1.append("<tr><td>" + ++overcharC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.urlName.trim().length() + "</td></tr>");

                }
                if (linkInfo1.url.hasError()) {
                    docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    URLErrors++;
                } else {
                    docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                }
            }
            docs.getElementsMatchingOwnText("OverCharacter").first().text("OverCharacter (" + overcharC + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With Error (" + URLErrors + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without Error (" + (webPages.size() - (URLErrors)) + ")");
            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Over Char URL</em><span>" + overcharC + "</span> </li><li><em>Page With Error</em><span>" + (URLErrors) + "</span> </li> <li><em>Page Without Error</em><span>" + (webPages.size() - (URLErrors)) + "</span> </li></ul>");
            output.write(docs.toString());
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {

                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createStatusCodeFile() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/statusCode.html")));
            Document docs = Jsoup.connect(data.address + "/Status").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int missingC = 0, multiC = 0, blankC = 0, duplicateC = 0, overcharC = 0, pageError = 0, pageNoError = 0, redirC = 0;
            for (WebPage linkInfo1 : webPages) {
                if (200 <= (linkInfo1.sc.getStatusCode()) && (linkInfo1.sc.getStatusCode()) < 300) {
                    Element DuplicateH1 = docs.getElementById("DuplicateH1");
                    DuplicateH1.append("<tr><td>" + ++duplicateC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                } else if (300 <= (linkInfo1.sc.getStatusCode()) && (linkInfo1.sc.getStatusCode()) < 400) {
                    Element redir = docs.getElementById("redirections");
                    redir.append("<tr><td>" + ++redirC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                } else if (400 <= (linkInfo1.sc.getStatusCode()) && (linkInfo1.sc.getStatusCode()) < 500) {
                    Element overCharH1 = docs.getElementById("overCharH1");
                    overCharH1.append("<tr><td>" + ++overcharC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                } else if (500 <= (linkInfo1.sc.getStatusCode()) && (linkInfo1.sc.getStatusCode()) < 600 && (linkInfo1.sc.getStatusCode()) != 598) {
                    Element blankH1 = docs.getElementById("blankH1");
                    blankH1.append("<tr><td>" + ++blankC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                } else if (598 == (linkInfo1.sc.getStatusCode())) {
                    Element multipleH1 = docs.getElementById("multipleH1");
                    multipleH1.append("<tr><td>" + ++multiC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                } else {
                    Element missingH1 = docs.getElementById("missingH1");
                    missingH1.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                }

                if (linkInfo1.sc.hasError()) {
                    docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                    scErrors++;
                } else {
                    docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + linkInfo1.sc.getStatusMsg() + "</td><td>" + linkInfo1.sc.getStatusCode() + "</td></tr>");
                }
            }

            duplicateSCCount = duplicateC;
            docs.getElementsMatchingOwnText("Duplicate").first().text("2xx (" + duplicateC + ")");
            docs.getElementsMatchingOwnText("3xx").first().text("3xx (" + redirC + ")");
            docs.getElementsMatchingOwnText("Over Character").first().text("4xx (" + overcharC + ")");
            docs.getElementsMatchingOwnText("Without Text").first().text("5xx (" + blankC + ")");
            docs.getElementsMatchingOwnText("Multiple H1").first().text("TimeOut (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing H1").first().text("Others (" + missingC + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With Error (" + scErrors + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without Error (" + (webPages.size() - (scErrors)) + ")");

            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>2xx</em><span>" + duplicateC + "</span> </li> <li><em>3xx </em><span>" + redirC + "</span> </li> <li><em>4xx </em><span>" + overcharC + "</span> </li> <li><em>5xx</em><span>" + blankC + "</span> </li> <li><em>TimeOut</em><span>" + multiC + "</span> </li> <li><em>Others</em><span>" + missingC + "</span> </li><li><em>Page With Error</em><span>" + (scErrors) + "</span> </li> <li><em>Page WithoutError</em><span>" + (webPages.size() - (scErrors)) + "</span> </li></ul>");
            output.write(docs.toString());
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {

                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createH1File() {
        try {
            int missingC = 0, multiC = 0, blankC = 0, duplicateC = 0, overcharC = 0, pageError = 0, pageNoError = 0;
            File file = new File(data.outputLocation);
            file.mkdirs();
            output = new BufferedWriter(new FileWriter(file + "/H1.html"));
            Document docs = Jsoup.connect(data.address + "/H1").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();

            Iterator itr = data.duplicateH1.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.duplicateH1.get(key);
                String values[] = value.split(",");
                if (key.length() > data.h1CharLimit) {
                    Element overCharH1 = docs.getElementById("overCharH1");
                    overCharH1.append("<tr><td>" + ++overcharC + "</td><td>" + key + "</td><td>" + key.length() + "</td><td>" + getURLLi(values) + "</td></tr>");
                }

                if (values.length > 1) {
                    Element DuplicateH1 = docs.getElementById("DuplicateH1");
                    DuplicateH1.append("<tr><td>" + ++duplicateC + "</td><td>" + key + "</td><td>" + getURLLi(values) + "</td></tr>");
                }
                if (key.trim().isEmpty()) {
                    Element blankH1 = docs.getElementById("blankH1");
                    for (String val : values) {
                        blankH1.append("<tr><td>" + ++blankC + "</td><td><a href='pages/" + val.trim().hashCode() + ".html'>" + val + "</a></td></tr>");
                    }
                }
            }
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.link.getStatusCode() == 200) {
                    if (linkInfo1.h1.isMissing) {
                        Element missingH1 = docs.getElementById("missingH1");
                        missingH1.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    if (linkInfo1.h1.isMultiple) {
                        Element multipleH1 = docs.getElementById("multipleH1");
                        multipleH1.append("<tr><td>" + ++multiC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + getStringLi(linkInfo1.h1.getH1().toArray()) + "</td></tr>");
                    }
                    duplicateH1Count = duplicateC;

                    if (linkInfo1.h1.hasError()) {
                        docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                        H1Error++;
                    } else {
                        docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                }
            }
            docs.getElementsMatchingOwnText("Duplicate").first().text("Duplicate (" + duplicateC + ")");
            docs.getElementsMatchingOwnText("OverCharacter").first().text("OverCharacter (" + overcharC + ")");
            docs.getElementsMatchingOwnText("Without Text").first().text("Without Text (" + blankC + ")");
            docs.getElementsMatchingOwnText("Multiple H1").first().text("Multiple H1 (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing H1").first().text("Missing H1 (" + missingC + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With H1 Error (" + H1Error + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without H1 Error (" + (webPages.size() - (H1Error)) + ")");
            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Duplicate H1</em><span>" + duplicateC + "</span> </li> <li><em>Missing H1</em><span>" + missingC + "</span> </li> <li><em>Multiple H1</em><span>" + multiC + "</span> </li> <li><em>Blank H1</em><span>" + blankC + "</span> </li> <li><em>Over Char H1</em><span>" + overcharC + "</span> </li><li><em>Page With H1 Error</em><span>" + (H1Error) + "</span> </li> <li><em>Page Without H1 Error</em><span>" + (webPages.size() - (H1Error)) + "</span> </li></ul>");
            output.write(docs.toString());
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createH2File() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/H2.html")));
            Document docs = Jsoup.connect(data.address + "/H2").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int missingC = 0, multiC = 0, blankC = 0, duplicateC = 0, overcharC = 0, pageError = 0, pageNoError = 0;
            Iterator itr = data.duplicateH2.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.duplicateH2.get(key);
                String values[] = value.split(",");
                if (key.length() > data.h2CharLimit) {
                    Element overCharH2 = docs.getElementById("overCharH2");
                    overCharH2.append("<tr><td>" + ++overcharC + "</td><td>" + key + "</td><td>" + key.length() + "</td><td>" + getURLLi(values) + "</td></tr>");
                }

                if (values.length > 1) {
                    Element DuplicateH2 = docs.getElementById("DuplicateH2");
                    DuplicateH2.append("<tr><td>" + ++duplicateC + "</td><td>" + key + "</td><td>" + getURLLi(values) + "</td></tr>");
                }
                if (key.equals("H2 without text on Page") || key.trim().isEmpty()) {
                    Element blankH2 = docs.getElementById("blankH2");
                    for (String val : values) {
                        blankH2.append("<tr><td>" + ++blankC + "</td><td><a href='pages/" + val.trim().hashCode() + ".html'>" + val + "</a></td></tr>");
                    }
                }
            }
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.link.getStatusCode() == 200) {
                    if (linkInfo1.h2.isMissing) {
                        Element missingH2 = docs.getElementById("missingH2");
                        missingH2.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    if (linkInfo1.h2.isMultiple) {
                        Element multipleH2 = docs.getElementById("multipleH2");
                        multipleH2.append("<tr><td>" + ++multiC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + getStringLi(linkInfo1.h2.getH2().toArray()) + "</td></tr>");
                    }
                    if (linkInfo1.h2.hasError()) {
                        docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                        ++H2Error;
                    } else {
                        docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    duplicateH2Count = duplicateC;
                }
            }

            docs.getElementsMatchingOwnText("Duplicate").first().text("Duplicate (" + duplicateC + ")");
            docs.getElementsMatchingOwnText("OverCharacter").first().text("OverCharacter (" + overcharC + ")");
            docs.getElementsMatchingOwnText("Without Text").first().text("Without Text (" + blankC + ")");
            docs.getElementsMatchingOwnText("Multiple H2").first().text("Multiple H2 (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing H2").first().text("Missing H2 (" + missingC + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without H2 Error (" + (webPages.size() - H2Error) + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With H2 Error (" + H2Error + ")");
            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Duplicate H2</em><span>" + duplicateC + "</span> </li> <li><em>Missing H2</em><span>" + missingC + "</span> </li> <li><em>Multiple H2</em><span>" + multiC + "</span> </li> <li><em>Blank H2</em><span>" + blankC + "</span> </li> <li><em>Over Char H2</em><span>" + overcharC + "</span> </li><li><em>Page With H2 Error</em><span>" + (H2Error) + "</span> </li> <li><em>Page Without H2 Error</em><span>" + (webPages.size() - (H2Error)) + "</span> </li></ul>");
            output.write(docs.toString());
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createTitleFile() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/title.html")));
            Document docs = Jsoup.connect(data.address + "/Title").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int missingC = 0, multiC = 0, blankC = 0, duplicateC = 0, overcharC = 0, pageError = 0, pageNoError = 0;
            Iterator itr = data.duplicateTitle.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.duplicateTitle.get(key);
                String values[] = value.split(",");
                if (key.length() > data.titleCharLimit) {
                    Element overCharTitle = docs.getElementById("overCharTitle");
                    overCharTitle.append("<tr><td>" + ++overcharC + "</td><td>" + key + "</td><td>" + key.length() + "</td><td>" + getURLLi(values) + "</td></tr>");
                }

                if (values.length > 1) {
                    Element DuplicateTitle = docs.getElementById("DuplicateTitle");
                    DuplicateTitle.append("<tr><td>" + ++duplicateC + "</td><td>" + key + "</td><td>" + getURLLi(values) + "</td></tr>");
                }
                if (key.equals("Title without text on Page") || key.trim().isEmpty()) {
                    Element blankTitle = docs.getElementById("blankTitle");
                    for (String val : values) {
                        blankTitle.append("<tr><td>" + ++blankC + "</td><td><a href='pages/" + val.trim().hashCode() + ".html'>" + val + "</a></td></tr>");
                    }
                }
            }
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.link.getStatusCode() == 200) {
                    if (linkInfo1.pageTitle.isMissing) {
                        Element missingTitle = docs.getElementById("missingTitle");
                        missingTitle.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    if (linkInfo1.pageTitle.isMultiple) {
                        Element multipleTitle = docs.getElementById("multipleTitle");
                        multipleTitle.append("<tr><td>" + ++multiC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + getStringLi(linkInfo1.pageTitle.getTitle().toArray()) + "</td></tr>");
                    }
                    if (linkInfo1.pageTitle.hasError()) {
                        docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                        ++titleErrors;
                    } else {
                        docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    duplicateTitleCount = duplicateC;
                }
            }
            docs.getElementsMatchingOwnText("Duplicate").first().text("Duplicate (" + duplicateC + ")");
            docs.getElementsMatchingOwnText("OverCharacter").first().text("OverCharacter (" + overcharC + ")");
            docs.getElementsMatchingOwnText("Without Text").first().text("Without Text (" + blankC + ")");
            docs.getElementsMatchingOwnText("Multiple Title").first().text("Multiple Title (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing Title").first().text("Missing Title (" + missingC + ")");
            docs.getElementsMatchingOwnText("Pages with Error").first().text("Pages with Error (" + titleErrors + ")");
            docs.getElementsMatchingOwnText("Pages without Error").first().text("Pages without Error (" + (webPages.size() - (titleErrors)) + ")");
            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Duplicate Title</em><span>" + duplicateC + "</span> </li> <li><em>Missing Title</em><span>" + missingC + "</span> </li> <li><em>Multiple Title</em><span>" + multiC + "</span> </li> <li><em>Blank Title</em><span>" + blankC + "</span> </li> <li><em>Over Char Title</em><span>" + overcharC + "</span> </li><li><em>Page With Title Error</em><span>" + (titleErrors) + "</span> </li> <li><em>Page Without Title Error</em><span>" + (webPages.size() - (titleErrors)) + "</span> </li></ul>");
            output.write(docs.toString());
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {

                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createCanonicalFile() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/canonical.html")));
            Document docs = Jsoup.connect(data.address + "/Canonical").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int missingC = 0, multiC = 0, blankC = 0, duplicateC = 0, overcharC = 0, pageError = 0, pageNoError = 0;
            Iterator itr = data.canonicalURLs.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String value = (String) data.canonicalURLs.get(key);
                String values[] = value.split(",");
                if (key.length() > data.canonicalCharLimit) {
                    Element overCharH1 = docs.getElementById("overCharH1");
                    overCharH1.append("<tr><td>" + ++overcharC + "</td><td>" + key + "</td><td>" + key.length() + "</td><td>" + getURLLi(values) + "</td></tr>");
                }
                if (key.trim().isEmpty()) {
                    Element blankH1 = docs.getElementById("blankH1");
                    for (String val : values) {
                        blankH1.append("<tr><td>" + ++blankC + "</td><td><a href='pages/" + val.trim().hashCode() + ".html'>" + val + "</a></td></tr>");
                    }
                }
            }
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.link.getStatusCode() == 200) {
                    if (linkInfo1.can.isMissing) {
                        Element missingH1 = docs.getElementById("missingH1");
                        missingH1.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                    if (linkInfo1.can.isMultiple) {
                        Element multipleH1 = docs.getElementById("multipleH1");
                        multipleH1.append("<tr><td>" + ++multiC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + getStringLi(linkInfo1.can.getCanonical().toArray()) + "</td></tr>");
                    }

                    if (linkInfo1.can.hasError()) {
                        docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                        ++canErrors;
                    } else {
                        docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                }
            }

            docs.getElementsMatchingOwnText("OverCharacter").first().text("OverCharacter (" + overcharC + ")");
            docs.getElementsMatchingOwnText("Without Text").first().text("Without Text (" + blankC + ")");
            docs.getElementsMatchingOwnText("Multiple canonical").first().text("Multiple Canonical (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing canonical").first().text("Missing Canonical (" + missingC + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With Error (" + canErrors + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without Error (" + (webPages.size() - (canErrors)) + ")");
            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Missing Canonical</em><span>" + missingC + "</span> </li> <li><em>Multiple Canonical</em><span>" + multiC + "</span> </li> <li><em>Blank Canonical</em><span>" + blankC + "</span> </li> <li><em>Over Char Canonical</em><span>" + overcharC + "</span> </li><li><em>Page With Error</em><span>" + (canErrors) + "</span> </li> <li><em>Page Without Error</em><span>" + (webPages.size() - (canErrors)) + "</span> </li></ul>");
            output.write(docs.toString());
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createAltTextFile() {
        try {
            output = new BufferedWriter(new FileWriter(new File(data.outputLocation + "/altText.html")));
            Document docs = Jsoup.connect(data.address + "/Alt").ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            int missingC = 0, multiC = 0, pageError = 0, pageNoError = 0;
            for (WebPage linkInfo1 : webPages) {
                if (linkInfo1.link.getStatusCode() == 200) {
                    if (linkInfo1.image.hasMissingAltText) {
                        Element missingH1 = docs.getElementById("missingH1");
                        missingH1.append("<tr><td>" + ++missingC + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td>" + linkInfo1.link.getTemplateName() + "</td><td>" + getStringLi(linkInfo1.image.getMissingAlts().toArray()) + "</td></tr>");
                    }
                    if (linkInfo1.image.hasOverText) {
                        Element multipleH1 = docs.getElementById("overCharH1");
                        multipleH1.append("<tr><td rowspan=" + linkInfo1.image.getOverTextList().size() + ">" + ++multiC + "</td><td rowspan=" + linkInfo1.image.getOverTextList().size() + "><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td><td rowspan=" + linkInfo1.image.getOverTextList().size() + ">" + linkInfo1.link.getTemplateName() + "</td><td colspan='3'>" + getStringLiMap(linkInfo1.image.getOverTextList()) + "</td></tr>");
                    }

                    if (linkInfo1.image.hasError()) {
                        docs.getElementById("pageHasError").append("<tr><td>" + ++pageError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                        ++altErrors;
                    } else {
                        docs.getElementById("pageHasNoError").append("<tr><td>" + ++pageNoError + "</td><td><a href='" + data.outputAddress + "pages/" + linkInfo1.urlName.trim().hashCode() + ".html'>" + linkInfo1.urlName + "</a></td></tr>");
                    }
                }
            }
            docs.getElementsMatchingOwnText("Over Character Alt Text").first().text("Over Character Alt Text (" + multiC + ")");
            docs.getElementsMatchingOwnText("Missing Alt Text").first().text("Missing Alt Text (" + missingC + ")");
            docs.getElementsMatchingOwnText("Page With Error").first().text("Page With Error (" + altErrors + ")");
            docs.getElementsMatchingOwnText("Page Without Error").first().text("Page Without Error (" + (webPages.size() - (altErrors)) + ")");
            docs.getElementById("site-info").append(getSiteInfo());
            docs.getElementById("pie-chart-info").append("<ul class='pieID legend'> <li><em>Missing Alt Text</em><span>" + missingC + "</span> </li> <li><em>Over Char Alt Text</em><span>" + multiC + "</span> </li><li><em>Page With Error</em><span>" + (altErrors) + "</span> </li> <li><em>Page Without Error</em><span>" + (webPages.size() - (altErrors)) + "</span> </li></ul>");
            output.write(docs.toString());
        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException ex) {

                Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getURLLi(Object str[]) {
        String ul = "<ul class='list-group'></ul>";
        Document listDoc = Jsoup.parse(ul);
        Element ulTag = listDoc.getElementsByClass("list-group").first();
        for (Object val1 : str) {
            String val = (String) val1;
            ulTag.append("<li class='list-group-item'><a href='pages/" + val.trim().hashCode() + ".html'>" + val + "</a></li>");
        }
        return listDoc.toString();
    }

    public static String getStringLi(Object str[]) {
        String ul = "<ul class='list-group'></ul>";
        Document listDoc = Jsoup.parse(ul);
        Element ulTag = listDoc.getElementsByClass("list-group").first();
        for (Object val1 : str) {
            String val = (String) val1;
            ulTag.append("<li class='list-group-item'>" + val + "</li>");
        }
        return listDoc.toString();
    }

    public String getSiteInfo() {
        String ss = "", CT = "*****", s = "*****";
        boolean responsive = false;
        Document doc = null;
        try {
            Connection c = Jsoup.connect(data.site).userAgent(data.agentName).ignoreHttpErrors(true).followRedirects(data.redirect).timeout(0);
            doc = c.timeout(0).get();
            CT = doc.toString().contains("bas.js") || doc.toString().contains("ContainerTags.js") ? "Yes" : "No";
            ss = data.releaseVer;
            responsive = data.isResponsive;

        } catch (IOException ex) {
            data.completed = false;
            Logger.getLogger(CreateMainOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        s = "<tr><td>Brand Site:</td><td>" + data.site + "</td></tr>";
        if (!ss.isEmpty()) {
            s = s.concat("<tr><td>Build Release Version:</td><td>" + ss + "</td></tr>");
        }

        s = s.concat("<tr><td>Report Execution Time: </td><td>" + time[time.length - 2] + ", " + time[time.length - 1] + "</td></tr>");
        s = s.concat("<tr><td>Responsive:</td><td>" + (responsive ? "Yes" : "No") + "</td></tr>");
        if ("*****" != CT) {
            s = s.concat("<tr><td>Report in Excel:</td><td><a href='" + data.outputAddress + "SEOReport.xlsx'>Download</a> </td></tr>");
        }
        s = s.concat("<tr><td>Pages Crawled:</td><td>" + webPages.size() + "</td></tr>");
        return s;
    }

    public String getStringLiMap(HashMap<String, String> map) {
        Set<String> s = map.keySet();
        String outpt = "<table id='image-table' class='table table-condensed table-bordered'></table>";
        Document docs = Jsoup.parse(outpt);
        for (String key : s) {
            String val = (String) map.get(key);
            docs.getElementById("image-table").append("<tr><td>" + key + "</td><td>" + val + "</td><td>" + val.length() + "</td></tr>");
        }
        return docs.toString();
    }

}
