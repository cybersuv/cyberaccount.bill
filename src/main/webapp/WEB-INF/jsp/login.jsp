<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<c:url value="/resources/core/jquery.min.js" />"></script>
<link href="<c:url value="/resources/core/login-form.css" />" rel="stylesheet">
<title>${appName} :: Login</title>
<style type="text/css">
html {
    height: 100%;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
    background: #70bg32;
    background-repeat:no-repeat;
    background: -webkit-linear-gradient( to left top, #F5F6CE, #BDBDBD);
    background: -moz-linear-gradient( to left top, #F5F6CE, #BDBDBD);
    background: -ms-linear-gradient( to left top, #F5F6CE, #BDBDBD);
    background: -o-linear-gradient( to left top, #F5F6CE, #BDBDBD);
    background: linear-gradient( to left top, #F5F6CE, #BDBDBD);
}
</style>
<script>
$(document).ready(function() {
 	$('#user_login').focus();
    // Check if JavaScript is enabled
    $('body').addClass('js');
 
    // Make the checkbox checked on load
    $('.login-form span').addClass('checked').children('input').attr('checked', true);
 
    // Click function
    $('.login-form span').on('click', function() {
 
        if ($(this).children('input').attr('checked')) {
            $(this).children('input').attr('checked', false);
            $(this).removeClass('checked');
        }
 
        else {
            $(this).children('input').attr('checked', true);
            $(this).addClass('checked');
        }
 
    });
 
});
</script>
</head>
<body>
<div class="login-form" style="display: inline-block;position: fixed;top: 0;bottom: 0;left: 0;right: 0;margin: auto; 0">
    <h1>${appName} Login</h1>
    <form:form method="POST" action="login">
        <form:input type="text" id="user_login" path="user_login" placeholder="User ID"/>
        <form:input type="password" path="password" placeholder="Password"/>
        <input type="submit" value="log in">
    </form:form>
</div>
<br/>
<div id="msg" style="text-align: center;color: red">${loginmessage}</div>
</body>
</html>





