package objects;

import java.util.ArrayList;
import java.util.List;

public class StaffMember {
	private String type;
	private Integer id;
	private Name name;
	private String email;
	private String image;
	private String phone;
	private String office;
	private List<TimePeriod> officeHours;

	public StaffMember() {
		officeHours = new ArrayList<>();
	}

	public String getJobType() {
		return type;
	}

	public Integer getID() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getImage() {
		return image;
	}

	public String getOffice() {
		return office;
	}

	public List<TimePeriod> getOH() {
		return officeHours;
	}
}
