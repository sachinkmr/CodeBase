<%-- 
    Document   : H2
    Created on : 20 Jan, 2015, 11:18:00 PM
    Author     : Sachin
--%>

<%@page import="com.sachin.site.Data"%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>H2 Tags</title>        
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
                                    <h1>H2 Tags</h1>
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
                                            <li><a href="#multi">Multiple H2</a></li> 
                                            <li><a href="#missing">Missing H2</a></li> 
                                            <li><a href="#H2-erros">Page With Error</a></li> 
                                            <li><a href="#all-success">Page Without Error</a></li> 
                                        </ul>
                                        <div id="dupli">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Duplicate H2</h2>
                                                    </div>
                                                    <table id="DuplicateH2" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>H2 Name</th>
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
                                                        <h2 class="panel-title">Over Character H2 (70)</h2>
                                                    </div>
                                                    <table id="overCharH2" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>H2 Name</th>
                                                                <th>H2 Length</th>
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
                                                        <h2 class="panel-title">Blank H2</h2>
                                                    </div>
                                                    <table id="blankH2" class='table table-bordered table-condensed table-hover'>
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
                                        <div id="multi">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Multiple H2</h2>
                                                    </div>
                                                    <table id="multipleH2" class='table table-bordered table-condensed table-hover'>
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Page URL</th>
                                                                <th>H2 Name</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="missing">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Missing H2</h2>
                                                    </div>
                                                    <table id="missingH2" class='table table-bordered table-condensed table-hover'>
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
                                        <div id="H2-erros">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Page With Error</h2>
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
                                                        <h2 class="panel-title">Page Without Error</h2>
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