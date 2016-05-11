$(document).ready(function () {
    $('#threads').val( $('#threadHidden').val());
    
    $('#Login-form').bootstrapValidator({
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
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        var $form = $('#Login-form');
        var bv = $form.data('bootstrapValidator');
        var data = {};
        data['password'] = $('#password').val();
        $.ajax({
            url: 'LoginServlet',
            type: 'POST',
            cache: false,
            data: data,
            dataType: 'json',
            success: function (results) {
                if (results.value == true) {
                    alert("Login Successful, Redirecting...!!!");
                    window.location.replace("admin");
                } else {
                    alert("Invalid Password, Login Failed");
                }
            }
        });
    });

    $('#newSite').click(function () {
        $('#addNewSite').removeClass('hidden');
    });

    $('.edit').click(function () {
        $('#editSite').removeClass('hidden');
        var site = $(this).siblings('.sites').attr('value');
        var data = {};
        data['exampleSite'] = site;
        $.ajax({
            url: 'AdminSite',
            type: 'POST',
            cache: false,
            data: data,
            dataType: 'json',
            success: function (results) {
                $('#editSite #url1').val(site);
                $('#editSite #sitepubid').val(results.sitePubId);
                $('#editSite #setAuthentication').click();
                $('#editSite #username').val(results.username);
                $('#editSite #password').val(results.password);                
                $('#editSite #sitepubid').val(results.sitePubId);
                $('#editSite #appPoolEnv').val(results.appPoolEnv);
                $('#editSite #appPoolSite').val(results.appPoolSite);                
                $('#editSite #siteSearch').val(results.siteSearch);
                $('#editSite #storeLocatorZIP').val(results.storeLocatorZIP);
                $('#editSite #recipeSearch').val(results.recipeSearch);
                $('#editSite #virtualAgent').val(results.virtualAgent);
                var x = false;
                while (x) {
                    if ($('#editSite #brand1 option').length > 2) {
                        x = true;
                    }
                }
                $('#editSite #brand').val(results.brand);
                $('#editSite #culture').val(results.culture);
                $('#editSite #environment').val(results.environment);
            }
        });
    });


    $('div#LoginID').fadeIn(2000);
    $(function () {
        $("#tabs").accordion({
            collapsible: true,
			active: false,
            heightStyle: "content"
        });
    });
    $('#addNewSite').modal({
        keyboard: false,
        backdrop: 'static',
        show: false
    });

    $('#editSiteForm').bootstrapValidator({
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
            url1: {
                validators: {
                    uri: {
                        message: 'The website address is not valid'
                    }
                }
            },
            appPoolSite: {
                validators: {
                    notEmpty: {
                        message: 'The website address can\'t be empty'
                    }
                }
            },
            virtualAgent: {
                validators: {
                    notEmpty: {
                        message: 'The keyword can\'t be empty'
                    }
                }
            },
            storeLocatorZIP: {
                validators: {
                    notEmpty: {
                        message: 'The store locator zip code can\'t be empty'
                    }
                }
            },
            recipeSearch: {
                validators: {
                    notEmpty: {
                        message: 'The keyword can\'t be empty'
                    }
                }
            },
            siteSearch: {
                validators: {
                    notEmpty: {
                        message: 'The keyword can\'t be empty'
                    }
                }
            },
            sitepubid: {
                validators: {
                    integer: {
                        message: 'The value is not an integer'
                    },
                    callback: {
                        message: 'Value should be greater than 0',
                        callback: function (value, validator, $field) {
                            return $("#sitepubid").val().trim() > 0;
                        }
                    }
                }
            }
        }
    });

    $('#addNewSiteForm').bootstrapValidator({
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            password1: {
                validators: {
                    notEmpty: {
                        message: 'Password is required'
                    }
                }
            },
            username1: {
                validators: {
                    notEmpty: {
                        message: 'Username is required'
                    }
                }
            },
            brand1: {
                validators: {
                    callback: {
                        message: 'Please select brand',
                        callback: function (value, validator, $field) {
                            return $("#brand1")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            environment1: {
                validators: {
                    callback: {
                        message: 'Please select environment',
                        callback: function (value, validator, $field) {
                            return $("#environment1")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            culture1: {
                validators: {
                    callback: {
                        message: 'Please select culture',
                        callback: function (value, validator, $field) {
                            return $("#culture1")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            ua1: {
                validators: {
                    callback: {
                        message: 'Please select user agent',
                        callback: function (value, validator, $field) {
                            return $("#ua1")[0].selectedIndex != 0;
                        }
                    }
                }
            },
            url11: {
                validators: {
                    uri: {
                        message: 'The website address is not valid'
                    }
                }
            },
            appPoolSite1: {
                validators: {
                    notEmpty: {
                        message: 'The website address can\'t be empty'
                    }
                }
            },
            
            virtualAgent1: {
                validators: {
                    notEmpty: {
                        message: 'The keyword can\'t be empty'
                    }
                }
            },
            storeLocatorZIP1: {
                validators: {
                    notEmpty: {
                        message: 'The store locator zip code can\'t be empty'
                    }
                }
            },
            recipeSearch1: {
                validators: {
                    notEmpty: {
                        message: 'The keyword can\'t be empty'
                    }
                }
            },
            siteSearch1: {
                validators: {
                    notEmpty: {
                        message: 'The keyword can\'t be empty'
                    }
                }
            },
            sitepubid1: {
                validators: {
                    integer: {
                        message: 'The value is not an integer'
                    },
                    callback: {
                        message: 'Value should be greater than 0',
                        callback: function (value, validator, $field) {
                            return $("#sitepubid1").val() > 0;
                        }
                    }
                }
            }
        }
    });

    $('#emailForm').bootstrapValidator({
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'email is required'
                    }
                }
            },
            subject: {
                validators: {
                    notEmpty: {
                        message: 'subject is required'
                    }
                }
            },
            mailbody: {
                validators: {
                    notEmpty: {
                        message: 'Body is required'
                    }
                }
            }
        }
    });


    $('#addNewSite #auth-label').click(function () {
        if ($('#addNewSite #setAuthentication').prop('checked')) {
            $('#addNewSite #username1').attr('disabled', false);
            $('#addNewSite #password1').attr('disabled', false);
        } else {
            $('#addNewSite #username1').attr('disabled', true);
            $('#addNewSite #password1').attr('disabled', true);
        }
    });
    $('#editSite #auth-label').click(function () {
        if ($('#editSite #setAuthentication').prop('checked')) {
            $('#editSite #username').attr('disabled', false);
            $('#editSite #password').attr('disabled', false);
        } else {
            $('#editSite #username').attr('disabled', true);
            $('#editSite #password').attr('disabled', true);
        }
    });
    $('.clockpicker').clockpicker({
        placement: 'bottom',
        align: 'left',
        autoclose: true
    });

});

