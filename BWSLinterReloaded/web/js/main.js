$(document).ready(function () {
    $('#performanceLable').click(function () {
        if ($('#setPerformance').is(':checked')) {
            $('#threads').removeAttr('disabled');
            $('#timeout').removeAttr('disabled');
        } else {
            $('#threads').attr('disabled', 'disabled');
            $('#timeout').attr('disabled', 'disabled');
        }
    });

    $('#auth-label').click(function () {
        if ($('#setAuthentication').prop('checked')) {
            $('#username').attr('disabled', false);
            $('#password').attr('disabled', false);
        } else {
            $('#username').attr('disabled', true);
            $('#password').attr('disabled', true);
        }
    });

    $('#sitesForm').bootstrapValidator({
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            password: {
                validators: {
                    notEmpty: {
                        message: 'Password is required'
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
            mail: {
                validators: {
                    notEmpty: {
                        message: 'email is required'
                    }
                }
            },
            brand: {
                validators: {
                    callback: {
                        message: 'Please select brand',
                        callback: function (value, validator, $field) {
                            return $("#brand")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            environment: {
                validators: {
                    callback: {
                        message: 'Please select environment',
                        callback: function (value, validator, $field) {
                            return $("#environment")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            culture: {
                validators: {
                    callback: {
                        message: 'Please select culture',
                        callback: function (value, validator, $field) {
                            return $("#culture")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            ua: {
                validators: {
                    callback: {
                        message: 'Please select user agent',
                        callback: function (value, validator, $field) {
                            return $("#ua")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            url: {
                trigger: 'blur',
                validators: {
                    uri: {
                        message: 'The website address is not valid'
                    }
                }
            }
        }
    }).on('success.field.bv', '[name="url"]', function (e, data) {
//        $('#mask').css('display', 'block');
//        $('#mask').css('min-height', $('#content').height() + 100);
//        $('#mask').width($('#sachin').width());
//        $('#status').html("Please Wait....");
//        var data = {};
//        data['address'] = $('#url').val();
//        data['siteInfo'] = "no";
//        $.ajax({
//            url: 'GetSiteInfoForPage',
//            type: 'POST',
//            cache: false,
//            data: data,
//            dataType: 'json',
//            success: function (results) {
//                var error = results.hasError;
//                if (error == "Yes") {
//                    if (results.errors != "") {
//                        $('#status').html("Unable to find site info, Please choose manually");
////                        $('#status').prepend(results.errors + "<br/>")
//                        $('#mask').delay(4000)
//                                .queue(function (next) {
//                                    $(this).css('display', 'none');
//                                    next();
//                                    $('#sitesForm')[0].reset();
//                                    $('#url').val(data.address);
//                                });
//                    }
//                } else {
//                    $("#brand").val(results.brands);
//                    $('#environment').val(results.env);
//                    $('#culture').val(results.cultures);
//                    $('#mask').css('display', 'none');
//                    $('.demo').html(results);
//                }
//            },
//            error: function (results) {
//                if (results.errors != "") {
//                    $('#status').html("Unable to find site info, Please choose manually");
////                    $('#status').prepend(results.errors + "<br/>")
//                    $('#mask').delay(4000)
//                            .queue(function (next) {
//                                $(this).css('display', 'none');
//                                next();
//                                $('#sitesForm')[0].reset();
//                                $('#url').val(data.address);
//                            });
//                }
//            }
//        });
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        $('#mask').css('display', 'block');
        $('#mask').css('min-height', $('#content').height() + 100);
        $('#mask').width($('#sachin').width());
        $('#status').html("Please Wait....<br/>Report is being generated. You can close this window, you will get an email when process will completed.");
        var $form = $('#sitesForm');
        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');
        $.ajax({
            url: $form.attr('action'),
            type: 'POST',
            cache: false,
            data: $form.serialize(),
            dataType: 'json',
            success: function (result) {
                if (result.hasError == false) {
                    $('#status').html("Report has been generated. Follow the link<br/><a href='"+result.add+"'>"+result.add+"</a>");
                } else {
                    $('#status').html("Something went wrong  <br/>:(");
                }
            },
            error:{
                
            }
        });
    });
});

$('.sitesAttributes').click();
//--------------------------------------------------------------------------------------------------
