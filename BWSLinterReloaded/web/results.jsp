<%-- 
    Document   : output
    Created on : Aug 19, 2015, 10:52:40 AM
    Author     : sku202
--%>

<%@page import="java.io.File"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.List"%>
<%@page import="org.json.JSONArray"%>
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
                                    <h1>BWS Site Results</h1>
                                    <h4>General Insight...</h4>
                                </div>
                            </div>
                            <div id="content">                                
                                <div class="section-data my-row">
                                    <div class="col-md-12" id="all-option">
                                        <div class="row">
                                            <div class="col-md-4 table-responsive">
                                                <div class="section">
                                                    BWS Sites Information
                                                </div>
                                                <table class="table table-bordered table-striped" id="status-table">
                                                    <tr>
                                                        <th>Running</th>
                                                        <td id="running"></td>
                                                    </tr>
                                                    <tr>
                                                        <th>Not Running</th>
                                                        <td id="not-running"></td>
                                                    </tr>
                                                    <tr>
                                                        <th>Total Sites</th>
                                                        <td id="total"></td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div id="chart-area" class="col-md-4">
                                                <canvas id="chart"/>
                                            </div>
                                            <div class="col-md-4">&nbsp;</div>
                                            <div class="row col-md-12 table-responsive">
                                                <div class="col-md-offset-2 col-md-8 alert alert-danger fade in hidden" id="error">
                                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                    <strong>Ohh Snap!</strong> Something went wrong...!!!
                                                </div>
                                                <table class="table table-bordered table-striped table-condensed" id="result-table">
                                                    <thead>
                                                        <tr class="alert-info text-left"> 
                                                            <th>SN</th>
                                                            <th>URL</th>
                                                            <th>Status</th>
                                                            <th>Branch Version</th>
                                                            <th>Brand</th>
                                                            <th>Culture</th>
                                                            <th>Env</th>
                                                            <th>Type</th>
                                                            <th>Status Code</th>
                                                            <th>Status Msg</th>
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
            <%  String path = HelperClass.getRelativePathToWebApp(new File(HelperClass.getStatusDirectory(), "BWSstatus.json").getAbsolutePath());%>
            <input type="hidden" name="path" id="path" value="<%=path%>">
            <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/Chart.min.js"></script>
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/results.js"></script>

    </body>
</html>
