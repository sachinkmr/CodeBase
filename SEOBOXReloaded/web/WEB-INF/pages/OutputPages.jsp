
<%@page import="com.sachin.site.Constants"%>
<%@page import="java.net.URL"%>
<%@page import="com.sachin.site.WebPage"%>
<%@page import="com.sachin.site.HelperClass"%>
<%@page import="java.util.Map"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.sachin.site.Data"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.Writer"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.File"%>
<%@page import="com.sachin.site.Template"%>
<%@page import="com.sachin.site.WebPage"%>
<%@page import="com.sachin.site.SEO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.jsoup.Connection"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String name = request.getParameter("name");%>    
<%
    WebPage web = Constants.webPage;
    if (web.link.getStatusCode() !=  200) {
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>Page Detailed Report</title>        
    </head>
    <body role="document">    
        <div class="container-fluid">
            <div class="row">                
                <div class="col-md-2 sidebar" id="sidebar">                  
                    <%@include file="/WEB-INF/jspf/sidebar.jspf"%>
                </div>
                <div class="col-md-10" id="main1">                                 
                    <div class="" id="main2">
                        <div class="row">                            
                            <div class="" id="sachin">                            
                                <div class="tool-header">
                                    <h1>Page Information</h1>
                                    <h4>SEO Report...</h4>

                                </div>
                            </div>                           
                            <div id="content">
                                <%if (web != null) {%>
                                <div class="section">Detailed Report:</div>
                                <div class="my-row section-data">
                                    <div class="well">
                                        <div>
                                            <%if (!web.template.equalsIgnoreCase("****")) {%>    <strong>Page Template:</strong> <%=web.template%><br/><%}%>
                                            <strong>URL:</strong> <%=web.urlName%><br/>
                                            <strong>Page has errors:</strong>  <%=web.hasError() ? "Yes" : "No"%><br/>
                                        </div>
                                    </div>   
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h2 class="panel-title">SEO Report</h2>
                                        </div> 
                                        <div class="panel-body" id="seo-header"> 
                                            <div class="table-responsive">
                                                <table class="table table-bordered table-condensed" id="page-info">
                                                    <tbody>
                                                        <tr>
                                                            <th><strong>SN</strong></th>
                                                            <th><strong>SEO Tag</strong></th> 
                                                            <th><strong>Description</strong></th>
                                                            <th><strong>Page Status</strong></th>
                                                            <th><strong>SEO Error</strong></th>
                                                        </tr>
                                                        <tr>
                                                            <td>1</td>
                                                            <td><strong>H1</strong></td>
                                                            <td>   
                                                                <ul> 
                                                                    <c:forEach items="<%=web.h1.getH1()%>" var="item">
                                                                        <li> <c:out value="${item}"/> <br/>Char Count: ${fn:length(item)}</li>
                                                                        </c:forEach>                                                    
                                                                </ul>  
                                                            </td>
                                                            <td>
                                                                <ul class="list-group"> 
                                                                    <li class="list-group-item"><strong>H1 is Missing: </strong> <%=web.h1.isMissing ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>H1 is Multiple: </strong> <%=web.h1.isMultiple ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>H1 is Blank:</strong> <%=web.h1.isBlank ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>H1 is Over Char(<%=web.h1.charLimit%>): </strong> <%=web.h1.isOverChar ? "Yes" : "No"%></li> 
                                                                </ul>  
                                                            </td>
                                                            <td class=""><%=web.h1.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                        <tr>
                                                            <td>2</td>
                                                            <td><strong>H2</strong></td>
                                                            <td>   
                                                                <ul>
                                                                    <c:forEach items="<%=web.h2.getH2()%>" var="item">
                                                                        <li> <c:out value="${item}"/><br/>Char Count: ${fn:length(item)}</li>
                                                                        </c:forEach> 
                                                                </ul>  
                                                            </td>
                                                            <td>
                                                                <ul class="list-group"> 
                                                                    <li class="list-group-item"><strong>H2 is Missing: </strong> <%=web.h2.isMissing ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>H2 is Multiple: </strong> <%=web.h2.isMultiple ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>H2 is Blank:</strong> <%=web.h2.isBlank ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>H2 is Over Char(<%=web.h2.charLimit%>): </strong> <%=web.h2.isOverChar ? "Yes" : "No"%></li> 
                                                                </ul>  
                                                            </td>
                                                            <td class=""><%=web.h2.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                        <tr> 
                                                            <td>3</td> 
                                                            <td><strong>Title</strong></td>
                                                            <td>   
                                                                <ul> 
                                                                    <c:forEach items="<%=web.pageTitle.getTitle()%>" var="item">
                                                                        <li> <c:out value="${item}"/><br/>Char Count: ${fn:length(item)}</li>
                                                                        </c:forEach> 
                                                                </ul>  
                                                            </td>
                                                            <td>   
                                                                <ul class="list-group"> 
                                                                    <li class="list-group-item"><strong>Title is Missing: </strong> <%=web.pageTitle.isMissing ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Title is Multiple: </strong> <%=web.pageTitle.isMultiple ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Title is Blank:</strong> <%=web.pageTitle.isBlank ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Title is Over Char(<%=web.pageTitle.charLimit%>): </strong> <%=web.pageTitle.isOverChar ? "Yes" : "No"%></li> 
                                                                </ul>  
                                                            </td>
                                                            <td class=""><%=web.pageTitle.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                        <tr>
                                                            <td>4</td>
                                                            <td>
                                                                <strong>Canonical URL</strong>
                                                            </td> 
                                                            <td>   
                                                                <ul> 
                                                                    <c:forEach items="<%=web.can.getCanonical()%>" var="item">
                                                                        <li> <c:out value="${item}"/><br/>Char Count: ${fn:length(item)}</li>
                                                                        </c:forEach> 
                                                                </ul>  
                                                            </td> 
                                                            <td>
                                                                <ul class="list-group"> 
                                                                    <li class="list-group-item"><strong>Canonical URL is Missing: </strong> <%=web.can.isMissing ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Canonical URL is Multiple: </strong> <%=web.can.isMultiple ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Canonical URL is Blank:</strong> <%=web.can.isBlank ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Canonical URL is Over Char(<%=web.can.charLimit%>): </strong> <%=web.can.hasError() ? "Yes" : "No"%></li> 
                                                                </ul>  
                                                            </td> 
                                                            <td class=""><%=web.can.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                        <tr>
                                                            <td>5</td>
                                                            <td>
                                                                <strong>Status Code</strong>
                                                            </td>
                                                            <td>   
                                                                <ul> 
                                                                    <li><strong>Status Code:</strong> <%=web.sc.code%></li> 
                                                                </ul>  
                                                            </td>
                                                            <td>   
                                                                <ul class="list-group"> 
                                                                    <li class="list-group-item"><strong>Status Msg:</strong> <%=web.sc.msg%></li> 
                                                                </ul>  </td>
                                                            <td class=""><%=web.sc.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                        <tr> 
                                                            <td>6</td>
                                                            <td>
                                                                <strong>URL</strong>
                                                            </td>
                                                            <td>   
                                                                <ul> 
                                                                    <li><%=web.url.url%><br/>Char Count: <%=web.url.url.length()%></li> 
                                                                </ul>  
                                                            </td>
                                                            <td>
                                                                <ul class="list-group">
                                                                    <li class="list-group-item"><strong>URL is Over Char(<%=web.url.urlCharLimit%>): </strong> <%=web.url.isOverChar ? "Yes" : "No"%></li> 
                                                                </ul> 
                                                            </td>
                                                            <td class=""><%=web.url.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                        <tr>
                                                            <td>7</td> 
                                                            <td><strong>Meta Description</strong></td>
                                                            <td>   
                                                                <ul> 
                                                                    <c:forEach items="<%=web.md.getDescription()%>" var="item">
                                                                        <li> <c:out value="${item}"/><br/>Char Count: ${fn:length(item)}</li>
                                                                        </c:forEach> 
                                                                </ul>  
                                                            </td>
                                                            <td>
                                                                <ul class="list-group"> 
                                                                    <li class="list-group-item"><strong>Meta Description is Missing: </strong> <%=web.md.isMissing ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Meta Description is Multiple: </strong> <%=web.md.isMultiple ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Meta Description is Blank:</strong> <%=web.md.isBlank ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Meta Description is Over Char(<%=web.md.charLimit%>): </strong> <%=web.md.isOverChar ? "Yes" : "No"%></li> 
                                                                </ul>  
                                                            </td>
                                                            <td><%=web.md.hasError() ? "Yes" : "No"%></td>
                                                        </tr> 
                                                        <tr>
                                                            <td>8</td>
                                                            <td><strong>Meta Keywords</strong></td>
                                                            <td>   
                                                                <ul> 
                                                                    <c:forEach items="<%=web.mk.getKeywords()%>" var="item">
                                                                        <li> <c:out value="${item}"/><br/>Char Count: ${fn:length(item)}</li>
                                                                        </c:forEach> 
                                                                </ul>  
                                                            </td> 
                                                            <td>
                                                                <ul class="list-group"> 
                                                                    <li class="list-group-item"><strong>Meta Keywords are Missing: </strong> <%=web.mk.isMissing ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Meta Keywords are Multiple: </strong> <%=web.mk.isMultiple ? "Yes" : "No"%></li>
                                                                </ul>  
                                                            </td>
                                                            <td><%=web.mk.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                        <tr>
                                                            <td>9</td>
                                                            <td><strong>Missing Image Alt Text</strong></td>
                                                            <td>   
                                                                <ul>
                                                                    <c:set var="missing" scope="page" value="<%=web.image.hasMissingAltText%>"/>
                                                                    <c:if test="${missing ne false}">
                                                                        <li>Missing Alt:
                                                                            <ul>
                                                                                <c:forEach items="<%=web.image.getMissingAlts()%>" var="item">
                                                                                    <li> <c:out value="${item}"/></li>
                                                                                    </c:forEach> 
                                                                            </ul>
                                                                        </li>
                                                                    </c:if>
                                                                    <c:set var="over" scope="page" value="<%=web.image.hasOverText%>"/>
                                                                    <c:if test="${over ne false}">
                                                                        <li>Over Char Alt Text:
                                                                            <ul>
                                                                                <c:forEach items="<%=web.image.getOverTextList()%>" var="item">
                                                                                    <li> <c:out value="${item}"/><br/>Char Count: ${fn:length(item)}</li>
                                                                                    </c:forEach> 
                                                                            </ul>
                                                                        </li>
                                                                    </c:if>
                                                                </ul>

                                                            </td>
                                                            <td>
                                                                <ul class="list-group">
                                                                    <li class="list-group-item"><strong>Alt Text is Over Char(<%=web.image.altTextCharLimit%>): </strong> <%=web.image.hasOverText ? "Yes" : "No"%></li> 
                                                                    <li class="list-group-item"><strong>Alt Text is missing: </strong>  <%=web.image.hasMissingAltText ? "Yes" : "No"%></li> 
                                                                </ul>                                         
                                                            </td>
                                                            <td class=""> <%=web.image.hasError() ? "Yes" : "No"%></td>
                                                        </tr>
                                                    </tbody>
                                                </table> 
                                            </div>
                                        </div> 
                                    </div>
                                </div>
                            </div><%}%>
                            <%@include file="/WEB-INF/jspf/footer.jspf"%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/output.js"></script>        
    </body>
</html>