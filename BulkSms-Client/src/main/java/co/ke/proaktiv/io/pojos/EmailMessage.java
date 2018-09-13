package co.ke.proaktiv.io.pojos;

public class EmailMessage {

	private String to_address;
	private String Subject;
	private String body;
	public EmailMessage() {
		super();
	}
	public EmailMessage(String to_address, String subject, String body) {
		super();
		this.to_address = to_address;
		Subject = subject;
		this.body = body;
	}
	public String getTo_address() {
		return to_address;
	}
	public void setTo_address(String to_address) {
		this.to_address = to_address;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	};
	
}
