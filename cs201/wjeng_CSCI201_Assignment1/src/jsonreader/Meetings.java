package jsonreader;

import java.util.ArrayList;
import java.util.List;

public class Meetings {

	private String type;
	private String section;
	private String room;
	private List<OfficeHours> meetingPeriods = new ArrayList<OfficeHours>();
	public List<Assistant> getAssistants() {
		return assistants;
	}
	public void setAssistants(List<Assistant> assistants) {
		this.assistants = assistants;
	}
	private List<Assistant> assistants = new ArrayList<Assistant>();
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public List<OfficeHours> getMeetingPeriods() {
		return meetingPeriods;
	}
	public void setMeetingPeriods(List<OfficeHours> meetingPeriods) {
		this.meetingPeriods = meetingPeriods;
	}

	
}