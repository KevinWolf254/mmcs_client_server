package co.ke.proaktiv.io.pojos.helpers;

public enum Day {

	SUNDAY("SUN"), MONDAY("MON"), TUESDAY("TUE"), WEDNESDAY("WED"), 
	THURSDAY("THU"), FRIDAY("FRI"), SATURDAY("SAT");
	
	private String day;

	private Day(String day) {
		this.day = day;
	}
	public String getDay() {
		return day;
	}
}
