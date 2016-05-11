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
        <title>FAQs</title>        
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
                                    <h1>FAQs</h1>
                                    <h4>Frequently Asked Questions</h4>
                                </div>
                            </div>                            
                            <div id="content">                                
                                <div class="section">Questions:</div>
                                <div class="my-row section-data"> 
                                    <div id='' class="custom-div">
                                        <h2>Coming Soon...</h2>     
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
      
    </body>
</html>