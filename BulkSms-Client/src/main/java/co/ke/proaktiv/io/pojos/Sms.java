package co.ke.proaktiv.io.pojos;

import java.util.Set;

public class Sms {

	private String email;
	private String senderId;
	private String message;
	private _Schedule schedule;
	private Set<Long> groupIds;
	public Sms() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
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
}
