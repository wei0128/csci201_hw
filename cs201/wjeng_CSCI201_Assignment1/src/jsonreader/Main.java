//Wei Wen Jeng
//1639297169
//wjeng@usc.edu
//CSCI 201 FALL 2017

package jsonreader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Main {
	public static Collection readJson(FileReader fr) {
		Gson gson = new Gson();
		Collection temp = gson.fromJson(fr, Collection.class);
		return temp;
	}
	
	public static boolean checkJson(Collection overall)
	{
		try {
		//check if there is at least one school
		if(overall.getSchools()==null)
		{
			return false;
		}
		if(overall.getSchools().size() == 0)
			return false;
		List<School> schoolList = overall.getSchools();
		//check if each school has at least one department (and name)
		for(int i = 0 ; i < schoolList.size(); i++)
		{
			if(schoolList.get(i).getDepartments()==null)
				return false;
			if(schoolList.get(i).getDepartments().size() == 0)
				return false;
			if(schoolList.get(i).getName() == null)
				return false;
			
			//Check course# in each department (and longName, prefix)
			List<Departments> departmentList = schoolList.get(i).getDepartments();
			for(int k = 0; k < departmentList.size(); k++)
			{
				if(departmentList.get(k).getCourses()==null)
					return false;
				if(departmentList.get(k).getLongName()==null)
					return false;
				if(departmentList.get(k).getPrefix()==null)
					return false;
				
				//check if course is empty
				if(departmentList.get(k).getCourses() == null)
					return false;
				if(departmentList.get(k).getCourses().size() == 0)
					return false;
				//check if staff and meeting have at least one  and check course attributes (number, term, year)
				List<Courses> courseList = departmentList.get(k).getCourses();
				for(int j = 0; j < courseList.size(); j++)
				{
					//number
					if(courseList.get(j).getNumber() == null)
						return false;
					//term
					if(courseList.get(j).getTerm() == null)
						return false;
					//year
					if(courseList.get(j).getYear() == null)
						return false;
					//staff
					if(courseList.get(j).getstaffMembers() == null)
						return false;
					if(courseList.get(j).getstaffMembers().size() == 0)
						return false;
					//meeting
					if(courseList.get(j).getmeetings()==null)
						return false;
					if(courseList.get(j).getmeetings().size() == 0)
						return false;
					
					//checking meetings attribute
					List<Meetings> meetingList = courseList.get(j).getmeetings();
					for(int l = 0; l < meetingList.size(); l++)
					{
						if(meetingList.get(l).getType()==null)
							return false;
						if(meetingList.get(l).getSection() == null)
							return false;
					}
					
					//check staff attribute
					List<StaffMembers> staffList = courseList.get(j).getstaffMembers();
					for(int o = 0; o < staffList.size(); o++)
					{
						if(staffList.get(o).getType() == null)
							return false;
						if(staffList.get(o).getId() == null)
							return false;
						
						//check name;
						Name nameCheck = staffList.get(o).getName();
						if(nameCheck.getFname() == null)
							return false;
						if(nameCheck.getLname() == null)
							return false;
					}
					
				}
			}
		}
		
				
		} catch(JsonSyntaxException e) {
			System.out.println("This is not a valid JSON file");
			return false;
		}
		
		
		return true;
	}
	
	
	public static void mainMenu(Scanner in, Collection overall) {
		int choice = -1;
		System.out.print("\t1) Display schools\n");
		System.out.print("\t2) Exit\n");
		System.out.print("What would you like to do? ");
		boolean haveInput = false;
		while(!haveInput)
		{
			try {
				choice = Integer.valueOf(in.nextLine());
				haveInput = true;
			}catch(NumberFormatException e) {
				System.out.println("\nThis is not a valid option\n");
				choice = -1;
				System.out.print("\t1) Display schools\n");
				System.out.print("\t2) Exit\n");
				System.out.print("What would you like to do? ");
			}
		}
		while(choice != 2)
		{
			if(choice == 1)
			{
				schoolMenu(in, overall.getSchools());
				choice = -1;
				System.out.print("\t1) Display schools\n");
				System.out.print("\t2) Exit\n");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.print("\t1) Display schools\n");
						System.out.print("\t2) Exit\n");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else {
				System.out.println("\nThis is not a valid option");
				choice = -1;
				System.out.println("\t1) Display schools");
				System.out.println("\t2) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.print("\t1) Display schools\n");
						System.out.print("\t2) Exit\n");
						System.out.print("What would you like to do? ");
					}
				}
			}
		}
		System.out.println("\nThank you for using my program");
		in.close();
		System.exit(0);
		
	}
	
	public static void schoolMenu(Scanner in, List<School> list) {
		int i = 1;
		int choice = -1;
		System.out.println("\nSchools");
		for(; i <= list.size(); i++)
		{
			System.out.println("\t" + i + ") " + list.get(i-1).getName());
		}
		System.out.println("\t"+i+") Go to main menu"); i++;
		System.out.println("\t" + i + ") Exit"); //i now equal exit
		System.out.print("What would you like to do? ");
		//test for valid option input
		boolean haveInput = false;
		while(!haveInput)
		{
			try {
				choice = Integer.valueOf(in.nextLine());
				haveInput = true;
			}catch(NumberFormatException e) {
				System.out.println("\nThis is not a valid option\n");
				choice = -1;
				i=1;
				for(; i <= list.size(); i++)
				{
					System.out.println("\t" + i + ") " + list.get(i-1).getName());
				}
				System.out.println("\t"+i+") Go to main menu"); i++;
				System.out.println("\t" + i + ") Exit");
				System.out.print("What would you like to do? ");
			}
		}
		//end test
		while(choice != i)
		{
			if(choice < 1 || choice > i)
			{
				i=1;
				System.out.println("\nThat is not a valid option\n");
				choice = -1;
				for(; i <= list.size(); i++)
				{
					System.out.println("\t" + i + ") " + list.get(i-1).getName());
				}
				System.out.println("\t"+i+") Go to main menu"); i++;
				System.out.println("\t" + i + ") Exit"); //i now equal exit
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						i=1;
						for(; i <= list.size(); i++)
						{
							System.out.println("\t" + i + ") " + list.get(i-1).getName());
						}
						System.out.println("\t"+i+") Go to main menu"); i++;
						System.out.println("\t" + i + ") Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
				else if(choice == i-1)
				{
					return;
				}
				else {
					departmentMenu(in, list.get(choice-1).getDepartments());
					i=1;
					choice = -1;
					System.out.println("\nSchools");
					for(; i <= list.size(); i++)
					{
						System.out.println("\t" + i + ") " + list.get(i-1).getName());
					}
					System.out.println("\t"+i+") Go to main menu"); i++;
					System.out.println("\t" + i + ") Exit"); //i now equal exit
					System.out.print("What would you like to do? ");
					haveInput = false;
					while(!haveInput)
					{
						try {
							choice = Integer.valueOf(in.nextLine());
							haveInput = true;
						}catch(NumberFormatException e) {
							System.out.println("\nThis is not a valid option\n");
							choice = -1;
							i=1;
							for(; i <= list.size(); i++)
							{
								System.out.println("\t" + i + ") " + list.get(i-1).getName());
							}
							System.out.println("\t"+i+") Go to main menu"); i++;
							System.out.println("\t" + i + ") Exit");
							System.out.print("What would you like to do? ");
						}
					}
					}
			}
		

		System.out.println("\nThank you for using my program");
		in.close();
		System.exit(0);
	}
	
	public static void departmentMenu(Scanner in, List<Departments> list)
	{
		int i = 1;
		int choice = -1;
		System.out.println("\nDepartments");
		for(; i <= list.size(); i++)
		{
			System.out.println("\t" + i + ") " + list.get(i-1).getLongName() + "(" + list.get(i-1).getPrefix()+")");
		}
		System.out.println("\t"+i+") Go to Schools menu"); i++;
		System.out.println("\t" + i + ") Exit"); //i now equal exit
		System.out.print("What would you like to do? ");
		boolean haveInput = false;
		while(!haveInput)
		{
			try {
				choice = Integer.valueOf(in.nextLine());
				haveInput = true;
			}catch(NumberFormatException e) {
				System.out.println("\nThis is not a valid option\n");
				choice = -1;
				i=1;
				System.out.println("\nDepartments");
				for(; i <= list.size(); i++)
				{
					System.out.println("\t" + i + ") " + list.get(i-1).getLongName() + "(" + list.get(i-1).getPrefix()+")");
				}
				System.out.println("\t"+i+") Go to main menu"); i++;
				System.out.println("\t" + i + ") Exit");
				System.out.print("What would you like to do? ");
			}
		}
		
		while(choice != i)
		{
			if(choice < 1 || choice > i)
			{
				i=1;
				choice = -1;
				System.out.println("\nThat is not a valid option\n");
				System.out.println("\nDepartments");
				for(; i <= list.size(); i++)
				{
					System.out.println("\t" + i + ") " + list.get(i-1).getLongName() + "(" + list.get(i-1).getPrefix()+")");
				}
				System.out.println("\t"+i+") Go to main menu"); i++;
				System.out.println("\t" + i + ") Exit"); //i now equal exit
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						i=1;
						System.out.println("\nDepartments");
						for(; i <= list.size(); i++)
						{
							System.out.println("\t" + i + ") " + list.get(i-1).getLongName() + "(" + list.get(i-1).getPrefix()+")");
						}
						System.out.println("\t"+i+") Go to main menu"); i++;
						System.out.println("\t" + i + ") Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if(choice == i-1)
			{
				return;
			}
			else
			{
				courseMenu(in, list.get(choice-1).getCourses(), list.get(choice-1).getPrefix());
				i=1;
				choice = -1;
				System.out.println("\nDepartments");
				for(; i <= list.size(); i++)
				{
					System.out.println("\t" + i + ") " + list.get(i-1).getLongName() + "(" + list.get(i-1).getPrefix()+")");
				}
				System.out.println("\t"+i+") Go to Schools menu"); i++;
				System.out.println("\t" + i + ") Exit"); //i now equal exit
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						i=1;
						System.out.println("\nDepartments");
						for(; i <= list.size(); i++)
						{
							System.out.println("\t" + i + ") " + list.get(i-1).getLongName() + "(" + list.get(i-1).getPrefix()+")");
						}
						System.out.println("\t"+i+") Go to main menu"); i++;
						System.out.println("\t" + i + ") Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
		}
		
		System.out.println("\nThank you for using my program");
		in.close();
		System.exit(0);
	}
	
	public static void courseMenu(Scanner in, List<Courses> list, String prefix)
	{
		String tempName = null;
		int i = 1;
		int choice = -1;
		System.out.println("\n"+prefix+" Courses");
		for(; i <= list.size(); i++)
		{
			tempName = prefix + " " + list.get(i-1).getNumber()+" "+ list.get(i-1).getTerm() + " " + list.get(i-1).getYear();
			System.out.println("\t" + i + ") " + tempName);
		}
		System.out.println("\t"+i+") Go to Departments menu"); i++;
		System.out.println("\t" + i + ") Exit"); //i now equal exit
		System.out.print("What would you like to do? ");
		boolean haveInput = false;
		while(!haveInput)
		{
			try {
				choice = Integer.valueOf(in.nextLine());
				haveInput = true;
			}catch(NumberFormatException e) {
				System.out.println("\nThis is not a valid option\n");
				choice = -1;
				i=1;
				System.out.println("\n"+prefix+" Courses");
				for(; i <= list.size(); i++)
				{
					tempName = prefix + " " + list.get(i-1).getNumber()+" "+ list.get(i-1).getTerm() + " " + list.get(i-1).getYear();
					System.out.println("\t" + i + ") " + tempName);
				}
				System.out.println("\t"+i+") Go to main menu"); i++;
				System.out.println("\t" + i + ") Exit");
				System.out.print("What would you like to do? ");
			}
		}
		while(choice != i)
		{
			
			if(choice < 1 || choice > i)
			{
				System.out.println("\nThat is not a valid option");
				System.out.println("\n"+prefix+" Courses");
				i=1;
				choice = -1;
				for(; i <= list.size(); i++)
				{
					tempName = prefix + " " + list.get(i-1).getNumber()+" "+ list.get(i-1).getTerm() + " " + list.get(i-1).getYear();
					System.out.println("\t" + i + ") " + prefix + " " + tempName);
				}
				System.out.println("\t"+i+") Go to Departments menu"); i++;
				System.out.println("\t" + i + ") Exit"); //i now equal exit
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						i=1;
						System.out.println("\n"+prefix+" Courses");
						for(; i <= list.size(); i++)
						{
							tempName = prefix + " " + list.get(i-1).getNumber()+" "+ list.get(i-1).getTerm() + " " + list.get(i-1).getYear();
							System.out.println("\t" + i + ") " + tempName);
						}
						System.out.println("\t"+i+") Go to main menu"); i++;
						System.out.println("\t" + i + ") Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if(choice == i-1)
			{
				return;
			}
			else
			{
				tempName = prefix + " " + list.get(choice-1).getNumber() + " " + list.get(choice-1).getTerm() + " " + list.get(choice-1).getYear();
				courseDetail(in, list.get(choice-1), tempName, prefix);
				i=1;
				choice = -1;
				System.out.println("\n"+prefix+" Courses");
				for(; i <= list.size(); i++)
				{
					tempName = prefix + " " + list.get(i-1).getNumber()+" "+ list.get(i-1).getTerm() + " " + list.get(i-1).getYear();
					System.out.println("\t" + i + ") " + tempName);
				}
				System.out.println("\t"+i+") Go to Departments menu"); i++;
				System.out.println("\t" + i + ") Exit"); //i now equal exit
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						i=1;
						System.out.println("\n"+prefix+" Courses");
						for(; i <= list.size(); i++)
						{
							tempName = prefix + " " + list.get(i-1).getNumber()+" "+ list.get(i-1).getTerm() + " " + list.get(i-1).getYear();
							System.out.println("\t" + i + ") " + tempName);
						}
						System.out.println("\t"+i+") Go to main menu"); i++;
						System.out.println("\t" + i + ") Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}	
		}
		
		System.out.println("\nThank you for using my program");
		in.close();
		System.exit(0);
	}
	
	
	public static void courseDetail(Scanner in, Courses course, String tempName, String prefix) 
	{
		int choice = -1;
		System.out.println("\n"+tempName);
		System.out.println("\t1) View course staff");
		System.out.println("\t2) View meeting information");
		System.out.println("\t3) Go to " + prefix + " Courses menu");
		System.out.println("\t4) Exit");
		System.out.print("What would you like to do? ");
		boolean haveInput = false;
		while(!haveInput)
		{
			try {
				choice = Integer.valueOf(in.nextLine());
				haveInput = true;
			}catch(NumberFormatException e) {
				System.out.println("\nThis is not a valid option\n");
				choice = -1;
				System.out.println("\n"+tempName);
				System.out.println("\t1) View course staff");
				System.out.println("\t2) View meeting information");
				System.out.println("\t3) Go to " + prefix + " Courses menu");
				System.out.println("\t4) Exit");
				System.out.print("What would you like to do? ");
			}
		}
		
		//loop
		while(choice != 4)
		{
			if(choice == 1)
			{
				//Insert course staff
				staffMenu(in, course.getstaffMembers(), tempName);
				choice = -1;
				System.out.println("\n"+tempName);
				System.out.println("\t1) View course staff");
				System.out.println("\t2) View meeting information");
				System.out.println("\t3) Go to " + prefix + " Courses menu");
				System.out.println("\t4) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName);
						System.out.println("\t1) View course staff");
						System.out.println("\t2) View meeting information");
						System.out.println("\t3) Go to " + prefix + " Courses menu");
						System.out.println("\t4) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if (choice==2)
			{
				//Insert meetings
				meetingMenu(in, course.getmeetings(), course.getstaffMembers(), tempName);
				choice = -1;
				System.out.println("\n"+tempName);
				System.out.println("\t1) View course staff");
				System.out.println("\t2) View meeting information");
				System.out.println("\t3) Go to " + prefix + " Courses menu");
				System.out.println("\t4) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName);
						System.out.println("\t1) View course staff");
						System.out.println("\t2) View meeting information");
						System.out.println("\t3) Go to " + prefix + " Courses menu");
						System.out.println("\t4) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if(choice==3)
			{
				return;
			}
			else {
				//Out of ranges
				System.out.println("\nThat is not a valid option\n");
				choice = -1;
				System.out.println("\n"+tempName);
				System.out.println("\t1) View course staff");
				System.out.println("\t2) View meeting information");
				System.out.println("\t3) Go to " + prefix + " Courses menu");
				System.out.println("\t4) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName);
						System.out.println("\t1) View course staff");
						System.out.println("\t2) View meeting information");
						System.out.println("\t3) Go to " + prefix + " Courses menu");
						System.out.println("\t4) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
		}
		System.out.println("\nThank you for using my program");
		in.close();
		System.exit(0);
	}
	
	public static void staffMenu(Scanner in, List<StaffMembers> list, String tempName) // Incomplete
	{
		
		
		int choice = -1;
		System.out.println("\n"+tempName + "\nCourse Staff");
		System.out.println("\t1) View Instructors");
		System.out.println("\t2) View TAs");
		System.out.println("\t3) CPs");
		System.out.println("\t4) Graders");
		System.out.println("\t5) Go to " + tempName + " menu");
		System.out.println("\t6) Exit");
		System.out.print("What would you like to do? ");
		boolean haveInput = false;
		while(!haveInput)
		{
			try {
				choice = Integer.valueOf(in.nextLine());
				haveInput = true;
			}catch(NumberFormatException e) {
				System.out.println("\nThis is not a valid option\n");
				choice = -1;
				System.out.println("\n"+tempName + "\nCourse Staff");
				System.out.println("\t1) View Instructors");
				System.out.println("\t2) View TAs");
				System.out.println("\t3) CPs");
				System.out.println("\t4) Graders");
				System.out.println("\t5) Go to " + tempName + " menu");
				System.out.println("\t6) Exit");
				System.out.print("What would you like to do? ");
			}
		}
		
		//Creating sublist for each type
		List<StaffMembers> instructors = new ArrayList<StaffMembers>();
		List<StaffMembers> tas = new ArrayList<StaffMembers>();
		List<StaffMembers> cps = new ArrayList<StaffMembers>();
		List<StaffMembers> graders = new ArrayList<StaffMembers>();
		//Check for existing staff type
		
		int size = list.size();
		for(int i = 0 ;i < size; i++)
		{
			if (list.get(i).getType().equals("instructor"))
			{
				instructors.add(list.get(i));
			}
			else if(list.get(i).getType().equals("ta"))
			{
				tas.add(list.get(i));
			}
			else if(list.get(i).getType().equals("cp"))
			{
				cps.add(list.get(i));
			}
			else if(list.get(i).getType().equals("grader"))
			{
				graders.add(list.get(i));
			}
		}
		
		
		while(choice!=6)
		{
			if(choice == 1)
			{
				if(instructors.size() == 0)
				{
					System.out.println("\nThere is no instructor\n");
				}
				else
				{
					for(int i = 0; i < instructors.size(); i++)
					{
						//Check for email, image, phone and office, office hours
						System.out.println("\n"+tempName + "\nInstructors");
						System.out.println("Name: "+ instructors.get(i).getName().getFname() + " " + instructors.get(i).getName().getLname());
						if(instructors.get(i).getEmail()==null)
						{
							System.out.println("Email: N/A");
						}
						else {
							System.out.println("Email: "+ instructors.get(i).getEmail());
						}
						if(instructors.get(i).getImage()==null)
						{
							System.out.println("Image: N/A");
						}
						else
						{
							System.out.println("Image: "+ instructors.get(i).getImage());
						}
						if(instructors.get(i).getPhone()==null)
						{
							System.out.println("Phone: N/A");
						}
						else
						{
							System.out.println("Phone: "+ instructors.get(i).getPhone());
						}
						if(instructors.get(i).getOffice()==null)
						{
							System.out.println("Office: N/A");
						}
						else
						{
							System.out.println("Office: "+ instructors.get(i).getOffice());
						}
						List<OfficeHours> hourList = instructors.get(i).getOfficeHours();
						System.out.print("Office Hours: ");
						for(int k = 0; k < hourList.size(); k++)
						{
							//With commas
							if(k!=hourList.size()-1)
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA ");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.print("TBA, ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getEnd() + ", ");
									}
								}
							}
							//Without
							else
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.println("TBA\n");
									}
									else
									{
										System.out.println(hourList.get(k).getHours().getEnd()+"\n");
									}
								}
							}
						}
					}
				}
				choice = -1;
				System.out.println("\n"+tempName + "\nCourse Staff");
				System.out.println("\t1) View Instructors");
				System.out.println("\t2) View TAs");
				System.out.println("\t3) CPs");
				System.out.println("\t4) Graders");
				System.out.println("\t5) Go to " + tempName + " menu");
				System.out.println("\t6) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nCourse Staff");
						System.out.println("\t1) View Instructors");
						System.out.println("\t2) View TAs");
						System.out.println("\t3) CPs");
						System.out.println("\t4) Graders");
						System.out.println("\t5) Go to " + tempName + " menu");
						System.out.println("\t6) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if(choice == 2)
			{
				//TAs
				if(tas.size() == 0)
				{
					System.out.println("\nThere is no TA\n");
				}
				else
				{
					for(int i = 0; i < tas.size(); i++)
					{
						System.out.println("\n"+tempName + "\nTAs");
						System.out.println("Name: "+ tas.get(i).getName().getFname() + " " + tas.get(i).getName().getLname());
						if(tas.get(i).getEmail()==null)
						{
							System.out.println("Email: N/A");
						}
						else {
							System.out.println("Email: "+ tas.get(i).getEmail());
						}
						if(tas.get(i).getImage()==null)
						{
							System.out.println("Image: N/A");
						}
						else
						{
							System.out.println("Image: "+ tas.get(i).getImage());
						}
						if(tas.get(i).getPhone()==null)
						{
							System.out.println("Phone: N/A");
						}
						else
						{
							System.out.println("Phone: "+ tas.get(i).getPhone());
						}
						if(tas.get(i).getOffice()==null)
						{
							System.out.println("Office: N/A");
						}
						else
						{
							System.out.println("Office: "+ tas.get(i).getOffice());
						}
						List<OfficeHours> hourList = tas.get(i).getOfficeHours();
						System.out.print("Office Hours: ");
						for(int k = 0; k < hourList.size(); k++)
						{
							//With commas
							if(k!=hourList.size()-1)
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA ");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.print("TBA, ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getEnd() + ", ");
									}
								}
							}
							//Without
							else
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.println("TBA\n");
									}
									else
									{
										System.out.println(hourList.get(k).getHours().getEnd()+"\n");
									}
								}
							}
						}
						
					}
				}
				choice = -1;
				System.out.println("\n"+tempName + "\nCourse Staff");
				System.out.println("\t1) View Instructors");
				System.out.println("\t2) View TAs");
				System.out.println("\t3) CPs");
				System.out.println("\t4) Graders");
				System.out.println("\t5) Go to " + tempName + " menu");
				System.out.println("\t6) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nCourse Staff");
						System.out.println("\t1) View Instructors");
						System.out.println("\t2) View TAs");
						System.out.println("\t3) CPs");
						System.out.println("\t4) Graders");
						System.out.println("\t5) Go to " + tempName + " menu");
						System.out.println("\t6) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if(choice == 3)
			{
				//CPs
				if(cps.size() == 0)
				{
					System.out.println("\nThere is no CP\n");
				}
				else
				{
					for(int i = 0; i < cps.size(); i++)
					{
						System.out.println("\n"+tempName + "\nCPs");
						System.out.println("Name: "+ cps.get(i).getName().getFname() + " " + cps.get(i).getName().getLname());
						if(cps.get(i).getEmail()==null)
						{
							System.out.println("Email: N/A");
						}
						else {
							System.out.println("Email: " + cps.get(i).getEmail());
						}
						if(cps.get(i).getImage()==null)
						{
							System.out.println("Image: N/A");
						}
						else
						{
							System.out.println("Image: "+ cps.get(i).getImage());
						}
						if(cps.get(i).getPhone()==null)
						{
							System.out.println("Phone: N/A");
						}
						else
						{
							System.out.println("Phone: "+ cps.get(i).getPhone());
						}
						if(cps.get(i).getOffice()==null)
						{
							System.out.println("Office: N/A");
						}
						else
						{
							System.out.println("Office: "+ cps.get(i).getOffice());
						}
						List<OfficeHours> hourList = cps.get(i).getOfficeHours();
						System.out.print("Office Hours: ");
						for(int k = 0; k < hourList.size(); k++)
						{
							//With commas
							if(k!=hourList.size()-1)
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA ");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.print("TBA, ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getEnd() + ", ");
									}
								}
							}
							//Without
							else
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("\n");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.println("TBA\n");
									}
									else
									{
										System.out.println(hourList.get(k).getHours().getEnd()+"\n");
									}
								}
							}
						}
					}		
				}
				choice = -1;
				System.out.println("\n"+tempName + "\nCourse Staff");
				System.out.println("\t1) View Instructors");
				System.out.println("\t2) View TAs");
				System.out.println("\t3) CPs");
				System.out.println("\t4) Graders");
				System.out.println("\t5) Go to " + tempName + " menu");
				System.out.println("\t6) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nCourse Staff");
						System.out.println("\t1) View Instructors");
						System.out.println("\t2) View TAs");
						System.out.println("\t3) CPs");
						System.out.println("\t4) Graders");
						System.out.println("\t5) Go to " + tempName + " menu");
						System.out.println("\t6) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if(choice == 4)
			{
				//Graders
				if(graders.size() == 0)
				{
					System.out.println("\nThere is no grader\n");
				}
				else
				{
					for(int i = 0; i < graders.size(); i++)
					{
						System.out.println("\n"+tempName + "\nGraders");
						System.out.println("Name: "+ graders.get(i).getName().getFname() + " " + graders.get(i).getName().getLname());
						if(graders.get(i).getEmail()==null)
						{
							System.out.println("Email: N/A");
						}
						else {
							System.out.println("Email: "+ graders.get(i).getEmail());
						}
						if(graders.get(i).getImage()==null)
						{
							System.out.println("Image: N/A");
						}
						else
						{
							System.out.println("Image: "+ graders.get(i).getImage());
						}
						if(graders.get(i).getPhone()==null)
						{
							System.out.println("Phone: N/A");
						}
						else
						{
							System.out.println("Phone: "+ graders.get(i).getPhone());
						}
						if(graders.get(i).getOffice()==null)
						{
							System.out.println("Office: N/A");
						}
						else
						{
							System.out.println("Office: "+ graders.get(i).getOffice());
						}
						List<OfficeHours> hourList = graders.get(i).getOfficeHours();
						System.out.print("Office Hours: ");
						for(int k = 0; k < hourList.size(); k++)
						{
							//With commas
							if(k!=hourList.size()-1)
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA ");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("\n");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.print("TBA, ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getEnd() + ", ");
									}
								}
							}
							//Without
							else
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("\n");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.println("TBA\n");
									}
									else
									{
										System.out.println(hourList.get(k).getHours().getEnd()+"\n");
									}
								}
							}
						}
						
					}
				}
				choice = -1;
				System.out.println("\n"+tempName + "\nCourse Staff");
				System.out.println("\t1) View Instructors");
				System.out.println("\t2) View TAs");
				System.out.println("\t3) CPs");
				System.out.println("\t4) Graders");
				System.out.println("\t5) Go to " + tempName + " menu");
				System.out.println("\t6) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nCourse Staff");
						System.out.println("\t1) View Instructors");
						System.out.println("\t2) View TAs");
						System.out.println("\t3) CPs");
						System.out.println("\t4) Graders");
						System.out.println("\t5) Go to " + tempName + " menu");
						System.out.println("\t6) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else if (choice < 1 || choice > 6)
			{
				System.out.println("\nThat is not a valid option\n");
				choice = -1;
				System.out.println("\n"+tempName + "\n Course Staff");
				System.out.println("\t1) View Instructors");
				System.out.println("\t2) View TAs");
				System.out.println("\t3) CPs");
				System.out.println("\t4) Graders");
				System.out.println("\t5) Go to " + tempName + " menu");
				System.out.println("\t6) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nCourse Staff");
						System.out.println("\t1) View Instructors");
						System.out.println("\t2) View TAs");
						System.out.println("\t3) CPs");
						System.out.println("\t4) Graders");
						System.out.println("\t5) Go to " + tempName + " menu");
						System.out.println("\t6) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
			else
			{
				return;
			}
		}
		
		
		System.out.println("\nThank you for using my program");
		in.close();
		System.exit(0);
	}
	
	public static void meetingMenu(Scanner in, List<Meetings> mList, List<StaffMembers> sList, String tempName)
	{
		int choice = -1;
		System.out.println("\n"+tempName + "\nMeeting Information");
		System.out.println("\t1) Lecture");
		System.out.println("\t2) Lab");
		System.out.println("\t3) Quiz");
		System.out.println("\t4) Go to " + tempName + " menu");
		System.out.println("\t5) Exit");
		System.out.print("What would you like to do? ");
		boolean haveInput = false;
		while(!haveInput)
		{
			try {
				choice = Integer.valueOf(in.nextLine());
				haveInput = true;
			}catch(NumberFormatException e) {
				System.out.println("\nThis is not a valid option\n");
				choice = -1;
				System.out.println("\n"+tempName + "\nMeeting Information");
				System.out.println("\t1) Lecture");
				System.out.println("\t2) Lab");
				System.out.println("\t3) Quiz");
				System.out.println("\t4) Go to " + tempName + " menu");
				System.out.println("\t5) Exit");
				System.out.print("What would you like to do? ");
			}
		}
		
		
		//Separating Lecture / Quiz / Lab into corresponding list
		List<Meetings> lec = new ArrayList<Meetings>();
		List<Meetings> quiz = new ArrayList<Meetings>();
		List<Meetings> lab = new ArrayList<Meetings>();
		
		int size = mList.size();
		for(int i = 0; i < size; i++) 
		{
			if(mList.get(i).getType().equals("lecture"))
			{
				lec.add(mList.get(i));
			}
			else if(mList.get(i).getType().equals("quiz"))
			{
				quiz.add(mList.get(i));
			}
			else if(mList.get(i).getType().equals("lab"))
			{
				lab.add(mList.get(i));
			}
		}
		
		while(choice != 5)
		{
			//lecture
			if(choice==1)
			{
				if(lec.size() == 0)
				{
					System.out.println("There is no lecture section\n");
				}
				else
				{
					//Print title
					System.out.println("\n"+tempName);
					System.out.println("Lecture Meeting Information");
					for(int i = 0; i < lec.size(); i++)
					{
						System.out.println("Section: " + lec.get(i).getSection());
						//Check room
						if(lec.get(i).getRoom() == null)
						{
							System.out.println("Room: TBA");
						}
						else
						{
							System.out.println("Room: " + lec.get(i).getRoom());
						}
						//Hours
						
						List<OfficeHours> hourList = lec.get(i).getMeetingPeriods();
						System.out.print("Meetings: ");
						for(int k = 0; k < hourList.size(); k++)
						{
							//With commas
							if(k!=hourList.size()-1)
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA ");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.print("TBA, ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getEnd() + ", ");
									}
								}
							}
							//Without
							else
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.println("TBA");
									}
									else
									{
										System.out.println(hourList.get(k).getHours().getEnd());
									}
								}
							}
						}//end for loop (hour)
						
						if(lec.get(i).getAssistants()==null)
						{
							System.out.println("Assistant: N/A");
						}
						else
						{
							//Matching assistants with assistant ids
							//Match ta cps with assistant ID
							List<Assistant> id = lec.get(i).getAssistants();
							List<StaffMembers> helper = new ArrayList<StaffMembers>();
							int idSize = id.size();
							//Loop over id list and match it with one of staff
							for(int j = 0 ;j < idSize; j++)
							{
								int sSize = sList.size();
								//loop over staff list to find a match
								for(int l = 0; l < sSize; l++)
								{
									if(id.get(j).getStaffMemberID() == sList.get(l).getId())
									{
										helper.add(sList.get(l));
									}
								}
							}
							
							
							if(helper.size() == 0)
							{
								System.out.println("Assitants: N/A");
							}
							else
							{
								System.out.print("Assistants: ");
								for(int k = 0; k<helper.size(); k++)
								{
									//with comma
									if(k!= helper.size()-1)
									{
										System.out.print(helper.get(k).getName().getFname() +" " + helper.get(k).getName().getLname() + ", ");
									}
									//without comma
									else
									{
										System.out.println(helper.get(k).getName().getFname() +" " + helper.get(k).getName().getLname());
									}
								}
							}
						}
						
					}//end lec loop
				}
				choice = -1;
				System.out.println("\n"+tempName + "\nMeeting Information");
				System.out.println("\t1) Lecture");
				System.out.println("\t2) Lab");
				System.out.println("\t3) Quiz");
				System.out.println("\t4) Go to " + tempName + " menu");
				System.out.println("\t5) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nMeeting Information");
						System.out.println("\t1) Lecture");
						System.out.println("\t2) Lab");
						System.out.println("\t3) Quiz");
						System.out.println("\t4) Go to " + tempName + " menu");
						System.out.println("\t5) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}//end choice 1
				
			//Lab
			else if (choice == 2)
			{
				if(lab.size() == 0)
				{
					System.out.println("There is no lab section\n");
				}
				else
				{
					//Print title
					System.out.println("\n"+tempName);
					System.out.println("Lab Meeting Information");
					for(int i = 0; i < lab.size(); i++)
					{
						System.out.println("Section: " + lab.get(i).getSection());
						//Check room
						if(lab.get(i).getRoom() == null)
						{
							System.out.println("Room: TBA");
						}
						else
						{
							System.out.println("Room: " + lab.get(i).getRoom());
						}
						//Hours
						
						List<OfficeHours> hourList = lab.get(i).getMeetingPeriods();
						System.out.print("Meetings: ");
						for(int k = 0; k < hourList.size(); k++)
						{
							//With commas
							if(k!=hourList.size()-1)
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA ");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.print("TBA, ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getEnd() + ", ");
									}
								}
							}
							//Without
							else
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.println("TBA");
									}
									else
									{
										System.out.println(hourList.get(k).getHours().getEnd());
									}
								}
							}
						}//end for loop (hour)
						
						if(lab.get(i).getAssistants()==null)
						{
							System.out.println("Assistant: N/A");
						}
						else
						{
							//Matching assistants with assistant ids
							//Match ta cps with assistant ID
							List<Assistant> id = lab.get(i).getAssistants();
							List<StaffMembers> helper = new ArrayList<StaffMembers>();
							int idSize = id.size();
							//Loop over id list and match it with one of staff
							for(int j = 0 ;j < idSize; j++)
							{
								int sSize = sList.size();
								//loop over staff list to find a match
								for(int l = 0; l < sSize; l++)
								{
									if(id.get(j).getStaffMemberID() == sList.get(l).getId())
									{
										helper.add(sList.get(l));
									}
								}
							}
							
							
							if(helper.size() == 0)
							{
								System.out.println("Assitants: N/A");
							}
							else
							{
								System.out.print("Assistants: ");
								for(int k = 0; k<helper.size(); k++)
								{
									//with comma
									if(k!= helper.size()-1)
									{
										System.out.print(helper.get(k).getName().getFname() +" " + helper.get(k).getName().getLname() + ", ");
									}
									//without comma
									else
									{
										System.out.println(helper.get(k).getName().getFname() +" " + helper.get(k).getName().getLname());
									}
								}
							}
						}
					}//end lab loop
				}
				choice = -1;
				System.out.println("\n"+tempName + "\nMeeting Information");
				System.out.println("\t1) Lecture");
				System.out.println("\t2) Lab");
				System.out.println("\t3) Quiz");
				System.out.println("\t4) Go to " + tempName + " menu");
				System.out.println("\t5) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nMeeting Information");
						System.out.println("\t1) Lecture");
						System.out.println("\t2) Lab");
						System.out.println("\t3) Quiz");
						System.out.println("\t4) Go to " + tempName + " menu");
						System.out.println("\t5) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}//end choice 2
			
			//Quiz
			else if (choice == 3)
			{
				if(quiz.size() == 0)
				{
					System.out.println("There is no quiz section\n");
				}
				else
				{
					//Print title
					System.out.println("\n"+tempName);
					System.out.println("Quiz Meeting Information");
					for(int i = 0; i < quiz.size(); i++)
					{
						System.out.println("Section: " + quiz.get(i).getSection());
						//Check room
						if(quiz.get(i).getRoom() == null)
						{
							System.out.println("Room: TBA");
						}
						else
						{
							System.out.println("Room: " + quiz.get(i).getRoom());
						}
						//Hours
						
						List<OfficeHours> hourList = quiz.get(i).getMeetingPeriods();
						System.out.print("Meetings: ");
						for(int k = 0; k < hourList.size(); k++)
						{
							//With commas
							if(k!=hourList.size()-1)
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA ");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.print("TBA, ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getEnd() + ", ");
									}
								}
							}
							//Without
							else
							{
								if(hourList.get(k).getDay() == null)
								{
									System.out.print("TBA");
								}
								else
								{
									System.out.print(hourList.get(k).getDay() + " ");
								}
								if(hourList.get(k).getHours() == null)
								{
									System.out.print("");
								}
								else
								{
									if(hourList.get(k).getHours().getStart() ==null)
									{
										System.out.print("TBA - ");
									}
									else
									{
										System.out.print(hourList.get(k).getHours().getStart() + " - ");
									}
									if(hourList.get(k).getHours().getEnd() == null)
									{
										System.out.println("TBA");
									}
									else
									{
										System.out.println(hourList.get(k).getHours().getEnd());
									}
								}
							}
						}//end for loop (hour)
						
						if(quiz.get(i).getAssistants()==null)
						{
							System.out.println("Assistant: N/A");
						}
						else
						{
							//Matching assistants with assistant ids
							//Match ta cps with assistant ID
							List<Assistant> id = quiz.get(i).getAssistants();
							List<StaffMembers> helper = new ArrayList<StaffMembers>();
							int idSize = id.size();
							//Loop over id list and match it with one of staff
							for(int j = 0 ;j < idSize; j++)
							{
								int sSize = sList.size();
								//loop over staff list to find a match
								for(int l = 0; l < sSize; l++)
								{
									if(id.get(j).getStaffMemberID() == sList.get(l).getId())
									{
										helper.add(sList.get(l));
									}
								}
							}
							
							
							if(helper.size() == 0)
							{
								System.out.println("Assitants: N/A");
							}
							else
							{
								System.out.print("Assistants: ");
								for(int k = 0; k<helper.size(); k++)
								{
									//with comma
									if(k!= helper.size()-1)
									{
										System.out.print(helper.get(k).getName().getFname() +" " + helper.get(k).getName().getLname() + ", ");
									}
									//without comma
									else
									{
										System.out.println(helper.get(k).getName().getFname() +" " + helper.get(k).getName().getLname());
									}
								}
							}
						}
					}//end quiz loop
				}
				choice = -1;
				System.out.println("\n"+tempName + "\nMeeting Information");
				System.out.println("\t1) Lecture");
				System.out.println("\t2) Lab");
				System.out.println("\t3) Quiz");
				System.out.println("\t4) Go to " + tempName + " menu");
				System.out.println("\t5) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nMeeting Information");
						System.out.println("\t1) Lecture");
						System.out.println("\t2) Lab");
						System.out.println("\t3) Quiz");
						System.out.println("\t4) Go to " + tempName + " menu");
						System.out.println("\t5) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}//end choice 3
			
			else if(choice == 4)
			{
				return;
			}
			else if (choice < 1 || choice > 5)
			{
				choice = -1;
				System.out.println("\nThat is not a valid option");
				System.out.println("\n"+tempName + "\nMeeting Information");
				System.out.println("\t1) Lecture");
				System.out.println("\t2) Lab");
				System.out.println("\t3) Quiz");
				System.out.println("\t4) Go to " + tempName + " menu");
				System.out.println("\t5) Exit");
				System.out.print("What would you like to do? ");
				haveInput = false;
				while(!haveInput)
				{
					try {
						choice = Integer.valueOf(in.nextLine());
						haveInput = true;
					}catch(NumberFormatException e) {
						System.out.println("\nThis is not a valid option\n");
						choice = -1;
						System.out.println("\n"+tempName + "\nMeeting Information");
						System.out.println("\t1) Lecture");
						System.out.println("\t2) Lab");
						System.out.println("\t3) Quiz");
						System.out.println("\t4) Go to " + tempName + " menu");
						System.out.println("\t5) Exit");
						System.out.print("What would you like to do? ");
					}
				}
			}
		}//end while
		
		System.out.println("\nThank you for using my program");
		in.close();
		System.exit(0);
	}//end method
	
	
	public static void main(String []args) {
		boolean passCheck = false;
		Collection overall = new Collection();
		FileReader fr = null;
		Scanner in = new Scanner(System.in);
		
		System.out.print("What is the name of the input file?");
		String fileName = in.nextLine();
		
		while(!passCheck)
		{
			//Checking file exist
			File f = new File(fileName);
			try {
				if(f.exists())
				{
					fr = new FileReader(fileName);
					overall = readJson(fr);
					passCheck = checkJson(overall);
					if(passCheck) {
						
						break;
					}
					else
					{
						System.out.println("That file is not a well-formed JSON file.\n");
					}
				}
				else
				{
					System.out.println("That file could not be found.\n");
					
				}
				
				System.out.print("What is the name of the input file?");
				fileName = in.nextLine();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(JsonSyntaxException e) {
				System.out.println("That file is not a well-formed JSON file.\n");
				passCheck = false;
				System.out.print("What is the name of the input file?");
				fileName = in.nextLine();
			}
		}
		
		mainMenu(in, overall);
		
		in.close();
	} // end main
}
