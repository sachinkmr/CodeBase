$(document).ready(function () {
    if ($(window).height() > $('#main1').height())
        $('#sidebar, #main1').css('min-height', $(window).height());
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
});

var data = {};
data['siteInfo'] = "yes";
$.ajax({
    url: 'GetSiteInfoForPage',
    type: 'POST',
    cache: false,
    data: data,
    dataType: 'json',
    success: function (result) {
        $.each(result.brands, function (index, value) {
            $('.brand').append('<option>' + value + '</option>');
        });
        $.each(result.env, function (index, value) {
            $('.environment').append('<option>' + value + '</option>');
        });
        $.each(result.cultures, function (index, value) {
            $('.culture').append('<option>' + value + '</option>');
        });
    }
});
