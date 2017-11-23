package jsonreader;

import java.util.ArrayList;
import java.util.List;

public class Courses {

	private Integer number;
	private String term;
	private Integer year;
	private List<StaffMembers> staffMembers = new ArrayList<StaffMembers>();
	private List<Meetings> meetings = new ArrayList<Meetings>();
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<StaffMembers> getstaffMembers() {
		return staffMembers;
	}
	public void setstaffMembers(List<StaffMembers> staffMembers) {
		this.staffMembers = staffMembers;
	}
	public List<Meetings> getmeetings() {
		return meetings;
	}
	public void setmeetings(List<Meetings> meetings) {
		this.meetings = meetings;
	}

	
}
