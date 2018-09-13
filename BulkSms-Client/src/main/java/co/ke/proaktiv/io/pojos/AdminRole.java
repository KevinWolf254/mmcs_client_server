package co.ke.proaktiv.io.pojos;

import java.util.ArrayList;
import java.util.List;

import co.ke.proaktiv.io.pojos.helpers.Role;

public class AdminRole extends AdminCredentials{

	private List<Role> roles = new ArrayList<>();
	public AdminRole() {
		super();
	}
	public AdminRole(String email, String phoneNo, String surname, 
			String otherNames, String password,
			Role role) {
		super(email, phoneNo, surname, otherNames, password);
		this.roles.add(role);
	}
	public void setRole(Role role) {
		this.roles.add(role);
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
