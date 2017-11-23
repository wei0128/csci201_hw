package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonSyntaxException;

import objects.Course;
import objects.Department;
import objects.Meeting;
import objects.School;
import objects.StaffMember;
import parsing.Parser;

public class Main {
	
	private BufferedReader br;
	//member variable that contains all the JSON data
	private Parser parser;
	
	//static final member variables
	//string that has just the last two menu options
	private static final String MENU = "%d) Go to %s"+System.lineSeparator()+"%d) Exit"+System.lineSeparator()+"what would you like to do?";
	//string for the main menu
	private static final String MAIN_MENU = "Main Menu"+System.lineSeparator()+"1) View Schools"+System.lineSeparator()+"2) Exit"+System.lineSeparator()+"what would you like to do?";
	//string for a course menu
	private static final String COURSE_MENU = "1) View course staff"+System.lineSeparator()+"2) View meeting information"+System.lineSeparator()+String.format(MENU, 3, "%s", 4);
	//string for meetings menu
	private static final String MEETING_MENU = "Meeting Information"+System.lineSeparator()+"1) Lecture"+System.lineSeparator()+"2) Lab"+System.lineSeparator()+"3) Quiz"+System.lineSeparator()+String.format(MENU, 4, "%s", 5);
	//string for staff menu
	private static final String STAFF_MENU = "Course Staff"+System.lineSeparator()+"1) View Instructors"+System.lineSeparator()+"2) View TAs"+System.lineSeparator()+"3) View CPs"+System.lineSeparator()+"4) View Graders"+System.lineSeparator()+String.format(MENU, 5, "%s", 6);
	//set containing the valid integer inputs for the main menu
	private static final Set<Integer> MAIN_MENU_OPTIONS = new HashSet<>(Arrays.asList(1, 2));
	//set containing the valid integer inputs for the course menu
	private static final Set<Integer> COURSE_OPTIONS = new HashSet<>(Arrays.asList(1,2,3,4));
	//set containing the valid integer inputs for the meetings menu
	private static final Set<Integer> MEETING_OPTIONS = new HashSet<>(Arrays.asList(1,2,3,4,5));
	//set containing the valid integer inputs for the staff members menu
	private static final Set<Integer> STAFF_OPTIONS = new HashSet<>(Arrays.asList(1,2,3,4,5,6));
	//map from integer input to the type of meeting
	private static final Map<Integer, String> MEETING_CHOICE;
	//map from integer input to the type of staff member
	private static final Map<Integer, String> STAFF_CHOICE;
	
	//static constructor to initialize and populate final static maps
	static{
		MEETING_CHOICE = new HashMap<>();
		STAFF_CHOICE = new HashMap<>();
		MEETING_CHOICE.put(1, "lecture");
		MEETING_CHOICE.put(2, "lab");
		MEETING_CHOICE.put(3, "quiz");
		STAFF_CHOICE.put(1, "instructor");
		STAFF_CHOICE.put(2, "ta");
		STAFF_CHOICE.put(3, "cp");
		STAFF_CHOICE.put(4, "grader");
	}
	
	//default constructor for Main object
	public Main(){
		br = new BufferedReader(new InputStreamReader(System.in));
		//loop until given a valid file
		Boolean gotFile = getFilename();
		
		while (!gotFile){
			gotFile = getFilename();
		}
		//loop through the schools to create the schools menu
		StringBuilder sb = new StringBuilder("Schools"+System.lineSeparator());
		List<School> schools = parser.getData().getSchools();
		int i;
		for (i = 0; i<schools.size(); i++){
			sb.append((i+1)+") "+schools.get(i).getName()+System.lineSeparator());
		}
		//format the static menu string with the 'go to' and 'exit' options and append to main menu
		sb.append(String.format(MENU, i+1, "Main Menu", i+2));
		parser.getData().setSchoolsMenu(sb.toString());
		//add the valid integer inputs to the schools menu
		parser.getData().setMenuOptions(getOptions(i));
		
		mainMenu();
	}
	
	//method that queries for an input file and parses it
	private Boolean getFilename(){
		try {
			System.out.println("What is the name of the input file?");
			parser = new Parser(br.readLine());
			//check we have at least one school
			if (parser.getData() == null || parser.getData().getSchools() == null || parser.getData().getSchools().isEmpty()){
				System.out.println("That file is not a well-formed JSON file.");
				return false;
			}
			
			else{
				
				for (School school : parser.getData().getSchools()){
					//check we have at least one department and schools have all attributes
					if (school.getDepartments() == null || school.getDepartments().isEmpty() || school.getName() == null){
						System.out.println("That file is not a well-formed JSON file.");
						return false;
					}
					
					else{
						
						for (Department dept : school.getDepartments()){
							//check we have at least one course and departments have all attributes
							if (dept.getCourses() == null || dept.getCourses().isEmpty() || dept.getLongName() == null || dept.getPrefix() == null){
								System.out.println("That file is not a well-formed JSON file.");
								return false;
							}
							
							else{
								
								for (Course course : dept.getCourses()){
									//check course attributes
									if (course.getNumber() == null || course.getTerm() == null || course.getYear() == null){
										System.out.println("That file is not a well-formed JSON file.");
										return false;
									}
									//check we have at least one staff member and at least one meeting
									else if (course.getMeetings() == null || course.getStaffMembers() == null || course.getMeetings().isEmpty() || course.getStaffMembers().isEmpty()){
										System.out.println("That file is not a well-formed JSON file.");
										return false;
									}
									
									else{
										//check we that meetings have type and section attributes
										for (Meeting meeting : course.getMeetings()){
											
											if (meeting.getMeetingType() == null || meeting.getSection() == null){
												System.out.println("That file is not a well-formed JSON file.");
												return false;
											}
										}
										//check that staff members have required attributes
										for (StaffMember staff: course.getStaffMembers()){
											
											if (staff.getName() == null || staff.getName().getFirstName() == null || staff.getName().getLastName() == null){
												System.out.println("That file is not a well-formed JSON file.");
												return false;
											}
											
											else if (staff.getID() == null || staff.getJobType() == null){
												System.out.println("That file is not a well-formed JSON file.");
												return false;
											}
										}
									}
								}
							}
						}
					} 
				}
			}
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("That file could not be found.");
			return false;
		} catch (IOException | JsonSyntaxException e){
			System.out.println("That file is not a well-formed JSON file.");
			return false;
		} 
	}
	
	//this method will print the specified menu and query for an input until the user provides
	//a valid integer input that is contained in the options set
	public Integer promptUser(String menuMsg, Set<Integer> options){
		Boolean haveInput = false;
		Integer input = null;
		//loop until provided a valid input
		while (!haveInput){

			try{
				System.out.println(menuMsg);
				input = Integer.parseInt(br.readLine());
				
				if (options.contains(input)){
					haveInput = true;
				}
				else{
					System.out.println("That is not a valid option");
				}
			}
			catch (IOException | NumberFormatException e){
				System.out.println("That is not a valid option");
			}
			
		}
		return input;
	}
	
	//given an integer i, return a set of integers from 1 -> i+2
	private Set<Integer> getOptions(int i){
		Set<Integer> options = new HashSet<>();
		
		for (int j = i+2; j>0; j--) {
			options.add(j);
		}
		
		return options;
	}
	
	private void exit(){
		System.out.println("Thanks for using my program!");
		System.exit(0);
	}
	
	//prints the main menu
	private void mainMenu(){
		Integer menuChoice = promptUser(MAIN_MENU, MAIN_MENU_OPTIONS);
		
		if (menuChoice == 2){
			exit();
		}
		else{
			schoolsMenu();
		}
	}
	
	//prints the menu for choosing a school
	private void schoolsMenu(){
		//get a valid input from the user
		Integer schoolChoice = promptUser(parser.getData().getSchoolsMenu(), parser.getData().getMenuOptions());
		Integer numSchools = parser.getData().getSchools().size();
		//if the user's input was less than or equal to the number of departments, we know they chose to see a department
		if (schoolChoice <= numSchools){
			departmentsMenu(parser.getData().getSchools().get(schoolChoice-1));
		}
		//they chose to go back to the previous menu
		else if (schoolChoice == numSchools+1){
			mainMenu();
		}
		else{
			exit();
		}
	}

	//prints the menu for choosing a department
	private void departmentsMenu(School school){
		//set the departments menu on the school object if it hasn't been created yet
		if (school.getDepartmentsMenu() == null){
			StringBuilder sb = new StringBuilder("Departments"+System.lineSeparator());
			int i;
			for (i = 0; i<school.getDepartments().size(); i++){
				Department department = school.getDepartments().get(i);
				sb.append((i+1)+") "+department.getLongName()+"("+department.getPrefix()+")"+System.lineSeparator());
			}
			
			sb.append(String.format(MENU, i+1, school.getName()+" menu", i+2));
			//set the menu as a member variable of the school object
			school.setDepartmentsMenu(sb.toString());
			//set the set of valid options as a member variable of the school object
			school.setMenuOptions(getOptions(i));
		}
		//get a valid input from the user
		Integer menuChoice = promptUser(school.getDepartmentsMenu(), school.getMenuOptions());
		Integer numDepartments = school.getDepartments().size();
		//if the menu choice is less than or equal to the number of choices, we know they chose a department
		if (menuChoice <= numDepartments){
			coursesMenu(school, school.getDepartments().get(menuChoice-1));
		}
		//they chose to go back to the previous menu
		else if (menuChoice == numDepartments+1){
			schoolsMenu();
		}
		else{
			exit();
		}
	}
	
	//prints the menu for choosing a course
	private void coursesMenu(School school, Department department){
		//check if we have not yet created the menu string for this department
		if (department.getCoursesMenu() == null){
			//build the menu for this department
			StringBuilder sb = new StringBuilder(department.getPrefix()+" Courses"+System.lineSeparator());
			int i;
			for (i = 0; i<department.getCourses().size(); i++){
				Course course = department.getCourses().get(i);
				sb.append((i+1)+") "+department.getPrefix()+" "+course.getNumber()+" "+course.getTerm()+" "+course.getYear()+System.lineSeparator());
			}
			
			sb.append(String.format(MENU, i+1, "Departments menu", i+2));
			//set the menu as a member variable of the department object
			department.setCoursesMenu(sb.toString());
			//set the set of valid options as a member variable of the department object
			department.setMenuOptions(getOptions(i));
		}
	
		//get a valid input from the user
		Integer menuChoice = promptUser(department.getCoursesMenu(), department.getMenuOptions());
		Integer numCourses = department.getCourses().size();
		//if the menu choice is less than or equal to the number of choices, we know they chose a course
		if (menuChoice <= numCourses){
			courseMenu(school, department, department.getCourses().get(menuChoice-1));
		}
		//the user chose to go back to the previous menu
		else if (menuChoice == numCourses+1){
			departmentsMenu(school);
		}
		//the user chose to exit
		else{
			exit();
		}
		
	}
	
	//prints the menu for choosing either staff or meetings for a course
	private void courseMenu(School school, Department department, Course course){
		String courseName = department.getPrefix()+" "+course.getNumber()+" "+course.getTerm()+" "+course.getYear();
		//get a valid input from the user -- pass in the formatted course menu string
		Integer menuChoice = promptUser(courseName+System.lineSeparator()+String.format(COURSE_MENU, department.getPrefix()+" Courses "), COURSE_OPTIONS);
		
		switch (menuChoice){
			case 4:
				exit();
				break;
			case 3:
				coursesMenu(school, department);
				break;
			case 2:
				meetingsMenu(school, department, course, courseName);
				break;
			case 1:
				staffMenu(school, department, course, courseName);
				break;
				
		}
		
	}
	
	//since both staffMenu and meetingsMenu go back to the same previous menu, we can have a helper method for that part
	private void exitOrCourseMenu(Integer courseMenu, Integer exit, Integer menuChoice, School school, Department department, Course course){
		
		if (menuChoice == courseMenu){
			courseMenu(school, department, course);
		}
		
		else if (menuChoice == exit){
			exit();
		}
	}
	
	//prints the menu for choosing a staff type
	private void staffMenu(School school, Department department, Course course, String courseName){
		//get a valid input from the user -- pass in the formatted staff menu
		Integer menuChoice = promptUser(courseName+System.lineSeparator()+String.format(STAFF_MENU, courseName+" menu"), STAFF_OPTIONS);
		//check to see if we need to exit or go back to the previous menu
		exitOrCourseMenu(5, 6, menuChoice, school, department, course);
		
		//get the staff objects sorted into different buckets based on 'type' attribute
		Map<String, List<StaffMember>> sortedStaff = course.getSortedStaff();
		//get the list of staff members to display
		String staffChoice = STAFF_CHOICE.get(menuChoice);
		List<StaffMember> toShow = sortedStaff.get(staffChoice);
		//using the staffChoice, figure out the string to display (ex: cp -> CP, ex: grader -> Grader)
		String title = ((staffChoice.length() == 2) ? staffChoice.toUpperCase() : staffChoice.substring(0,1).toUpperCase()+staffChoice.substring(1));
		//print information
		System.out.println(courseName);
		System.out.println(title);
		
		if (toShow == null || toShow.isEmpty()){
			System.out.println("No "+title+"s");
			System.out.println();
		}
		
		else{
			
			for (StaffMember staff : toShow){
				System.out.println(staff.toString());
			}
		}
		//reprint the staff menu
		staffMenu(school, department, course, courseName);
	}
	
	//prints the menu for choosing a meeting type
	private void meetingsMenu(School school, Department department, Course course, String courseName){
		//get valid input from the user -- pass in formatted meetings menu
		Integer menuChoice = promptUser(courseName+System.lineSeparator()+String.format(MEETING_MENU, courseName+" menu"), MEETING_OPTIONS);
		//check to see if we need to exit or go back to the previous menu
		exitOrCourseMenu(4, 5, menuChoice, school, department, course);
		//get the meeting objects sorted into different buckets based on 'type' attribute
		Map<String, List<Meeting>> sortedMeetings = course.getSortedMeetings();
		//determine which bucket of meetings to print
		String meetingChoice = MEETING_CHOICE.get(menuChoice);
		List<Meeting> toShow = sortedMeetings.get(meetingChoice);
		//print information
		System.out.println(courseName);
		System.out.println(meetingChoice.substring(0,1).toUpperCase() + meetingChoice.substring(1) + " meetings");
		
		if (toShow == null || toShow.isEmpty()){
			System.out.println("No "+meetingChoice+" meetings");
			System.out.println();
		}
		
		else{
			
			for (Meeting meeting : toShow){
				//pass in a map from staff id number to staff member object
				System.out.println(meeting.toString(course.getMappedStaff()));
			}
		}
		//reprint meetings menu
		meetingsMenu(school, department, course, courseName);
		
	}
	
	public static void main (String [] args){
		//instantiate a Main class object, and the constructor will do all the work
		new Main();
	}
}
