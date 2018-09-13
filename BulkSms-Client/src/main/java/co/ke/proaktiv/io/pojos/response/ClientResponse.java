package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.pojos.pro.Client;

public class ClientResponse extends Response {
	
	private Client client;

	public ClientResponse() {
		super();
	}

	public ClientResponse(int code, String title, 
			String message, Client client) {
		super(code, title, message);
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
