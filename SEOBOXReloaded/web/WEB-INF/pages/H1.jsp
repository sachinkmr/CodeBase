<%-- 
    Document   : H1
    Created on : 20 Jan, 2015, 10:10:33 PM
    Author     : Sachin
--%>

<%@page import="com.sachin.site.Data"%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>H1 Report</title>        
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
                                    <h1>H1 Tag</h1>
                                    <h4>SEO Report</h4>
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
                                    <div id='tabs' class="custom-div">
                                        <ul>
                                            <li><a href="#dupli">Duplicate</a></li> 
                                            <li><a href="#over">OverCharacter</a></li> 
                                            <li><a href="#blank">Without Text</a></li> 
                                            <li><a href="#multi">Multiple H1</a></li> 
                                            <li><a href="#missing">Missing H1</a></li> 
                                            <li><a href="#H1-erros">Page With Error</a></li> 
                                            <li><a href="#all-success">Page Without Error</a></li> 
                                        </ul>
                                        <div id="dupli">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Duplicate H1</h2>
                                                    </div>
                                                    <table id="DuplicateH1" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>H1 Name</th>
                                                                <th>Page URL</th>
                                                            </tr>
                                                        </thead>                                        
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="over">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Over Character H1 (<%=Data.h1CharLimit%>)</h2>
                                                    </div>
                                                    <table id="overCharH1" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>H1 Name</th>
                                                                <th>H1 Length</th>
                                                                <th>Page URL</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody></tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="blank">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Blank H1</h2>
                                                    </div>
                                                    <table id="blankH1" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Page URL</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody></tbody>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="multi">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Multiple H1</h2>
                                                    </div>
                                                    <table id="multipleH1" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Page URL</th>
                                                                <th>H1 Name</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="missing">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Missing H1</h2>
                                                    </div>
                                                    <table id="missingH1" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Page URL</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="H1-erros">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Page With H1 Error</h2>
                                                    </div>
                                                    <table id="pageHasError" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Page URL</th>
                                                            </tr>
                                                        </thead>                                        
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="all-success">
                                            <div class="row setVisible">
                                                <div class="panel panel-success">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Page Without H1 Error</h2>
                                                    </div>
                                                    <table id="pageHasNoError" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Page URL</th>
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