<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>

<%@ page import="objects.*" import="java.util.List" import="java.util.ArrayList" import="java.util.Comparator" import="java.util.Collections"%>

<%
	Collection myCollection = (Collection)session.getAttribute("Schools");
	//Setting up some basic variable and names
	if(myCollection == null){
		%>expired<%
		return;
	}
	
	List<School> schools = myCollection.getSchools();
	//Hard code the only school and Departmnet and Class
	School  mySchool = schools.get(0);
	Departments myDepartment = mySchool.getDepartments().get(0);
	Courses myCourse = myDepartment.getCourses().get(0);
	
	String dOrder = request.getParameter("dOrder");
	String aOrder = request.getParameter("aOrder");
	String dSort = request.getParameter("dSort");
	String aSort = request.getParameter("aSort");
	
	
	%>
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

	         		List<Assignment> shortenList = new ArrayList<Assignment>();
	         		for(int i = 0; i< assignmentList.size(); i++){
	         			shortenList.add(assignmentList.get(i));
	         		}
	         		Assignment finalProject = null;
	         		for(int i = 0; i < shortenList.size(); i++){
	         			if(shortenList.get(i).getNumber().equals("Final Project")){
	         				finalProject = shortenList.get(i);
	         				//removing final project from normal list
	         				shortenList.remove(i);
	         			}
	         		}

	         		//Creating new list based on comparison
	         		Collections.sort(shortenList, new Comparator<Assignment>(){
	         			public int compare(Assignment a1, Assignment a2){
	         				if(aSort.equals("dueDate")){
	         					String[] date1 = a1.getDueDate().split("-");
	         					String[] date2 = a2.getDueDate().split("-");
	         					if(aOrder.equals("ascend")){
	         						if(Integer.valueOf(date1[2]) > Integer.valueOf(date2[2])){
	         							return 1;
	         						}
	         						else if(Integer.valueOf(date1[2]) < Integer.valueOf(date2[2])){
	         							return -1;
	         						}
	         						else{
	         							if(Integer.valueOf(date1[0]) > Integer.valueOf(date2[0])){
	         								return 1;
	         							}
	         							else if(Integer.valueOf(date1[0]) < Integer.valueOf(date2[0])){
	         								return -1;
	         							}
	         							else{
	         								if(Integer.valueOf(date1[1]) > Integer.valueOf(date2[1])){
	         									return 1;
	         								}
	         								else if(Integer.valueOf(date1[1]) < Integer.valueOf(date2[1])){
	         									return -1;
	         								}
	         								else{
	         									return 0;
	         								}
	         							}
	         						}
	         					}
	         					else{
	         						if(Integer.valueOf(date1[2]) > Integer.valueOf(date2[2])){
	         							return -1;
	         						}
	         						else if(Integer.valueOf(date1[2]) < Integer.valueOf(date2[2])){
	         							return 1;
	         						}
	         						else{
	         							if(Integer.valueOf(date1[0]) > Integer.valueOf(date2[0])){
	         								return -1;
	         							}
	         							else if(Integer.valueOf(date1[0]) < Integer.valueOf(date2[0])){
	         								return 1;
	         							}
	         							else{
	         								if(Integer.valueOf(date1[1]) > Integer.valueOf(date2[1])){
	         									return -1;
	         								}
	         								else if(Integer.valueOf(date1[1]) < Integer.valueOf(date2[1])){
	         									return 1;
	         								}
	         								else{
	         									return 0;
	         								}
	         							}
	         						}
	         					}
	         				}
	         				else if(aSort.equals("assignedDate")){
	         					String[] date1 = a1.getAssignedDate().split("-");
	         					String[] date2 = a2.getAssignedDate().split("-");
	         					if(aOrder.equals("ascend")){
	         						if(Integer.valueOf(date1[2]) > Integer.valueOf(date2[2])){
	         							return 1;
	         						}
	         						else if(Integer.valueOf(date1[2]) < Integer.valueOf(date2[2])){
	         							return -1;
	         						}
	         						else{
	         							if(Integer.valueOf(date1[0]) > Integer.valueOf(date2[0])){
	         								return 1;
	         							}
	         							else if(Integer.valueOf(date1[0]) < Integer.valueOf(date2[0])){
	         								return -1;
	         							}
	         							else{
	         								if(Integer.valueOf(date1[1]) > Integer.valueOf(date2[1])){
	         									return 1;
	         								}
	         								else if(Integer.valueOf(date1[1]) < Integer.valueOf(date2[1])){
	         									return -1;
	         								}
	         								else{
	         									return 0;
	         								}
	         							}
	         						}
	         					}
	         					else{
	         						if(Integer.valueOf(date1[2]) > Integer.valueOf(date2[2])){
	         							return -1;
	         						}
	         						else if(Integer.valueOf(date1[2]) < Integer.valueOf(date2[2])){
	         							return 1;
	         						}
	         						else{
	         							if(Integer.valueOf(date1[0]) > Integer.valueOf(date2[0])){
	         								return -1;
	         							}
	         							else if(Integer.valueOf(date1[0]) < Integer.valueOf(date2[0])){
	         								return 1;
	         							}
	         							else{
	         								if(Integer.valueOf(date1[1]) > Integer.valueOf(date2[1])){
	         									return -1;
	         								}
	         								else if(Integer.valueOf(date1[1]) < Integer.valueOf(date2[1])){
	         									return 1;
	         								}
	         								else{
	         									return 0;
	         								}
	         							}
	         						}
	         					}
	         					
	         				}
	         				else{
	         					String g1 = a1.getGradePercentage();
	         					String g2 = a2.getGradePercentage();
	         					g1 = g1.substring(0,g1.length()-1);
	         					g2 = g2.substring(0,g2.length()-1);
	         					if(Double.valueOf(g1) > Double.valueOf(g2)){
	         						if(aOrder.equals("ascend")){
	         							return 1;
	         						}
	         						else{
	         							return -1;
	         						}
	         					}
	         					else if(Double.valueOf(g1) < Double.valueOf(g2)){
	         						if(aOrder.equals("ascend")){
	         							return -1;
	         						}
	         						else{
	         							return 1;
	         						}
	         					}
	         					else{
	         						return 0;
	         					}
	         				}
	         			}        			
	         		});
	         		
	         		
	         		
	         		
         				for(int k = 0; k < shortenList.size(); k++){
         						%>
         							<tr>
	         							<td align="center"><%= shortenList.get(k).getNumber() %></td>
	         							<td align="center"><%= shortenList.get(k).getAssignedDate() %></td>
	         							<td align="center"><%= shortenList.get(k).getDueDate() %></td>
	         							<!-- Files -->
	         							<td align ="center">
	         							<%
	         								List<File> fileList = shortenList.get(k).getFiles();
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
	         								<%= shortenList.get(k).getGradePercentage() %>
	         							</td>
	         							<!-- Grading Criteria -->
	         							<td align="center">
	         							<% 
	         								List<File> gradingCriteria = shortenList.get(k).getGradingCriteriaFiles();
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
	         								List<File> solutionList = shortenList.get(k).getSolutionFiles();
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
	         	%>
	         	<!-- End of normal Assignment -->
	         	<!-- Final Project -->

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
         							
         							//sort deliverables
         							Collections.sort(deliverables, new Comparator<Deliverable>(){
         								public int compare(Deliverable d1, Deliverable d2){
         									if(dSort.equals("dueDate")){
         										String[] date1 = d1.getDueDate().split("-");
         			         					String[] date2 = d2.getDueDate().split("-");
         			         					if(dOrder.equals("ascend")){
         			         						if(Integer.valueOf(date1[2]) > Integer.valueOf(date2[2])){
         			         							return 1;
         			         						}
         			         						else if(Integer.valueOf(date1[2]) < Integer.valueOf(date2[2])){
         			         							return -1;
         			         						}
         			         						else{
         			         							if(Integer.valueOf(date1[0]) > Integer.valueOf(date2[0])){
         			         								return 1;
         			         							}
         			         							else if(Integer.valueOf(date1[0]) < Integer.valueOf(date2[0])){
         			         								return -1;
         			         							}
         			         							else{
         			         								if(Integer.valueOf(date1[1]) > Integer.valueOf(date2[1])){
         			         									return 1;
         			         								}
         			         								else if(Integer.valueOf(date1[1]) < Integer.valueOf(date2[1])){
         			         									return -1;
         			         								}
         			         								else{
         			         									return 0;
         			         								}
         			         							}
         			         						}
         			         					}
         			         					else{
         			         						if(Integer.valueOf(date1[2]) > Integer.valueOf(date2[2])){
         			         							return -1;
         			         						}
         			         						else if(Integer.valueOf(date1[2]) < Integer.valueOf(date2[2])){
         			         							return 1;
         			         						}
         			         						else{
         			         							if(Integer.valueOf(date1[0]) > Integer.valueOf(date2[0])){
         			         								return -1;
         			         							}
         			         							else if(Integer.valueOf(date1[0]) < Integer.valueOf(date2[0])){
         			         								return 1;
         			         							}
         			         							else{
         			         								if(Integer.valueOf(date1[1]) > Integer.valueOf(date2[1])){
         			         									return -1;
         			         								}
         			         								else if(Integer.valueOf(date1[1]) < Integer.valueOf(date2[1])){
         			         									return 1;
         			         								}
         			         								else{
         			         									return 0;
         			         								}
         			         							}
         			         						}
         			         					}
         									}
         									else if(dSort.equals("title")){
         										if(dOrder.equals("ascend")){
         											return d1.getTitle().compareTo(d2.getTitle());
         										}
         										else{
         											return (d1.getTitle().compareTo(d2.getTitle()))*-1;
         										}
         										
         									}
         									else{
         										String g1 = d1.getGradePercentage();
         			         					String g2 = d2.getGradePercentage();
         			         					g1 = g1.substring(0,g1.length()-1);
         			         					g2 = g2.substring(0,g2.length()-1);
         			         					if(Double.valueOf(g1) > Double.valueOf(g2)){
         			         						if(dOrder.equals("ascend")){
         			         							return 1;
         			         						}
         			         						else{
         			         							return -1;
         			         						}
         			         					}
         			         					else if(Double.valueOf(g1) < Double.valueOf(g2)){
         			         						if(dOrder.equals("ascend")){
         			         							return -1;
         			         						}
         			         						else{
         			         							return 1;
         			         						}
         			         					}
         			         					else{
         			         						return 0;
         			         					}
         									}
         								}
         							});
         							

     								for(int k = 0; k < deliverables.size(); k++){
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
         					%>
	         			</table>
	         	</tr>
	         </table>