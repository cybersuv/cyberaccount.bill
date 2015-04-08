<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${appName} :: Flat Bill</title>
</head>
<body>
<center>
<form:form id="flatBillForm" action="#" method="POST">

<table class="formtable" cellspacing="0" >
<tr>
<td colspan="2" bgcolor="#848484" align="center"><font color="white">Flat Bill Entry</font></td>
</tr>
<tr>
<td>Account</td>
<td><form:input id="owner_name" type="text" path="account.ownerName" />
<form:input id="owner_id" type="hidden" path="account.ownerId"/>
<form:input type="hidden" path="flatBillId"/>
<input type="hidden" name="operation" value="${operation}"/></td>
</tr>
<tr>
<td>Month</td>
<td><form:select path="monthStamp" items="${monthStamps}" /></td>
</tr>
<%-- <tr>
<td>Meter Type</td>
<td><form:select path="meterType" items="${meterList}" /></td>
</tr> --%>
<tr>
<td>Reading From</td>
<td><form:input id="reading_from" path="readingFrom" type="text" placeholder="Reading From" /></td>
</tr>
<tr>
<td>Reading Till</td>
<td><form:input id="reading_till" path="readingTill" type="text" placeholder="Reading Till" /></td>
</tr>
<tr>
<td>Last Reading</td>
<td><form:input id="last_reading" type="text" path="prevReading" placeholder="0"/></td>
</tr>
<tr>
<td>Current Reading</td>
<td><form:input id="current_reading" type="text" path="currentReading" placeholder="0"/></td>
</tr>
<tr>
<td>Unit Consumption</td>
<td><form:input id="unit_consumption" type="text" path="consumptionUnit" placeholder="0" readonly="true"/></td>
</tr>
<%-- <tr>
<td>Sub-Meter Consumption</td>
<td><form:input id="submeter_consumption" type="text" path="subMeterConsumptionUnit" placeholder="0"/></td>
</tr>
<tr>
<td>Bill Amount</td>
<td><form:input id="bill_amount" type="text" path="billAmount" placeholder="0.00"/></td>
</tr> --%>
<tr>
<td colspan="2" align="center"><input type="submit" id="submit_btn" value="Submit"/></td>
</tr>
</table>
</form:form>
<br></br>

<div id="resultcontainer" class="ui-state-highlight ui-corner-all" style="display:none;margin-top: 20px; padding: 0 .7em;">
		<p><span id="resulticon" class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<span id="result"></span></p>
</div>
</center>
<script>
  $(document).ready(function() {
	  $('#reading_from').datepicker({
		  dateFormat: 'dd-M-yy'  
	  });
	  $('#reading_till').datepicker({
		  dateFormat: 'dd-M-yy'  
	  });
	  
	  $('#month_str').datepicker({
		  dateFormat: 'M-yy'  
	  });
	$('#owner_name').autocomplete({
		source: '${pageContext.request.contextPath}/getOwners',
		select: function( event, ui ) {
			$( "#owner_name" ).val(ui.item.ownerName);
			$("#owner_id").val(ui.item.ownerId);
			return false;
		}
	}) .autocomplete( "instance" )._renderItem = function( ul, item ) {
		return $( "<li>" )
		.append( item.ownerName + "(" + item.ownerId + ")" )
		.appendTo( ul );
		};
		
		
		$("#last_reading").keyup(function(){
			if($("#last_reading").length>0 && $("#current_reading").length>0){
		    	$("#unit_consumption").val($("#current_reading").val()-$("#last_reading").val());
		    }
		});
		
		$("#current_reading").keyup(function(){
			if($("#last_reading").length>0 && $("#current_reading").length>0){
		    	$("#unit_consumption").val($("#current_reading").val()-$("#last_reading").val());
		    }
		});
 	 
  });
  
  $( "#flatBillForm" ).submit(function( event ) {
	// Stop form from submitting normally
	event.preventDefault();
	// Get some values from elements on the page:
	var $form = $( this ),
	term = $form.find( "input[name='s']" ).val(),
	url = '${pageContext.request.contextPath}/flatentry';
	// Send the data using post
	var postData = $form.serialize();
	var posting = $.post( url, postData );
	// Put the results in a div
	posting.done(function( data ) {
		console.log("Response : " + data.split('|')[0]);
		if(data.split('|')[0]=='true'){
			console.log("Inside True...")
			$('#resulticon').removeClass();
			$('#resultcontainer').removeClass();
			$('#resultcontainer').addClass('ui-state-highlight ui-corner-all');
			$('#resulticon').addClass('ui-icon ui-icon-info');
			$("#result").empty().append(data.split('|')[1]);
			$('#resultcontainer').show();
			$('#flatBillForm')[0].reset();
		}else{
			$('#resulticon').removeClass();
			$('#resultcontainer').removeClass();
			$('#resultcontainer').addClass('ui-state-error ui-corner-all');
			$('#resulticon').addClass('ui-icon ui-icon-alert');
			$("#result").empty().append(data.split('|')[1]);
			$('#resultcontainer').show();
		}
		$('#resultcontainer').delay(2000).fadeOut();
	
	}).fail(function(xhr, textStatus, errorThrown) {
		$('#resulticon').removeClass();
		$('#resultcontainer').removeClass();
		$('#resultcontainer').addClass('ui-state-error ui-corner-all');
		$('#resulticon').addClass('ui-icon ui-icon-alert');
		$("#result").empty().append(errorThrown);
		$('#resultcontainer').show();
		$('#resultcontainer').delay(2000).fadeOut();
	});
	});
  </script>
</body>
</html>