<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="driver.*"%>
<%
if (!session.getAttribute("authenticated").equals("yes")){
	response.sendRedirect("login.jsp");
}else{
	JDBCDriver.increment(session.getAttribute("username").toString(), "fish.jsp");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Fish Page</h1>
	<img src="img/fish.jpeg" style = "width: 800px; height:500px;"/><br />
	<a href="cat.jsp">Cat Page</a>,
	<a href="dog.jsp">Dog Page</a>,
	<a href="stats.jsp">Stats Page</a>
</body>
</html>