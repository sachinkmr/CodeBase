<%-- 
    Document   : Contact
    Created on : 24 Jan, 2015, 6:38:43 AM
    Author     : Sachin
--%>


<!DOCTYPE html>
<html lang="en">
    <head> 
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="<%=serverName%>${pageContext.request.contextPath}/css/output.css" rel="stylesheet">        
        <title>Contact Us</title>        
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
                                    <h1>Contact Us</h1>
                                    <h4>Let's get connected...</h4>
                                </div>
                            </div> 
                            <div class="" id="mask">
                                <div>
                                    <img src="img/spin.gif" class="img-responsive center-block"/>
                                    <h3>Sending Email...</h3> 
                                </div>
                            </div>
                            <div id="content">
                                <div class="section">Issue/Suggestion:</div>
                                <div class="my-row section-data"> 
                                    <div id='' class="custom-div">
                                        <div class='col-md-8' id='form-div'>
                                            <div id='response-msg'></div>
                                            <p>
                                                Have any kind of query or any suggestion? Please feel free to reach out to us.
                                            <h5>Email me at: <a href="mailto:skumar213@sapient.com">skumar213@sapient.com</a></h5> 
                                            <h3>OR</h3> 
                                            <button class="btn btn-primary" data-toggle="modal" data-target="#addUA">Fill the Contact us Form</button>
                                            </p>
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
                                    
                                    
        <div class="modal fade" id="addUA" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h3 class="modal-title"> Contact Us</h3>
                    </div>
                    <div class="modal-body">
                        <form name="contact-form" id="contact-form" action='SendMail' method='post'>
                            <div class="form-group">
                                <label for="fullname" class="control-label">Full Name <span>(Required)</span>:</label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input type="text" class="form-control" name="fullname" id="fullname" placeholder="Enter your Full Name" title="Enter your full name" data-toggle="tooltip">
                                    </div> 
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="mail" class=" control-label">Email ID <span>(Required)</span>:</label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                        <input type="text" class="form-control" name="mail" id="mail" placeholder="Enter your Email ID" title="Enter your Email ID" data-toggle="tooltip">
                                    </div> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="subject" class="control-label">Subject:</label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                                        <input type="text" class="form-control" name="subject" id="subject" placeholder="Subject of Email " title="Enter subject of your email" data-toggle="tooltip">
                                    </div> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="mobile" class="control-label">Mobile: </label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                                        <input maxlength="10" type="text" class="form-control" id="mobile" name="mobile" placeholder="Provide mobile number" title="Provide mobile number" data-toggle="tooltip">
                                    </div> 
                                </div>
                            </div> 

                            <div class="form-group">
                                <label for="msg" class="control-label">Message <span>(Required)</span>:</label>
                                <div class="input-group col-md-12">
                                    <textarea class="form-control" rows='7' name="msg" id="msg" title="What is on your mind?" placeholder="What is on your mind?" data-toggle="tooltip"></textarea>
                                </div>
                            </div>
                            <%--
                            <div class="form-group">
                                <label class=" control-label">Fill the Captcha</label>
                                <div class="captchaContainer">
                                    <div id="recaptcha"></div>
                                </div>
                            </div>
                            --%>
                            <div class="form-group">
                                <div class="input-group">                                                
                                    <button type="submit" class="btn btn-block btn-primary"><i class="glyphicon glyphicon-send"></i>&nbsp;&nbsp; Send Message</button>
                                </div>
                            </div>
                            <%-- <input type="hidden" id="captcha-key" name="captcha-key" value='<%=request.getServletContext().getInitParameter("ReCaptchaPublicKey")%>'>
                            --%>
                        </form>
                    </div>
                </div>
            </div>
        </div>  
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>

        <script>
            $('#contact-form').bootstrapValidator({
                feedbackIcons: {
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    fullname: {
                        validators: {
                            notEmpty: {
                                message: 'Name is required and cannot be empty'
                            }
                        }
                    },
                    mail: {
                        validators: {
                            notEmpty: {
                                message: 'Email is required and cannot be empty'
                            },
                            emailAddress: {
                                message: 'The value is not a valid email address'
                            }
                        }
                    },
                    msg: {
                        validators: {
                            notEmpty: {
                                message: 'Message is required'
                            }
                        }
                    },
                    mobile: {
                        validators: {
                            integer: {
                                message: 'The value is not an integer'
                            }
                        }
                    }
                }
            }).on('success.form.bv', function(e) {
                $('#mask').css('display', 'block');
                e.preventDefault();
                // Get the form instance
                var $form = $(e.target);
                // Get the BootstrapValidator instance
                var bv = $form.data('bootstrapValidator');
                $('#addUA').modal('hide');
                $.post($form.attr('action'), $form.serialize(), function(result) {
                    $('#mask').css('display', 'none');
                    $('#response-msg').empty();
                    $('#response-msg').html();
                    $('#response-msg').append(result);
                });
            });

        </script>
    </body>
</html>