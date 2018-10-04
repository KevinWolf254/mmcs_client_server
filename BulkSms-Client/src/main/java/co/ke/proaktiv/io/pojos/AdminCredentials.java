package co.ke.proaktiv.io.pojos;

import co.ke.proaktiv.io.pojos.pro.ClientUser;

public class AdminCredentials extends ClientUser{	
	private String surname;
	private String otherNames;
	private String password;
	public AdminCredentials() {
		super();
	}
	public AdminCredentials(String email, String phoneNo, 
			String surname, String otherNames, String password) {
		super(email, phoneNo);
		this.surname = surname;
		this.otherNames = otherNames;
		this.password = password;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getOtherNames() {
		return otherNames;
	}
	public void setOtherNames(String otherNames) {
		this.otherNames = otherNames;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
