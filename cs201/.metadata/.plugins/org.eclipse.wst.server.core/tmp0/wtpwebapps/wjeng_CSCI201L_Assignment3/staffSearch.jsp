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
	
	List<StaffMembers> staffList = myCourse.getStaffMembers();
	
	String term = request.getParameter("term");
	for(int i = 0; i < staffList.size(); i++){
		if(term.equals(staffList.get(i).getName().getFname().toLowerCase()) || term.equals(staffList.get(i).getName().getLname().toLowerCase()) || 
				term.equals(staffList.get(i).getName().getFname().toLowerCase() +" "+ staffList.get(i).getName().getLname().toLowerCase())){
			%> <%= staffList.get(i).getName().getFname().toLowerCase() + " " +staffList.get(i).getName().getLname().toLowerCase() + "," %><%
		}
	}

%>