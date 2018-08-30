package co.ke.aeontech.pojos;

public class MpesaConfirmation{

	private double requestedUnits;
	private String mpesaTransNo;
	
	public MpesaConfirmation() {
		super();
	}
	public MpesaConfirmation(double requestedUnits, String mpesaTransNo) {
		super();
		this.requestedUnits = requestedUnits;
		this.mpesaTransNo = mpesaTransNo;
	}
	public double getRequestedUnits() {
		return requestedUnits;
	}
	public void setRequestedUnits(double requestedUnits) {
		this.requestedUnits = requestedUnits;
	}
	public String getMpesaTransNo() {
		return mpesaTransNo;
	}
	public void setMpesaTransNo(String mpesaTransNo) {
		this.mpesaTransNo = mpesaTransNo;
	}	
}
