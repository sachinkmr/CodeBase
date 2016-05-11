$(document).ready(function() {
    if ($(window).height() > $('#main1').height())
       $('#sidebar, #main1').css('min-height',$(window).height());
    $(function() {
        $('[data-toggle="tooltip"]').tooltip();
    });
});

$('.output-nav-item').hide();
