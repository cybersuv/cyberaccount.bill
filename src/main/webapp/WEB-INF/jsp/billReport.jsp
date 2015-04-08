<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlatAccount :: Owners</title>
<script src="<c:url value="/resources/core/jquery.min.js" />"></script>
<script src="<c:url value="/resources/core/jquery.printElement.min.js" />"></script>
<script src="<c:url value="/resources/ui/jquery-ui.js" />"></script>

<link href="<c:url value="/resources/ui/jquery-ui.css" />" rel="stylesheet" media="all">
<link href="<c:url value="/resources/core/core.css" />" rel="stylesheet" media="all">
<style type="text/css" media="print">
   .no-print { display: none; }
</style>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<center>
<a class="no-print" href="javascript:printThis();">Print</a>
<div id="responsecontainer">${responseTable}</div>
</center>
<script>
var responseContent='${responseTable}';
var sessionStatus='${sessionStatus}';
/* if(sessionStatus!='OK'){
	console.log('Session : ' + sessionStatus);
	location.href="/flataccount.bill/";
} */
$(document).ready(function() {
	//$( "#responsecontainer" ).empty().append(responseContent);
});

function printThis(){
	$("#responsecontainer").printElement({
        printBodyOptions:
        {
            styleToAdd:'padding:1px;margin:1px;color:#000000 ;width:100%;',
            classNameToAdd : 'datacell',
            leaveOpen:true,
            pageTitle:'Monthly Bill Report'
        }
    });
}

function fetchGlobalBill(b){
	$.get( "${contextPath}/getglobalentry?billId="+b, function( data ) {
		$('#container').html(data);
		});
}

function fetchFlatBill(b){
	$.get( "${contextPath}/getflatentry?billId="+b, function( data ) {
		$('#container').html(data);
		});
}

function addUser(){
	$.get( "${contextPath}/showcreateuser", function( data ) {
		$('#container').html(data);
		});
}

function deleteGlobalBill(o){
	$.get( "${contextPath}/deleteGlobalBill?billId="+o, function( data ) {
		if(data.status==true){
			$("#dialog-msg").html(data.responseMsg);
			$("#dialog-msg").dialog({
	               autoOpen: false, 
	               modal: true,
	               title: "Success",
	               buttons: {
	                  OK: function() {
	                	  $(this).dialog("close");
	                	  $("#submit_btn").click();
	                	  }
	               }
	            }).dialog("open");
		}else{
			$("#dialog-msg").html(data.responseMsg);
			$("#dialog-msg").dialog({
	               autoOpen: false, 
	               modal: true,
	               title: "Fail !!",
	               buttons: {
	                  OK: function() {
	                	  $(this).dialog("close");
	                	  }
	               }
	            }).dialog("open");
		}
		});
}


function deleteFlatBill(o){
	$.get( "${contextPath}/deleteFlatBill?billId="+o, function( data ) {
		if(data.status==true){
			$("#dialog-msg").html(data.responseMsg);
			$("#dialog-msg").dialog({
	               autoOpen: false, 
	               modal: true,
	               title: "Success",
	               buttons: {
	                  OK: function() {
	                	  $(this).dialog("close");
	                	  $("#submit_btn").click();
	                	  }
	               }
	            }).dialog("open");
		}else{
			$("#dialog-msg").html(data.responseMsg);
			$("#dialog-msg").dialog({
	               autoOpen: false, 
	               modal: true,
	               title: "Fail !!",
	               buttons: {
	                  OK: function() {
	                	  $(this).dialog("close");
	                	  }
	               }
	            }).dialog("open");
		}
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