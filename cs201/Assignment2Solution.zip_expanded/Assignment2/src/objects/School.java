package objects;

import java.util.ArrayList;
import java.util.List;

public class School {
	private String name;
	private String image;
	private List<Department> departments;

	public School() {
		departments = new ArrayList<>();
	}

	public String getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}

	public List<Department> getDepartments() {
		return departments;
	}
}
