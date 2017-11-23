package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Department {
	private String longName;
	private String prefix;
	private List<Course> courses;
	//menu to print
	private String coursesMenu;
	//valid inputs for that menu
	private Set<Integer> menuOptions;
	
	public Department(){
		courses = new ArrayList<>();
	}
	
	public String getLongName(){
		return longName;
	}
	
	public String getPrefix(){
		return prefix;
	}
	
	public List<Course> getCourses(){
		return courses;
	}
	
	public void setCoursesMenu(String menu){
		this.coursesMenu = menu;
	}
	
	public String getCoursesMenu(){
		return coursesMenu;
	}
	
	public void setMenuOptions(Set<Integer> options){
		this.menuOptions = options;
	}
	
	public Set<Integer> getMenuOptions(){
		return menuOptions;
	}
}
