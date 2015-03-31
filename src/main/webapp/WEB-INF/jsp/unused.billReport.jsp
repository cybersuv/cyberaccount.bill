<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlatAccount :: Owners</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<center>
<div id="responsecontainer">
</div>
</center>
<script>
var responseContent='${responseTable}';
var sessionStatus='${sessionStatus}';
if(sessionStatus!='OK'){
	console.log('Session : ' + sessionStatus);
	location.href="/FlatAccount/";
}
$(document).ready(function() {
	$( "#responsecontainer" ).empty().append(responseContent);
});

function addOwner(){
	$.get( "${contextPath}/showcreateowner", function( data ) {
		$('#container').html(data);
		});
}

function fetchOwner(o){
	$.get( "${contextPath}/fetchowner?owner_id="+o, function( data ) {
		$('#container').html(data);
		});
}

function addUser(){
	$.get( "${contextPath}/showcreateuser", function( data ) {
		$('#container').html(data);
		});
}

function fetchUser(o){
	$.get( "${contextPath}/fetchuser?user_id="+o, function( data ) {
		$('#container').html(data);
		});
}

function addReason(){
	$.get( "${contextPath}/showcreatereason", function( data ) {
		$('#container').html(data);
		});
}

function fetchReason(o){
	$.get( "${contextPath}/fetchreason?reason_id="+o, function( data ) {
		$('#container').html(data);
		});
}
</script>
</body>
</html>