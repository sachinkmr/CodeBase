/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachin.site;

import com.sachin.seo.parameters.Canonical;
import com.sachin.seo.parameters.H1;
import com.sachin.seo.parameters.H2;
import com.sachin.seo.parameters.Image;
import com.sachin.seo.parameters.MetaDescription;
import com.sachin.seo.parameters.MetaKeyword;
import com.sachin.seo.parameters.StatusCode;
import com.sachin.seo.parameters.Title;
import com.sachin.seo.parameters.URL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author sku202
 */
public final class WebPage implements Serializable {

    public boolean hasError;
    public final String urlName;
    public H1 h1;
    public H2 h2;
    public Image image;
    public Title pageTitle;
    public MetaDescription md;
    public MetaKeyword mk;
    public StatusCode sc;
    public Canonical can;
    public URL url;
    Data data;
    public UrlLink link;
    transient Document doc;
    public String template;

    WebPage(String urlName, UrlLink link, Data data) {
        this.link = link;
        if (link.getStatusCode() == 200) {
            this.doc = Jsoup.parse(link.getDocument(), link.getSiteName());
            this.template = link.getTemplateName();
        }
        this.data = data;
        this.urlName = urlName.trim();
    }

    public void run() {
        if (link.getStatusCode() == 200) {
            verifyH1();
            verifyH2();
            verifyImage();
            verifyDescription();
            verifyMetaKeywords();
            verifyCanonical();
            verifyTitle();
        }
        verifyStatusCode();
        verifyURL();

        hasError = hasError();
        doc = null;
    }

    public synchronized void writeData() {
        try {
            File f = new File(data.outputLocation + "pages/");
            f.mkdirs();
            String fname = "/" + urlName.hashCode() + ".html";
            BufferedWriter fw = new BufferedWriter(new FileWriter(f.getAbsolutePath() + fname));
            Document doc1 = Jsoup.connect(data.address + "/CreatePage?name=" + urlName).ignoreHttpErrors(true).timeout(0).ignoreContentType(true).get();
            fw.write(doc1.toString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(WebPage.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void verifyH1() {
        h1 = new H1(urlName, doc, data);
    }

    public void verifyH2() {
        h2 = new H2(urlName, doc, data);
    }

    public void verifyImage() {
        image = new Image(urlName, doc, data);
    }

    public void verifyDescription() {
        md = new MetaDescription(urlName, doc, data);
    }

    public void verifyMetaKeywords() {
        mk = new MetaKeyword(urlName, doc, data);
    }

    public void verifyCanonical() {
        can = new Canonical(urlName, doc, data);
    }

    public void verifyTitle() {
        pageTitle = new Title(urlName, doc, data);
    }

    public void verifyStatusCode() {
        sc = new StatusCode(urlName, link.getStatusCode(), link.getStatusMsg());
    }

    public void verifyURL() {
        url = new URL(urlName, data);
    }

    public boolean hasError() {
        if (link.getStatusCode() == 200) {
            return h1.hasError() || h2.hasError() || image.hasError() || pageTitle.hasError() || md.hasError() || mk.hasError() || sc.hasError() || can.hasError() || url.hasError();
        } else {
            return true;
        }
    }
//-----------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.urlName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WebPage other = (WebPage) obj;
        if (!Objects.equals(this.urlName, other.urlName)) {
            return false;
        }
        return true;
    }
}
