
/////------------------------------JS for seo-options in header-----------------

$(function () {
    $("#tabs").tabs();
});
$(document).ready(function () {
    var file = $('#path').val();
    $.ajax({
        url: file,
        cache: false,
        dataType: 'json',
        success: function (result) {
            var sites = Object.keys(result);
            var notrunning = 0;
            $.each(sites, function (index, value) {
                var siteData = result[value];
                $('#result-table tbody').append('<tr></tr>');
                var lastNode = $('#result-table tbody tr:last-child');
                lastNode.append('<td>' + (index + 1) + '</td>');
                lastNode.append('<td><a href='+siteData.outputAddress+'>' + siteData.SiteUrl + '</a></td>');
                lastNode.append('<td>' + siteData.RunningStatus + '</td>');
                if (siteData.RunningStatus == 'Not Running') {
                    lastNode.addClass('alert-danger');
                    notrunning = notrunning + 1;
                }
                lastNode.append('<td>' + siteData.BranchVersion + '</td>');
                lastNode.append('<td>' + siteData.BrandName + '</td>');
                lastNode.append('<td>' + siteData.Culture + '</td>');
                lastNode.append('<td>' + siteData.Environment + '</td>');
                lastNode.append('<td>' + siteData.SiteType + '</td>');
                lastNode.append('<td>' + siteData.StatusCode + '</td>');
                lastNode.append('<td>' + siteData.StatusMsg + '</td>');
            });
            $('#not-running').text(notrunning);
            $('#running').text(sites.length - notrunning);
            $('#total').text(sites.length);
            var pieData = [
                {
                    value: notrunning,
                    color: "#F7464A",
                    highlight: "#FF5A5E",
                    label: "Not Running"
                },
                {
                    value: sites.length - notrunning,
                    color: "#46BFBD",
                    highlight: "#5AD3D1",
                    label: "Running"
                }
            ];
            var ctx = document.getElementById('chart').getContext("2d");
            var myPieChart = new Chart(ctx).Pie(pieData);
        },
        error: function (xhr) {
            console.log("An error occured: " + xhr.status + " " + xhr.statusText);
            $('#error').removeClass('hidden');
        }
    });
});
