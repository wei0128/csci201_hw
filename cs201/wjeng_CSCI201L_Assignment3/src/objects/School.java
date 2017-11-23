package objects;

import java.util.List;

public class School {
	private String name;
	private String image;
	private List<Departments> departments;
	
	//getter setters
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

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
