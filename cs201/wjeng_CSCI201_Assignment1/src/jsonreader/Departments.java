package jsonreader;

import java.util.ArrayList;
import java.util.List;

public class Departments {
	private String longName;
	private String prefix;
	private List<Courses> courses = new ArrayList<Courses>();
	
	
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public List<Courses> getCourses() {
		return courses;
	}
	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}
}
