package objects;

import java.util.List;

//the assignment and deliverable classes inherit from this class
public abstract class GeneralAssignment {
	private String gradePercentage;
	private List<File> files;
	private String number;
	private String dueDate;
	private String title;

	public String getNumber() {
		return number;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getTitle() {
		return title;
	}

	public String getGradePercentage() {
		return gradePercentage;
	}

	public List<File> getFiles() {
		return files;
	}
}
