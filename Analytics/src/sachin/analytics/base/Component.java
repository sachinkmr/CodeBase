/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.analytics.base;

/**
 *
 * @author sku202
 */
public class Component {

    private String name;
    private String expectedParams;
    private String actual;
    private String status;
    private String eventType;
    private String eventPath;
    private String comment;
    private String testCaseNumber;

    
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventPath() {
        return eventPath;
    }

    public void setEventPath(String eventPath) {
        this.eventPath = eventPath;
    }

    public String getName() {
        return name;
    }

    public Component(String name) {
        this.name = name;
    }

    public String getExpectedParams() {
        return expectedParams;
    }

    public void setExpectedParams(String expectedParams) {
        this.expectedParams = expectedParams;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTestCaseNumber() {
        return testCaseNumber;
    }

    public void setTestCaseNumber(String testCaseNumber) {
        this.testCaseNumber = testCaseNumber;
    }
    
    
}
