<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="driver.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lab10.login</title>
</head>
<% 
	JDBCDriver.increment("Anonymous", "login.jsp");
 %>
<body>
<h1>Login Page</h1>
<span style="color: red;font-weight:bold">${errmsg!=null? errmsg : ''}</span><br>
<form name = "loginForm" method = "POST" action= "authenticate.jsp">
Username<input type = "text" name = 'username' /><br>
Password<input type = "password" name = "password"/><br>
<input type = "submit" name = "submit" value = "submit">
</form>
</body>
</html>