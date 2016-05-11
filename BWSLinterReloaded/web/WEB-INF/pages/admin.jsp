<%-- 
    Document   : index
    Created on : 20 Jan, 2015, 5:26:01 PM
    Author     : Sachin
--%>
<%@page import="java.util.List"%>
<%@page import="java.io.FileReader"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="sachin.sapient.unilever.bws.site.ConfigDev"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.File"%>
<%@page import="sachin.sapient.unilever.bws.site.HelperClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/isUserAuthenticated.jspf"%>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="${pageContext.request.contextPath}/css/bootstrap-clockpicker.min.css" rel="stylesheet"> 
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet"> 
        <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet"> 
        <title>Admin</title>      
        <%if (!isUserAuthenticated) {
                response.sendRedirect("Login");
            }%>
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
                            <div id="content" class="">
                                <div id="tabs">  
                                    <h3> BWS Linter Configuration  </h3>  
                                    <div class="data"> 
                                        <form name="linterForm" action="AddNewSiteServlet" method="post">
                                            <%
                                                JSONObject contact = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "config.json");
                                            %>
                                            <input type='hidden' value='yes' name='linter' id='linter'>
                                            <input type='hidden' value='yes' name='update' id='update'>                                          
                                            <div class="col-md-12"> 
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" data-placement="bottom" title ="Enter config Dev URL " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-globe" aria-hidden="true"></i></i> &nbsp;Config Dev URL
                                                            </span>                                        
                                                            <input type="text" class="form-control" placeholder="http://example.com" id="configDevURL" name="configDevURL"  value='<%=contact.getString("ConfigDevUrl")%>'>
                                                        </div>                                            
                                                    </div>
                                                </div>  
                                            </div>
                                            <div class="col-md-6"> 
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter config Dev Username " data-toggle="tooltip" >
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-user" aria-hidden="true"></i></i> &nbsp;Config Dev Username
                                                            </span>                                       
                                                            <input data-placement="bottom" type="text" class="form-control"  id="configDevUsername" name="configDevUsername" value='<%=contact.getString("ConfigDevUsername")%>'>
                                                        </div>                                            
                                                    </div>
                                                </div>  
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter password for config Dev " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-pencil" aria-hidden="true"></i></i> &nbsp;Config Dev Password
                                                            </span>                                        
                                                            <input value='<%=contact.getString("ConfigDevPassword")%>' type="text" class="form-control"  id="configDevPassword" name="configDevPassword" >
                                                        </div>                                            
                                                    </div>
                                                </div>  
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter desktop user agent string" data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-camera" aria-hidden="true"></i></i> &nbsp;Desktop User Agent
                                                            </span>                                        
                                                            <input value='<%=contact.getString("desktopUserAgent")%>' type="text" class="form-control"  id="desktopUserAgent" name="desktopUserAgent" >
                                                        </div>                                            
                                                    </div>
                                                </div>   
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter tablet User Agent " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-phone-alt" aria-hidden="true"></i></i> &nbsp;Tablet User Agent
                                                            </span>                                        
                                                            <input value='<%=contact.getString("tabletUserAgent")%>' type="text" class="form-control"  id="tabletUserAgent" name="tabletUserAgent">
                                                        </div>                                            
                                                    </div>
                                                </div>   
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter mobile User Agent " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-phone" aria-hidden="true"></i></i> &nbsp;Mobile User Agent
                                                            </span>                                        
                                                            <input value='<%=contact.getString("mobileUserAgent")%>' type="text" class="form-control"  id="mobileUserAgent" name="mobileUserAgent">
                                                        </div>                                            
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter Request Timeout in seconds." data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-time" aria-hidden="true"></i></i> &nbsp;Request Timeout
                                                            </span>                                        
                                                            <input value='<%=contact.getString("Timeout")%>' type="text" class="form-control"  id="Timeout" name="Timeout">
                                                        </div>                                            
                                                    </div>
                                                </div>   
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">                                    
                                                    <div class="controls"> 
                                                        <input value='<%=contact.getString("threads")%>' type="hidden" name='threadHidden' id='threadHidden'>                                                        
                                                        <div class="input-group" title="Select No of Threads" data-toggle="tooltip">
                                                            <span class="input-group-addon"><i class="glyphicon glyphicon-tasks"></i> &nbsp;Threads:</span>                                            
                                                            <select class="form-control"  name="threads" id="threads">
                                                                <option>1</option>
                                                                <option>2</option>
                                                                <option>3</option>   
                                                                <option>4</option>   
                                                                <option>5</option>   
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
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group clockpicker"  title ="Select time to run utility at scheduled time. Utility will run automatically on weekdays on selected time. " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-time" aria-hidden="true"></i></i> &nbsp;Scheduled Time
                                                            </span>                                        
                                                            <input value='<%=contact.getString("runningHoursIn24fromat") + ":" + contact.getString("runningMinIn24fromat")%>' type="text" class="form-control"  id="time" name="time">
                                                        </div>                                            
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter host Machine address" data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-cloud" aria-hidden="true"></i></i> &nbsp;Host Machine
                                                            </span>                                       
                                                            <input value='<%=contact.getString("hostMachine")%>' type="text" onclick='alert("Donot modify, until you know the correct value to modify");' class="form-control"  id="hostMachine" name="hostMachine">
                                                        </div>                                            
                                                    </div>
                                                </div>  
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group"  title ="Enter output address " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></i></i> &nbsp;Host Machine Output
                                                            </span>                                       
                                                            <input value='<%=contact.getString("outputAddressWithHostMachine")%>' onclick='alert("Donot modify, until you know the correct value to modify");' type="text" class="form-control"  id="outputAddressWithHostMachine" name="outputAddressWithHostMachine">
                                                        </div>                                            
                                                    </div>
                                                </div>   
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group"  title ="Enter app Pool Url " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-globe" aria-hidden="true"></i></i> &nbsp;App Pool URL
                                                            </span>                                        
                                                            <input value='<%=contact.getString("appPoolUrl")%>' type="text" class="form-control"  id="appPoolUrl" name="appPoolUrl">
                                                        </div>                                            
                                                    </div>
                                                </div>  
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter username for app Pool Username" data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-user" aria-hidden="true"></i></i> &nbsp;App Pool Username
                                                            </span>                                       
                                                            <input value='<%=contact.getString("appPoolUsername")%>' type="text" class="form-control"  id="appPoolUsername" name="appPoolUsername">
                                                        </div>                                            
                                                    </div>
                                                </div>   
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">                                    
                                                    <div class="controls">                                                
                                                        <div class="input-group" title ="Enter password for appPool " data-toggle="tooltip">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-pencil" aria-hidden="true"></i></i> &nbsp;App Pool Password
                                                            </span>                                        
                                                            <input value='<%=contact.getString("appPoolPassword")%>' type="text" class="form-control"  id="appPoolPassword" name="appPoolPassword">
                                                        </div>                                            
                                                    </div>
                                                </div>   
                                            </div>                                           

                                            <div class="col-md-12">
                                                <button type="submit" class="btn btn-primary">Update BWS Linter Configuration</button>          
                                            </div>                                            
                                            <div class="clearfix"></div>                                     
                                        </form>
                                    </div>
                                    <h3> Example Sites:  </h3>   
                                    <div class="data"> 
                                        <div class="table-responsive">
                                            <div class="addNew"> <button class="btn btn-primary right" data-toggle="modal" data-target="#addNewSite">Add New Site</button></div>                                   
                                            <table class="table table-bordered table-striped table-condensed">                                        
                                                <thead>
                                                    <tr>
                                                        <th>SN</th>      
                                                        <th>Site</th>
                                                        <th>Edit</th>
                                                        <th>Delete</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%JSONObject object = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "exampleSites.json");
                                                        JSONArray a = object.names();
                                                        for (int i = 0; i < a.length(); i++) {
                                                            String name = (String) a.get(i);
                                                    %>
                                                    <tr>
                                                        <td><%=i + 1%></td>      
                                                        <td><%=name%></td>
                                                        <td><input type="hidden" class="sites" value="<%=name%>"><button data-toggle="modal" data-target="#editSite" title='Edit/View site data' class='edit'><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button></td>
                                                        <td><a class='delete' onclick="return confirm('This will delete the data, are you sure?');" href='AdminSite?site=<%=name%>&param=delete'><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> </a></td>
                                                    </tr>
                                                    <%
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div> 
                                    <h3> Contact Us Data:  </h3>  
                                    <div class="data"> 
                                        <form name="ContactData">
                                            <%
                                                contact = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "ContactUsData.json");
                                                String res = contact.getJSONObject("Responsive").toString();
                                                String nr = contact.getJSONObject("NonResponsive").toString();
                                            %>
                                            <input type="hidden" name="contact">
                                            Responsive: <br/>
                                            <textarea class="form-control col-md-12" rows="5" id="responsive" name="responsive"><%=res%></textarea><br/>
                                            Non-Responsive: <br/>
                                            <textarea class="form-control col-md-12" rows="5" id="nonresponsive" name="nonresponsive"><%=nr%></textarea>
                                        </form>
                                    </div>
                                    <h3> Sign Us Data:  </h3>   
                                    <div class="data"> 
                                        <form name="signupData">
                                            <%
                                                contact = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "signUpData.json");
                                                res = contact.getJSONObject("Responsive").toString();
                                                nr = contact.getJSONObject("NonResponsive").toString();
                                            %>
                                            <input type="hidden" name="signup">
                                            Responsive: <br/>
                                            <textarea class="form-control col-md-12" rows="5" id="responsive" name="responsive"><%=res%></textarea><br/>
                                            Non-Responsive: <br/>
                                            <textarea class="form-control col-md-12" rows="5" id="nonresponsive" name="nonresponsive"><%=nr%></textarea>
                                        </form>
                                    </div>
                                    <h3> Email Configuration  </h3>
                                    <%
                                        contact = HelperClass.readJsonFromFile(HelperClass.getDataFolderPath() + File.separator + "mail.json");
                                    %>
                                    <div class="data">
                                        <form id="emailForm" name="emailForm" method="post" action="AddNewSiteServlet">
                                            <div class="col-md-12"> 
                                                <div class="col-md-12 row content-row" id="siteURL"> 
                                                    <div class="col-md-3"> 
                                                        <strong>Emails: </strong>
                                                    </div>
                                                    <div class="col-md-8"> 
                                                        <div class="form-group">                                    
                                                            <div class="controls">                                                
                                                                <div class="input-group">
                                                                    <span class="input-group-addon">
                                                                        <span class="glyphicon glyphicon-inbox" aria-hidden="true"></span>
                                                                    </span>                                        
                                                                    <input data-placement="bottom" type="text" class="form-control" placeholder="http://example.com" id="email" name="email" title ="Enter email id to deliever reports. Multiple emails can be saperated by comma" data-toggle="tooltip" value='<%=contact.getString("to")%>'>
                                                                </div>                                            
                                                            </div>
                                                        </div>  
                                                    </div>
                                                </div>
                                                <div class="row col-md-12 content-row">
                                                    <div class="col-md-3">
                                                        <strong>Mail Subject: </strong>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <div class="form-group">                                    
                                                            <div class="controls">                                                
                                                                <div class="input-group">
                                                                    <span class="input-group-addon">
                                                                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                                                    </span>                                        
                                                                    <input value='<%=contact.getString("subject")%>' type="text" class="form-control" placeholder="Subject" id="subject" name="subject" title ="Enter subject of the mail" data-toggle="tooltip">
                                                                </div>                                            
                                                            </div>
                                                        </div>   
                                                    </div>
                                                    <div class="col-md-3">    
                                                        <strong>Mail Body: </strong>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <div class="form-group">                                    
                                                            <div class="controls">                                                
                                                                <div class="input-group col-md-12">
                                                                    <textarea title="Enter the mail body. You can add HTML tags" class="form-control" rows="5" id="mailbody" name="mailbody" data-toggle="tooltip"><%=contact.getString("message")%></textarea>
                                                                </div>                                            
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">             </div>  
                                                    <div class="col-md-8">         
                                                        <button type="submit" class="btn btn-primary">Update Mail Configuration</button>          
                                                    </div> 
                                                </div>                                           
                                                <input type='hidden' value='yes' name='mail' id='mail'>
                                                <input type='hidden' value='yes' name='update' id='update'>   
                                                <div class="clearfix"></div>
                                            </div>                                    
                                        </form>
                                    </div> 
                                    <h3> Templates Data  </h3>  
                                    <div class="data"> 
                                        <form id="templateForm" name="templateForm" method="post" action="AddNewSiteServlet">
                                            <%
                                                FileReader reader = new FileReader(HelperClass.getDataFolderPath() + File.separator + "templates.txt");
                                                List<String> list = IOUtils.readLines(reader);
                                                reader.close();
                                            %>             
                                            <div class="col-md-12">
                                                <div class="col-md-5">    
                                                    <strong>Template Names: </strong>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="form-group">                                    
                                                        <div class="controls">                                                
                                                            <div class="input-group col-md-12">
                                                                <textarea title="Enter template names used in BWS Sites(Responsive, Non-responsive, MOS). New template should be added in new Line" class="form-control" rows="8" id="templateName" name="templateName" data-toggle="tooltip"><%=list%></textarea>
                                                            </div>                                            
                                                        </div>
                                                    </div>
                                                </div>                                                    
                                                <div class="col-md-8">         
                                                    <button type="submit" class="btn btn-primary">Update Templates Names</button>          
                                                </div> 
                                                <input type='hidden' value='yes' name='template' id='template'>
                                                <input type='hidden' value='yes' name='update' id='update'>   
                                                <div class="clearfix"></div>
                                            </div>                                    
                                        </form>
                                    </div>
                                    <h3> Bazaar Voice Data:  </h3> 
                                    <div class="data"> 

                                    </div>
                                    <h3> Search/Virtual Agent Data:  </h3> 
                                    <div class="data"> 

                                    </div>
                                    <h3> User Management Data:  </h3> 
                                    <div class="data"> 

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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-clockpicker.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin.js"></script>

        <!--Add site modal-->
        <div class="modal fade" id="addNewSite" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Add New Site</h4>
                    </div>
                    <div class="modal-body">
                        <form id="addNewSiteForm" name="addNewSiteForm" method="post" action="AddNewSiteServlet" class='site'>
                            <input type='hidden' value='yes' name='insert' id='insert'>
                            <input type='hidden' value='yes' name='sites' id='sites'>
                            <div class="col-md-12"> 
                                <div class="col-md-12 row" id="siteURL"> 
                                    <div class="col-md-3"> 
                                        <strong>BWS Site URL: </strong>
                                    </div>
                                    <div class="col-md-9"> 
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter site url starting with protocol" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-globe" aria-hidden="true"></i></i> &nbsp;Site URL
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="http://example.com" id="url11" name="url11">
                                                </div>                                            
                                            </div>
                                        </div>  
                                    </div>
                                </div>
                                <div class="row col-md-12">
                                    <div class="col-md-3">
                                        <div class='checkbox'>    
                                            <label id="auth-label" >
                                                <input type="checkbox" name="setAuthentication" id="setAuthentication" class="setAuthentication"> Authentication:
                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>                                                                      
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-md-5">                                        
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group"  title ="Enter LDAP username for site" data-toggle="tooltip" >                                                        
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-user" aria-hidden="true"></i></i> &nbsp;UserName
                                                    </span>  
                                                    <input type="text" class="form-control" disabled placeholder="Username" id="username1" name="username1"value="wlnonproduser">
                                                </div>                                            
                                            </div>
                                        </div>  
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter LDAP password for site" data-toggle="tooltip" >
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-pencil" aria-hidden="true"></i></i> &nbsp;Password
                                                    </span>                                                                                          
                                                    <input type="password" class="form-control" disabled placeholder="Password" id="password1" name="password1" value="Pass@word11">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter keyword for site search" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-search" aria-hidden="true"></i></i> &nbsp;Site Search
                                                    </span>                                                                                          
                                                    <input type="text" class="form-control" placeholder="tea" id="siteSearch1" name="siteSearch1" >
                                                </div>                                            
                                            </div>
                                        </div>
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter keyword for virtual agent search" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-search" aria-hidden="true"></i></i> &nbsp;Virtual Agent
                                                    </span>                                                                                          
                                                    <input type="text" class="form-control" placeholder="product" id="virtualAgent1" name="virtualAgent1">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12 row content-row">
                                    <div class="col-md-3"><strong>BWS Site Configuration: </strong></div>

                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Brand Name" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i> &nbsp;Brand</span>                                            
                                                    <select class="form-control brand"  name="brand1" id="brand1">
                                                        <option>Choose Brand</option>
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter keyword for recipe search" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-search" aria-hidden="true"></i></i> &nbsp;Recipe Search
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="tea" id="recipeSearch1" name="recipeSearch1">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>

                                    <div class="clearfix"></div>
                                    <div class="col-md-3">&nbsp; </div>
                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Environment Name" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-file"></i> &nbsp;Environment</span>                                            
                                                    <select class="form-control environment"  name="environment1" id="environment1">
                                                        <option>Choose Environment</option>
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter zip code for store locator" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-cog" aria-hidden="true"></i></i> &nbsp;Store Locator
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="ZIP" id="storeLocatorZIP1" name="storeLocatorZIP1">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">    </div>
                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Culture Name" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i> &nbsp;Culture</span>                                            
                                                    <select class="form-control culture"  name="culture1" id="culture1">
                                                        <option>Choose Culture</option>    
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group"  title ="enter site pub id for site" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-cog" aria-hidden="true"></i></i> &nbsp;Site PubID
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="Site PubID" id="sitepubid1" name="sitepubid1">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">    </div>
                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Environment for Recycle App Cache" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-list-alt"></i> &nbsp;App Cache Env</span>                                            
                                                    <select class="form-control"  name="appPoolEnv1" id="appPoolEnv1">
                                                        <option  value="DEV">DEV</option>
                                                        <option value="QA">QA</option>
                                                        <option value="UAT">UAT</option>
                                                        <option selected="true" value="Example-QA">Example-QA</option>
                                                        <option value="Product-QA">Product-QA</option>
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select User Agent" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i> &nbsp;User Agent</span>                                            
                                                    <select class="form-control"  name="ua1" id="ua1">
                                                        <option>Select UA</option>  
                                                        <option value="desktop">Desktop</option> 
                                                        <option value="tablet">Tablet</option> 
                                                        <option value="mobile">Mobile</option> 
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">    </div>
                                    <div class="col-md-9">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group"  title ="enter site name for app pool recycle" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-globe" aria-hidden="true"></i></i> &nbsp;App Pool Site
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="app pool site" id="appPoolSite1" name="appPoolSite1">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                </div>                                
                                <div class="pull-right">
                                    <button type="submit" class="btn btn-primary">Add Site Data</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                </div>
                            </div>

                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>



        <!-- Edit Site info modal-->
        <div class="modal fade" id="editSite" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Edit Site Details</h4>
                    </div>
                    <div class="modal-body">
                        <form id="editSiteForm" name="editSiteForm" method="post" action="AddNewSiteServlet" class='site'>
                            <input type='hidden' value='yes' name='update' id='update'>                            
                            <input type='hidden' value='yes' name='sites' id='sites'>
                            <div class="col-md-12"> 
                                <div class="col-md-12 row" id="siteURL"> 
                                    <div class="col-md-3"> 
                                        <strong>BWS Site URL: </strong>
                                    </div>
                                    <div class="col-md-9"> 
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter site url starting with protocol" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-globe" aria-hidden="true"></i></i> &nbsp;Site URL
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="http://example.com" id="url1" name="url1">
                                                </div>                                            
                                            </div>
                                        </div>  
                                    </div>
                                </div>
                                <div class="row col-md-12">
                                    <div class="col-md-3">
                                        <div class='checkbox'>    
                                            <label id="auth-label" >
                                                <input type="checkbox" name="setAuthentication" id="setAuthentication" class="setAuthentication"> Authentication:
                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>                                                                      
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-md-5">                                        
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group"  title ="Enter LDAP username for site" data-toggle="tooltip" >                                                        
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-user" aria-hidden="true"></i></i> &nbsp;UserName
                                                    </span>  
                                                    <input type="text" class="form-control" disabled placeholder="Username" id="username" name="username"value="wlnonproduser">
                                                </div>                                            
                                            </div>
                                        </div>  
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter LDAP password for site" data-toggle="tooltip" >
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-pencil" aria-hidden="true"></i></i> &nbsp;Password
                                                    </span>                                                                                          
                                                    <input type="password" class="form-control" disabled placeholder="Password" id="password" name="password" value="Pass@word11">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter keyword for site search" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-search" aria-hidden="true"></i></i> &nbsp;Site Search
                                                    </span>                                                                                          
                                                    <input type="text" class="form-control" placeholder="tea" id="siteSearch" name="siteSearch" >
                                                </div>                                            
                                            </div>
                                        </div>
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter keyword for virtual agent search" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-search" aria-hidden="true"></i></i> &nbsp;Virtual Agent
                                                    </span>                                                                                          
                                                    <input type="text" class="form-control" placeholder="product" id="virtualAgent" name="virtualAgent">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12 row content-row">
                                    <div class="col-md-3"><strong>BWS Site Configuration: </strong></div>

                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Brand Name" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i> &nbsp;Brand</span>                                            
                                                    <select class="form-control brand"  name="brand" id="brand">
                                                        <option>Choose Brand</option>
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter keyword for recipe search" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-search" aria-hidden="true"></i></i> &nbsp;Recipe Search
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="tea" id="recipeSearch" name="recipeSearch">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>

                                    <div class="clearfix"></div>
                                    <div class="col-md-3">&nbsp; </div>
                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Environment Name" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-file"></i> &nbsp;Environment</span>                                            
                                                    <select class="form-control environment"  name="environment" id="environment">
                                                        <option>Choose Environment</option>
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group" title ="Enter zip code for store locator" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-cog" aria-hidden="true"></i></i> &nbsp;Store Locator
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="ZIP" id="storeLocatorZIP" name="storeLocatorZIP">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">    </div>
                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Culture Name" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i> &nbsp;Culture</span>                                            
                                                    <select class="form-control culture"  name="culture" id="culture">
                                                        <option>Choose Culture</option>    
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group"  title ="enter site pub id for site" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-cog" aria-hidden="true"></i></i> &nbsp;Site PubID
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="Site PubID" id="sitepubid" name="sitepubid">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">    </div>
                                    <div class="col-md-5">                                                            
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select Environment for Recycle App Cache" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-list-alt"></i> &nbsp;App Cache Env</span>                                            
                                                    <select class="form-control"  name="appPoolEnv" id="appPoolEnv">
                                                        <option  value="DEV">DEV</option>
                                                        <option value="QA">QA</option>
                                                        <option value="UAT">UAT</option>
                                                        <option selected="true" value="Example-QA">Example-QA</option>
                                                        <option value="Product-QA">Product-QA</option>
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">                                    
                                            <div class="controls">                                            
                                                <div class="input-group" title="Select User Agent" data-toggle="tooltip">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i> &nbsp;User Agent</span>                                            
                                                    <select class="form-control"  name="ua" id="ua">
                                                        <option>Select UA</option>  
                                                        <option value="desktop">Desktop</option> 
                                                        <option value="tablet">Tablet</option> 
                                                        <option value="mobile">Mobile</option> 
                                                    </select>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">    </div>
                                    <div class="col-md-9">
                                        <div class="form-group">                                    
                                            <div class="controls">                                                
                                                <div class="input-group"  title ="enter site name for app pool recycle" data-toggle="tooltip">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-globe" aria-hidden="true"></i></i> &nbsp;App Pool Site
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="app pool site" id="appPoolSite" name="appPoolSite">
                                                </div>                                            
                                            </div>
                                        </div>
                                    </div>                                
                                    <div class="pull-right">
                                        <button type="submit" class="btn btn-primary">Update Site Data</button>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                    </div>                
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
