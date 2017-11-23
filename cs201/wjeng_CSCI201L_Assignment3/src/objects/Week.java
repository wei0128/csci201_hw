package objects;

import java.util.ArrayList;
import java.util.List;

public class Week {

	private String week;
	private List<Lab> labs = new ArrayList<Lab>();
	private List<Lecture> lectures = new ArrayList<Lecture>();
	
	//getter setter
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public List<Lab> getLabs() {
		return labs;
	}
	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}
	public List<Lecture> getLectures() {
		return lectures;
	}
	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	
}
