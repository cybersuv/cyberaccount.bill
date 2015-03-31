<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashboard</title>
<script src="<c:url value="/resources/core/jquery.min.js" />"></script>
<script src="<c:url value="/resources/ui/jquery-ui.js" />"></script>

<link href="<c:url value="/resources/ui/jquery-ui.css" />" rel="stylesheet">
<style type="text/css">
html{
	height:100%;
}

.top-header,
.top-header h1,
.top-header span,
.top-header input,
.top-header label {
	border: 0;
	outline: 0;
}
.top-header {
	width: 100%;
	height: 100px;
	background-color: #919EAC;

	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;

	-webkit-box-shadow: 0px 1px 1px 0px rgba(255,255,255, .2), inset 0px 1px 1px 0px rgb(0,0,0);
	-moz-box-shadow: 0px 1px 1px 0px rgba(255,255,255, .2), inset 0px 1px 1px 0px rgb(0,0,0);
	box-shadow: 0px 1px 1px 0px rgba(255,255,255, .2), inset 0px 1px 1px 0px rgb(0,0,0);
}
.user-pane {
	position: fixed;
	padding: 10px 10px 10px 10px;
	margin-top: 0px;
	margin-left: 0px;
	margin-right: 0px;
	cursor: default;
	text-indent: 50px;
	text-align: center;
	color: #ffffff;
	font-weight: bold;
	right : 80px;
	top: 10px;
}
.setting-pane {
	position: fixed;
	padding: 10px 10px 10px 10px;
	margin-top: 10px;
	margin-left: 200px;
	margin-right: 10px;
	margin-bottom: 10px;
	background-image: url('<c:url value="/resources/images/settings_icon.png" />');
	background-repeat:no-repeat;
	background-size:contain;
	height:5px;
	width:5px;
	right : 40px;
	top: 10px;
	cursor: pointer;
	
}

.logout-pane {
	position: fixed;
	padding: 10px 10px 10px 10px;
	margin-top: 10px;
	margin-left: 200px;
	margin-right: 10px;
	margin-bottom: 10px;
	background-image: url('<c:url value="/resources/images/logout.png" />');
	background-repeat:no-repeat;
	background-size:contain;
	height:5px;
	width:5px;
	right : 10px;
	top: 10px;
	cursor: pointer;
}

.logo-pane {
	position: absolute;
	left:0;
	padding: 0px 0px 0px 0px;
	margin-top: 20px;
	margin-left: 10px;
	margin-right: 0px;
	margin-bottom: 20px;
	background-image: url('<c:url value="/resources/images/logo.png" />');
	background-repeat:no-repeat;
	background-size:contain;
	height:50px;
	width:50px;
	
}

.title-pane {
	position: absolute;
	left:10px;
	z-index:999;
	top: 20px;
	padding: 1px 1px 1px 1px;
	margin: 0 auto;
	
}
.strong-text {
	text-indent: 50px;
	text-align: center;
	color: #ffffff;
	font-weight: bold;
}

.title-text {
	text-indent: 50px;
	text-align: center;
	color: #ffffff;
	font-weight: bold;
	font-size: 40px;
}

.menu-bar {
	display:table-cell;
	vertical-align: top;
 	width:150px;
	margin-right: 6px;
	margin-top: 5px;
	padding: 5px;
	
}

div.vertical-line{
  display:table-cell;
  vertical-align: top;
  width: 1px; /* Line width */
  padding: 0px;
  background-color: #919EAC; /* Line color */
  height: 100%; /* Override in-line if you want specific height. */
  /* position:fixed;
  top: 115px;
  left : 165px; */ /* Causes the line to float to left of content. 
    You can instead use position:absolute or display:inline-block
    if this fits better with your design */
}

.container {
	display:table-cell;
	vertical-align: top;
	border: 1px solid;
    border-color:#919EAC;
	border-radius: 5px;
	padding: 5px;
	width:auto;
	height: 100%;
}

.ui-tooltip
{
    font-size:10pt;
    font-family:Calibri;
}

div.ui-datepicker{
 font-size:10px;
}

table.formtable {
    border-collapse: collapse;
    margin-top:20px;
    min-width: 250px;
    border: 1px solid black;
    font-size:14px;
}

table.datatable {
    border-collapse: collapse;
    margin-top:20px;
    min-width: 250px;
    border: 1px solid black;
    font-size:14px;
}

td.datacell{
	border-collapse: collapse;
	border: 1px solid black;
}
</style>
</head>
<body >
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="top-header" style="width: 100%;height: 100px;">
<div class="title-pane title-text"><span id="app_title" title="Application Title">${appName} [${authuser.organisation.orgName}]</span></div>
	<div class="logo-pane"></div>
	<div id="setting_button" class="setting-pane" title="Change user settings"></div>
	<div class="user-pane strong-text">${authuser.user_name} (${authuser.user_login})</div>
	<div id="logout_button" class="logout-pane"></div>
</div> 
<div id="body-container" style="display:table;width:100%;height:100%;border-spacing: 5px;border-collapse: separate;">

<div class="menu-bar">
<ul style="width:150px;" id="menu">
	<li onClick="javascript:navigateSummary();">Home</li>
	<li onClick="javascript:navigateReports();">Reports</li>
	<li>Bill Entry
		<ul>
			<li onClick="javascript:navigateGlobalEntry();">Global</li>
			<li onClick="javascript:navigateFlatEntry();">Flat</li>
		</ul>
	</li>
	<li>Process Bill</li>
	<li onClick="javascript:logOutOperation()">Log Out</li>
</ul>
</div>
<div class="vertical-line"></div>
<div id="container" class="container">.</div>
</div>

<script type="text/javascript">
$("#menu").menu();
$("#app_title").tooltip();
$("#setting_button").tooltip();

$('#menu *[title]').tooltip('disable');

$(function(){
	var docHeight=$( document ).height();
	$('#body-container').height(docHeight-120);
	navigateSummary();
	$('#logout_button').click(function(){
		/* var form = document.createElement('frm1');
		form.setAttribute('action', '/logout');
		form.setAttribute('method', 'GET');
		form.setAttribute('id', 'frm1');
		$('#frm1').submit();
		alert("Submitted..."); */
		logOutOperation(); 
	});	
});

function logOutOperation(){
	$.get("logout",function(data){
		location.href="${contextPath}";
	});
}

function navigateGlobalEntry(){
	$.get( "${contextPath}/globalentry", function( data ) {
		$('#container').html(data);
		});
}

function navigateFlatEntry(){
	$.get( "${contextPath}/flatentry", function( data ) {
		$('#container').html(data);
		});
}

function navigateReports(){
	$.get( "${contextPath}/reportindex", function( data ) {
		$('#container').html(data);
		});
}

function navigateSummary(){
	$.get( "${contextPath}/getsummary", function( data ) {
		$('#container').html(data);
		});
}

</script>
</body>
</html>