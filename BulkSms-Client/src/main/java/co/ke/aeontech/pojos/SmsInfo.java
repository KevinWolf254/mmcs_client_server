package co.ke.aeontech.pojos;

public class SmsInfo {
	
	private String sender;
	private String contacts;
	private String message;
	public SmsInfo() {
		super();
	}
	public String getSender() {
		return sender;
	}
	public SmsInfo(String sender, String contacts, String message) {
		super();
		this.sender = sender;
		this.contacts = contacts;
		this.message = message;
	}
	public void setSender(String senderId) {
		this.sender = senderId;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
