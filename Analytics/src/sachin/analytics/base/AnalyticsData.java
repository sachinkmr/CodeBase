/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sku202
 */
public class AnalyticsData {

    private String template;
    final private List<Component> component;
    private String url;

    public AnalyticsData(String template) {
        this.template = template;
        component = new ArrayList<>();
    }

    public String getTemplate() {
        return template;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Component> getComponent() {
        return component;
    }

}
