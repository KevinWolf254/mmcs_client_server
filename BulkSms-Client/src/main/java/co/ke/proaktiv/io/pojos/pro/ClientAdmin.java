package co.ke.proaktiv.io.pojos.pro;

public class ClientAdmin {
	
	private Long id;
	private String email;
	private String phoneNo;
	public ClientAdmin() {
		super();
	}
	public ClientAdmin(String email, String phoneNo) {
		super();
		this.email = email;
		this.phoneNo = phoneNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
