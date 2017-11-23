<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
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
	
	String term = request.getParameter("term");
	Schedule mySchedule = myCourse.getSchedule();
	List<Week> weekList = mySchedule.getWeeks();
	for(int i = 0 ; i < weekList.size(); i++){
		List<Lecture> lectureList = weekList.get(i).getLectures();
		if(lectureList!=null){
			for(int j = 0; j < lectureList.size(); j++){
				List<Topic> topicList = lectureList.get(j).getTopics();
				if(topicList!=null){
					for(int k = 0; k<topicList.size(); k++){
						if(topicList.get(k).getTitle().toLowerCase().contains(term)){
							%><%= topicList.get(k).getTitle().toLowerCase() + "," %><%
						}
					}
				}
			}
		}
	}
	
	

%>
