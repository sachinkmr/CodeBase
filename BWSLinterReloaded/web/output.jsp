<%-- 
    Document   : output
    Created on : Aug 19, 2015, 10:52:40 AM
    Author     : sku202
--%>

<%@page import="java.net.URL"%>
<%@page import="sachin.bws.site.HelperClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        <link href="css/output.css" rel="stylesheet">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <title>Report</title>
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
                                    <h1>Site Report</h1>
                                    <h4>General Insight...</h4>
                                </div>
                            </div>
                            <div id="content">
                                <div class="section">
                                    Overview:
                                </div>
                                <div class="section-data my-row">
                                    <div class="row" id="all-option">
                                        <div id="header-panel-content" class="col-md-5">
                                            <table class="table table-bordered table-hover" id="site-info">                                                

                                            </table>
                                            <input type="hidden" name='branch' id='branch'>
                                        </div>
                                        <div class="col-md-3">
                                            <table class="table table-bordered table-hover table-condensed" id="site-status">                                                
                                                <thead>
                                                    <tr>
                                                        <th>Functionality</th>
                                                        <th>Enabled</th>
                                                        <th>Working</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr id="Analytics">
                                                        <td><a href="#Analytics" class="funtion">Analytics</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="Signup">
                                                        <td><a href="#Signup" class="funtion">Sign up</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="SiteSearch">
                                                        <td><a href="#SiteSearch" class="funtion">Site Search</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="BVSEO">
                                                        <td><a href="#BVSEO" class="funtion">BV SEO</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="BazaarVoice">
                                                        <td><a href="#BazaarVoice" class="funtion">Bazaar Voice</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="BuyOnline">
                                                        <td><a href="#BuyOnline" class="funtion">Buy Online</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="ContactUs">
                                                        <td><a href="#ContactUs" class="funtion">Contact Us</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="Traction">
                                                        <td><a href="#Traction" class="funtion">Traction</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="Kritique">
                                                        <td><a href="#Kritique" class="funtion">Kritique</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="RecipeRating">
                                                        <td><a href="#RecipeRating" class="funtion">Recipe Rating</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="RecipeSearch">
                                                        <td><a href="#RecipeSearch" class="funtion">Recipe Search</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="StoreLocator">
                                                        <td><a href="#StoreLocator" class="funtion">Store Locator</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="UserManagement">
                                                        <td><a href="#UserManagement" class="funtion">User Management</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>
                                                    <tr id="VirtualAgent">
                                                        <td><a href="#VirtualAgent" class="funtion">Virtual Agent</a></td>
                                                        <td class="enabled"></td>
                                                        <td class="working"></td>
                                                    </tr>                                                    
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-md-3">
                                            <a href="" id='siteImg' name='siteImg' target="_blank" title="view image"><img src='' id='imageSite' class="img-responsive status-img" name='imageSite' alt='site screenshot' title='screenshot'/></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="section">
                                    Detailed Report:
                                </div>
                                <div class="my-row section-data">
                                    <input type="hidden" value="" id="urlValue" name="urlValue">

                                    <div class="col-md-6"> 
                                        <div class="acc" id="Analytics">
                                            <h3>Analytics</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/Analytics.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#Analytics .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#Analytics .working').html($.getSign(!result.hasErrors));
                                                                $('div#Analytics table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#Analytics table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#Analytics table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#Analytics table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc " id="Signup">
                                            <h3>Sign Up</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/SignUp.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#Signup .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#Signup .working').html($.getSign(!result.hasErrors));
                                                                $('div#Signup table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#Signup table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');
                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#Signup table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#Signup table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="SiteSearch">
                                            <h3>Site Search</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/SiteSearch.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#SiteSearch .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#SiteSearch .working').html($.getSign(!result.hasErrors));
                                                                $('div#SiteSearch table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#SiteSearch table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#SiteSearch table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#SiteSearch table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="ContactUs">
                                            <h3>Contact Us</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/ContactUs.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#ContactUs .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#ContactUs .working').html($.getSign(!result.hasErrors));
                                                                $('div#ContactUs table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#ContactUs table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#ContactUs table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#ContactUs table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="BVSEO">
                                            <h3>BVSEO</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/BVSEO.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#BVSEO .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#BVSEO .working').html($.getSign(!result.hasErrors));
                                                                $('div#BVSEO table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#BVSEO table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#BVSEO table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#BVSEO table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="Traction">
                                            <h3>Traction</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/DataCaptureTraction.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#Traction .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#Traction .working').html($.getSign(!result.hasErrors));
                                                                $('div#Traction table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#Traction table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#Traction table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#Traction table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="Kritique">
                                            <h3>Kritique</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/Kritique.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#Kritique .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#Kritique .working').html($.getSign(!result.hasErrors));
                                                                $('div#Kritique table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#Kritique table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#Kritique table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#Kritique table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="RecipeRating">
                                            <h3>Recipe Rating</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/RecipeRating.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#RecipeRating .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#RecipeRating .working').html($.getSign(!result.hasErrors));
                                                                $('div#RecipeRating table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#RecipeRating table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#RecipeRating table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#RecipeRating table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="RecipeSearch">
                                            <h3>Recipe Search</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/RecipeSearch.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#RecipeSearch .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#RecipeSearch .working').html($.getSign(!result.hasErrors));
                                                                $('div#RecipeSearch table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#RecipeSearch table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#RecipeSearch table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#RecipeSearch table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>                                    
                                    <div class="col-md-6"> 
                                        <div class="acc" id="UserManagement">
                                            <h3>User Management</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/UserManagement.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#UserManagement .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#UserManagement .working').html($.getSign(!result.hasErrors));
                                                                $('div#UserManagement table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#UserManagement table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#UserManagement table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#UserManagement table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>                                    
                                    <div class="col-md-6"> 
                                        <div class="acc" id="StoreLocator">
                                            <h3>Store Locator</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/StoreLocator.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#StoreLocator .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#StoreLocator .working').html($.getSign(!result.hasErrors));
                                                                $('div#StoreLocator table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#StoreLocator table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#StoreLocator table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#StoreLocator table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6"> 
                                        <div class="acc" id="VirtualAgent">
                                            <h3>Virtual Agent</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/VirtualAgent.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#VirtualAgent .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#VirtualAgent .working').html($.getSign(!result.hasErrors));
                                                                $('div#VirtualAgent table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#VirtualAgent table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#VirtualAgent table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#VirtualAgent table').append('<tr><td>' + value + '</td><td>' + result.data[value] + '</td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12"> 
                                        <div class="acc" id="BuyOnline">
                                            <h3>Buy Online</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/BuyOnline.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#BuyOnline .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#BuyOnline .working').html($.getSign(!result.hasErrors));
                                                                $('div#BuyOnline table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#BuyOnline table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#BuyOnline table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#BuyOnline table').append('<tr><td>Working: &emsp;(' + $.getSign(result.data[value].working=="yes"?true:false) + ')</td><td><pre>' + $.getJSON(result.data[value]) + '</pre></td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12"> 
                                        <div class="acc" id="BazaarVoice">
                                            <h3>Bazaar Voice</h3>
                                            <div>
                                                <script>
                                                    $(document).ready(function () {
                                                        $.ajax({
                                                            url: $('#urlValue').val() + '/BazaarVoice.json',
                                                            cache: false,
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                $('tr#BazaarVoice .enabled').html($.getSign(result.hasFeature));
                                                                $('tr#BazaarVoice .working').html($.getSign(!result.hasErrors));
                                                                $('div#BazaarVoice table').append('<tr><td>Enabled</td><td>' + result.hasFeature + '</td></tr>');
                                                                $('div#BazaarVoice table').append('<tr><td>has Errors</td><td>' + result.hasErrors + '</td></tr>');

                                                                var errorKeys = Object.keys(result.errors);
                                                                var dataKeys = Object.keys(result.data);
                                                                if (errorKeys.length > 0) {
                                                                    $.each(errorKeys, function (index, value) {
                                                                        $('div#BazaarVoice table').append('<tr><td>' + value + '</td><td>' + result.errors[value] + '</td></tr>');
                                                                    });
                                                                }
                                                                if (dataKeys.length > 0) {
                                                                    $.each(dataKeys, function (index, value) {
                                                                        $('div#BazaarVoice table').append('<tr><td>Working: ('+$.getSign(value=="no"?false:true)+') </td><td><pre>' + $.getJSON(result.data[value]) + '</pre></td></tr>');
                                                                    });
                                                                }
                                                            }
                                                        })
                                                    });
                                                </script>
                                                <table class="table table-bordered table-hover table-condensed">                                                

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12"> 
                                        <div class="acc" id="TemplatesAll">
                                            <h3>Templates</h3>
                                            <div id="Templates" class="custom-div">
                                                <ul>
                                                    <li><a href="#tabs-1">Article</a></li>
                                                    <li><a href="#tabs-2">Product</a></li>
                                                    <li><a href="#tabs-3">Recipe</a></li>
                                                    <li><a href="#tabs-4">Miscellaneous</a> 
                                                </ul>
                                                <div id="tabs-1">
                                                    <div class="table-responsive">
                                                        <table id="Article" class="table table-bordered table-striped table-condensed">
                                                            <thead>
                                                                <tr class="alert-warning">                                                                
                                                                    <th>Template Name</th>
                                                                    <th>URL</th>                                                    
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>                                                                                            
                                                    </div>
                                                </div>
                                                <div id="tabs-2">
                                                    <div class="table-responsive">
                                                        <table id="Product" class="table table-bordered table-striped table-condensed">
                                                            <thead>
                                                                <tr class="alert-warning">                                                                
                                                                    <th>Template Name</th>
                                                                    <th>URL</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>                                                                                            
                                                    </div>
                                                </div>
                                                <div id="tabs-3">
                                                    <div class="table-responsive">
                                                        <table id="Recipe" class="table table-bordered table-striped table-condensed">
                                                            <thead>
                                                                <tr class="alert-warning">                                                                
                                                                    <th>Template Name</th>
                                                                    <th>URL</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>                                                
                                                    </div>
                                                </div>
                                                <div id="tabs-4">                                           
                                                    <div class="table-responsive">
                                                        <table id="Other-Templates" class="table table-bordered table-striped table-condensed">
                                                            <thead>
                                                                <tr class="alert-warning">                                                                
                                                                    <th>Template Name</th>
                                                                    <th>URL</th>
                                                                </tr>
                                                            </thead>                                                        
                                                            <tbody>

                                                            </tbody>
                                                        </table>
                                                    </div>                                           
                                                </div>                             
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" value="<%=HelperClass.getLatestJSONoutputWithTemplate(request.getParameter("host"))%>" id="json" name="json">
        <%@include file="/WEB-INF/jspf/footerScripts.jspf"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/output.js"></script>
    </body>
</html>
