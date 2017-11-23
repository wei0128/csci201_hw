package objects;

import java.util.ArrayList;
import java.util.List;

public class Lecture {
	private Integer number;
	private String date;
	private String day;
	private List<Topic> topics;
	//list containing all programs from all topics
	private List<Program> allPrograms;
	//string that will be used on the front end to display all chapters
	private String allChapters;

	/*
	 * this method populates the full list of programs and creates the chapters string
	 * these member variables will come in handy on the lectures.jsp page
	 */
	public void organize(){
		allPrograms = new ArrayList<>();
		for (Topic topic : topics)
		{
  			if (allChapters == null && topic.getChapter() != null){
	  			allChapters = topic.getChapter();
  			}
  			
  			else if (topic.getChapter() != null){
				allChapters += ", " + topic.getChapter();
  			}
  			
  			if (topic.getPrograms() != null) {
  				allPrograms.addAll(topic.getPrograms());
  			}
		}
	}
	
	public List<Program> getAllPrograms() {
		return allPrograms;
	}
	
	public String getAllChapters(){
		return allChapters;
	}
	
	public Integer getNumber() {
		return number;
	}

	public String getDate() {
		return date;
	}

	public String getDay() {
		return day;
	}

	public List<Topic> getTopics() {
		return topics;
	}
}
