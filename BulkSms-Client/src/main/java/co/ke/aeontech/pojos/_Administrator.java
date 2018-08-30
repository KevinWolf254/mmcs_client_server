package co.ke.aeontech.pojos;

import co.ke.aeontech.pojos.helpers.Country;

public class _Administrator {

	private Long id;
	private String surname;
	private String otherNames;
	private String email;
	private Country countryCode;
	private String phoneNo;
	private String password;
	
	public _Administrator() {
		super();
	}
	
	public _Administrator(String surname, String otherNames, String email, Country countryCode, String phoneNo,
			String password) {
		super();
		this.surname = surname;
		this.otherNames = otherNames;
		this.email = email;
		this.countryCode = countryCode;
		this.phoneNo = phoneNo;
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Country getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(Country countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
