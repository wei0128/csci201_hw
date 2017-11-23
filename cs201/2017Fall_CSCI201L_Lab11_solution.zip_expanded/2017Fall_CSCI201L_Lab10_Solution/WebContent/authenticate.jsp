<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="driver.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>authenticate</title>
</head>
<% 
String username = request.getParameter("username");
System.out.println("inside authenticate");
System.out.println(username+ " "+request.getParameter("password"));
if( JDBCDriver.validate(username, request.getParameter("password"))){
	JDBCDriver.increment(username, "authenticate.jsp");
	request.getSession().setAttribute("username", username);
	request.getSession().setAttribute("authenticated", "yes");
	response.sendRedirect("cat.jsp");
}else{
	request.setAttribute("errmsg", "Invalid username and password.");
	response.sendRedirect("login.jsp");
}
%>
<body>

</body>
</html>