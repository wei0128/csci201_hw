package jsonreader;

import java.util.ArrayList;
import java.util.List;

public class School {
	private String name;
	private List<Departments> departments = new ArrayList<Departments>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Departments> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Departments> departments) {
		this.departments = departments;
	}


}
