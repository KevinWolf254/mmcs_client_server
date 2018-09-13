package co.ke.proaktiv.io.pojos.helpers;

public enum ScheduleType {

	DATE("Date"), DAILY ("Daily"), WEEKLY ("Weekly"), 
	MONTHLY ("Monthly"), NONE ("None");
	
	private String type;

	private ScheduleType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}	
}
