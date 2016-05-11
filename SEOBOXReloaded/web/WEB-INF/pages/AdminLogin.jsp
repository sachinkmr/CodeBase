<%-- 
    Document   : URL
    Created on : 20 Jan, 2015, 10:58:17 PM
    Author     : Sachin
--%>

<%
    String serverName="http://"+InetAddress.getLocalHost().getHostAddress()+":"+request.getLocalPort();
    session.setAttribute("serverName", serverName);
%>
<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">  
        <style type="text/css">

            #login-form {                
                width:70%;
                position: absolute;
                left: 30%;
                top: 40%;
                margin-left: -110px;
                margin-top: -75px;
                -webkit-animation: login 1s ease-in-out;
                -moz-animation: login 1s ease-in-out;
                -ms-animation: login 1s ease-in-out;
                -o-animation: login 1s ease-in-out;
                animation: login 1s ease-in-out;
            }

            #avtar{
                border-radius: 6px;
                width:150px;
            }


            /* CSS Animations */
            @keyframes "login" {
                0% {
                    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
                    filter: alpha(opacity=0);
                    opacity: 0;
                    margin-top: -50px;
                }
                100% {
                    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";
                    filter: alpha(opacity=100);
                    opacity: 1;
                    margin-top: -75px;
                }
            }
            @-moz-keyframes login {
                0% {
                    filter: alpha(opacity=0);
                    opacity: 0;
                    margin-top: -50px;
                }
                100% {
                    filter: alpha(opacity=100);
                    opacity: 1;
                    margin-top: -75px;
                }
            }
            @-webkit-keyframes "login" {
                0% {
                    filter: alpha(opacity=0);
                    opacity: 0;
                    margin-top: -50px;
                }
                100% {
                    filter: alpha(opacity=100);
                    opacity: 1;
                    margin-top: -75px;
                }
            }
            @-ms-keyframes "login" {
                0% {
                    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
                    filter: alpha(opacity=0);
                    opacity: 0;
                    margin-top: -50px;
                }
                100% {
                    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";
                    filter: alpha(opacity=100);
                    opacity: 1;
                    margin-top: -75px;
                }
            }
            @-o-keyframes "login" {
                0% {
                    filter: alpha(opacity=0);
                    opacity: 0;
                    margin-top: -50px;
                }
                100% {
                    filter: alpha(opacity=100);
                    opacity: 1;
                    margin-top: -75px;
                }
            }
            #myModal .modal-header{
                border-top-left-radius:  6px;
                border-top-right-radius:  6px;
            }
        </style>
        <title>Login</title>        
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
                                    <h1>Login</h1>
                                    <h4>Let's Authenticate</h4>
                                </div>
                            </div>                            
                            <div id="content">
                                <div class="section">Login</div>
                                <div class="section-data my-row"> 
                                    <div id="msg"> </div>
                                    <form id="login-form" class="form-horizontal" action="Login" method="post">
                                        <div class="col-md-3 text-center">
                                            <img src='img/login.png' class="img-responsive" id="avtar" />                                            
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <div class="col-sm-12">
                                                    <input type="text" class="form-control" placeholder="Username" id="user" name="user">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-sm-12">
                                                    <input type="password" class="form-control" placeholder="Password" id="pass" name="pass">
                                                </div>
                                            </div>
                                            <button class="btn btn-primary col-md-4" type="submit">Log in</button>
                                        </div>
                                    </form>
                                </div>                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-sm modal-dialog">
                <div class="modal-content">
                    <div class="modal-header btn-danger">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>                            
                        <h4 class="modal-title " id="myModalLabel">
                            Error...!!!
                        </h4>
                    </div>
                    <div class="modal-body">
                        <div class= row">
                            Username or Password are not correct. Please provide valid credentials.
                        </div>
                        <br/>
                        <button type="button" class="btn btn-primary" data-dismiss="modal">OK </button>   
                    </div>                    
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->        
            <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
            <script type="text/javascript" src="<%=serverName%>${pageContext.request.contextPath}/js/output.js"></script>    
            <script type="text/javascript">             $('#login-form').bootstrapValidator({
                    // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later    
                    feedbackIcons: {
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        user: {
                            validators: {
                                notEmpty: {
                                    message: 'Username is required'
                                }
                            }
                        },
                        pass: {
                            validators: {
                                notEmpty: {
                                    message: 'Password is required'
                                }
                            }
                        }
                    }
                }).on('success.form.bv', function(e) {
                    e.preventDefault();
                    var $form = $('#login-form');
                    var bv = $form.data('bootstrapValidator');
                    $.post($form.attr('action'), $form.serialize(), function(result, txt, res) {
                        var error = res.getResponseHeader("error");
                        if (error === 'true') {
                            $('#myModal').modal({
                                keyboard: true,
                                backdrop: 'static'
                            });
                        } else {
                            window.location.replace("Admin");
                        }
                    });
                });
            </script>  
    </body>
</html>