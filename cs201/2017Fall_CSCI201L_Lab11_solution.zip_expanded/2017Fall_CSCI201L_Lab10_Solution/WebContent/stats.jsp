<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "driver.*, java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>visit.stat</title>
<style>
table, tr, td {
    border: 1px solid black;
}
</style>
</head>
<%
ArrayList<ArrayList<String>> stat = JDBCDriver.getData();
/* for (int i = 0; i < stat.size(); i++){
	for(int j = 0; j < stat.get(0).size(); j++){
		System.out.println(stat.get(i).get(j)+" ");
	}
	System.out.println();
} */
System.out.println("from stats jsp, getdata finished");
request.setAttribute("list", stat);
%>   
<body>

<h1>Statistics for Pages</h1>
<table>
	<tr>
	<td>Username</td>
	<td>Page</td>
	<td># Visits</td>
	<%
	for (int i = 0; i < stat.size(); i++) {
		out.println("<tr>");
		for(int j = 0; j < stat.get(i).size(); j++){
			out.println("<td>"+stat.get(i).get(j)+"</td>");
		}
        out.println("</tr>");
    }
	%>
</table><br>
<a href="cat.jsp">Cat Page</a>,
<a href="dog.jsp">Dog Page</a>,
<a href="fish.jsp">Fish Page</a>,
<a href="login.jsp">Login Page</a>
</body>
</html>