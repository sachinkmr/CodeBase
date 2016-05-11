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
        <title>BWS Linter</title>        
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
                            <div class="" id="mask">
                                <div class="col-md-6 col-md-offset-3 mask-data">
                                    <img src="img/spin.gif" class="img-responsive center-block"/>
                                    <h3 id="status">Processing...</h3> 
                                    <div class="hidden" id='bar-mask'>
                                        <div class="progress" >
                                            <div id="orangeBar-mask" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div>
                                        </div>  
                                    </div> 
                                </div>
                                <div class='clearfix'></div>
                            </div>
                            <div id="content" class="">                                
                                <form id="sitesForm" name="sitesForm" action="getInfo" method="POST" >  
                                    <div id="tabs1" class=''> 
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#custom" data-toggle="tab">BWS Site<i class="fa"></i></a></li>
                                            <li class=""><a href="#user-tab" data-toggle="tab">Performance<i class="fa"></i></a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="custom">
                                                <div class="tab-pane-data col-md-12"> 
                                                    <div class="col-md-12 row content-row" id="siteURL"> 
                                                        <div class="col-md-2"> 
                                                            <strong>BWS Site URL: </strong>
                                                        </div>
                                                        <div class="col-md-10"> 
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                                
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon">
                                                                            <span class="glyphicon glyphicon-globe" aria-hidden="true"></span>
                                                                        </span>                                        
                                                                        <input type="text" class="form-control" placeholder="http://example.com" id="url" name="url" title ="Enter site url starting with protocol" data-toggle="tooltip">
                                                                    </div>                                            
                                                                </div>
                                                            </div>  
                                                        </div>
                                                    </div>
                                                    <div class="row col-md-12 content-row">
                                                        <div class="col-md-2">
                                                            <div class='checkbox'>    
                                                                <label id="auth-label" class="auth-label">
                                                                    <input type="checkbox" name="setAuthentication" id="setAuthentication" class="setAuthentication"> Authentication:
                                                                    <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>                                                                      
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-5">
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
                                                        </div>
                                                        <div class="col-md-5">
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
                                                    </div>
                                                    <div class="col-md-12 row content-row">
                                                        <div class="col-md-2"><strong>BWS Site Configuration: </strong></div>
                                                        <div class="col-md-5">                                                            
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                            
                                                                    <div class="input-group" title="If selected, whole site will be crawled freshly, no previously saved data will be used" data-toggle="tooltip">
                                                                        <div class="checkbox">
                                                                            <label class="crawler-label" for="crawler">
                                                                                <input type="checkbox" name="crawler" id="crawler"> &nbsp; Crawl whole site, already saved data will not be used
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div>
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                        <div class="clearfix"></div>
                                                        <div class="col-md-2">&nbsp; </div>
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
                                                        <div class="clearfix"></div>
                                                        <div class="col-md-2">&nbsp; </div>
                                                        <div class="col-md-5">                                                            
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                            
                                                                    <div class="input-group" title="Select User Agent" data-toggle="tooltip">
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-file"></i> &nbsp;User Agent</span>                                            
                                                                        <select class="form-control"  name="ua" id="ua">
                                                                            <option>Choose user Agent</option>
                                                                            <option value="desktop">Desktop</option> 
                                                                            <option value="tablet">Tablet</option> 
                                                                            <option value="mobile">Mobile</option> 
                                                                        </select>
                                                                    </div> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row col-md-12 content-row">
                                                        <div class="col-md-2"><strong>Email ID: </strong></div>
                                                        <div class="col-md-10">
                                                            <div class="form-group">                                    
                                                                <div class="controls">                                                
                                                                    <div class="input-group" title ="Enter email id/ids to get report. Saperate multiple ids by comma" data-toggle="tooltip">
                                                                           <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i> &nbsp;Email ID</span>                                                                                 
                                                                        <input type="text" class="form-control" placeholder="email ID" id="mail" name="mail" >
                                                                    </div>                                            
                                                                </div>
                                                            </div>   
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12 row content-row">
                                                        <div class="col-md-2"><strong>Choose BWS Functionalities:</strong></div>
                                                        <div class="col-md-10 row ">                                                                                                                         
                                                            <div class="col-md-4">
                                                                <ul>                                                                    
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='signup' id='Signup' class="sitesAttributes" > &nbsp; Signup 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='UM' id='UM' class="sitesAttributes" > &nbsp; User Management 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div>  
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='SiteSearch' id='SiteSearch' class="sitesAttributes" > &nbsp; Site Search 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='RecipeSearch' id='RecipeSearch' class="sitesAttributes" > &nbsp; Recipe Search 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='RecipeRatings' id='RecipeRatings' class="sitesAttributes" > &nbsp; Recipe Rating 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <ul>                                                                    
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='BV' id='BV' class="sitesAttributes" > &nbsp; Bazaar Voice 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='Kritique' id='Kritique' class="sitesAttributes" > &nbsp; Kritique 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div>  
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='analytics' id='analytics' class="sitesAttributes" > &nbsp; Analytics 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div>  
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='BVSEO' id='BVSEO' class="sitesAttributes" > &nbsp; BV SEO 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div>  
                                                                    </li>                                                                    
                                                                </ul>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <ul>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='BuyOnline' id='BuyOnline' class="sitesAttributes" > &nbsp; Buy Online 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='WTB' id='WTB' class="sitesAttributes" > &nbsp; Store Locator 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='VirtualAgent' id='VirtualAgent' class="sitesAttributes" > &nbsp; Virtual Agent
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div>    
                                                                    </li>
                                                                    <li>
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name='Contactus' id='Contactus' class="sitesAttributes" > &nbsp; Contact Us 
                                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                                            </label>
                                                                        </div> 
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>                                                   
                                            </div>
                                            <div class="tab-pane " id="user-tab">
                                                <div class="tab-pane-data col-md-12"> 
                                                    <div id='performance' class='col-md-4'>                                                           
                                                        <div class="checkbox">                                                                               
                                                            <label id="performanceLable">
                                                                <input type="checkbox" name="setPerformance" id="setPerformance"> Edit Performance &emsp;
                                                                <span class="cr"><i class="cr-icon glyphicon glyphicon-ok"></i></span>
                                                            </label>   
                                                        </div> 
                                                        <div class="form-group">                                    
                                                            <div class="controls">                                            
                                                                <div class="input-group" title="Select No of Threads" data-toggle="tooltip">
                                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-tasks"></i> &nbsp;Threads:</span>                                            
                                                                    <select class="form-control"  name="threads" id="threads" disabled>
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
                                            <div class='clearfix'></div>
                                            <div class=" col-md-5">
                                                <button type="submit" class="btn btn-primary col-md-8 row">Get Information</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>                                
                            </div>                            
                        </div>
                        <%@include file="/WEB-INF/jspf/footer.jspf"%>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
    </body>
</html>
