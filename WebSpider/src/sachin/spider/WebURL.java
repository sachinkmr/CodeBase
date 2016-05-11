/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sachin.spider;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.http.Header;

/**
 *
 * @author JARVIS
 */
public class WebURL {
    private final String url;
    private int statusCode;
    private String statusMessage;
    private boolean proccessed;
    private int docid;
    private int resposneTime;
    private String redirectTo;
    private Header[] headers;
    private String baseHref;
    private String errorMsg;
    private Set<WebURL> parents;
    
    public String getUrl() {
        return url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isProccessed() {
        return proccessed;
    }

    public void setProccessed(boolean proccessed) {
        this.proccessed = proccessed;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.url);
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
        final WebURL other = (WebURL) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return url;
    }

    public WebURL(String url) {
        this.url = url;
        this.proccessed=false;
        this.statusCode=-01;
        this.parents=new HashSet<>();
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public int getResposneTime() {
        return resposneTime;
    }

    public void setResposneTime(int resposneTime) {
        this.resposneTime = resposneTime;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

 

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getBaseHref() {
        return baseHref;
    }

    public void setBaseHref(String baseHref) {
        this.baseHref = baseHref;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Set<WebURL> getParents() {
        return parents;
    }
    
    public void addParent(WebURL webURL){
        parents.add(webURL);
    }
}
