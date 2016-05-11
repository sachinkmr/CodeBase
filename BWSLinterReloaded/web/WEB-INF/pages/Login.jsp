<%-- 
    Document   : index
    Created on : 20 Jan, 2015, 5:26:01 PM
    Author     : Sachin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/isUserAuthenticated.jspf"%>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet"> 
        <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet"> 
        <title>Admin Login</title>      
        <%if (isUserAuthenticated) {
                response.sendRedirect("admin");
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
                                    <h4>Please login to access as Admin</h4>
                                </div>
                            </div>                           
                            <div id="content" class="">                                
                                <div class="container">
                                    <div class="col-md-offset-3 col-md-4" id="LoginID" >
                                        <h1 class="text-center login-title">Sign in to continue</h1>
                                        <div class="account-wall">
                                            <img class="profile-img" src="img/login.png"
                                                 alt="">
                                            <p class="profile-name">BWS Linter Admin</p>
                                            <form class="form-signin" id="Login-form" autocomplete="off" method="post">
                                                <input type="password" class="form-control" placeholder="Password" required name="password" id="password">
                                                <button class="btn btn-lg btn-primary btn-block" type="submit">
                                                    Sign in</button>
                                                <a href="mailto:skumar213@sapient.com" class="need-help">Need help? </a><span class="clearfix"></span>
                                            </form>
                                        </div>                                            
                                    </div>                                                                 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>        
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin.js"></script>
    </body>
</html>
