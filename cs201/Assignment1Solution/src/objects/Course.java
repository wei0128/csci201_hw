package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Course {
	private Integer number;
	private String term;
	private Integer year;
	private List<StaffMember> staffMembers;
	private List<Meeting> meetings;
	//meetings sorted into a map, key is the meeting type, value is a list of all meetings of that type
	private Map<String, List<Meeting>> sortedMeetings;
	//staff sorted into a map, key is the staff type, value is a list of all staff members of that type
	private Map<String, List<StaffMember>> sortedStaff;
	//map from staff member ID to staff member object
	private Map<Integer, StaffMember> staffMap;
	
	public Course(){
		staffMembers = new ArrayList<>();
		meetings = new ArrayList<>();
	}
	
	public Integer getNumber(){
		return number;
	}
	
	public String getTerm(){
		return term;
	}
	
	public Integer getYear(){
		return year;
	}
	
	public List<StaffMember> getStaffMembers(){
		return staffMembers;
	}
	
	public List<Meeting> getMeetings(){
		return meetings;
	}
	
	public Map<String, List<Meeting>> getSortedMeetings(){
		if (sortedMeetings == null){
			//group the meetings by their type (by the method getMeetingType)
			sortedMeetings = meetings.stream().collect(Collectors.groupingBy(Meeting::getMeetingType));
		}
		return sortedMeetings;
	}
	
	public Map<String, List<StaffMember>> getSortedStaff(){
		if (sortedStaff == null){
			//group the staffMembers by their type (by the method getJobType)
			sortedStaff = staffMembers.stream().collect(Collectors.groupingBy(StaffMember::getJobType));
		}
		return sortedStaff;
	}
	
	public Map<Integer, StaffMember> getMappedStaff(){
		if (staffMap == null){
			staffMap = new HashMap<>();
			//create a map from the ID number to the StaffMember object
			staffMembers.stream().forEach(staff->{
				staffMap.put(staff.getID(), staff);
			});
		}
		return staffMap;
	}
}
