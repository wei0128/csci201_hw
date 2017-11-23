package objects;

import java.util.List;

public class Assignment extends GeneralAssignment {
	private String assignedDate;
	private String url;
	private List<File> gradingCriteriaFiles;
	private List<File> solutionFiles;
	private List<Deliverable> deliverables;

	public String getAssignedDate() {
		return assignedDate;
	}

	public String getUrl() {
		return url;
	}

	public List<Deliverable> getDeliverables() {
		return deliverables;
	}

	public List<File> getGradingCriteriaFiles() {
		return gradingCriteriaFiles;
	}

	public List<File> getSolutionFiles() {
		return solutionFiles;
	}

}
