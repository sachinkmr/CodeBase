
/////------------------------------JS for seo-options in header-----------------

$(function () {
    $("#Templates").tabs();
    $("#tabs").tabs();
});
$(function () {
    $(".acc").accordion({
        collapsible: true,
        active: false,
        heightStyle: "content"
    });
});

$('.output-nav-item').show();
$('#seo-option a').click(function () {
    $('#seo-option').find('a').removeClass('active');
});
$('#seo-option-nav a').trigger('click');

$(document).ready(function () {
    var file = $('#json').val();
    $.ajax({
        url: file,
        cache: false,
        dataType: 'json',
        success: function (result) {
            $("<tr><td>Site URL</td><td><strong>" + result.siteInfo.SiteUrl + "</strong></td></tr>").appendTo('#site-info');
            $("<tr><td>Site Running Status</td><td>" + result.siteInfo.RunningStatus + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Site Status Msg</td><td>" + result.siteInfo.StatusMsg + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Site Status Code</td><td>" + result.siteInfo.StatusCode + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Site Brand Name</td><td>" + result.siteInfo.BrandName + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Site Environment</td><td>" + result.siteInfo.Environment + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Site Culture</td><td>" + result.siteInfo.Culture + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Site Type</td><td>" + result.siteInfo.SiteType + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Site Branch Version</td><td>" + result.siteInfo.BranchVersion + "</td></tr>").appendTo('#site-info');
            $("<tr><td>Report Generation Time</td><td>" + result.siteInfo.reportGenerationTime + "</td></tr>").appendTo('#site-info');
//            $("<tr><td>Build on</td><td>" + result.siteInfo.buildTime + "</td></tr>").appendTo('#site-info');
//            $("<tr><td>Site Screenshot</td><td><a href='" + result.siteInfo.Screenshot + "' target='_blank' name='pic' id='pic'>Open screenshot</a></td></tr>").appendTo('#site-info');
            $("<tr><td>URLs with template name</td><td><a href='#TemplatesAll' id='templateFinder'>Find Templates</a></td></tr>").appendTo('#site-info');
            $('#siteImg').attr('href', result.siteInfo.Screenshot);
            $('#imageSite').attr('src', result.siteInfo.Screenshot);
            var articleKeys = Object.keys(result.article);
            var productKeys = Object.keys(result.product);
            var recipeKeys = Object.keys(result.recipe);
            var miscKeys = Object.keys(result.misc);

            $.each(articleKeys, function (index, value) {
                var val = result.article[value].split(',');
                var s = "";
                $.each(val, function (index1, value1) {
                    s = s + value1 + '<br/>';
                });
                $('#Article tbody').append("<tr><td>" + articleKeys[index] + "</td><td>" + s + "</td></tr>");
            });

            $.each(productKeys, function (index, value) {
                var val = result.product[value].split(',');
                var s = "";
                $.each(val, function (index1, value1) {
                    s = s + value1 + '<br/>';
                });
                $('#Product tbody').append("<tr><td>" + productKeys[index] + "</td><td>" + s + "</td></tr>");
            });

            $.each(recipeKeys, function (index, value) {
                var val = result.recipe[value].split(',');
                var s = "";
                $.each(val, function (index1, value1) {
                    s = s + value1 + '<br/>';
                });
                $('#Recipe tbody').append("<tr><td>" + recipeKeys[index] + "</td><td>" + s + "</td></tr>");
            });

            $.each(miscKeys, function (index, value) {
                var val = result.misc[value].split(',');
                var s = "";
                $.each(val, function (index1, value1) {
                    s = s + value1 + '<br/>';
                });
                $('#Other-Templates tbody').append("<tr><td>" + miscKeys[index] + "</td><td>" + s + "</td></tr>");
            });
        }
    });
});
$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return null;
    }
    else {
        return results[1] || 0;
    }
};
$('#urlValue').val('output/dataRepository/' + decodeURIComponent($.urlParam('host')));

$.getSign = function (name) {
    if (name === true) {
        return '<a href="javascript:void(0);" class="greenColor"><i class="fa fa-check"></i></a>';
    }
    if (name === false) {
        return '<a href="javascript:void(0);" class="redColor"><i class="fa fa-remove"></i></a>';
    } else {
        return '';
    }
};
$.getJSON = function (name) {
    return JSON.stringify(name, null, 2);
};
//$('.funtion').click(function(){
//    var link=$(this).attr('href');
////    link=link.replace('#','');
//    alert(link);
////    $(link).click();
////    var node='div#'+link;
////    alert(node);
////      if($('.acc').has(node)){
////          alert("yes");
////          
////      }
//});
//$('a#templateFinder').click(function(){
//    $('#TemplatesAll h3').click();
//});