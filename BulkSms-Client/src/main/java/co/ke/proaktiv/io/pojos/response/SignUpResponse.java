package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.pro.ClientAdmin;
import co.ke.proaktiv.io.pojos.pro.Credit;
import co.ke.proaktiv.io.pojos.pro.ShortCode;

public class SignUpResponse extends Response {
	
	private Client client;
	private ShortCode shortCode;
	private ClientAdmin admin;
	private Credit credit;
	public SignUpResponse() {
		super();
	}
	public SignUpResponse(int code, String title, String message) {
		super(code, title, message);
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public ShortCode getShortCode() {
		return shortCode;
	}
	public void setShortCode(ShortCode shortCode) {
		this.shortCode = shortCode;
	}
	public ClientAdmin getAdmin() {
		return admin;
	}
	public void setAdmin(ClientAdmin admin) {
		this.admin = admin;
	}
	public Credit getCredit() {
		return credit;
	}
	public void setCredit(Credit credit) {
		this.credit = credit;
	}
	
}
