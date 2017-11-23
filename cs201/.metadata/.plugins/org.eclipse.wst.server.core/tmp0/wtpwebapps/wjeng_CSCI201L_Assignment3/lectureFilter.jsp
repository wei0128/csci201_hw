<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>


<%@ page import="objects.*" import="java.util.List" import="java.util.ArrayList" %>

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
	
	//getting text and schedule
	Schedule mySchedule = myCourse.getSchedule();
	TextBooks myText = mySchedule.getTextbooks().get(0);
	
	String displayType = request.getParameter("displayType");
	
	//ALL CASE
	if(displayType.equals("all")){
		%>
		<b>Lectures</b>
     	<table border="2" cellpadding="5" width="590">
     		<tr>
     			<th align="center">Week #</th>
     			<th align="center">Lab</th>
     			<th align="center">Lecture #</th>
     			<th align="center">Day</th>
     			<th align="center">Date</th>
     			<th align="center">Lecture Topic</th>
     			<th align="center">Chapter</th>
     			<th align="center">Program</th>
 			</tr>
 			
 				<!-- Getting week list -->
 			<%
		
		List<Week> weekList = mySchedule.getWeeks();
			for(int i = 0; i < weekList.size(); i++){
				List<Lab> labList = weekList.get(i).getLabs();
				List<Lecture> lectureList = weekList.get(i).getLectures();
				List<Assignment> assignmentList = myCourse.getAssignments();
				List<Deliverable> deliverableList = new ArrayList<Deliverable>();
				//For row span count
				List<Assignment> dueAssignment = new ArrayList<Assignment>();
				List<Deliverable> dueDeliverable = new ArrayList<Deliverable>();
				List<Assignment> finalProject = new ArrayList<Assignment>();
				//getting final project
				for(int l = 0; l < assignmentList.size(); l++){
	     			if(assignmentList.get(l).getNumber().length() > 1){
	     				finalProject.add(assignmentList.get(l));
	 				} 
				}
			
				//Getting assignment due for the week
				for(int n = 0; n < lectureList.size(); n++){
					for(int m = 0; m < assignmentList.size(); m++){
						if(!assignmentList.get(m).getNumber().equals("Final Project")){
							if(assignmentList.get(m).getDueDate()!=null){
								if(assignmentList.get(m).getDueDate().equals(lectureList.get(n).getDate())){
									dueAssignment.add(assignmentList.get(m));
								}
							}													
						}
						
					}
					for(int m = 0; m<finalProject.size(); m++){
						deliverableList = finalProject.get(m).getDeliverables();
						for(int k=0; k<deliverableList.size(); k++){
							if(deliverableList.get(k).getDueDate()!=null){
								if(deliverableList.get(k).getDueDate().equals(lectureList.get(n).getDate())){
									dueDeliverable.add(deliverableList.get(k));
								}
							}													
						}
					}
				}
				
			
				
				%>
				<tr>
					<td align="center" rowspan=<%= lectureList.size() + dueDeliverable.size()+dueAssignment.size()%>><%= weekList.get(i).getWeek() %></td>
					<td align="center" rowspan=<%= lectureList.size() + dueDeliverable.size()+dueAssignment.size()%>>
						<!-- Starting Lab column -->
						<%
							
							if(labList!=null){
								for(int j = 0; j< labList.size(); j++){
									if(j!=labList.size()-1){
										%>
											
												<a href=<%= labList.get(j).getUrl()%>><%= labList.get(j).getTitle()%></a><hr />
											
										<%
									}
									else{
										%>
										
											<a href=<%= labList.get(j).getUrl()%>><%= labList.get(j).getTitle() %></a>
										
										<%
									}
								}			
							}%>			         								
					</td>
					<!-- End Lab -->
					<!-- Start of Lecture # -->
					<!-- Grab Lecture List -->
					<%
						
						if(lectureList!=null){
							for(int j = 0; j < lectureList.size(); j++){
								if(j==0){
									%>
										<td align="center"><%= lectureList.get(j).getNumber() %></td>
										<td align="center"><%= lectureList.get(j).getDay() %></td>
										<td align="center"><%= lectureList.get(j).getDate() %></td>
										<!-- Getting Lecture Topics -->
										<td align="center">
										<%
											List<Topic> topicList = lectureList.get(j).getTopics();
											//Gettign File list to print
											List<File> fileList = new ArrayList<File>();
											if(topicList!=null){
												for(int k=0; k<topicList.size(); k++){
													if(k!=topicList.size()-1){
														%>
															<div class="<%=topicList.get(k).getTitle().toLowerCase() %>">
																<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a><hr />
															</div>
														<%
													}
													else{
														%>
														<div class="<%=topicList.get(k).getTitle().toLowerCase() %>">
															<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a>
														</div>
														<%
													}
													List<Program> topicProgram = topicList.get(k).getPrograms();
													if(topicProgram != null){
														for(int l = 0; l<topicProgram.size();l++){
															List<File> programFiles = topicProgram.get(l).getFiles();
															if(programFiles!=null){
																for(int z = 0; z< programFiles.size(); z++){
																	fileList.add(programFiles.get(z));
																}
															}
														}
													}
													
												}
											}
											
										%>
										</td>
										<!-- Chapter -->
										<td align="center">
										<!-- Combining chapter to a list -->
										<% 
											String chapters = "";
											if(topicList!=null){
												for(int k=0; k < topicList.size();k++){
													if(k!=topicList.size()-1){
														if(topicList.get(k).getChapter()!=null){
     													chapters += topicList.get(k).getChapter() + ", ";
     												}
													}
													else{
														if(topicList.get(k).getChapter()!=null){
															chapters += topicList.get(k).getChapter() + ".";
														}
													}
 												
 											}
											}				         											
										%>
										<%= chapters%>
										</td>
										<!-- Program -->
										<td align="center">
										<%
											for(int m = 0; m < fileList.size(); m++){
												if(m!=fileList.size()-1){
													%>
 												<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a><br />
 												<%
												}
												else{
													%>
 												<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a>
 												<%
												}	
											}
										%>
										</td>
									</tr>
										<!-- End Program -->
										
									<%
								}
								else{
									%><tr>
									<td align="center"><%= lectureList.get(j).getNumber() %></td>
									<td align="center"><%= lectureList.get(j).getDay() %></td>
									<td align="center"><%= lectureList.get(j).getDate() %></td>
									<!-- Getting Lecture Topics -->
									<td align="center">
									<%
										List<Topic> topicList = lectureList.get(j).getTopics();
										//Gettign File list to print
										List<File> fileList = new ArrayList<File>();
										if(topicList!=null){
											for(int k=0; k<topicList.size(); k++){
												if(k!=topicList.size()-1){
													%>
														<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a><hr />
													<%
												}
												else{
													%>
													<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a>
													<%
												}
												List<Program> topicProgram = topicList.get(k).getPrograms();
												if(topicProgram != null){
													for(int l = 0; l<topicProgram.size();l++){
														List<File> programFiles = topicProgram.get(l).getFiles();
														if(programFiles!=null){
															for(int z = 0; z< programFiles.size(); z++){
																fileList.add(programFiles.get(z));
															}
														}
													}
												}
												
											}
										}
										
									%>
									</td>
									<!-- Chapter -->
									<td align="center">
									<!-- Combining chapter to a list -->
									<% 
										String chapters = "";
										if(topicList!=null){
											for(int k=0; k < topicList.size();k++){
												if(k!=topicList.size()-1){
													if(topicList.get(k).getChapter()!=null){
 													chapters += topicList.get(k).getChapter() + ", ";
 												}
												}
												else{
													if(topicList.get(k).getChapter()!=null){
														chapters += topicList.get(k).getChapter() + ".";
													}
												}
												
											}
										}				         											
									%>
									<%= chapters%>
									</td>
									<!-- Program -->
									<td align="center">
									<%
										for(int m = 0; m < fileList.size(); m++){
											if(m!=fileList.size()-1){
												%>
												<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a><br />
												<%
											}
											else{
												%>
												<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a>
												<%
											}	
										}
									%>
									</td>
								</tr>
									<!-- End Program -->
									
								<%
								}
								%>
								<!-- Check For Assignment -->
								<%
								for(int n = 0; n<dueAssignment.size(); n++){
									if(dueAssignment.get(n).getDueDate().equals(lectureList.get(j).getDate())){
										%>
											<tr>
												<td align="center"></td>
												<td align="center"><%= lectureList.get(j).getDay() %></td>
												<td align="center"><%= dueAssignment.get(n).getDueDate() %></td>
												<td align="center" colspan="3"><font size="+1" color="red"><b>ASSIGNMENT <%= dueAssignment.get(n).getNumber()%> DUE</b></font></td>
											</tr>
										<%
									}
								}
								for(int n = 0; n < dueDeliverable.size(); n++){
									if(dueDeliverable.get(n).getDueDate().equals(lectureList.get(j).getDate())){
										
										%>
											<tr>
												<td align="center"></td>
												<td align="center"><%= lectureList.get(j).getDay() %></td>
												<td align="center"><%= dueDeliverable.get(n).getDueDate() %></td>
												<td align="center" colspan="3"><font size="+1" color="red"><b>FP - <%= dueDeliverable.get(n).getTitle()%> DUE</b></font></td>
											</tr>
										<%
									}
								}
							}				         						
						}
					}
			%></table><%
	}
	//DueDates
	else if(displayType.equals("dueDates")){
			%>
			<b>Lectures</b>
	     	<table border="2" cellpadding="5" width="590">
	     		<tr>
	     			<th align="center">Week #</th>
	     			<th align="center">Lab</th>
	     			<th align="center">Lecture #</th>
	     			<th align="center">Day</th>
	     			<th align="center">Date</th>
	     			<th align="center">Lecture Topic</th>
	     			<th align="center">Chapter</th>
	     			<th align="center">Program</th>
	 			</tr>
	 			
	 				<!-- Getting week list -->
	 			<%
		
			List<Week> weekList = mySchedule.getWeeks();
			for(int i = 0; i < weekList.size(); i++){
				List<Lab> labList = weekList.get(i).getLabs();
				List<Lecture> lectureList = weekList.get(i).getLectures();
				List<Assignment> assignmentList = myCourse.getAssignments();
			List<Deliverable> deliverableList = new ArrayList<Deliverable>();
			//For row span count
			List<Assignment> dueAssignment = new ArrayList<Assignment>();
			List<Deliverable> dueDeliverable = new ArrayList<Deliverable>();
			List<Assignment> finalProject = new ArrayList<Assignment>();
			//getting final project
			for(int l = 0; l < assignmentList.size(); l++){
     			if(assignmentList.get(l).getNumber().length() > 1){
     				finalProject.add(assignmentList.get(l));
 				} 
			}
			
			//Getting assignment due for the week
			for(int n = 0; n < lectureList.size(); n++){
				for(int m = 0; m < assignmentList.size(); m++){
					if(!assignmentList.get(m).getNumber().equals("Final Project")){
						if(assignmentList.get(m).getDueDate()!=null){
							if(assignmentList.get(m).getDueDate().equals(lectureList.get(n).getDate())){
								dueAssignment.add(assignmentList.get(m));
							}
						}													
					}
					
				}
				for(int m = 0; m<finalProject.size(); m++){
					deliverableList = finalProject.get(m).getDeliverables();
					for(int k=0; k<deliverableList.size(); k++){
						if(deliverableList.get(k).getDueDate()!=null){
							if(deliverableList.get(k).getDueDate().equals(lectureList.get(n).getDate())){
								dueDeliverable.add(deliverableList.get(k));
							}
						}													
					}
				}
			}
			
			
				
				%>
				<tr>
					<td align="center" rowspan=<%= dueDeliverable.size()+dueAssignment.size()%> colspan="3"><%= weekList.get(i).getWeek()%>
					</td>
					<%
						
						if(lectureList!=null){
							for(int j = 0; j < lectureList.size(); j++){								
								%>
								<!-- Check For Assignment -->
								<%
								for(int n = 0; n<dueAssignment.size(); n++){
									if(dueAssignment.get(n).getDueDate().equals(lectureList.get(j).getDate())){
										if(n!=0){
											%>
											<tr>
												<td align="center"><%= lectureList.get(j).getDay() %></td>
												<td align="center"><%= dueAssignment.get(n).getDueDate() %></td>
												<td align="center" colspan="3"><font size="+1" color="red"><b>ASSIGNMENT <%= dueAssignment.get(n).getNumber()%> DUE</b></font></td>
											</tr>
											<%
										}
										else{
										%>
											<td align="center"><%= lectureList.get(j).getDay() %></td>
											<td align="center"><%= dueAssignment.get(n).getDueDate() %></td>
											<td align="center" colspan="3"><font size="+1" color="red"><b>ASSIGNMENT <%= dueAssignment.get(n).getNumber()%> DUE</b></font></td>
										<%
										}
										
									}
								}
								for(int n = 0; n < dueDeliverable.size(); n++){
									if(dueDeliverable.get(n).getDueDate().equals(lectureList.get(j).getDate())){
										if(dueAssignment.size() ==0 && n==0){
											%>
												<td align="center"><%= lectureList.get(j).getDay() %></td>
												<td align="center"><%= dueDeliverable.get(n).getDueDate() %></td>
												<td align="center" colspan="3"><font size="+1" color="red"><b>FP - <%= dueDeliverable.get(n).getTitle()%> DUE</b></font></td>
										<%
										}
										else if((dueAssignment.size() == 0 && n!=0 )||(dueAssignment.size()>0&& n==0)){
											%>
											<tr>
												<td align="center"><%= lectureList.get(j).getDay() %></td>
												<td align="center"><%= dueDeliverable.get(n).getDueDate() %></td>
												<td align="center" colspan="3"><font size="+1" color="red"><b>FP - <%= dueDeliverable.get(n).getTitle()%> DUE</b></font></td>
											</tr>
											<%
										}										
									}
								}
							}				         						
						} %>
						<%
					
			}
			%></table><%
		}
	
	//lecture case
	else if(displayType.equals("lectures")){
		%>
		<b>Lectures</b>
     	<table border="2" cellpadding="5" width="590">
     		<tr>
     			<th align="center">Week #</th>
     			<th align="center">Lab</th>
     			<th align="center">Lecture #</th>
     			<th align="center">Day</th>
     			<th align="center">Date</th>
     			<th align="center">Lecture Topic</th>
     			<th align="center">Chapter</th>
     			<th align="center">Program</th>
 			</tr>
 			
 				<!-- Getting week list -->
 			<%
		List<Week> weekList = mySchedule.getWeeks();
		for(int i = 0; i < weekList.size(); i++){
			List<Lab> labList = weekList.get(i).getLabs();
			List<Lecture> lectureList = weekList.get(i).getLectures();
			List<Assignment> assignmentList = myCourse.getAssignments();
			List<Deliverable> deliverableList = new ArrayList<Deliverable>();
			%>
			<tr>
				<td align="center" rowspan=<%= lectureList.size()%>><%= weekList.get(i).getWeek() %></td>
				<td align="center" rowspan=<%= lectureList.size()%>>
					<!-- Starting Lab column -->
					<%
						
						if(labList!=null){
							for(int j = 0; j< labList.size(); j++){
								if(j!=labList.size()-1){
									%>
										<a href=<%= labList.get(j).getUrl()%>><%= labList.get(j).getTitle()%></a><hr />
									<%
								}
								else{
									%>
									<a href=<%= labList.get(j).getUrl()%>><%= labList.get(j).getTitle() %></a>
									<%
								}
							}			
						}%>			         								
				</td>
				<!-- End Lab -->
				<!-- Start of Lecture # -->
				<!-- Grab Lecture List -->
				<%
					
					if(lectureList!=null){
						for(int j = 0; j < lectureList.size(); j++){
							if(j==0){
								%>
									<td align="center"><%= lectureList.get(j).getNumber() %></td>
									<td align="center"><%= lectureList.get(j).getDay() %></td>
									<td align="center"><%= lectureList.get(j).getDate() %></td>
									<!-- Getting Lecture Topics -->
									<td align="center">
									<%
										List<Topic> topicList = lectureList.get(j).getTopics();
										//Gettign File list to print
										List<File> fileList = new ArrayList<File>();
										if(topicList!=null){
											for(int k=0; k<topicList.size(); k++){
												if(k!=topicList.size()-1){
													%>
													<div class="<%=topicList.get(k).getTitle().toLowerCase() %>">
														<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a><hr />
													</div>
													<%
												}
												else{
													%>
													<div class="<%=topicList.get(k).getTitle().toLowerCase() %>">
														<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a>
													</div>
													<%
												}
												List<Program> topicProgram = topicList.get(k).getPrograms();
												if(topicProgram != null){
													for(int l = 0; l<topicProgram.size();l++){
														List<File> programFiles = topicProgram.get(l).getFiles();
														if(programFiles!=null){
															for(int z = 0; z< programFiles.size(); z++){
																fileList.add(programFiles.get(z));
															}
														}
													}
												}
												
											}
										}
										
									%>
									</td>
									<!-- Chapter -->
									<td align="center">
									<!-- Combining chapter to a list -->
									<% 
										String chapters = "";
										if(topicList!=null){
											for(int k=0; k < topicList.size();k++){
												if(k!=topicList.size()-1){
													if(topicList.get(k).getChapter()!=null){
 													chapters += topicList.get(k).getChapter() + ", ";
 												}
												}
												else{
													if(topicList.get(k).getChapter()!=null){
														chapters += topicList.get(k).getChapter() + ".";
													}
												}
												
											}
										}				         											
									%>
									<%= chapters%>
									</td>
									<!-- Program -->
									<td align="center">
									<%
										for(int m = 0; m < fileList.size(); m++){
											if(m!=fileList.size()-1){
												%>
												<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a><br />
												<%
											}
											else{
												%>
												<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a>
												<%
											}	
										}
									%>
									</td>
								</tr>
									<!-- End Program -->
									
								<%
							}
							else{
								%><tr>
								<td align="center"><%= lectureList.get(j).getNumber() %></td>
								<td align="center"><%= lectureList.get(j).getDay() %></td>
								<td align="center"><%= lectureList.get(j).getDate() %></td>
								<!-- Getting Lecture Topics -->
								<td align="center">
								<%
									List<Topic> topicList = lectureList.get(j).getTopics();
									//Gettign File list to print
									List<File> fileList = new ArrayList<File>();
									if(topicList!=null){
										for(int k=0; k<topicList.size(); k++){
											if(k!=topicList.size()-1){
												%>
													<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a><hr />
												<%
											}
											else{
												%>
												<a href=<%= topicList.get(k).getUrl()%>><%= topicList.get(k).getTitle() %></a>
												<%
											}
											List<Program> topicProgram = topicList.get(k).getPrograms();
											if(topicProgram != null){
												for(int l = 0; l<topicProgram.size();l++){
													List<File> programFiles = topicProgram.get(l).getFiles();
													if(programFiles!=null){
														for(int z = 0; z< programFiles.size(); z++){
															fileList.add(programFiles.get(z));
														}
													}
												}
											}
											
										}
									}
									
								%>
								</td>
								<!-- Chapter -->
								<td align="center">
								<!-- Combining chapter to a list -->
								<% 
									String chapters = "";
									if(topicList!=null){
										for(int k=0; k < topicList.size();k++){
											if(k!=topicList.size()-1){
												if(topicList.get(k).getChapter()!=null){
													chapters += topicList.get(k).getChapter() + ", ";
												}
											}
											else{
												if(topicList.get(k).getChapter()!=null){
													chapters += topicList.get(k).getChapter() + ".";
												}
											}
											
										}
									}				         											
								%>
								<%= chapters%>
								</td>
								<!-- Program -->
								<td align="center">
								<%
									for(int m = 0; m < fileList.size(); m++){
										if(m!=fileList.size()-1){
											%>
											<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a><br />
											<%
										}
										else{
											%>
											<a href=<%= fileList.get(m).getUrl() %>><%= fileList.get(m).getTitle() %></a>
											<%
										}	
									}
								%>
								</td>
							</tr>
								<!-- End Program -->
								
							<%
							}			
						}				         						
					}
				}
		}
	%></table><%
%>
