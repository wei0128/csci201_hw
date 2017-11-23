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
						<font size="+1"><%=myDepartment.getPrefix()+myCourse.getNumber()%> Home</font><br /><br />
						<font size="+1"><a href=<%=myCourse.getSyllabus().getUrl()%>>Syllabus</a></font><br /><br />
						<font size="+1"><a href="lectures.jsp">Lectures</a></font><br /><br />
          				<font size="+1"><a href="assignments.jsp">Assignments</a></font><br /><br />
          				<font size="+1"><a href="exam.jsp">Previous Exams</a></font><br /><br />
					</td>
					<!--  breaking column -->
					<td width="5"><br /></td>
					
					<!-- center large -->
					<td align="baseline" width="615">
						<br />
						<p>
				            <b><font size="+3"><%= myDepartment.getPrefix() + " " + myCourse.getNumber()%></font></b><br />
				            <b><i><font size="+1"><%= myCourse.getTitle() + " - " + myCourse.getUnits() + " units" %></font></i></b><br />
				            <b><i><font size="+1"><%= myCourse.getTerm() + " " + myCourse.getYear() %></font></i></b><br />
				          <p><hr size="4"/>
				          <p></p>
				          <p>
						  <table border="1">
								<tr>
                					<th align="center">Picture</th>
					                <th align="center">Professor</th>
					                <th align="center">Office</th>
					                <th align="center">Phone</th>
					                <th align="center">Email</th>
					                <th align="center">Office Hours</th>
              					</tr>
              					<!-- Professor's imforation -->
              					<%	
	              					StaffMembers professor = null;
	              					List<StaffMembers> staffList = myCourse.getStaffMembers();
	              					for(int i = 0; i < staffList.size(); i++){
	              						if(staffList.get(i).getType().equals("instructor")){
	              							professor = staffList.get(i);
	              						}
	              					}
              					%>
              					<tr>
              						<td><img width="100" height="100" src=<%=professor.getImage()%> /></td>
              						<td><font size="-1"><%=professor.getName().getFname() + " " + professor.getName().getLname()%></font></td>
              						<td><font size="-1"><%=professor.getOffice() %></font></td>
              						<td><font size="-1"><%=professor.getPhone() %></font></td>
              						<td><font size="-1"><%=professor.getEmail() %></font></td>
              						<td><font size="-1">
              						<%
              							List<OfficeHours> profHours; 
              							profHours = professor.getOfficeHours();
              							for(int i = 0; i < profHours.size(); i++){
              								Time t = profHours.get(i).getTime();
              								String day = profHours.get(i).getDay();
              								%><%= day + " " + t.getStart() + "-" + t.getEnd() %>
              						<%
              								if(i!=profHours.size()-1){
              									%><hr /><% 
              								}
              							}
              						%>
              						</font></td>
              					</tr>		
						</table>
						<!-- End of professor -->	
						<br />	
						<p><hr size="4" />
						<!--  Start of lecture Schedule -->
						<b><font size="+1">Lecture Schedule</font></b>	
						<table border="2" cellpadding="5" width="590">
							<tr>		
								<th align="center">Sect. No.</th>
								<th align="center">Day &amp; Time</th>
								<th align="center">Room</th>
								<th>Lecture Assistant</th>
							</tr>
								<!-- Get lecture information -->
								<%
									List<Meetings> lectures = new ArrayList<Meetings>();
									for(int i = 0; i < myCourse.getMeetings().size(); i++){
										if(myCourse.getMeetings().get(i).getType().equals("lecture")){
											lectures.add(myCourse.getMeetings().get(i));
										}
									}
									
									//Looping over lecture only
									for(int i = 0; i < lectures.size(); i++){
										%>
										<tr>
										<td align="center"><font size="-1"><%=lectures.get(i).getSection() %></font></td>
										<!-- Getting hours -->
										<%
											List<OfficeHours> lectureHours = lectures.get(i).getMeetingPeriods();
											%><td align="center"><font size="-1"><%
											for(int k = 0; k<lectureHours.size(); k++){
												Time t = lectureHours.get(k).getTime();
												%><%= lectureHours.get(k).getDay() +" " + t.getStart()+"-"+t.getEnd() %><%
												if(k!=lectureHours.size()-1){
													%>,<br /><%
												}
											}
										%></font></td>
										<td align="center"><font size="-1"><%= lectures.get(i).getRoom() %></font></td>
										<!-- Getting assitant -->
										<% 
											StaffMembers assistant = null;
											String id = null;
											if(lectures.get(i).getAssistants()!=null){
												id = lectures.get(i).getAssistants().get(0).getStaffMemberID(); // only one for lecture
											}
						
											for(int j = 0; j < staffList.size(); j++){
												if(staffList.get(j).getId().equals(id)){
													assistant = staffList.get(j);
												}
											}
										%>
										<%if(assistant !=null){
											if(assistant.getImage() == null){
												%><td align="center"><font size="-1"><img src=<%= mySchool.getImage() %> /><br /><a href=<%="mailto:" + assistant.getEmail()%>><%=assistant.getName().getFname() + " "+assistant.getName().getLname() %></a></font></td> 
												<%
											}
											else{
												%><td align="center"><font size="-1"><img src=<%= assistant.getImage() %> /><br /><a href=<%="mailto:" + assistant.getEmail()%>><%=assistant.getName().getFname() + " "+assistant.getName().getLname() %></a></font></td> 
												<%
											}
										}
										%>
										</tr>	
										<%
									}
								%>
						</table>
						<!-- End of lecture -->
						<!-- Start of lab schedule -->
						<p><hr size="4" /></p>
						<b><font size="+1">Lab Schedule</font></b>
						<table border="2" cellpadding="5" width="590">
							<tr>
								<th align="center">Sect. No.</th>
								<th align="center">Day &amp; Time</th>
								<th align="center">Room</th>
								<th align="center">Lab Assistants</th>
							</tr>
							<!-- Creating Lab List -->
							<%
								List<Meetings> labList = new ArrayList<Meetings>();
								for(int i = 0; i < myCourse.getMeetings().size(); i++){
									if(myCourse.getMeetings().get(i).getType().equals("lab")){
										labList.add(myCourse.getMeetings().get(i));
									}
								}							
								for(int i=0; i<labList.size(); i++){
									%><tr>
									<td align="center"><font size="-1"><%=labList.get(i).getSection() %></font></td>
									<%
										List<OfficeHours> meetingPeriod = labList.get(i).getMeetingPeriods();
										%><td align="center"><font size="-1"><%
										for(int k =0; k<meetingPeriod.size(); k++){
											Time t = meetingPeriod.get(k).getTime();
											%><%= meetingPeriod.get(k).getDay() +" " + t.getStart()+"-"+t.getEnd() %><%
											if(k!=meetingPeriod.size()-1){
											%>,<br /><%
											}
										}
									%></font></td>
									<td align="center"><font size="-1"><%=labList.get(i).getRoom() %></font></td>
									<td align="center">
										<table border="0">
											<tr>
											<!-- Lab Assistants -->
											<%
												List<StaffMembers> labStaff = new ArrayList<StaffMembers>();
												List<Assistant> assistants = labList.get(i).getAssistants();
												//Match lab assistant id with staff id
												for(int j = 0; j < assistants.size(); j++)
												{
													for(int z = 0; z<staffList.size();z++){
														if(staffList.get(z).getId().equals(assistants.get(j).getStaffMemberID())){
															labStaff.add(staffList.get(z));
														}
													}
												}
												for(int j = 0; j < labStaff.size(); j++){
													%><td align="center"><font size="-1">
													<%if(labStaff.get(j).getImage() == null){
														%><img src=<%= mySchool.getImage() %>><br /><a href=<%= "mailto:"+labStaff.get(j).getEmail() %>><%=labStaff.get(j).getName().getFname() +" " +labStaff.get(j).getName().getLname()  %></a><%
													}
													else{
														%><img src=<%= labStaff.get(j).getImage() %>><br /><a href=<%= "mailto:"+labStaff.get(j).getEmail() %>><%=labStaff.get(j).getName().getFname() +" " +labStaff.get(j).getName().getLname() %></a><%
													}
													%></font></td><%
												}
											%>
											</tr>
										</table>
									</td>
								<%
								}
							%>
						</table>
						<!-- End of Lab Assistant -->
						<!-- Start of Office Hours -->
						<br /><hr size="4" /><br />
							<b><font size="+1">Office Hours</font></b> - All staff office hours are held in the SAL Open Lab. Prof. <%=professor.getName().getLname() %>'s office hours are listed above.<br />
							<table border ="1">
								<tr>
									<th></th>
									<th>10:00a.m.-12:00p.m.</th>
					                <th>12:00p.m.-2:00p.m.</th>
					                <th>2:00p.m.-4:00p.m.</th>
					                <th>4:00p.m.-6:00p.m.</th>
					                <th>6:00p.m.-8:00p.m.</th>
					            </tr>
					            <!-- Ordering the office hours  -->
					            <%
					            	//Initialize to null
					            	StaffMembers[][] officeHours = new StaffMembers[5][6];	
					            	for(int i = 0 ; i < 5; i++){
					            		for(int j = 0; j<6; j++){
					            			officeHours[i][j] = null;
					            		}
					            	}
					            
					            	//Fit stall to proper slot
						            for(int i = 0; i < staffList.size(); i++){
					            		List<OfficeHours> oh = new ArrayList<OfficeHours>();
					            		//Exclude professor
					            		if(staffList.get(i).getType()!="instructor" && staffList.get(i).getOfficeHours()!=null){
					            			oh = staffList.get(i).getOfficeHours();
					            		}
					            		//Check all office hour and put into appropriate position
					            		for(int j = 0; j < oh.size(); j++){
					            			if(oh.get(j).getDay().equals("Monday")){
					            				if(oh.get(j).getTime().getStart().equals("10:00a.m.")){
					            					officeHours[0][0] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("12:00p.m.")){
					            					officeHours[1][0] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("2:00p.m.")){
					            					officeHours[2][0] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("4:00p.m.")){
					            					officeHours[3][0] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("6:00p.m.")){
					            					officeHours[4][0] = staffList.get(i);
					            				}
					            			}
					            			else if(oh.get(j).getDay().equals("Tuesday")){
					            				if(oh.get(j).getTime().getStart().equals("10:00a.m.")){
					            					officeHours[0][1] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("12:00p.m.")){
					            					officeHours[1][1] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("2:00p.m.")){
					            					officeHours[2][1] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("4:00p.m.")){
					            					officeHours[3][1] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("6:00p.m.")){
					            					officeHours[4][1] = staffList.get(i);
					            				}
					            			}
					            			else if(oh.get(j).getDay().equals("Wednesday")){
					            				if(oh.get(j).getTime().getStart().equals("10:00a.m.")){
					            					officeHours[0][2] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("12:00p.m.")){
					            					officeHours[1][2] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("2:00p.m.")){
					            					officeHours[2][2] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("4:00p.m.")){
					            					officeHours[3][2] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("6:00p.m.")){
					            					officeHours[4][2] = staffList.get(i);
					            				}
					            			}
					            			else if(oh.get(j).getDay().equals("Thursday")){
					            				if(oh.get(j).getTime().getStart().equals("10:00a.m.")){
					            					officeHours[0][3] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("12:00p.m.")){
					            					officeHours[1][3] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("2:00p.m.")){
					            					officeHours[2][3] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("4:00p.m.")){
					            					officeHours[3][3] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("6:00p.m.")){
					            					officeHours[4][3] = staffList.get(i);
					            				}
					            			}
					            			else if(oh.get(j).getDay().equals("Friday")){
					            				if(oh.get(j).getTime().getStart().equals("10:00a.m.")){
					            					officeHours[0][4] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("12:00p.m.")){
					            					officeHours[1][4] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("2:00p.m.")){
					            					officeHours[2][4] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("4:00p.m.")){
					            					officeHours[3][4] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("6:00p.m.")){
					            					officeHours[4][4] = staffList.get(i);
					            				}
					            			}
					            			else if(oh.get(j).getDay().equals("Saturday")){
					            				if(oh.get(j).getTime().getStart().equals("10:00a.m.")){
					            					officeHours[0][5] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("12:00p.m.")){
					            					officeHours[1][5] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("2:00p.m.")){
					            					officeHours[2][5] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("4:00p.m.")){
					            					officeHours[3][5] = staffList.get(i);
					            				}
					            				else if(oh.get(j).getTime().getStart().equals("6:00p.m.")){
					            					officeHours[4][5] = staffList.get(i);
					            				}
					            			}
					            		}
					            	}
					            %>
					            <!-- Putting office hour into table -->
					            <%
					            	//List of day to match loop
					            	List<String> days = new ArrayList<String>();
					            	days.add("Monday");
					            	days.add("Tuesday");
					            	days.add("Wednesday");
					            	days.add("Thursday");
					            	days.add("Friday");
					            	days.add("Saturday");
					            	for(int i = 0; i < 6; i++){
					            		%><tr>
					            		<th><%=days.get(i) %></th>
					            		<% for(int j = 0; j < 5; j++){
					            			//Case if no img avialble
					            			%><td><% if(officeHours[j][i]!=null){
					            				%><table border="0"><tr><td><img src=<%= officeHours[j][i].getImage()%> /></td></tr>
					            				<tr><td><font size="-1"><a href=<%= "mailto:"+ officeHours[j][i].getEmail() %>><%= officeHours[j][i].getName().getFname() + " " + officeHours[j][i].getName().getLname() %></a><br />&nbsp;</font></td></tr></table></td><%
					            			}
					            			else{
					            				%><table border="0"><tr><td><img src=<%= mySchool.getImage()%> /></td></tr>
					            				<tr><td><font size="-1">No Staff Available<br />&nbsp;</font></td></tr></table></td><%
					            			}
					            		}
					            	}
					            %>
							</table>	
							<!-- End of Office Hours -->	
					</td>
					<!-- End of Center-->	
				</tr>
			</tbody>
		</table>

	</body>
</html>