<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session = "true" import="driver.*"%>
<%
if (!request.getSession().getAttribute("authenticated").equals("yes") ){
	response.sendRedirect("login.jsp");
}else{
	System.out.println("getting username from cat page: "+ session.getAttribute("username").toString());
	JDBCDriver.increment(session.getAttribute("username").toString(), "cat.jsp");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Cat Page</h1>
	<img src="img/cat.jpg" style = "width: 800px; height:500px;"/><br />
	<a href="dog.jsp">Dog Page</a>,
    <a href="fish.jsp">Fish Page</a>, 
    <a href="stats.jsp">Stats Page</a>
</body>
</html>