

<%@page import="java.net.InetAddress"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String address = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().lastIndexOf('/'));
    session.setAttribute("address", address);
    
%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/main.css" rel="stylesheet"> 
        <title>SEO BOX</title>        
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
                            <div class="" id="mask">
                                <div class="col-md-6 col-md-offset-3 mask-data">
                                    <img src="img/spin.gif" class="img-responsive center-block"/>
                                    <h3 id="status">Processing...</h3> 
                                    <span id="site"></span>
                                    <div class="hidden" id='bar-mask'>
                                        <div class="progress" >
                                            <div id="orangeBar-mask" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>
                                        </div>  
                                    </div> 
                                </div>
                                <div class='clearfix'></div>
                            </div>
                            <div id="content">                                                       
                                <form id="single-site-form" name="single-site-form" action="SEO" method="POST" enctype="multipart/form-data"> 
                                    <div>
                                        <div id='page' name='page'>
                                            <div class="section">Enter Site URL: </div>
                                            <div class="section-data">                                        
                                                <div class="form-group">
                                                    <div class="input-group form-section">
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-globe" aria-hidden="true"></span>
                                                        </span>                                        
                                                        <input type="text" class="form-control" placeholder="http://example.com" id="siteURL" name="siteURL" title ="Enter URL with http:// or https://" data-toggle="tooltip">
                                                        <span class="input-group-btn">
                                                            <button class="btn btn-primary" type="submit">Submit</button>
                                                        </span>                                        
                                                    </div>
                                                    <span class="messages"></span> 
                                                </div>
                                            </div>
                                        </div>
                                        <div class="hidden" id='txt' name='txt'>
                                            <div for="fileURL" class="section">Select Text File:</div>
                                            <div class="section-data"> 
                                                <div class=" form-group" >                                            
                                                    <div class="input-group form-section">
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                                        </span>                                        
                                                        <input type="file" class="form-control hidden" placeholder="Browse text file containing URLs" id="fileURL" name="fileURL" title ="Browse a text file containing URLs" data-toggle="tooltip">
                                                        <span class="input-group-btn">
                                                            <button class="btn btn-primary" type="submit">Submit</button>
                                                        </span>                                        
                                                    </div>
                                                    <span class="messages"></span>
                                                </div> 
                                            </div>
                                        </div>
                                        <div class='my-row form-section'>
										<input type="hidden" name="crawler" id="allPages" value="allPages">
                                            <div class="col-md-8 hidden">                                                
                                                <label>
                                                    <input type="radio" name="crawler" id="allPages" checked="true" value="allPages"> All pages &emsp;
                                                </label>
                                                <label>
                                                    <input type="radio" name="crawler" id="singlePage" value="singlePage"> Single Page &emsp;
                                                </label>
                                                <label>
                                                    <input type="radio" name="crawler" id="txtFile" value="txtFile"> URL from Text File &emsp;
                                                </label> 
                                            </div>         
                                            <div class="hidden" id='bar'>
                                                <div class="progress" >
                                                    <div id="orangeBar" class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>
                                                </div>  
                                            </div> 
                                            <div class='clearfix'></div>
                                        </div>                                    
                                    </div>
                                    <!--
                                    <div id="results" class="hidden">
                                        <div class='section'>Results: </div>
                                        <div class="section-data"> 
                                            <div id='resultsData' class="">

                                            </div>
                                        </div>
                                    </div>
                                    -->
                                    <div class='section' id="preferencesTxt"><a href="javascript:void(0);" id="preferencesClick" name="preferencesClick">Search Preferences</a> <i class="fa fa-arrow-down"></i> </div>
                                    <div class="section-data my-row">
                                        <div id="tabs1" class=''> 
                                            <ul class="nav nav-tabs">
                                                <li class="active"><a href="#user-tab" data-toggle="tab">User Preferences<i class="fa"></i></a></li>
                                                <li><a href="#seo-tab" data-toggle="tab">SEO Checklist <i class="fa"></i></a></li>
                                            </ul>
                                            <div class="tab-content">
                                                <div class="tab-pane active" id="user-tab">
                                                    <div class="tab-pane-data">
                                                        <div id='preferences' class='col-md-4'>                                                       
                                                            <div class="">
                                                                <label for="redirect"  data-toggle="tooltip" title="Enable the URL redirections like 301">
                                                                    <input type="checkbox" name="redirect" id="redirect" checked="true"> Enable Redirection &nbsp;
                                                                </label>
                                                                <label class="pull-right" for="stay" data-toggle="tooltip" title="Select it if you want to override Geo Location feature">
                                                                    <input type="checkbox" name="stay" id="stay" > Stay on site &nbsp;
                                                                </label>                                                               
                                                            </div>  
                                                            <div class="form-group">
                                                                <label for='ua'>User-Agent:</label>
                                                                <div class="controls">                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i></span>                                            
                                                                        <select class="form-control" name="ua" id="ua" title="Select User Agent" data-toggle="tooltip">

                                                                        </select>
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div id='authentication' class='col-md-4'>
                                                            <div class="" >
                                                                <label>
                                                                    <input type="checkbox" name="setAuthentication" id="setAuthentication"> Authentication &emsp;
                                                                </label>                                
                                                            </div> 
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon">
                                                                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                                                        </span>                                        
                                                                        <input type="text" class="form-control" disabled placeholder="Username" id="username" name="username" title ="Enter LDAP username for site" data-toggle="tooltip" value="wlnonproduser">
                                                                    </div>                                            
                                                                </div>
                                                            </div>   
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon">
                                                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                                                        </span>                                        
                                                                        <input type="password" class="form-control" disabled placeholder="Password" id="password" name="password" title ="Enter LDAP password for site" data-toggle="tooltip" value="Pass@word11">
                                                                    </div>                                            
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div id='performance' class='col-md-4'>
                                                            <div class="" >
                                                                <label>
                                                                    <input type="checkbox" name="setPerformance" id="setPerformance"> Edit Performance &emsp;
                                                                </label>                                
                                                            </div> 
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                            
                                                                    <div class="input-group" title="Select No of Threads" data-toggle="tooltip">
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-tasks"></i> &nbsp;Threads:</span>                                            
                                                                        <select class="form-control" disabled name="threads" id="threads">
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
                                                                            <option>11</option>   
                                                                            <option>12</option>   
                                                                            <option>13</option>   
                                                                            <option>14</option>   
                                                                            <option>15</option>   
                                                                            <option>16</option>  
                                                                            <option>17</option>   
                                                                            <option>18</option>   
                                                                            <option>19</option>   
                                                                            <option>20</option> 
                                                                        </select>
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                            <div class="form-group">                                    
                                                                <div class="controls">
                                                                    <div class="input-group" title ="Enter page timeout in sec" data-toggle="tooltip" >
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i> &nbsp;Timeout:</span>                                            
                                                                        <input type="text" class="form-control" disabled placeholder="timeout" id="timeout" name="timeout" value="180">
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="tab-pane" id="seo-tab"> 
                                                    <div class="tab-pane-data">
                                                        <div class="col-md-12" >
                                                            <div class="col-md-8">
                                                                <label>
                                                                    <input type="checkbox" name="editSEO" id="editSEO"> Edit SEO Parameters &emsp;
                                                                </label>
                                                            </div>
                                                            <div id='seo' class="col-md-6">
                                                                <div class="form-group col-md-12">
                                                                    <label class="col-md-8 control-label">URL Char Limit:</label>
                                                                    <div class="col-md-3">
                                                                        <input type="text" disabled class="form-control" name="urlCharLimit" id='urlCharLimit' value="115"/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-md-12">
                                                                    <label class="col-md-8 control-label">H1 Char Limit:</label>
                                                                    <div class="col-md-3">
                                                                        <input type="text" disabled class="form-control" name="h1CharLimit" id='h1CharLimit' value="70"/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-md-12">
                                                                    <label class="col-md-8 control-label">H2 Char Limit:</label>
                                                                    <div class="col-md-3">
                                                                        <input type="text" disabled class="form-control" name="h2CharLimit" id='h2CharLimit' value="70"/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-md-12">
                                                                    <label class="col-md-8 control-label">Page Title Char Limit:</label>
                                                                    <div class="col-md-3">
                                                                        <input type="text" disabled class="form-control" name="titleCharLimit" id='titleCharLimit' value="65"/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-md-12">
                                                                    <label class="col-md-8 control-label">Meta Description Char Limit:</label>
                                                                    <div class="col-md-3">
                                                                        <input type="text" disabled class="form-control" name="descriptionCharLimit" id='descriptionCharLimit' value="156"/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-md-12">
                                                                    <label class="col-md-8 control-label">Canonical URL Char Limit:</label>
                                                                    <div class="col-md-3">
                                                                        <input type="text" disabled class="form-control" name="canonicalCharLimit" id='canonicalCharLimit' value="115"/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group col-md-12">
                                                                    <label class="col-md-8 control-label">Image Alt Text Char Limit:</label>
                                                                    <div class="col-md-3">
                                                                        <input type="text" disabled class="form-control" name="altTextCharLimit" id='altTextCharLimit' value="100"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div> 
                                                    </div> 
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <input type="hidden" name="crawlingType" id="crawlingType" value="allPages">
                                    <input type="hidden" name="uaName" id="uaName" value="">
                                    <div class='clearfix'></div>                                    
                                </form> 
                                <div class='section'>Output: </div>
                                <div class="section-data"> 
                                    <div id='output' class="">

                                    </div>
                                </div>
                            </div>
                            <%@include file="/WEB-INF/jspf/footer.jspf"%>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="hidden">
            <button id='uploadBtn' name="uploadBtn"> c</button>
        </div>
        <%--User Agent Modal--%>
        <div class="modal fade" id="addUA" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Add New User-Agent</h4>
                    </div>
                    <div class="modal-body">
                        <form name="addUA-form" id="addUA-form" action='UserAgents' method='post'>
                            <div class="form-group">
                                <label for="id1" class=" control-label">Name:</label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i></span>
                                        <input type="text" class="form-control" name="id1" id="id1" placeholder="User-Agent Name" title="Enter User-Agent Name" data-toggle="tooltip">
                                    </div> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="uaVal" class=" control-label">Value:</label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i></span>
                                        <input type="text" class="form-control" name="uaVal" id="uaVal" placeholder="User-Agent Value" title="Enter User-Agent Value" data-toggle="tooltip">
                                    </div> 
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="">
                                    <button type="submit" class="btn btn-block btn-primary">Add </button>
                                </div>
                            </div>
                            <input type="hidden" name="q" id="q" value="add">
                        </form>
                    </div>
                </div>
            </div>
        </div>  
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="<%=serverName%>${pageContext.request.contextPath}/js/jquery.fileupload.js"></script>
        <script type="text/javascript" src="<%=serverName%>${pageContext.request.contextPath}/js/main.js"></script>        

    </body>
</html>
<script>
    $(document).ready(function() {

    });
</script>