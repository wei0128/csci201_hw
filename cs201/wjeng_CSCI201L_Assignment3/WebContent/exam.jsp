<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="objects.*" import="java.util.List" import="java.util.ArrayList"
%>

<%
	Collection myCollection = (Collection)session.getAttribute("Schools");
	if(myCollection == null){
		response.sendRedirect("index.jsp");
		return;
	}
	//Setting up some basic variable and names
	List<School> schools = myCollection.getSchools();
	//Hard code the only school and Departmnet and Class
	School  mySchool = schools.get(0);
	Departments myDepartment = mySchool.getDepartments().get(0);
	Courses myCourse = myDepartment.getCourses().get(0);
	
	String style = (String)session.getAttribute("style");

%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
		<title><%= mySchool.getName() + ": " + myDepartment.getPrefix() + " " + myCourse.getNumber() + " " +
		myCourse.getTerm() + " " + myCourse.getYear()%></title>
		<link rel="stylesheet" type="text/css" href=<%=style %> />
	</head>
	<body text="#333333" bgcolor="#EEEEEE" link="#0000EE" vlink="#551A8B" alink="#336633">
		<table cellpadding="10" width="800">
			<tbody>
				<tr>
					<!--  left column for navigation -->
					<td class="nav" width="180" valign="top" align="right">
					<div id="sidebar">
						<a href="http://www.usc.edu"><img class="pic" src=<%=mySchool.getImage()%> border="0"></a><br /><br />
						<div class="navCont"><font size="+1"><a href="http://cs.usc.edu">CS Department</a></font><br /><br /></div>
						<div class="navCont"><font size="+1"><a href="home.jsp"><%=myDepartment.getPrefix()+myCourse.getNumber()%> Home</a></font><br /><br /></div>
						<div class="navCont"><font size="+1"><a href=<%=myCourse.getSyllabus().getUrl()%>>Syllabus</a></font><br /><br /></div>
						<div class="navCont"><font size="+1"><a href="lectures.jsp">Lectures</a></font><br /><br /></div>
          				<div class="navCont"><font size="+1"><a href="assignments.jsp">Assignments</a></font><br /><br /></div>
          				<div class="navCont"><font size="+1">Previous Exams</font><br /><br /></div>
          			</div>
					</td>
					<!--  breaking column -->
					<td width="5"><br /></td>
					
					<!-- center large -->
					<td class="body" align="baseline" width="615">
						<br />
						<p>
				           <b><font size="+3"><%= myDepartment.getPrefix() + " " + myCourse.getNumber()%></font></b><br />
				           <b><i><font size="+1"><%= myCourse.getTitle()%></font></i></b><br />
				           <b><i><font size="+1"><%= myCourse.getTerm() + " " + myCourse.getYear() %></font></i></b><br />
				         <p><hr size="4"/>
				       	 
				       	 <!-- Exam table -->
				       	 <table border="2" cellpadding="5" width="590">
				       	 	<tr>
				       	 		<th align="center">Semester</th>
				       	 		<th align="center">Written Exam #1</th>
				       	 		<th align="center">Programming Exam #1</th>
				       	 		<th align="center">Written Exam #2</th>
				       	 		<th align="center">Programming Exam #2</th>
				       	 	</tr>
				       	 	<!-- Start to loop over previous exams -->
				       	 	<%
				       	 		List<Exam> examList = myCourse.getExams();
				       	 		for(int i = 0; i<examList.size(); i++){
				       	 			%><tr>
				       	 			<td align="center"><%= examList.get(i).getSemester() + " " + examList.get(i).getYear() %></td>
				       	 			<%
				       	 				List<Test> testList = examList.get(i).getTests();
				       	 			%>
				       	 			<!-- For written #1 -->
				       	 			<td align="center">
				       	 			<% 
				       	 				//Find all test that is written #1
				       	 				for(int j = 0; j < testList.size(); j++){
				       	 					if(testList.get(j).getTitle().equals("Written Exam #1")){
				       	 						List<File> fileList = testList.get(j).getFiles();
				       	 						if(fileList!=null){
					       	 						for(int k = 0; k< fileList.size(); k++){
					       	 							if(k!=fileList.size()-1){
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
					       	 							}
					       	 							else{
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a>
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a>
							       	 							<%
					       	 								}
						       	 							else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
						       	 							
					       	 							}
					       	 						}
				       	 						}
				       	 						
				       	 					}
				       	 				}
				       	 			%>
				       	 			</td>
				       	 			<!-- End Written #1 -->
				       	 			<!-- For Programming #1 -->
				       	 			<td align="center">
				       	 			<% 
				       	 				//Find all test that is written #1
				       	 				for(int j = 0; j < testList.size(); j++){
				       	 					if(testList.get(j).getTitle().equals("Programming Exam #1")){
				       	 						List<File> fileList = testList.get(j).getFiles();
					       	 					if(fileList!=null){
					       	 						for(int k = 0; k< fileList.size(); k++){
					       	 							if(k!=fileList.size()-1){
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
					       	 							}
					       	 							else{
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a>
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a>
							       	 							<%
					       	 								}
						       	 							else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
						       	 							
					       	 							}
					       	 						}
				       	 						}
				       	 					}
				       	 				}
				       	 			%>
				       	 			</td>
				       	 			<!-- Programming Exam #1 -->
				       	 			<!-- For written #2 -->
				       	 			<td align="center">
				       	 			<% 
				       	 				//Find all test that is written #1
				       	 				for(int j = 0; j < testList.size(); j++){
				       	 					if(testList.get(j).getTitle().equals("Written Exam #2")){
				       	 						List<File> fileList = testList.get(j).getFiles();
					       	 					if(fileList!=null){
					       	 						for(int k = 0; k< fileList.size(); k++){
					       	 							if(k!=fileList.size()-1){
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
					       	 							}
					       	 							else{
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a>
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a>
							       	 							<%
					       	 								}
						       	 							else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
						       	 							
					       	 							}
					       	 						}
				       	 						}
				       	 					}
				       	 				}
				       	 			%>
				       	 			</td>
				       	 			<!-- End Written #2 -->
				       	 			<!-- For Programming Exam #2 -->
				       	 			<td align="center">
				       	 			<% 
				       	 				//Find all test that is written #1
				       	 				for(int j = 0; j < testList.size(); j++){
				       	 					if(testList.get(j).getTitle().equals("Programming Exam #2")){
				       	 						List<File> fileList = testList.get(j).getFiles();
					       	 					if(fileList!=null){
					       	 						for(int k = 0; k< fileList.size(); k++){
					       	 							if(k!=fileList.size()-1){
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a><hr />
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
					       	 							}
					       	 							else{
					       	 								if(fileList.get(k).getTitle()!=null && fileList.get(k).getUrl()!=null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>><%= fileList.get(k).getTitle() %></a>
							       	 							<%
					       	 								}
					       	 								else if(fileList.get(k).getUrl()!=null && fileList.get(k).getTitle()==null){
						       	 								%>
							       	 							<a href=<%= fileList.get(k).getUrl() %>>No Title</a>
							       	 							<%
					       	 								}
						       	 							else if(fileList.get(k).getTitle()!= null && fileList.get(k).getUrl()==null){
						       	 								%>
							       	 							<%= fileList.get(k).getTitle() %><hr />
							       	 							<%
					       	 								}
						       	 							
					       	 							}
					       	 						}
				       	 						}
				       	 					}
				       	 				}
				       	 			%>
				       	 			</td>
				       	 			<!-- End Programming #2 -->
				       	 			
				       	 				
				       	 		<%
				       	 		}
				       	 	%>
				       	 	
				       	 </table> 
					</td>
					<!-- End of center -->
					
				
				<!-- Start of end of Tbody -->
				</tr>
			</tbody>
		</table>
	</body>
</html>