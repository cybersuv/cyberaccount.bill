<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashboard</title>
<script src="<c:url value="/resources/core/jquery.min.js" />"></script>
<script src="<c:url value="/resources/core/jquery.printElement.min.js" />"></script>
<script src="<c:url value="/resources/ui/jquery-ui.js" />"></script>

<link href="<c:url value="/resources/ui/jquery-ui.css" />" rel="stylesheet">
<link href="<c:url value="/resources/core/core.css" />" rel="stylesheet">
<style type="text/css">
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
</style>

</head>
<body >
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="top-header" style="width: 100%;height: 100px;">
<div class="title-pane title-text"><span id="app_title" title="Application Title">${appName}</span></div>
	<div class="logo-pane"></div>
	<div id="setting_button" class="setting-pane" title="Change user settings"></div>
	<div class="user-pane strong-text">${authuser.user_name} (${authuser.user_login})</div>
	<div id="logout_button" class="logout-pane"></div>
</div> 
<div id="body-container" style="display:table;width:100%;height:100%;border-spacing: 5px;border-collapse: separate;">

<div class="menu-bar">
<ul style="width:150px;" id="menu">
	<li>Home</li>
	<li onClick="javascript:navigateReports();">Reports</li>
	<li>Bill Entry
		<ul>
			<li onClick="javascript:navigateGlobalEntry();">Global</li>
			<li onClick="javascript:navigateFlatEntry();">Flat</li>
		</ul>
	</li>
	<li onClick="javascript:navigateProcessBill();">Process Bill</li>
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
	//navigateSummary();
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

function navigateProcessBill(){
	$.get( "${contextPath}/getProcessBillUI", function( data ) {
		$('#container').html(data);
		});
}

</script>
</body>
</html>