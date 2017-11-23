package jsonreader;

import java.util.ArrayList;
import java.util.List;

public class StaffMembers {

	private String type;
	private Integer id;
	private Name name;
	private String email;
	private String image;
	private String phone;
	private String office;
	private List<OfficeHours> officeHours = new ArrayList<OfficeHours>();
	
	
	public List<OfficeHours> getOfficeHours() {
		return officeHours;
	}
	public void setOfficeHours(List<OfficeHours> officeHours) {
		this.officeHours = officeHours;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	
}
