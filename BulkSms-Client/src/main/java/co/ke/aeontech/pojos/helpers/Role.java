package co.ke.aeontech.pojos.helpers;

public enum Role {
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
	
	private final String role;
	
	private Role(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
	
}
