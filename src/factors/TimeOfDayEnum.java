package factors;

public enum TimeOfDayEnum {
	DAY("Day"),
	NIGHT("Night");
	
	private String timeOfDay;
	
	private TimeOfDayEnum (String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	public String getTimeOfDay() {
		return this.timeOfDay;
	}
}
