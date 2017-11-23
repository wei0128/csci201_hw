package objects;

import java.util.List;
import java.util.Map;

public class Schedule {
	private List<Textbook> textbooks;
	private List<Week> weeks;

	public List<Textbook> getTextbooks() {
		return textbooks;
	}

	public List<Week> getWeeks() {
		return weeks;
	}
	
	/*
	 * Organize each week of the schedule
	 */
	public void organize(Map<String, List<GeneralAssignment>> mappedAssignments) {
		weeks.stream().forEach(week -> week.organize(mappedAssignments));
	}
}
