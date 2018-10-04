package co.ke.proaktiv.io.pojos.reports;

import java.util.Set;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.pro.Disbursement;

public class SignInReport extends UserReport {

	private Client client;
	private Disbursement disbursement;
	public SignInReport() {
		super();
	}
	public SignInReport(int code, String title, String message, 
			Client client, Disbursement disbursement, User user, UserCredentials credentials, Set<UserRole> roles) {
		super(code, title, message, user, credentials, roles);
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
