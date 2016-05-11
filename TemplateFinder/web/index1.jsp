<%-- 
    Document   : index
    Created on : 20 Jan, 2015, 5:26:01 PM
    Author     : Sachin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet"> 
        <title>Template Finder</title>        
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
                                            <div id="orangeBar-mask" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div>
                                        </div>  
                                    </div> 
                                </div>
                                <div class='clearfix'></div>
                            </div>
                            <div id="content">                                                       
                                <form id="single-site-form" name="single-site-form" action="Verify" method="POST" enctype="multipart/form-data">                                     
                                    <div class="excelDiv" id='txt' name='txt'>
                                        <div for="fileURL" class="section">Enter URL:</div>
                                        <div class="section-data row"> 
                                            <div class="col-md-8 form-group" >                                            
                                                <div class="input-group form-section">
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                                    </span>                                        
                                                    <input type="text" class="form-control" placeholder="Enter site URL" id="fileURL" name="fileURL" title ="Enter site URL" data-toggle="tooltip">
                                                    <span class="input-group-btn">
                                                        <button class="btn btn-primary" type="submit">Submit</button>
                                                    </span>                                        
                                                </div>                                                   
                                                <div class='clearfix'></div>
                                                <span class="messages"></span>
                                            </div>
                                            <div class="col-md-4 hidden" id='bar'>
                                                <div class="progress" >
                                                    <div id="orangeBar" class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>
                                                </div>  
                                            </div>     
                                        </div>
                                    </div>                                                                        
                                    <div class="clearfix"></div>
                                    <div class='section' id="preferencesTxt"><a href="javascript:void(0);" id="preferencesClick" name="preferencesClick">Search Preferences</a> <i class="fa fa-arrow-down"></i> </div>
                                    <div class="section-data my-row">
                                        <div id="tabs1" class=''> 
                                            <ul class="nav nav-tabs">
                                                <li class="active"><a href="#user-tab" data-toggle="tab">User Preferences<i class="fa"></i></a></li>
                                               
                                            </ul>
                                            <div class="tab-content">
                                                <div class="tab-pane active" id="user-tab">
                                                    <div class="tab-pane-data">    
                                                        <div id='preferences' class='col-md-4'>                                                       
                                                            <div class="form-group"> 
                                                                <lable for="emails" class="labelClass">Emails to deliver report:</lable>
                                                                <div class="controls">                                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon">
                                                                            <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                                                                        </span>                                        
                                                                        <input type="text" class="form-control" placeholder="emails" id="emails" name="emails" title ="Enter emails to get reports, multiple emails can be saperated by comma(,)" data-toggle="tooltip">
                                                                    </div>                                            
                                                                </div>
                                                            </div>                                                            
                                                        </div>
                                                        <div id='authentication' class='col-md-4'>
                                                            <div >
                                                                <label  class="myLable">
                                                                    <input type="checkbox" name="setAuthentication" id="setAuthentication"> Authentication &emsp;
                                                                </label>                                
                                                            </div> 
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon">
                                                                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                                                        </span>                                        
                                                                        <input type="text" class="form-control" disabled placeholder="Username" id="username" name="username" title ="Enter LDAP username for site" data-toggle="tooltip">
                                                                    </div>                                            
                                                                </div>
                                                            </div>   
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon">
                                                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                                                        </span>                                        
                                                                        <input type="password" class="form-control" disabled placeholder="Password" id="password" name="password" title ="Enter LDAP password for site" data-toggle="tooltip">
                                                                    </div>                                            
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div id='performance' class='col-md-4'>
                                                            <div >
                                                                <label  class="myLable">
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
                                                                        <input type="text" class="form-control" disabled placeholder="timeout" id="timeout" name="timeout" value="20">
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                                
                                            </div>
                                        </div>
                                    </div>    
                                    <input type="hidden" name="filePath" id="filePath" value="" class="hidden">
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
            <button id='uploadBtn' name="uploadBtn">c</button>
        </div>

        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>        

    </body>
</html>
<script>
    $(document).ready(function () {

    });
</script>