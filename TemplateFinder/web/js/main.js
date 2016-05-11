$(document).ready(function () {
    $.ajax({
        url: 'GetTemplatesList',
        type: 'POST',
        cache: false,
        success: function (result) {
            var jsn = $.parseJSON(result).templates;
            $.each(jsn, function (key, value) {
                $('#template').append('<option>' + value + '</option>');
            });
        }
    });

    $('#single-site-form').bootstrapValidator({
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            siteURL: {
                validators: {
                    notEmpty: {
                        message: 'Site name is required'
                    },
                    uri: {
                        message: 'The URL is not valid'
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
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        var $form = $('#single-site-form');
//        if ($('#tabs1').css('display') === 'block') {
//            $('#preferencesClick').click();
//        }
        $('#templateTable tbody').html('');
        $('#mask').css('display', 'block');
        $('#mask').css('min-height', $('#single-site-form>div').height() + 130);
        $('#mask').width($('#sachin').width());
        $.ajax({
            url: $form.attr('action'),
            type: 'POST',
            cache: false,
            data: $form.serialize(),
            success: function (result) {
                $('#single-site-form').trigger('reset');
                $('#mask').css('display', 'none');
                $('#reports').css('display', 'block');
                var jsn = $.parseJSON(result);
                $.each(jsn, function (key, value) {
                    var x = value.split(',');
                    var s = "";
                    $.each(x, function (index1, value1) {
                        s = s + value1 + '<br/>';
                    });
                    $('#templateTable tbody').append('<tr><td>' + key + '</td><td>' + s + '</td></tr>');
                   
                });
            },
            error: function (request, status, error) {
                $('#single-site-form').trigger('reset');
                $('#mask').css('display', 'none');
                $('#errorDiv').css('display', 'block');
//                $('#preferencesClick').click();
            }
        });
    });


    $('#selectTemplate').click(function () {
        if ($('#selectTemplate').is(':checked')) {
            $('#template').removeAttr('disabled');
        } else {
            $('#template').attr('disabled', 'disabled');
        }
    });
    $('#setAuthentication').click(function () {
        if ($('#setAuthentication').is(':checked')) {
            $('#username').removeAttr('disabled');
            $('#password').removeAttr('disabled');
        } else {
            $('#username').attr('disabled', 'disabled');
            $('#password').attr('disabled', 'disabled');
        }
    });

    $('#setPerformance').click(function () {
        if ($('#setPerformance').is(':checked')) {
            $('#threads').removeAttr('disabled');
            $('#timeout').removeAttr('disabled');
        } else {
            $('#threads').attr('disabled', 'disabled');
            $('#timeout').attr('disabled', 'disabled');
        }
    });

    $('#preferencesClick').click(function () {
        $('#tabs1').slideToggle(1000);
    });
    $('#user-tab').tab('show');
});
