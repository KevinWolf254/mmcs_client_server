package co.ke.aeontech.pojos.helpers;

public enum Reply {
	FAILED("Failed"), SUCCESS("Success");

private final String status;
	
	private Reply(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
