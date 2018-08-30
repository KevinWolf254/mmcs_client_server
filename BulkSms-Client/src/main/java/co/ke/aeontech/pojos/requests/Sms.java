package co.ke.aeontech.pojos.requests;

import java.util.Set;

import co.ke.aeontech.pojos._Schedule;

public class Sms {

	private String message;
	private _Schedule schedule;
	private Set<Long> groupIds;
	public Sms() {
		super();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public _Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(_Schedule schedule) {
		this.schedule = schedule;
	}
	public Set<Long> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(Set<Long> groupIds) {
		this.groupIds = groupIds;
	}
	@Override
	public String toString() {
		return "Sms [message=" + message + ", schedule=" + schedule + ", groupIds=" + groupIds + "]";
	}
	
}
