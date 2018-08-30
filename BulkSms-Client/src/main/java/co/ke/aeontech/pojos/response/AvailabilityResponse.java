package co.ke.aeontech.pojos.response;

public class AvailabilityResponse implements Response {

	private Boolean isAvailable;

	public AvailabilityResponse(Boolean isAvailable) {
		super();
		this.isAvailable = isAvailable;
	}

	public AvailabilityResponse() {
		super();
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
