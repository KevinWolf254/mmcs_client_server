package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.pojos.pro.ClientAdmin;

public class AdminResponse extends Response {

	private ClientAdmin admin;

	public AdminResponse() {
		super();
	}

	public AdminResponse(int code, String title, 
			String message, ClientAdmin admin) {
		super(code, title, message);
		this.admin = admin;
	}

	public ClientAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(ClientAdmin admin) {
		this.admin = admin;
	}
	
}
