package objects;

import java.util.List;

public class Topic extends GeneralObject {
	private String chapter;
	private List<Program> programs;

	public String getChapter() {
		return chapter;
	}

	public List<Program> getPrograms() {
		return programs;
	}
}
