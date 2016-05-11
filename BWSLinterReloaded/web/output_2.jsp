<%-- 
    Document   : output
    Created on : Aug 19, 2015, 10:52:40 AM
    Author     : sku202
--%>

<%@page import="java.net.URL"%>
<%@page import="sachin.sapient.unilever.bws.site.HelperClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="css/output.css" rel="stylesheet">
        <title>Report</title>
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
                                    <h1>Site Report</h1>
                                    <h4>General Insight...</h4>
                                </div>
                            </div>
                            <div id="content">
                                <div class="section">
                                    Overview:
                                </div>
                                <div class="section-data my-row">
                                    <div class="row" id="all-option">
                                        <div id="header-panel-content" class="col-md-8">
                                            <table class="table table-bordered table-hover" id="site-info">                                                
                                                                                                
                                            </table>
                                            <input type="hidden" name='branch' id='branch'>
                                        </div>
                                        <div class="col-md-3">
                                            <a href="" id='siteImg' name='siteImg' target="_blank" title="view image"><img src='' id='imageSite' class="img-responsive status-img" name='imageSite' alt='site screenshot' title='screenshot'height=""/></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="section">
                                    Detailed Report:
                                </div>
                                <div class="my-row section-data">
                                    <div id="tabs" class="custom-div">
                                        <ul>
                                            <li><a href="#tabs-1">Article</a></li>
                                            <li><a href="#tabs-2">Product</a></li>
                                            <li><a href="#tabs-3">Recipe</a></li>
                                            <li><a href="#tabs-4">Miscellaneous</a> 
                                        </ul>
                                        <div id="tabs-1">
                                            <div class="table-responsive">
                                                <table id="Article" class="table table-bordered table-striped table-condensed">
                                                    <thead>
                                                        <tr class="alert-warning">                                                                
                                                            <th>Template Name</th>
                                                            <th>URL</th>                                                    
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>                                                                                            
                                            </div>
                                        </div>
                                        <div id="tabs-2">
                                            <div class="table-responsive">
                                                <table id="Product" class="table table-bordered table-striped table-condensed">
                                                    <thead>
                                                        <tr class="alert-warning">                                                                
                                                            <th>Template Name</th>
                                                            <th>URL</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>                                                                                            
                                            </div>
                                        </div>
                                        <div id="tabs-3">
                                            <div class="table-responsive">
                                                <table id="Recipe" class="table table-bordered table-striped table-condensed">
                                                    <thead>
                                                        <tr class="alert-warning">                                                                
                                                            <th>Template Name</th>
                                                            <th>URL</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>                                                
                                            </div>
                                        </div>
                                        <div id="tabs-4">                                           
                                            <div class="table-responsive">
                                                <table id="Other-Templates" class="table table-bordered table-striped table-condensed">
                                                    <thead>
                                                        <tr class="alert-warning">                                                                
                                                            <th>Template Name</th>
                                                            <th>URL</th>
                                                        </tr>
                                                    </thead>                                                        
                                                    <tbody>

                                                    </tbody>
                                                </table>
                                            </div>                                           
                                        </div>                             
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" value="<%=HelperClass.getLatestJSONoutputWithTemplate(request.getParameter("host"))%>" id="json" name="json">
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="js/output.js"></script>
    </body>
</html>
