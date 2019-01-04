package co.ke.proaktiv.io.pojos;

import java.util.Collection;

public class SmsInfo {
	
	private String sender;
	private String senderId;
	private String recipients;
	private Collection<ServiceProviderReport> phoneNosTotals;
	private String message;
	public SmsInfo() {
		super();
	}
	public SmsInfo(String sender, String senderId, String recipients, 
			Collection<ServiceProviderReport> phoneNosTotals, String message) {
		super();
		this.sender = sender;
		this.senderId = senderId;
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
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
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
	public Collection<ServiceProviderReport> getPhoneNosTotals() {
		return phoneNosTotals;
	}
	public void setPhoneNosTotals(Collection<ServiceProviderReport> phoneNosTotals) {
		this.phoneNosTotals = phoneNosTotals;
	}
	@Override
	public String toString() {
		return "SmsInfo [sender=" + sender + ", senderId=" + senderId + ", recipients=" + recipients
				+ ", phoneNosTotals=" + phoneNosTotals + ", message=" + message + "]";
	}
}
