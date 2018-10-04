package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.pojos.pro.ClientUser;

public class AdminResponse extends Response {

	private ClientUser admin;

	public AdminResponse() {
		super();
	}

	public AdminResponse(int code, String title, 
			String message, ClientUser admin) {
		super(code, title, message);
		this.admin = admin;
	}

	public ClientUser getAdmin() {
		return admin;
	}

	public void setAdmin(ClientUser admin) {
		this.admin = admin;
	}
	
}
