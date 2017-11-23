package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Meeting {
	private String type;
	private String section;
	private String room;
	private List<TimePeriod> meetingPeriods;
	private List<StaffMemberID> assistants;
	
	public Meeting(){
		meetingPeriods = new ArrayList<>();
		assistants = new ArrayList<>();
	}
	
	public String getMeetingType(){
		return type;
	}
	
	public String getSection(){
		return section;
	}
	
	//custom to string method since we need the staff members map to get their names
	public String toString(Map<Integer, StaffMember> staffMembers){
		StringBuilder sb = new StringBuilder("Section: ");
		sb.append(section == null ? "" : section);
		sb.append(System.lineSeparator());
		sb.append("Room: ");
		sb.append(room == null ? "" : room);
		sb.append(System.lineSeparator());
		sb.append("Meetings: ");
		
		if (meetingPeriods != null && meetingPeriods.size() > 0){
			
			int i;
			for (i = 0; i<meetingPeriods.size()-1; i++){
				sb.append(meetingPeriods.get(i).toString()+", ");
			}
			
			sb.append(meetingPeriods.get(i).toString());
		}

		sb.append(System.lineSeparator());
		sb.append("Assistants:");
		
		if (assistants != null && assistants.size() > 0){
			//loop through assistants list
			int i;
			for (i = 0; i<assistants.size()-1; i++){
				StaffMemberID id = assistants.get(i);
				//get the staff member object from the map with the corresponding id
				StaffMember assistant = staffMembers.get(id.getID());
				Name name = assistant.getName();
				sb.append(name.getFirstName()+" "+name.getLastName()+", ");
			}
			
			StaffMember assistant = staffMembers.get(assistants.get(i).getID());
			Name name = assistant.getName();
			sb.append(name.getFirstName()+" "+name.getLastName());
		}
		
		sb.append(System.lineSeparator());
		return sb.toString();
	}
}