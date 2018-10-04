package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.pro.ClientUser;

public class SignUp extends Response {
	
	private Client client;
	private ClientUser user;
	public SignUp() {
		super();
	}
	public SignUp(int code, String title, String message) {
		super(code, title, message);
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public ClientUser getAdmin() {
		return user;
	}
	public void setAdmin(ClientUser admin) {
		this.user = admin;
	}
	public ClientUser getUser() {
		return user;
	}
	public void setUser(ClientUser user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "SignUpResponse [client=" + client + ", user=" + user + "]";
	}
}
