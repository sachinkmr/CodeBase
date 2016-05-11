<%@page import="com.sachin.site.Data"%>
<!DOCTYPE html>

<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>Image Alt Text Report</title>        
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
                                    <h1>Image Alt Text</h1>
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
                                            <li><a href="#over">Over Character Alt Text</a></li> 
                                            <li><a href="#missing">Missing Alt Text</a></li>
                                            <li><a href="#canonical-erros">Page With Error</a></li>
                                            <li><a href="#all-success">Page Without Error</a></li> 
                                        </ul>
                                        <div id="over">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Over Character (<%=Data.altTextCharLimit%>)</h2>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <table id="overCharH1" class="table table-condensed">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>Page URL</th>
                                                                    <th>Template</th>
                                                                    <th>Image Link</th>
                                                                    <th>Image Alt Text</th>
                                                                    <th>Alt Text Characters</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="missing">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Missing Alt Text</h2>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <table id="missingH1" class="table table-condensed">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>Page URL</th>
                                                                    <th>Template</th>
                                                                    <th>Image Link</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="canonical-erros">
                                            <div class="row setVisible">
                                                <div class="panel panel-danger">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Pages With Error</h2>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <table id="pageHasError" class="table table-condensed">
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
                                        <div id="all-success">
                                            <div class="row setVisible">
                                                <div class="panel panel-success">
                                                    <div class="panel-heading">
                                                        <h2 class="panel-title">Pages Without Error</h2>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <table id="pageHasNoError" class="table table-condensed">
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