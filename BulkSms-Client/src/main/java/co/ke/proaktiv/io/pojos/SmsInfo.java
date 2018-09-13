package co.ke.proaktiv.io.pojos;

import java.util.Collection;

public class SmsInfo {
	
	private String sender;
	private String recipients;
	private Collection<SubscriberReport> phoneNosTotals;
	private String message;
	public SmsInfo() {
		super();
	}
	public SmsInfo(String sender, String recipients, 
			Collection<SubscriberReport> phoneNosTotals, String message) {
		super();
		this.sender = sender;
		this.recipients = recipients;
		this.phoneNosTotals = phoneNosTotals;
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Collection<SubscriberReport> getPhoneNosTotals() {
		return phoneNosTotals;
	}
	public void setPhoneNosTotals(Collection<SubscriberReport> phoneNosTotals) {
		this.phoneNosTotals = phoneNosTotals;
	}
}
