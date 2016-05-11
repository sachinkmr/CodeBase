<%-- 
    Document   : HistoryPage
    Created on : 22 Jan, 2015, 1:42:29 AM
    Author     : Sachin
--%>

<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/jqueryFileTree.css" rel="stylesheet">
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>Archive</title>
        <style type="text/css">
            #history{
                padding: 15px 0 15px 20px;
            }
        </style>
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
                                    <h1>Archive</h1>
                                    <h4>View Old Reports...</h4>
                                </div>
                            </div>                            
                            <div id="content">
                                <div class="section">History:</div>
                                <div class="section-data my-row">
                                    <p>Please select the site name followed by its report generation date and then time. </p>
                                    <div id='history' class="custom-div">
                                        
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jqueryFileTree.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#history').fileTree({
                    script: 'History',
                    folderEvent: 'click',
                    expandSpeed: 750,
                    collapseSpeed: 750,
                    multiFolder: false
                });
            });
        </script>
    </body>
</html>