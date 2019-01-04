package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.pro.Disbursement;

public class Credit extends Response {
	
	private Client client;
	private Disbursement disbursement;
	public Credit() {
		super();
	}
	public Credit(int code, String title, String message) {
		super(code, title, message);
	}
	public Credit(int code, String title, String message, 
			Client client, Disbursement disbursement) {
		super(code, title, message);
		this.client = client;
		this.disbursement = disbursement;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Disbursement getDisbursement() {
		return disbursement;
	}
	public void setDisbursement(Disbursement disbursement) {
		this.disbursement = disbursement;
	}
}
