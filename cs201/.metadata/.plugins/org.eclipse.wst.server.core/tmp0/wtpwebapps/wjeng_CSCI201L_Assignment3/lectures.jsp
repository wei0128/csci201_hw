<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="objects.*" import="java.util.List" import="java.util.ArrayList"
%>

<%
	Collection myCollection = (Collection)session.getAttribute("Schools");
	//Setting up some basic variable and names
	if(myCollection == null){
		response.sendRedirect("index.jsp");
		return;
	}
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
		<script>
			function handleSelection(){
				//get all selection and check whcih one is selected
				var radios = document.getElementsByName('selection');
				var displayType = "";
				for(var i = 0; i<radios.length; i++){
					if(radios[i].checked){
						displayType = radios[i].value;
					}
				}
				if(displayType === "dueDates"){
					var buttons = document.getElementsByName('searchButton');
					for(var i = 0; i < buttons.length; i++){
						buttons[i].disabled = true;
					}
				}
				else{
					var buttons = document.getElementsByName('searchButton');
					for(var i = 0; i < buttons.length; i++){
						buttons[i].disabled = false;
					}
				}
				
				//Send data to lectureFilter page through ajax
				var xhttp = new XMLHttpRequest();
				xhttp.open("GET", "lectureFilter.jsp?displayType="+displayType, false);
				xhttp.send();
				var filtered = xhttp.responseText;
				if(filtered.trim() === "expired"){
					window.location = "index.jsp";
				}

				document.getElementById("lectureBody").innerHTML = filtered;
				
			}
			
			function searchTopic(){
				resetSearch();
				var term = document.searchForm.searchTerm.value;
				term = term.toLowerCase();
				var xhttp = new XMLHttpRequest();
				xhttp.open("GET", "searchTopic.jsp?term="+term, false);
				xhttp.send();
				var matchString = xhttp.responseText.trim();
				var fix = matchString.substring(0,matchString.length -1);
				var matchList = fix.split(",");
				for(var i = 0; i < matchList.length; i++){
					var name = matchList[i];
					var classList = document.getElementsByClassName(name);					
					for(var k = 0; k< classList.length; k++){						
						classList[k].style.backgroundColor = "red";
					}
				}
			}
			function resetSearch(){
				var allClass = document.getElementsByTagName("*");
				var classNames = [];
				for(var i = 0; i < allClass.length; i++){
					if(allClass[i].className!==''){
						classNames.push(allClass[i]);
					}
				}
				for(var i = 0; i < classNames.length; i++){
					var classElements = document.getElementsByClassName(classNames[i].className)
					for(var k = 0; k < classElements.length; k++){
						if(classElements[k].style.background ==="red"){
							classElements[k].style.backgroundColor = "transparent";
						}
						
					}
				}	
			}
		</script>
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
						<div class="navCont"><font size="+1">Lectures</font><br /><br /></div>
          				<div class="navCont"><font size="+1"><a href="assignments.jsp">Assignments</a></font><br /><br /></div>
          				<div class="navCont"><font size="+1"><a href="exam.jsp">Previous Exams</a></font><br /><br /></div>
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
				         <p><hr size="4"/></p>
				         	<!-- Grabbing Schedule -->
				         	<%
				         		Schedule mySchedule = myCourse.getSchedule();
				         		TextBooks myText = mySchedule.getTextbooks().get(0);
				         	%>
				         	<div id="book">
				         	<b>Chapter references are from <%= myText.getAuthor() + ". "%><u><%= myText.getTitle()+", "%></u><%= myText.getPublisher()+", "+myText.getYear()+". "+myText.getIsbn()%></b>
				         	</div>
				         	<p><hr size="4"></p>
				         	
				         	<!-- Search  -->
				          <form name = "searchForm">
					          <input type="text" name="searchTerm">
					          <button class="buttons" name ="searchButton" type="button" onclick="searchTopic();">Search Topics</button>
					          <button class="buttons" name = "searchButton" type="reset" onclick="resetSearch();">Clear Search</button>
				          </form>
				          
				          <p><hr size="4"/>
				          <!-- End of Search -->
				         	
				         	<!-- Display selection -->
				         	<input  type="radio" name="selection" value="lectures" onclick="handleSelection();">Show Lectures
				         	<input type="radio" name="selection" value="dueDates" onclick="handleSelection();">Show Due Dates
				         	<input type="radio" name="selection" value="all" onclick="handleSelection();" checked>Show All </b>
				         	<p><hr size="4"></p>
				         	
				         	<div id="lectureBody">
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
			         			%>
				         	</table>
						</div>
				    </td>
				    <!--  End of Center -->
				</tr>
			</tbody>
		</table>
	</body>
</html>