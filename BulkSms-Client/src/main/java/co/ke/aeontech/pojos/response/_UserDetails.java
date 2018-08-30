package co.ke.aeontech.pojos.response;

import java.util.Date;

import co.ke.aeontech.pojos.helpers.Role;

public class _UserDetails {

	private String organisation;
	private String surname;
	private String otherNames;
	private String email;
	private Role role;
	private boolean isActive;
	private Date lastSignInDate;
	public _UserDetails() {
		super();
	}
	public _UserDetails(String organisation, String surname, String otherNames, String email, Role role,
			boolean isActive, Date lastSignInDate) {
		super();
		this.organisation = organisation;
		this.surname = surname;
		this.otherNames = otherNames;
		this.email = email;
		this.role = role;
		this.isActive = isActive;
		this.lastSignInDate = lastSignInDate;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getLastSignInDate() {
		return lastSignInDate;
	}
	public void setLastSignInDate(Date lastSignInDate) {
		this.lastSignInDate = lastSignInDate;
	}
	
}
