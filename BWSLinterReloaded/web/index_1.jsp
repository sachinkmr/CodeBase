<%-- 
    Document   : index
    Created on : 20 Jan, 2015, 5:26:01 PM
    Author     : Sachin
--%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="sachin.sapient.unilever.bws.site.HelperClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
    System.setProperty("BWSLinterPath", new HelperClass().getPath());
    List<String> list = FileUtils.readLines(new File(HelperClass.getDataFolderPath(), "Sites.txt"));    
%>
<c:set var="list" value="<%=list%>"></c:set>
    <!DOCTYPE html>
    <html lang="en">
        <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet"> 
        <title>BWS Linter</title>        
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
                                    <h1>BWS Linter</h1>
                                    <h4>Get Information about BWS sites</h4>
                                </div>
                            </div>
                            <div class="" id="mask">
                                <div class="col-md-6 col-md-offset-3 mask-data">
                                    <img src="img/spin.gif" class="img-responsive center-block"/>
                                    <h3 id="status">Processing...</h3> 
                                    <div class="hidden" id='bar-mask'>
                                        <div class="progress" >
                                            <div id="orangeBar-mask" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div>
                                        </div>  
                                    </div> 
                                </div>
                                <div class='clearfix'></div>
                            </div>
                            <div id="content" class="">                                
                                <form id="sitesForm" name="sitesForm" action="#" method="POST" >  
                                    <div id="tabs1" class=''> 
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#defined-sites" data-toggle="tab">Pre-defined Sites<i class="fa"></i></a></li> 
                                            <li class="active"><a href="#custom" data-toggle="tab">BWS Site<i class="fa"></i></a></li>
                                            <li class=""><a href="#user-tab" data-toggle="tab">Performance<i class="fa"></i></a></li>
                                        </ul>
                                        <div class="tab-content">
                                      <div class="tab-pane active" id="defined-sites">
                                                <div class="tab-pane-data  col-md-12">    
                                                    <div class="" id='txt' name='txt'>
                                                        <div for="sites" class="section">Please Select Sites</div>  
                                                        <div class="col-md-12 site-names section-data">
                                                            <ul>
                                                                <c:forEach items="${list}" var="site">                                              
                                                                    <li class="site"> 
                                                                        <div class="checkbox">
                                                                            <label class="site-label"> 
                                                                                <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>' class="sites" value="${site}"> &nbsp;${site}
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div>
                                                                        <div class="site-data col-md-12">    
                                                                            <div class="col-md-8 site-functions">
                                                                                <div class="col-md-12"><strong>Select functionality to verify:</strong></div>                                                                
                                                                                <div class="col-md-6 ">
                                                                                    <ul>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-UserAgent' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-UserAgent' class="sitesAttributes" > &nbsp; Virtual Agent
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div>    
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-Contactus' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-Contactus' class="sitesAttributes" > &nbsp; Contact Us 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-signup' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-Signup' class="sitesAttributes" > &nbsp; Signup 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-UM' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-UM' class="sitesAttributes" > &nbsp; User Management 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div>  
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-SiteSearch' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-SiteSearch' class="sitesAttributes" > &nbsp; Site Search 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-RecipeSearch' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-RecipeSearch' class="sitesAttributes" > &nbsp; Recipe Search 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-RecipeRatings' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-RecipeRatings' class="sitesAttributes" > &nbsp; Recipe Rating 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                    </ul>
                                                                                </div>
                                                                                <div class="col-md-6">
                                                                                    <ul>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-BuyOnline' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-BuyOnline' class="sitesAttributes" > &nbsp; Buy Online 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-WTB' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-WTB' class="sitesAttributes" > &nbsp; Store Locator 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-BV' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-BV' class="sitesAttributes" > &nbsp; Bazaar Voice 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-Kritique' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-Kritique' class="sitesAttributes" > &nbsp; Kritique 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div>  
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-analytics' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-analytics' class="sitesAttributes" > &nbsp; Analytics 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div>  
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-BVSEO' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-BVSEO' class="sitesAttributes" > &nbsp; BV SEO 
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div>  
                                                                                        </li>
                                                                                        <li>
                                                                                            <div class="checkbox">
                                                                                                <label>
                                                                                                    <input type="checkbox" name='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-Other' id='<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-Other' class="sitesAttributes" > &nbsp;  
                                                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                                </label>
                                                                                            </div> 
                                                                                        </li>
                                                                                    </ul>
                                                                                </div>
                                                                            </div>
                                                                            <div class='col-md-4'>
                                                                                <Strong>Site Parameters</strong>
                                                                                <div class="checkbox">
                                                                                    <label class="crawler-label" for="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-crawler" class="crawlers">
                                                                                        <input type="checkbox" name="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-crawler" id="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-crawler"> &nbsp; Crawl whole site 
                                                                                        <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                                    </label>
                                                                                </div>
                                                                                <div class='checkbox'>    
                                                                                    <label class="auth-label" for="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-setAuthentication">
                                                                                        <input type="checkbox" name="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-setAuthentication" id="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-setAuthentication" class="auths">&nbsp; Authentication &emsp;
                                                                                        <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>                                                                        
                                                                                    </label>
                                                                                </div>
                                                                                <div class="form-group">                                    
                                                                                    <div class="controls">                                                
                                                                                        <div class="input-group">
                                                                                            <span class="input-group-addon">
                                                                                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                                                                            </span>                                        
                                                                                            <input type="text" class="form-control" disabled placeholder="Username" id="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-username" name="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-username" title ="Enter LDAP username for site" data-toggle="tooltip" value="wlnonproduser">
                                                                                        </div>                                            
                                                                                    </div>
                                                                                </div>   
                                                                                <div class="form-group">                                    
                                                                                    <div class="controls">                                                
                                                                                        <div class="input-group">
                                                                                            <span class="input-group-addon">
                                                                                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                                                                            </span>                                        
                                                                                            <input type="password" class="form-control" disabled placeholder="Password" id="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-password" name="<%=HelperClass.getModifiedHostName((String) pageContext.getAttribute("site"))%>-password" title ="Enter LDAP password for site" data-toggle="tooltip" value="Pass@word11">
                                                                                        </div>                                            
                                                                                    </div>
                                                                                </div>
                                                                            </div>                                                            
                                                                        </div>
                                                                    </li>
                                                                </c:forEach>
                                                            </ul>                                                            
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div> 
                                            <div class="tab-pane" id="custom">
                                                <div class="tab-pane-data col-md-12"> 
                                                    <div class="col-md-12 row" id="siteURL"> 
                                                        <div class="col-md-2"> 
                                                            <strong>Enter Site URL: </strong>
                                                        </div>
                                                        <div class="col-md-10"> 
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon">
                                                                            <span class="glyphicon glyphicon-globe" aria-hidden="true"></span>
                                                                        </span>                                        
                                                                        <input type="text" class="form-control" placeholder="http://example.com" id="url" name="url" title ="Enter site url starting with protocol" data-toggle="tooltip">
                                                                    </div>                                            
                                                                </div>
                                                            </div>  
                                                        </div>
                                                    </div>

                                                    <div class="col-md-12 row">
                                                        <div class="col-md-2"><strong>Config Dev Values: </strong></div>
                                                        <div class="col-md-5">                                                            
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                            
                                                                    <div class="input-group" title="Select Brand Name" data-toggle="tooltip">
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i> &nbsp;Brand</span>                                            
                                                                        <select class="form-control"  name="brand" id="brand">
                                                                            
                                                                        </select>
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-5">                                                            
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                            
                                                                    <div class="input-group" title="Select Environment Name" data-toggle="tooltip">
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-file"></i> &nbsp;Environment</span>                                            
                                                                        <select class="form-control"  name="environment" id="environment">
                                                                           
                                                                        </select>
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 demo">&nbsp; </div>
                                                        <div class="col-md-5">                                                            
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                            
                                                                    <div class="input-group" title="Select Culture Name" data-toggle="tooltip">
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i> &nbsp;Culture</span>                                            
                                                                        <select class="form-control"  name="culture" id="culture">
                                                                            
                                                                        </select>
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>                                                   
                                            </div>
                                            <div class="tab-pane " id="user-tab">
                                                <div class="tab-pane-data col-md-12"> 
                                                    <div id='performance' class='col-md-4'>                                                           
                                                        <div class="checkbox">                                                                               
                                                            <label id="performanceLable">
                                                                <input type="checkbox" name="setPerformance" id="setPerformance"> Edit Performance &emsp;
                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                            </label>   
                                                        </div> 
                                                        <div class="form-group">                                    
                                                            <div class="controls">                                            
                                                                <div class="input-group" title="Select No of Threads" data-toggle="tooltip">
                                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-tasks"></i> &nbsp;Threads:</span>                                            
                                                                    <select class="form-control"  name="threads" id="threads" disabled>
                                                                        <option>1</option>
                                                                        <option>2</option>
                                                                        <option>3</option>   
                                                                        <option>4</option>   
                                                                        <option selected="true">5</option>   
                                                                        <option>6</option>   
                                                                        <option>7</option>   
                                                                        <option>8</option>   
                                                                        <option>9</option>   
                                                                        <option>10</option>   
                                                                    </select>
                                                                </div> 
                                                            </div>
                                                        </div>
                                                        <div class="form-group">                                    
                                                            <div class="controls">
                                                                <div class="input-group" title ="Enter page timeout in sec" data-toggle="tooltip" >
                                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i> &nbsp;Timeout:</span>                                            
                                                                    <input type="text" class="form-control" disabled placeholder="timeout" id="timeout" name="timeout" value="20">
                                                                </div> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>                                                   
                                            </div>
                                        </div>
                                    </div>   
                                    <input type="submit">
                                </form>                                
                            </div>
                            <%@include file="/WEB-INF/jspf/footer.jspf"%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>        

    </body>
</html>
