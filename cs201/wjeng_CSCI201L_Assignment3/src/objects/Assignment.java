package objects;

import java.util.List;

public class Assignment {

	private String number;
	private String assignedDate;
	private String dueDate;
	private String title;
	private String url;
	private List<File> files;
	private List<File> gradingCriteriaFiles;
	private List<File> solutionFiles;
	private String gradePercentage;
	private List<Deliverable> deliverables;
	
	//getter setter
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public List<File> getGradingCriteriaFiles() {
		return gradingCriteriaFiles;
	}
	public void setGradingCriteriaFiles(List<File> gradingCriteriaFiles) {
		this.gradingCriteriaFiles = gradingCriteriaFiles;
	}
	public List<File> getSolutionFiles() {
		return solutionFiles;
	}
	public void setSolutionFiles(List<File> solutionFiles) {
		this.solutionFiles = solutionFiles;
	}
	public String getGradePercentage() {
		return gradePercentage;
	}
	public void setGradePercentage(String gradePercentage) {
		this.gradePercentage = gradePercentage;
	}
	public List<Deliverable> getDeliverables() {
		return deliverables;
	}
	public void setDeliverables(List<Deliverable> deliverables) {
		this.deliverables = deliverables;
	}
	
	
}
