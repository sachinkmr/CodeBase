<%-- 
    Document   : URL
    Created on : 20 Jan, 2015, 10:58:17 PM
    Author     : Sachin
--%>

<%@page import="com.sachin.seo.SEO"%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>URL Report</title>        
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
                                    <h1>Getting Started</h1>
                                    <h4>Let's get started...</h4>
                                </div>
                            </div>                            
                            <div id="content">
                                <div class="section">Overview:</div>
                                <div class="section-data my-row"> 
                                    <div class='row' id='all-option'>                                    
                                        <div id='header-panel-content' class='col-md-6'>
                                            <table class='table table-bordered table-condensed table-hover' id='site-info'>
                                                <tbody>

                                                </tbody>
                                            </table>
                                        </div>
                                        <div id='pie-charts' class='pull-right col-md-6'> 
                                            <section id='pie-chart-info'>
                                                <div class='pieID pie'>

                                                </div> 
                                            </section>
                                        </div>                                    
                                    </div>
                                </div>
                                <div class="section">Detailed Report:</div>
                                <div class="my-row section-data"> 
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/output.js"></script>        
    </body>
</html>