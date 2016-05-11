<%-- 
    Document   : MainOutput
    Created on : 20 Jan, 2015, 7:23:51 PM
    Author     : Sachin
--%>

<!DOCTYPE html>

<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>SEO Report</title>        
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
                                    <h1>DashBoard</h1>
                                    <h4>General Insight...</h4>
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
                                            <li><a href='#tabs-7'>All Pages</a></li>
                                            <li><a href='#tabs-1'>Article</a></li>  
                                            <li><a href='#tabs-2'>Product</a></li>  
                                            <li><a href='#tabs-3'>Recipe</a></li>  
                                            <li><a href='#tabs-4'>Miscellaneous</a></li>
                                            <li><a href='#tabs-5'>Pages With Errors</a></li>
                                            <li><a href='#tabs-6'>Pages Without Errors</a></li>
                                             
                                        </ul>

                                        <div id='tabs-1'>                              
                                            <div id='accordion1'>
                                                <h3>Article Landing</h3>                            
                                                <div class='table-responsive'>                                        
                                                    <table id='Article-Landing' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>                            
                                                <h3>Article Category</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Article-Category' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                                <h3>Article Detail</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Article-Detail' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                                <h3>Other</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Other' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <div id='tabs-2'>
                                            <div id='accordion2'>
                                                <h3>Product Landing</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Product-Landing' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                                <h3>Product Category</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Product-Category' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                                <h3>Product Detail</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Product-Detail' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>	
                                            </div>
                                        </div>

                                        <div id='tabs-3'>
                                            <div id='accordion3'>
                                                <h3>Recipe Landing</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Recipe-Landing' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                    </table>
                                                </div>
                                                <h3>Recipe Detail</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Recipe-Detail' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <div id='tabs-4'>                            
                                            <div id='accordion4'>
                                                <h3>Other Templates</h3>
                                                <div class='table-responsive'>                                        
                                                    <table id='Other-Templates' class='table table-bordered table-striped table-condensed'>
                                                        <thead>
                                                            <tr class="alert-warning">
                                                                <th>#</th>
                                                                <th>URL</th>
                                                                <th>Template Name</th>
                                                                <th>Response Code</th>
                                                                <th>Response msg</th>
                                                                <th>Page has Error</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <div id='tabs-5'>                        
                                            <div class='table-responsive'>                                   
                                                <table id='errorPages' class='table table-bordered table-striped table-condensed'>
                                                    <thead>
                                                        <tr class="alert-danger">
                                                            <th>#</th>
                                                            <th>URL</th>
                                                            <th>Template Name</th>
                                                            <th>Response Code</th>
                                                            <th>Response msg</th>
                                                            <th>Page has error</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>

                                                    </tbody>
                                                </table>
                                            </div>                            
                                        </div>

                                        <div id='tabs-6'>
                                            <div class='table-responsive'>                                   
                                                <table id='all-success' class='table table-bordered table-striped table-condensed'>
                                                    <thead>
                                                        <tr class="alert-success">
                                                            <th>#</th>
                                                            <th>URL</th>
                                                            <th>Template Name</th>
                                                            <th>Response Code</th>
                                                            <th>Response msg</th>
                                                            <th>Page has error</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>

                                                    </tbody>
                                                </table>
                                            </div>                          
                                        </div>
                                        <div id='tabs-7'>
                                            <div class='table-responsive'>                                   
                                                <table id='all' class='table table-bordered table-striped table-condensed'>
                                                    <thead>
                                                        <tr class="alert-success">
                                                            <th>#</th>
                                                            <th>URL</th>
                                                            <th>Template Name</th>
                                                            <th>Response Code</th>
                                                            <th>Response msg</th>
                                                            <th>Page has error</th>
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