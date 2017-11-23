package objects;

import java.util.ArrayList;
import java.util.List;


public class Lecture {
	private String number;
	private String date;
	private String day;
	private List<Topic> topics = new ArrayList<Topic>();
	
	//getter setter
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<Topic> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	
	
}
