package objects;

public class Time {
	private String start;
	private String end;
	
	public String getStartTime(){
		return start;
	}
	
	public String getEndTime(){
		return end;
	}
	
	@Override
	public String toString(){
		String s = start == null ? "" : start;
		String e = end == null ? "" : end;
		return s + " - " + e;
	}
}
