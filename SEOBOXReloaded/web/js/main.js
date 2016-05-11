$(document).ready(function() {
    var divClone = $("#mask").clone();
    var formClone = $("#single-site-form").clone();
    var messagesWaiting = false;
    var tracker = 0, total = 0, site = "";
    $('#fileURL').fileupload({
        dataType: 'json',
        url: 'FileUploadServlet',
        type: 'POST',
        singleFileUploads: true,
        autoUpload: true,
        replaceFileInput: false,
        progress: function(e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#orangeBar').css(
                    'width',
                    progress + '%'
                    );
        },
        add: function(e, data) {
            $("#uploadBtn").unbind('click').on('click', function() {
                data.context = $('<p/>').text('Uploading...').appendTo("#orangeBar");
                data.submit();
            });
//            data.context = $('<p/>').text('Uploading...').appendTo("#orangeBar");
//            data.submit();
        },
        done: function(e, data) {
            data.context.text('Upload finished');
            var $form = $('#single-site-form');
            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');
            $('#output').empty();
            $('#output').append("Started....<br/>");
            messagesWaiting = true;
            getMessages();
            $.ajax({
                url: $form.attr('action'),
                type: 'POST',
                cache: false,
                data: $form.serialize(),
                success: function(result) {
                    messagesWaiting = false;
                    $('#single-site-form').trigger('reset');
                    bv.disableSubmitButtons(false);
                    $('#mask').css('opacity', '0.9');
                    $("#mask").html(result);
                }
            });
        }
    });
//    function getMessages() {
//        if (messagesWaiting) {
//            $.ajax({
//                url: "ShoutServlet",
//                dataType: 'json',
//                success: function(data, txt, res) {
//                    tracker = parseInt(res.getResponseHeader("tracker"), 10) + 1;
//                    total = parseInt(res.getResponseHeader("total", 10));
//                    site = res.getResponseHeader("site");
//                    $('#site').html(res.getResponseHeader("site"));
//                    if ((total === 0 && tracker === 0) || tracker < total) {
//                        $('#status').html("Processing...");
//                        $('#bar-mask').removeClass('hidden');
//                    } else {
//                        $('#status').html("Generating Output...");
//                    }
//                    var progress = parseInt(tracker / total * 100, 10);
//                    $('#orangeBar-mask').css('width', progress + '%');
//                    messagesWaiting = true;
//                    $.each(data, function() {
//                        $('#output').append(this);
//                        $('#output').append('<br/>');
//                    }); //                    
//                }
//            });
//        }
//    }
//    setInterval(getMessages, 500);
    $('#single-site-form').bootstrapValidator({
// To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        container: '.messages',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            siteURL: {
                validators: {
                    notEmpty: {
                        message: 'URL is required and cannot be empty'
                    },
                    uri: {
                        message: 'URL is not valid'
                    }
                }
            },
            fileURL: {
                validators: {
                    notEmpty: {
                        message: 'Text file is required'
                    },
                    file: {
                        extension: 'txt',
                        message: 'Please select a text file'
                    }
                }
            },
            username: {
                validators: {
                    notEmpty: {
                        message: 'Username is required'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'Password is required'
                    }
                }
            },
            timeout: {
                validators: {
                    notEmpty: {
                        message: 'Timeout is required'
                    }
                }
            },
            urlCharLimit: {
                validators: {
                    notEmpty: {
                        message: 'URL Char Limit is required'
                    }
                }
            },
            h1CharLimit: {
                validators: {
                    notEmpty: {
                        message: 'H1 Char Limit is required'
                    }
                }
            },
            h2CharLimit: {
                validators: {
                    notEmpty: {
                        message: 'H2 Char Limit is required'
                    }
                }
            },
            titleCharLimit: {
                validators: {
                    notEmpty: {
                        message: 'Title Char Limit is required'
                    }
                }
            },
            descriptionCharLimit: {
                validators: {
                    notEmpty: {
                        message: 'Description Char Limit is required'
                    }
                }
            },
            canonicalCharLimit: {
                validators: {
                    notEmpty: {
                        message: 'Canonical Char Limit is required'
                    }
                }
            },
            altTextCharLimit: {
                validators: {
                    notEmpty: {
                        message: 'Alt Text Char Limit is required'
                    }
                }
            }

        }
    }).on('success.field.bv', function(e) {
    })
            .on('success.form.bv', function(e) {
                e.preventDefault();
                var $form = $('#single-site-form');
                if ($('#tabs1').css('display') === 'block') {
                    $('#preferencesClick').click();
                }
//          Get the BootstrapValidator instance
                var bv = $form.data('bootstrapValidator');
                $('#mask').css('display', 'block');
                $('#mask').css('min-height', $('#single-site-form>div').height() + 120);
                $('#mask').width($('#sachin').width());
                if ($('#txtFile').is(':checked')) {
                    $("#uploadBtn").click();
                } else {
                    $('#output').empty();
                    $('#output').append("Started....<br/>");
//                    messagesWaiting = true;
//                    getMessages();
                    $.ajax({
                        url: $form.attr('action'),
                        type: 'POST',
                        cache: false,
                        data: $form.serialize(),
                        success: function(result) {
//                            messagesWaiting = false;
                            $('#single-site-form').trigger('reset');
                            $('#mask').css('opacity', '0.9');
//                            $('#mask').append(result);
                            bv.disableSubmitButtons(false);
                            $("#mask").html(result);
//                            $('#mask').append(result);
//                            $('#mask').css('display', 'none');
//                            $("#single-site-form").replaceWith(formClone.clone());
//                            tracker = 0, total = 0, site = "";
                        }
                    });
                }
            });

    $('#allPages').click(function() {
        $('#page').removeClass('hidden');
        $('#siteURL').removeClass('hidden');
        $('#crawlingType').prop('value', 'allPages');
        $('#txt').addClass('hidden');
        $('#fileURL').addClass('hidden');
        $('#bar').addClass('hidden');
    });
    $('#singlePage').click(function() {
        $('#page').removeClass('hidden');
        $('#siteURL').removeClass('hidden');
        $('#crawlingType').prop('value', 'singlePage');
        $('#txt').addClass('hidden');
        $('#fileURL').addClass('hidden');
        $('#bar').addClass('hidden');
    });
    $('#txtFile').click(function() {
        $('#page').addClass('hidden');
        $('#siteURL').addClass('hidden');
        $('#crawlingType').prop('value', 'txtFile');
        $('#txt').removeClass('hidden');
        $('#fileURL').removeClass('hidden');
        $('#bar').removeClass('hidden');
    });
    $('#setAuthentication').click(function() {
        if ($('#setAuthentication').is(':checked')) {
            $('#username').removeAttr('disabled');
            $('#password').removeAttr('disabled');
        } else {
            $('#username').attr('disabled', 'disabled');
            $('#password').attr('disabled', 'disabled');
        }
    });
    $('#setPerformance').click(function() {
        if ($('#setPerformance').is(':checked')) {
            $('#threads').removeAttr('disabled');
            $('#timeout').removeAttr('disabled');
        } else {
            $('#threads').attr('disabled', 'disabled');
            $('#timeout').attr('disabled', 'disabled');
        }
    });
//$('#tabs1').hide();
    $('#preferencesClick').click(function() {
//$('#tabs1').toggleClass('hidden');
        $('#tabs1').slideToggle(1000);
    });
    $('#editSEO').click(function() {
        if ($('#editSEO').is(':checked')) {
            $('#urlCharLimit').removeAttr('disabled');
            $('#h1CharLimit').removeAttr('disabled');
            $('#h2CharLimit').removeAttr('disabled');
            $('#titleCharLimit').removeAttr('disabled');
            $('#descriptionCharLimit').removeAttr('disabled');
            $('#canonicalCharLimit').removeAttr('disabled');
            $('#altTextCharLimit').removeAttr('disabled');
        } else {
            $('#urlCharLimit').attr('disabled', 'disabled');
            $('#h1CharLimit').attr('disabled', 'disabled');
            $('#h2CharLimit').attr('disabled', 'disabled');
            $('#titleCharLimit').attr('disabled', 'disabled');
            $('#descriptionCharLimit').attr('disabled', 'disabled');
            $('#canonicalCharLimit').attr('disabled', 'disabled');
            $('#altTextCharLimit').attr('disabled', 'disabled');
        }
    });
    userAgents();
    function userAgents() {
        $.ajax({
            url: 'UserAgents',
            type: 'GET',
            cache: false,
            data: 'q=getUserAgents',
            success: function(result) {
                //var agents = $.parseJSON(result);
                $('#ua').empty();
                $.each($.parseJSON(result), function(i) {
                    $('#ua').append('<option>' + i + '</option>');
                });
                $('#ua option').each(function() {
                    if ($(this).val() === 'Desktop(Windows 7) Firefox v33') {
                        $(this).attr('selected', 'true');
                        return;
                    }
                });
                userAgentValue();
                $('#ua').append('<option id=\'newAgent\'>Add New Agent...</option>');
            }
        });
    }

    $('#ua').change(function() {
        if ($("#ua option:selected").index() === $("#ua").children().length - 1) {
            $('#uaName').attr('value', '');
            $('#addUA').modal({
                keyboard: false,
                backdrop: 'static'
            });
        } else {
            userAgentValue();
        }
    });
    function userAgentValue() {
        var name = $("#ua :selected").val();
        $.post('UserAgents', 'q=value&name=' + name, function(result) {
            $('#uaName').attr('value', result);
        });
    }

    $('#addUA-form').bootstrapValidator({
// To use feedback icons, ensure that you use Bootstrap v3.1.0 or later    
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            id1: {
                validators: {
                    notEmpty: {
                        message: 'Name is required'
                    }
                }
            },
            uaVal: {
                validators: {
                    notEmpty: {
                        message: 'Value is required'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $('#addUA-form');
        var bv = $form.data('bootstrapValidator');
        $.post($form.attr('action'), $form.serialize(), function(result) {
            $('#addUA').modal('hide');
            if (result) {
                alert("Added");
                $('#ua').empty();
                userAgents();
                userAgentValue();
            } else {
                alert("Unable to add");
            }
        });
    });
    $('#fileURL').bind('fileuploadadd', function(e, data) {
        $('#bar').html("<div class='progress' ><div id='orangeBar' class='progress-bar progress-bar-info progress-bar-striped' role='progressbar' aria-valuenow='0' aria-valuemin='0' aria-valuemax='100' style='width: 0%;'></div></div>");
    });

    $('#user-tab').tab('show');

});
