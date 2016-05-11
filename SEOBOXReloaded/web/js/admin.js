/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('#addTemp-form').bootstrapValidator({
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
        }
    }
}).on('success.form.bv', function(e) {
    e.preventDefault();
    var $form = $('#addTemp-form');
    var bv = $form.data('bootstrapValidator');
    $.post($form.attr('action'), $form.serialize(), function(result) {
        $('#addTemp').modal('hide');
        loadTemplates();
    });
});

$('#addTemp-button').click(function() {
    $('#addTemp').modal({
        keyboard: false,
        backdrop: 'static'
    });
});


function loadTemplates() {
    $.ajax({
        url: "LoadTemplate",
        dataType: 'json',
        success: function(data, txt, res) {
            var error = res.getResponseHeader("error");
            $('#template-table').html('<thead><tr><td>SN</td><td>Template Name</td><td>Delete</td></tr></thead><tbody></tbody>');
            if (error === 'true') {
                $('#msg').append(data);
            } else {
                $.each(data, function(key, val)
                {
                    var a=parseInt(key);
                    $('#template-table').append('<tr><td>' + ++a + '</td><td>' + val + '</td><td><a href="DeleteTemplate?name='+val+'" class="btn btn-danger delete"><i class="fa fa-trash"></i>&ensp; Delete</a></td></tr>');
                });

            }
        }
    });
}
function loadReports() {
    $.ajax({
        url: "LoadReports",
        dataType: 'json',
        success: function(data, txt, res) {
            var error = res.getResponseHeader("error");
             $('#report-table').html('<thead><tr><td>SN</td><td>Site Name</td><td>Date </td><td>Time</td><td>Delete Report</td></tr></thead><tbody></tbody>');
            if (error === 'true') {
                $('#msg').append(data);
            } else {                
                for(var i=0;i<data.length;i++){                    
                    $('#report-table').append('<tr><td>'+(i+1)+'</td><td>' + data[i].report.split('\\')[0] + '</td><td>' + data[i].report.split('\\')[1] + '</td><td>' + data[i].report.split('\\')[2] + '</td><td><a href="DeleteReport?name='+data[i].reportPath+'" class="btn btn-danger delete"><i class="fa fa-trash"></i>&ensp; Delete</a></td></tr>');
                }
            }
        }
    });
}
$(document).ready(function() {
    loadTemplates();
    loadReports();
});