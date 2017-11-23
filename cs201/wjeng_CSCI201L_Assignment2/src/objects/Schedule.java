package objects;

import java.util.List;

public class Schedule {

	private List<TextBooks> textbooks;
	private List<Week> weeks;
	
	//getter setter

	public List<Week> getWeeks() {
		return weeks;
	}
	public List<TextBooks> getTextbooks() {
		return textbooks;
	}
	public void setTextbooks(List<TextBooks> textbooks) {
		this.textbooks = textbooks;
	}
	public void setWeeks(List<Week> weeks) {
		this.weeks = weeks;
	}
	
	
}

