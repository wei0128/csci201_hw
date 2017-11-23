package objects;

public class TimePeriod {
	private String day;
	private Time time;
	
	public String getDay(){
		return day;
	}
	
	public Time getTime(){
		return time;
	}
	
	@Override
	public String toString(){
		return day + (time == null ? "" : " " + time.toString());
	}
}
