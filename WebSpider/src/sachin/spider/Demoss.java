/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.spider;

/**
 *
 * @author sku202
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;

public class Demoss extends WebSpider {

    public final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|ico|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf|txt" + "|doc|xls|java|cs|cpp|xml|ppt" + "|rm|smil|wmv|swf|wma|zip|rar|gz))", Pattern.CASE_INSENSITIVE);
    final String site;
    String host;
    SpiderConfig config;

    public Demoss(String site) {
        this.site = site;
        try {
            this.host = new URL(site).getHost().replaceAll("www.", "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Demoss.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean shouldVisit(String url) {
//        return false;
        return (url != null && url.startsWith(config.getModifiedSiteName()) && (!FILTERS.matcher(url).find()));
    }

    @Override
    public void handleLink(WebURL webUrl, HttpResponse response, int statusCode, String statusDescription) {
        if (statusCode > 400) {
            System.out.println(statusCode + " : " + webUrl.getUrl());
            System.out.println("Parent : " + webUrl.getParents());
            System.out.println("--------------------------------------------------------------------------------------------------------");
        }
    }
//    /**
//     *
//     * @param page
//     */
//    @Override
//    public void viewPage(Page page) {
//        if (page.getAddress().equals(site)) {
//            System.out.println(page.getHyperLinks());
//        }
//    }

//    @Override
//    protected void visitPage(Document document, WebURL webUrl) {
//        super.visitPage(document, webUrl); //To change body of generated methods, choose Tools | Templates.
//    }
    void go() {
        config = new SpiderConfig(site.trim());
        config.setConnectionRequestTimeout(120000);
        config.setConnectionTimeout(120000);
        config.setSocketTimeout(120000);
        config.setTotalSpiders(5);
        config.setAuthenticate(true);
        config.setUsername("wlnonproduser");
        config.setPassword("Pass@word11");
        try {
            config.start(this, config);
        } catch (Exception ex) {
            Logger.getLogger(Demoss.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println(config.getModifiedSiteName());
    }

    public static void main(String... str) {

        new Demoss("http://www.comfortworld.co.uk/").go();
    }
}
