<%-- 
    Document   : URL
    Created on : 20 Jan, 2015, 10:58:17 PM
    Author     : Sachin
--%>

<%@page import="com.sachin.site.SEO"%>
<%
    String serverName="http://"+InetAddress.getLocalHost().getHostAddress()+":"+request.getLocalPort();
    session.setAttribute("serverName", serverName);
%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="/WEB-INF/jspf/isUserAuthenticated.jspf"%>
    <%@include file="/WEB-INF/jspf/AccessingAdminContents.jspf"%>
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/admin.css" rel="stylesheet">        
        <title>Admin Panel</title>        
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
                                    <h1>Administrator Panel</h1>
                                    <h4>Control & Modify.....</h4>
                                </div>
                            </div>                            
                            <div id="content">
                                <div id='msg'></div>
                                <div class="section">Admin Panel:</div>
                                <div class="section-data my-row"> 
                                    <div id="tabs1" class=''> 
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#template-tab" data-toggle="tab">Templates<i class="fa"></i></a></li>
                                            <li><a href="#report-tab" data-toggle="tab">Reports <i class="fa"></i></a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="template-tab">
                                                <div class="tab-pane-data">
                                                    <div class="section-data my-row" id="configure-template">
                                                        <a href="javascript:void(0)" id ='addTemp-button' class="btn btn-primary pull-right"><i class="fa fa-plus"></i>&ensp; Add Template</a>
                                                        <table id='template-table' class="table table-bordered table-condensed table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <td>SN</td>
                                                                    <td>Template Name</td>
                                                                    <td>Delete</td>
                                                                </tr>
                                                            </thead>
                                                            <tbody>

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="report-tab"> 
                                                <div class="tab-pane-data">
                                                    <div class="section-data my-row" id="configure-reports">                                             
                                                        <table id='report-table' class="table table-bordered table-condensed table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <td>SN</td>
                                                                    <td>Site Name</td>
                                                                    <td>Date </td>  
                                                                    <td>Time</td>  
                                                                    <td>Delete Report</td>
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
            </div>
        </div>
        <div class="modal fade" id="addTemp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Add New Template</h4>
                    </div>
                    <div class="modal-body">
                        <form name="addTemp-form" id="addTemp-form" action='addTemplate' method='post'>
                            <div class="form-group">
                                <label for="id1" class=" control-label">Name:</label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i></span>
                                        <input type="text" class="form-control" name="id1" id="id1" placeholder="Template Name" title="Enter Template Name" data-toggle="tooltip">
                                    </div> 
                                </div>
                            </div>                            
                            <div class="form-group">
                                <div class="">
                                    <button type="submit" class="btn btn-block btn-primary">Add </button>
                                </div>
                            </div>                            
                        </form>
                    </div>
                </div>
            </div>
        </div> 
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="<%=serverName%>${pageContext.request.contextPath}/js/admin.js"></script>        
    </body>
</html>