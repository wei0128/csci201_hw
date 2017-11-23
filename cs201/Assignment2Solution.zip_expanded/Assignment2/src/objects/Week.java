package objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week {
	private Integer week;
	private List<Lecture> lectures;
	private Map<Integer, List<GeneralAssignment>> mappedDueDates;
	private Integer rowSize;
	private List<Lab> labs;

	public Integer getWeek() {
		return week;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public List<Lab> getLabs() {
		return labs;
	}
	
	public Integer getRowSize(){
		return rowSize;
	}
	
	public Map<Integer, List<GeneralAssignment>> getMappedDueDates(){
		return mappedDueDates;
	}
	
	/*
	 * Populate the map that has lecture numbers as keys and lists of GeneralAssignments as values
	 * This is used to display due dates on the lectures page
	 */
	public void organize(Map<String, List<GeneralAssignment>> mappedAssignments){
		
		mappedDueDates = new HashMap<>();
		rowSize = lectures.size();
		
		for (Lecture lecture : lectures){
			lecture.organize();
			//if we have some assignments that are due on this lecture date
			if (mappedAssignments.containsKey(lecture.getDate())){
  				List<GeneralAssignment> assignmentsDue = mappedAssignments.get(lecture.getDate());
  				//increment our row size by the number of assignments due
	  			rowSize += assignmentsDue.size();
	  			mappedDueDates.put(lecture.getNumber(), assignmentsDue);
  			}
		}
	}
}
