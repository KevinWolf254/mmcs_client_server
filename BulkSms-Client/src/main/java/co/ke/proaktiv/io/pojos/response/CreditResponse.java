package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.pojos.pro.Credit;

public class CreditResponse extends Response {

	private Credit credit;
	private PendingDisbursementResponse payment;
	
	public CreditResponse() {
		super();
	}

	public CreditResponse(int code, String title, 
			String message, Credit credit, 
			PendingDisbursementResponse payment) {
		super(code, title, message);
		this.credit = credit;
		this.payment = payment;
	}

	public Credit getCredit() {
		return credit;
	}

	public void setCredit(Credit credit) {
		this.credit = credit;
	}

	public PendingDisbursementResponse getPayment() {
		return payment;
	}

	public void setPayment(PendingDisbursementResponse payment) {
		this.payment = payment;
	}
	
}
