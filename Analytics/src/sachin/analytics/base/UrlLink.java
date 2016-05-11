/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.io.Serializable;
import java.util.Objects;
import org.jsoup.nodes.Document;

/**
 *
 * @author sku202
 */
public class UrlLink implements Serializable {
//    public static final long serialVersionUID=42L;

    private final String url;
    private String templateName;
    private int statusCode;
    private String statusMsg;
    private String document;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.url);
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
        final UrlLink other = (UrlLink) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    public UrlLink(String url, int statusCode, String statusMsg) {
        this.url = url;
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public UrlLink(String url, String templateName, Document doc) {
        this.url = url;
        this.templateName = templateName;
        this.document = doc.toString();
    }

    public UrlLink(String url) {
        this.url = url;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getUrl() {
        return url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getDocument() {
        return document;
    }

}
