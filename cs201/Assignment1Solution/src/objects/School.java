package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class School {
	private String name;
	private List<Department> departments;
	//menu to print for departments
	private String departmentsMenu;
	//valid integer inputs for this menu
	private Set<Integer> menuOptions;
	
	public School(){
		departments = new ArrayList<>();
	}
	
	public String getName(){
		return name;
	}
	
	public List<Department> getDepartments(){
		return departments;
	}
	
	public void setDepartmentsMenu(String menu){
		this.departmentsMenu = menu;
	}
	
	public String getDepartmentsMenu(){
		return departmentsMenu;
	}
	
	public Set<Integer> getMenuOptions(){
		return menuOptions;
	}
	
	public void setMenuOptions(Set<Integer> menuOptions){
		this.menuOptions = menuOptions;
	}
}
