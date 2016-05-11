<%-- 
    Document   : URL
    Created on : 20 Jan, 2015, 10:58:17 PM
    Author     : Sachin
--%>



<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
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
                                    <h1>User Guide</h1>
                                    <h4>Let's get started...</h4>
                                </div>
                            </div>                            
                            <div id="content">
                                <div class="section">Features:</div>
                                <div class="section-data my-row features row"> 
                                    <div class="col-md-6"> 
                                        <p><strong>Key Features:-</strong></p>
                                        <ul>
                                            <li><i class="fa fa-hand-o-right"></i>Interactive Dash Board with pie-Charts.</li>
                                            <li><i class="fa fa-hand-o-right"></i>Single Page, All Pages SEO Report</li>
                                            <li><i class="fa fa-hand-o-right"></i>Batch mode for more than one sites.</li>
                                            <li><i class="fa fa-hand-o-right"></i>Controllable URL Redirection Support</li>
                                            <li><i class="fa fa-hand-o-right"></i>Predefined as well as Customized User-Agents</li>
                                            <li><i class="fa fa-hand-o-right"></i>LDAP Support</li>
                                            <li><i class="fa fa-hand-o-right"></i>Configurable Request Timeout</li>
                                            <li><i class="fa fa-hand-o-right"></i>History of Previous Reports</li>
                                            <li><i class="fa fa-hand-o-right"></i>Configurable SEO Tags Options</li>
                                            <li><i class="fa fa-hand-o-right"></i>Detailed SEO Tags Analysis of Each Page</li>  
                                            <li><i class="fa fa-hand-o-right"></i>Unilever BWS Site Information</li>  
                                        </ul>
                                    </div>
                                    <div class="col-md-6"> 
                                        <p><strong>Other Features:-</strong></p>
                                        <ul>
                                            <li><i class="fa fa-hand-o-right"></i>H1 - Missing, Duplicate, Over Characters, Multiple</li>
                                            <li><i class="fa fa-hand-o-right"></i>H2 - Missing, Duplicate, Over Characters, Multiple</li>
                                            <li><i class="fa fa-hand-o-right"></i>Page Title - Missing, Duplicate, Over Characters, Multiple</li>
                                            <li><i class="fa fa-hand-o-right"></i>Meta Description - Missing, Duplicate, Over Characters, Multiple</li>
                                            <li><i class="fa fa-hand-o-right"></i>Meta Keywords - Missing, Over Characters</li>
                                            <li><i class="fa fa-hand-o-right"></i>Canonical Link - Missing, Over Character, Multiple</li>
                                            <li><i class="fa fa-hand-o-right"></i>URL - Over Character</li>
                                            <li><i class="fa fa-hand-o-right"></i>Alt Text - Missing, Over Character from Images</li>
                                            <li><i class="fa fa-hand-o-right"></i>Errors - Client and server errors like No responses, 4XX, 5XX</li>
                                            <li><i class="fa fa-hand-o-right"></i>Redirects - 3XX, Permanent or Temporary</li>
                                            <li><i class="fa fa-hand-o-right"></i>User-Agent Switcher</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="section">Get Started:</div>
                                <div class="my-row section-data"> 
                                    <div class="guide-title">Provide URLs</div>
                                    <div class="guide-content">
                                        <ul>
                                            <li><span>Single Mode: </span> This is configurable as to run the test on whole site or single page. Choose the appropriate option. 
                                                <div class="guide-image">
                                                    <img src="GuideImages/1.png" class="img-responsive img-thumbnail"/>
                                                </div>
                                            </li>
                                            <li><span>Batch Mode: </span> To test more than one site, put all the URLs in a text file, and select <strong>URL from Text File</strong> radio button. Each URL should be in next line. Whole site will be scanned for each URL. 
                                                <div class="guide-image"><img src="GuideImages/2.png" class="img-responsive img-thumbnail">
                                                </div>
                                            </li>
                                        </ul> 
                                    </div>
                                    <div class="guide-title">Search Preferences</div>
                                    <div class="guide-content">
                                        <ul>
                                            <li><span>User Preferences: </span>
                                                <ul>
                                                    <li><span>Enable Redirection: </span>It is used to follow the url redirections if selected.</li>
                                                    <li><span>Stay on Site: </span>This option is BWS Specific. If selected, site will not be redirected to its locale specific site. </li>
                                                    <li><span>User-Agent: </span>Choose your user agent from predefined list or set your own user-agent string by selecting <strong>Add New Agent...</strong> </li>
                                                    <li><span>Authentication: </span>Check this box if site is LDAP or authentication enabled. Provide username and password for your site. </li>
                                                    <li><span>Edit Performance: </span>This option has two options: Number of Threads and Timeout. Request timeout is in seconds.</li>
                                                </ul>
                                                <div class="guide-image">
                                                    <img src="GuideImages/3.png" class="img-responsive img-thumbnail"/>
                                                </div>
                                            </li>  
                                            <li><span>SEO Options: </span> This gives the flexibility to modify the SEO parameters.                                              
                                                <div class="guide-image">
                                                    <img src="GuideImages/4.png" class="img-responsive img-thumbnail"/>
                                                </div>
                                            </li>
                                        </ul>  
                                    </div>
                                    <div class="guide-title">Output</div>
                                    <div class="guide-content">
                                        <ul>
                                            <li><span> </span> This contains the output messages returned from the server. Like Errors or report's location.
                                                <div class="guide-image">
                                                    <img src="GuideImages/5.png" class="img-responsive img-thumbnail"/>
                                                </div>
                                            </li>
                                        </ul>  
                                    </div>
                                    <div class="guide-title">Left Menu</div>
                                    <div class="guide-content">
                                        <ul>
                                            <li><span>Home: </span>Redirects to home page of the application.</li>
                                            <li><span>Archives: </span>To view previously generated SEO reports</li>
                                            <li><span>User Guide: </span>Learn, how to use this App.</li>
                                            <div class="guide-image">
                                                <img src="GuideImages/6.png" class="img-responsive img-thumbnail"/>
                                            </div>
                                        </ul>  
                                    </div>
                                    <div class="guide-title">SEO Report</div>
                                    <div class="guide-content">
                                        <ul>
                                            <li>Report contains detail information about SEO testing results. Just select the tag name from the left menu and the detailed information can be view in the right side of the panel with pie-charts. If any URL is clicked, it will redirect to more detailed information of the page on SEO Errors.</li>                                            
                                            <div class="guide-image">
                                                <img src="GuideImages/7.png" class="img-responsive img-thumbnail"/>
                                            </div>
                                        </ul>  
                                    </div>
                                    <div class="guide-title">Archives</div>
                                    <div class="guide-content">
                                        <ul>
                                            <li>To view previous reports, just select site name followed by it execution date and time and then click on view report.</li>                                            
                                            <div class="guide-image">
                                                <img src="GuideImages/8.png" class="img-responsive img-thumbnail"/>
                                            </div>
                                        </ul>  
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