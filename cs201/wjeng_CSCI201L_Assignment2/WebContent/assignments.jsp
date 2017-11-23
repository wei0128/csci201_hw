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
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
		<title><%= mySchool.getName() + ": " + myDepartment.getPrefix() + " " + myCourse.getNumber() + " " +
		myCourse.getTerm() + " " + myCourse.getYear()%></title>
	</head>
	<body text="#333333" bgcolor="#EEEEEE" link="#0000EE" vlink="#551A8B" alink="#336633">
		<table cellpadding="10" width="800">
			<tbody>
				<tr>
					<!--  left column for navigation -->
					<td width="180" valign="top" align="right">
						<a href="http://www.usc.edu"><img src=<%=mySchool.getImage()%> border="0"></a><br /><br />
						<font size="+1"><a href="http://cs.usc.edu">CS Department</a></font><br /><br />
						<font size="+1"><a href="home.jsp"><%=myDepartment.getPrefix()+myCourse.getNumber()%> Home</a></font><br /><br />
						<font size="+1"><a href=<%=myCourse.getSyllabus().getUrl()%>>Syllabus</a></font><br /><br />
						<font size="+1"><a href="lectures.jsp">Lectures</a></font><br /><br />
          				<font size="+1">Assignments</font><br /><br />
          				<font size="+1"><a href="exam.jsp">Previous Exams</a></font><br /><br />
					</td>
					<!--  breaking column -->
					<td width="5"><br /></td>
					
					<!-- center large -->
					<td align="baseline" width="615">
						<br />
						<p>
				           <b><font size="+3"><%= myDepartment.getPrefix() + " " + myCourse.getNumber()%></font></b><br />
				           <b><i><font size="+1"><%= myCourse.getTitle()%></font></i></b><br />
				           <b><i><font size="+1"><%= myCourse.getTerm() + " " + myCourse.getYear() %></font></i></b><br />
				         <p><hr size="4"/>
				         
				         <b>Assignments</b>
				         <table border="2" cellpadding="5" width="590">
				         	<tr>
				         		<th align="center">Homework #</th>
				         		<th align="center">Assigned</th>
				         		<th align="center">Due</th>
				         		<th align="center">Assignment</th>
				         		<th align="center">% Grade</th>
				         		<th align="center">Grading Criteria</th>
				         		<th align="center">Solution</th>
				         	</tr>
				         	<!-- For loop for files -->
				         	<%
				         		List<Assignment> assignmentList = myCourse.getAssignments();
				         			//Excluding final proejct which is at the very end
			         			for(int i = 0; i < 9; i++){
			         				//In case not in order
			         				for(int k = 0; k < assignmentList.size(); k++){
			         					if(assignmentList.get(k).getNumber().equals(String.valueOf(i+1))){
			         						%>
			         							<tr>
				         							<td align="center"><%= assignmentList.get(k).getNumber() %></td>
				         							<td align="center"><%= assignmentList.get(k).getAssignedDate() %></td>
				         							<td align="center"><%= assignmentList.get(k).getDueDate() %></td>
				         							<!-- Files -->
				         							<td align ="center">
				         							<%
				         								List<File> fileList = assignmentList.get(k).getFiles();
				         								if(fileList != null){
				         									for(int j = 0; j<fileList.size(); j++){
				         										if(j!=fileList.size()-1){
				         											if(fileList.get(j).getTitle()!=null){
				         												%>
				         												<a href=<%= fileList.get(j).getUrl() %>><%= fileList.get(j).getTitle()%></a><hr />
				         												<%
				         											}
				         											else{
				         												%>
				         												<a href=<%= fileList.get(j).getUrl() %>>No Title</a><hr />
				         												<%
				         											}
				         											
				         										}
				         										else{
				         											if(fileList.get(j).getTitle()!=null){
				         												%>				         											
				         												<a href=<%= fileList.get(j).getUrl() %>><%=fileList.get(j).getTitle() %></a>
				         												<%
				         											}
				         											else{
				         												%>				         											
				         												<a href=<%= fileList.get(j).getUrl() %>>No Title</a>
				         												<%
				         											}
				         											
				         										}
				         									}
				         								}
				         							%>
				         							</td>
				         							<!--% Grade  -->
				         							<td align="center">
				         								<%= assignmentList.get(k).getGradePercentage() %>
				         							</td>
				         							<!-- Grading Criteria -->
				         							<td align="center">
				         							<% 
				         								List<File> gradingCriteria = assignmentList.get(k).getGradingCriteriaFiles();
				         								if(gradingCriteria!=null){
				         									for(int j = 0; j < gradingCriteria.size(); j++){
				         										if(j!=gradingCriteria.size()-1){
				         											%>
				         												<a href=<%=gradingCriteria.get(j).getUrl() %>><%=gradingCriteria.get(j).getTitle() %></a><br />
				         											<%
				         										}
				         										else{
				         											%>
				         											<a href=<%=gradingCriteria.get(j).getUrl() %>><%=gradingCriteria.get(j).getTitle() %></a>
				         											<%
			         											}				         										
				         									}
				         								}				         							
				         							%>
				         							</td>
				         							<!-- Solution -->
				         							<td align="center">
				         							<%
				         								List<File> solutionList = assignmentList.get(k).getSolutionFiles();
				         								if(solutionList !=null){
				         									for(int j = 0; j < solutionList.size(); j++){
				         										if(j!=solutionList.size()-1){
				         											%>
			         												<a href=<%=solutionList.get(j).getUrl() %>><%=solutionList.get(j).getTitle() %></a><br />
			         												<%
				         										}
				         										else{
				         											%>
			         												<a href=<%=solutionList.get(j).getUrl() %>><%=solutionList.get(j).getTitle() %></a>
			         												<%
				         										}
				         									}
				         								}
				         							%>
				         							</td>
			         							</tr>
			         						<%
			         					}
			         				}
			         			}
				         	%>
				         	<!-- End of normal Assignment -->
				         	<!-- Final Project -->
				         	<%
				         		//getting final project
				         		Assignment finalProject = null;
				         		for(int i = 0; i < assignmentList.size(); i++){
				         			if(assignmentList.get(i).getNumber().equals("Final Project")){
				         				finalProject = assignmentList.get(i);
				         			}
				         		}				         		
				         	%>
				         	<tr>
				         		<td align="center"><%= finalProject.getNumber()%></td>
				         		<td align="center"><%= finalProject.getAssignedDate() %></td>
				         		<td align="center" colspan="3">
				         			<table border="1" cellpadding="5">
				         				<tr>
				         					<td colspan="3" align="center">
				         						<a href=<%= finalProject.getUrl() %>><%= finalProject.getTitle() %></a><hr />
				         						<%
				         							List<File> projectIdeas = finalProject.getFiles();
				         							if(projectIdeas != null){
				         								for(int k = 0; k < projectIdeas.size(); k++){
				         									if(k!=projectIdeas.size()-1){
				         										if(projectIdeas.get(k).getUrl()!=null && projectIdeas.get(k).getTitle()!=null){
				         											%>
					         										<a href=<%= projectIdeas.get(k).getUrl()%>><%= projectIdeas.get(k).getTitle() %></a><br />				        									
					         										<%
				         										}
				         										else if(projectIdeas.get(k).getUrl()!=null && projectIdeas.get(k).getTitle()==null){
				         											%>
					         										<a href=<%= projectIdeas.get(k).getUrl()%>>No Title></a><br />				        									
					         										<%
				         										}
				         										else if(projectIdeas.get(k).getUrl()==null && projectIdeas.get(k).getTitle()!=null){
				         											%>
					         										<%=projectIdeas.get(k).getTitle() %><br />				        									
					         										<%
				         										}
				         										
				         									}
				         									else{
				         										if(projectIdeas.get(k).getUrl()!=null && projectIdeas.get(k).getTitle()!=null){
				         											%>
					         										<a href=<%= projectIdeas.get(k).getUrl()%>><%= projectIdeas.get(k).getTitle() %></a><br />				        									
					         										<%
				         										}
				         										else if(projectIdeas.get(k).getUrl()!=null && projectIdeas.get(k).getTitle()==null){
				         											%>
					         										<a href=<%= projectIdeas.get(k).getUrl()%>>No Title></a><br />				        									
					         										<%
				         										}
				         										else if(projectIdeas.get(k).getUrl()==null && projectIdeas.get(k).getTitle()!=null){
				         											%>
					         										<%=projectIdeas.get(k).getTitle() %><br />				        									
					         										<%
				         										}
				         									}				         									
				         								}
				         							}				         						
				         						%>
				         					</td>
			         					</tr>
			         					<!-- Final Project Deliverables -->
			         					<%
			         						List<Deliverable> deliverables = finalProject.getDeliverables();
			         						if(deliverables!=null){
			         							for(int i=0; i < deliverables.size();i++){
			         								//sorting in number order
			         								for(int k = 0; k < deliverables.size(); k++){
			         									if(deliverables.get(k).getNumber().equals(String.valueOf(i+1))){
			         										%>
			         										<tr>
			         											<td><%= deliverables.get(k).getDueDate() %></td>
			         											<%
			         											if(deliverables.get(k).getTitle()!=null){
			         												%>
			         													<td><%= deliverables.get(k).getTitle() %>
			         												<%
			         											}
			         											%>
			         											<!-- Checking for files -->
			         											<%
			         												List<File> fileList = deliverables.get(k).getFiles();
			         												if(fileList!=null){
			         													for(int z = 0; z< fileList.size(); z++){
			         														if(z!=fileList.size()-1){
			         															%>
			         																<a href=<%= fileList.get(z).getUrl() %>><%= fileList.get(z).getTitle() %></a><hr />
			         															<%
			         														}
			         														else{
			         															%>
			         																<a href=<%= fileList.get(z).getUrl() %>><%= fileList.get(z).getTitle() %></a>
			         															<%
			         														}
			         													}
			         												}
			         											%></td>
			         											<td><%= deliverables.get(k).getGradePercentage() %></td>
			         										</tr>
			         										<%
			         									}
			         								}
			         							}
			         						}
			         					%>
				         			</table>
				         	</tr>
				         </table>
				         <!-- End of Assignment -->
				         
				    </td>
				    <!--  End of Center -->
				</tr>
			</tbody>
		</table>
	</body>
</html>