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
	
	public StaffMember(){
		officeHours = new ArrayList<>();
	}
	
	public String getJobType(){
		return type;
	}
	
	public Integer getID(){
		return id;
	}
	
	public Name getName(){
		return name;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getImage(){
		return image;
	}
	
	public String getOffice(){
		return office;
	}
	
	public List<TimePeriod> getOH(){
		return officeHours;
	}
	
	//override the toString method
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("Name: "+name.getFirstName()+" "+name.getLastName()+System.lineSeparator());
		sb.append("Email: ");
		sb.append(email == null ? "" : email);
		sb.append(System.lineSeparator());
		sb.append("Image: ");
		sb.append(image == null ? "" : image);
		sb.append(System.lineSeparator());
		sb.append("Phone: "+ (phone != null ? phone : ""));
		sb.append(System.lineSeparator());
		sb.append("Office: "+ (office != null ? office : ""));
		sb.append(System.lineSeparator());
		sb.append("Office Hours: ");
		
		if (officeHours != null && officeHours.size() > 0){
			
			int i;
			for (i = 0; i<officeHours.size()-1; i++){
				sb.append(officeHours.get(i).toString()+", ");
			}
			
			sb.append(officeHours.get(i).toString());
		}
		
		sb.append(System.lineSeparator());
		return sb.toString();
	}
}
