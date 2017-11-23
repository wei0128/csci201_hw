package objects;

import java.util.List;

public class Courses {

	private String number;
	private String title;
	private String units;
	private String term;
	private String year;
	private Syllabus syllabus;
	private List<StaffMembers> staffMembers;
	private List<Meetings> meetings;
	private Schedule schedule;
	private List<Assignment> assignments;
	private List<Exam> exams;
	
	
	//getter setter
	public String getTitle() {
		return title;
	}
	public Syllabus getSyllabus() {
		return syllabus;
	}
	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public List<StaffMembers> getStaffMembers() {
		return staffMembers;
	}
	public void setStaffMembers(List<StaffMembers> staffMembers) {
		this.staffMembers = staffMembers;
	}
	public List<Meetings> getMeetings() {
		return meetings;
	}
	public void setMeetings(List<Meetings> meetings) {
		this.meetings = meetings;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public List<Assignment> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	public List<Exam> getExams() {
		return exams;
	}
	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	
}
