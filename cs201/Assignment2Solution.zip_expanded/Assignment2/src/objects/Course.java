package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import parsing.StringConstants;

public class Course {
	private String number;
	private String term;
	private String title;
	private Integer year;
	private Integer units;
	private Syllabus syllabus;
	private Schedule schedule;
	private List<Assignment> assignments;
	private List<Exam> exams;
	private List<StaffMember> staffMembers;
	private List<Meeting> meetings;

	private Assignment finalProject;
	//holds a 2D array of staff members to prepare for displaying the OH in home.jsp
	private StaffMember[][] officeHours;
	private Boolean organized = false;
	//map from the start time string to the column index in officeHours
	private static Map<String, Integer> ohTimeToIndex;
	//map from the day string to the row index in officeHours
	private static Map<String, Integer> ohDayToIndex;
	//ordered list of the office hour days
	public static String[] officeHourDays;
	//ordered list of the office hour start times
	public static String[] officeHourTimes;

	// meetings sorted into a map, key is the meeting type, value is a list of all meetings of that type
	private Map<String, List<Meeting>> sortedMeetings;
	// staff sorted into a map, key is the staff type, value is a list of all staff members of that type
	private Map<String, List<StaffMember>> sortedStaff;
	// map from due date string to assignment and deliverable objects -- used on the lectures page to show due dates
	private Map<String, List<GeneralAssignment>> mappedAssignments;
	//map from staff member ID to staff member object
	private Map<Integer, StaffMember> staffMap;

	//static constructor to initialize and populate static member variables
	static {
		ohTimeToIndex = new HashMap<>();
		ohDayToIndex = new HashMap<>();
		officeHourDays = new String[6];
		officeHourTimes = new String[5];

		officeHourDays[0] = "Monday";
		officeHourDays[1] = "Tuesday";
		officeHourDays[2] = "Wednesday";
		officeHourDays[3] = "Thursday";
		officeHourDays[4] = "Friday";
		officeHourDays[5] = "Saturday";

		officeHourTimes[0] = "10:00a.m.-12:00p.m.";
		officeHourTimes[1] = "12:00p.m.-2:00p.m.";
		officeHourTimes[2] = "2:00p.m.-4:00p.m.";
		officeHourTimes[3] = "4:00p.m.-6:00p.m.";
		officeHourTimes[4] = "6:00p.m.-8:00p.m.";

		ohTimeToIndex.put("10:00a.m.", 0);
		ohTimeToIndex.put("12:00p.m.", 1);
		ohTimeToIndex.put("2:00p.m.", 2);
		ohTimeToIndex.put("4:00p.m.", 3);
		ohTimeToIndex.put("6:00p.m.", 4);

		ohDayToIndex.put("Monday", 0);
		ohDayToIndex.put("Tuesday", 1);
		ohDayToIndex.put("Wednesday", 2);
		ohDayToIndex.put("Thursday", 3);
		ohDayToIndex.put("Friday", 4);
		ohDayToIndex.put("Saturday", 5);
	}

	public StaffMember[][] getOfficeHours() {
		return officeHours;
	}

	public String getTitle() {
		return title;
	}

	public Integer getUnits() {
		return units;
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public Assignment getFinalProject() {
		return finalProject;
	}

	public String getNumber() {
		return number;
	}

	public String getTerm() {
		return term;
	}

	public Integer getYear() {
		return year;
	}

	public List<StaffMember> getStaffMembers() {
		return staffMembers;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public Map<String, List<Meeting>> getSortedMeetings() {
		return sortedMeetings;
	}

	public Map<String, List<StaffMember>> getSortedStaff() {
		return sortedStaff;
	}
	
	public Map<Integer, StaffMember> getMappedStaff() {
		return staffMap;
	}

	/*
	 * Prepares and populates on the data structures that were not populated by Gson
	 * All these data structures will make displaying the course content easier on the front end
	 */
	public void organize() {
		
		if (!organized) {
			sortedMeetings = meetings.stream().collect(Collectors.groupingBy(Meeting::getMeetingType));
			sortedStaff = staffMembers.stream().collect(Collectors.groupingBy(StaffMember::getJobType));
			mappedAssignments = new HashMap<>();
			staffMap = new HashMap<>();
			officeHours = new StaffMember[6][5];

			staffMembers.stream().forEach(staff -> {
				staffMap.put(staff.getID(), staff);
				//populate the 2D OH array
				if (staff.getOH() != null && !staff.getJobType().equals(StringConstants.INSTRUCTOR)) {
					
					for (TimePeriod time : staff.getOH()) {
						//column index based on the start time of this office hour
						Integer col = ohTimeToIndex.get(time.getTime().getStartTime());
						//row index based on the day of this office hour
						Integer row = ohDayToIndex.get(time.getDay());
						//set the object at these indices to the current staff member
						officeHours[row][col] = staff;
					}
				}

			});
			
			//iterate through all the assignments and deliverables and add them to the map
			for (Assignment assignment : assignments) {
				//try to parse the number member variable as an integer -- if we catch an exception, we know it is the final project
				try {
					Integer.parseInt(assignment.getNumber());
					//if we made it this far, this assignment is not the final project, so add it to the map
					addAssignmentToMap(assignment);
				} 
				catch (NumberFormatException nfe) {
					//set this assignment as the final project
					finalProject = assignment;
					//iterate through all the deliverables and add them to the map
					if (finalProject.getDeliverables() != null) {
						finalProject.getDeliverables().stream().forEach(del -> addAssignmentToMap(del));
					}
				}
			}

			schedule.organize(mappedAssignments);
			//just for fun, we are sorting the assignments by number
			Collections.sort(assignments, new CompareAssignments());
			organized = true;
		}
	}
	
	/*
	 * Given a GeneralAssignment, determine whether it's due date is already a key in the assignments map
	 * If so, add it to the existing list, otherwise create a new list and add it to the map
	 */
	private void addAssignmentToMap(GeneralAssignment assignment){
		String dueDate = assignment.getDueDate();

		if (mappedAssignments.containsKey(dueDate)) {
			mappedAssignments.get(dueDate).add(assignment);
		} 
		else {
			List<GeneralAssignment> list = new ArrayList<>();
			list.add(assignment);
			mappedAssignments.put(dueDate, list);
		}
	}
	
	/*
	 * Private Comparator class to be able to compare Assignment objects by their number member variable
	 */
	private class CompareAssignments implements Comparator<Assignment> {

		@Override
		public int compare(Assignment o1, Assignment o2) {
			Integer one = null;
			Integer two = null;
			//try to parse their number variables -- if we catch an exception, it was the final project so set their integer value to the max possible
			try {
				one = Integer.parseInt(o1.getNumber());
			} catch (NumberFormatException nfe) {
				one = Integer.MAX_VALUE;
			}

			try {
				two = Integer.parseInt(o2.getNumber());
			} catch (NumberFormatException nfe) {
				two = Integer.MAX_VALUE;
			}
			
			//return their difference
			return one - two;
		}

	}
}
