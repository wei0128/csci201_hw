<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="parsing.StringConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
	if ((session.getId() == null) || (session.getAttribute(StringConstants.DATA) == null)){
		request.getRequestDispatcher("index.jsp").forward(request, response);
		return;
	}

	DataContainer data = (DataContainer) session.getAttribute(StringConstants.DATA);
	School school = data.getSchools().get(0);
	Department dept = school.getDepartments().get(0);
	Course course = dept.getCourses().get(0);
	
	String location = (String) request.getAttribute(StringConstants.LOCATION);
	String [] hrefs = {"http://cs.usc.edu", StringConstants.HOME_JSP, course.getSyllabus().getUrl(), 
			StringConstants.LECTURES_JSP, StringConstants.ASSIGNMENTS_JSP, StringConstants.EXAMS_JSP};
	String [] labels = {dept.getPrefix()+" Department", dept.getPrefix()+" "+course.getNumber()+" Home", 
			"Syllabus", "Lectures", "Assignments", "Previous Exams"};
%>
<head>
	<title><%= school.getName() %>: <%=dept.getPrefix() %> <%= course.getNumber() %> <%= course.getTerm() %> <%= course.getYear() %></title>
</head>
<body text="#333333" bgcolor="#EEEEEE" link="#0000EE" vlink="#551A8B"
	alink="#336633">
	<table cellpadding="10" width="800">
		<tr>
			<!-- column for left side of page -->
			<td width="180" valign="top" align="right">
				<a href="http://www.usc.edu"><img src=<%= school.getImage() %> border="0" /></a><br />
				<br /> 
				<% 
				for (int i = 0; i<6; i++)
				{
	        		if (hrefs[i].equals(location))
	        		{
	        	%> 
        		<font size="+1"><%=labels[i]%></font><br />
				<br /> 
					<%
					} 
	        		else
	        		{ 
	        		%> 
	        	<font size="+1"><a href="<%= hrefs[i]%>"> <%= labels[i] %></a></font><br />
				<br /> 
				<%
					}
	        	} 
	        	%>
	        </td>
			<!-- center column to separate other two columns -->
			<td width="5"><br /></td>
			<!-- large column in center of page with dominant content -->
			<td align="baseline" width="615"><br />
				<p>
					<b><font size="+3"><%= dept.getPrefix() %> <%=course.getNumber() %></font></b><br />
					<b><i><font size="+1"><%= course.getTitle() %> - <%=course.getUnits() %> units</font></i></b><br /> 
					<b><i><font size="+1"><%= course.getTerm() %> <%= course.getYear() %></font></i></b><br />