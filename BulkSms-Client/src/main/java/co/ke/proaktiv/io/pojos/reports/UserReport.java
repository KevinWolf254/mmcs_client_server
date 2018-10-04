package co.ke.proaktiv.io.pojos.reports;

import java.util.Set;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.pojos.response.Response;

public class UserReport extends Response{

	private User user;
	private UserCredentials credentials;
	private Set<UserRole> roles;
	public UserReport() {
		super();
	}
	
	public UserReport(int code, String title, String message, 
			User user, UserCredentials credentials,
			Set<UserRole> roles) {
		super(code, title, message);
		this.user = user;
		this.credentials = credentials;
		this.roles = roles;
	}

	public UserReport(User user, UserCredentials credentials, 
			Set<UserRole> roles) {
		super();
		this.user = user;
		this.credentials = credentials;
		this.roles = roles;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserCredentials getCredentials() {
		return credentials;
	}
	public void setCredentials(UserCredentials credentials) {
		this.credentials = credentials;
	}
	public Set<UserRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
}
