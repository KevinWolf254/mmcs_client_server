package co.ke.aeontech.pojos;

public class MpesaConfirmationBy extends MpesaConfirmation{
	private String requestedBy;

	private MpesaConfirmation amount;

	public MpesaConfirmationBy(String requestedBy, MpesaConfirmation amount) {
		super(amount.getRequestedUnits(), amount.getMpesaTransNo());
		this.requestedBy = requestedBy;
	}

	public MpesaConfirmationBy() {
		super();
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public MpesaConfirmation getAmount() {
		return amount;
	}

	public void setAmount(MpesaConfirmation amount) {
		this.amount = amount;
	}	
}
