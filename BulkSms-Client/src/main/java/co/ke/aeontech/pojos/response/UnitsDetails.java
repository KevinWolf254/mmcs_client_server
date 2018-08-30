package co.ke.aeontech.pojos.response;

import co.ke.aeontech.pojos.helpers.Country;

public class UnitsDetails {
	private Country country;
	private double unitsAvailable;
	private double requestsPendingApproval;
	private double requestsPendingApprovalAmount;
	
	public UnitsDetails() {
	}

	public UnitsDetails(Country country, double unitsAvailable, 
			double requestsPendingApproval,	double requestsPendingApprovalAmount) {
		super();
		this.country = country;
		this.unitsAvailable = unitsAvailable;
		this.requestsPendingApproval = requestsPendingApproval;
		this.requestsPendingApprovalAmount = requestsPendingApprovalAmount;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setUnitsAvailable(double unitsAvailable) {
		this.unitsAvailable = unitsAvailable;
	}

	public void setRequestsPendingApproval(double requestsPendingApproval) {
		this.requestsPendingApproval = requestsPendingApproval;
	}

	public void setRequestsPendingApprovalAmount(double requestsPendingApprovalAmount) {
		this.requestsPendingApprovalAmount = requestsPendingApprovalAmount;
	}

	public double getUnitsAvailable() {
		return unitsAvailable;
	}

	public double getRequestsPendingApproval() {
		return requestsPendingApproval;
	}

	public double getRequestsPendingApprovalAmount() {
		return requestsPendingApprovalAmount;
	}
	
	@Override
	public String toString() {		
		final StringBuilder builder = new StringBuilder();
		builder.append("UnitsDetailsResponse [id=")
				.append(", unitsAvailable=")
				.append(unitsAvailable)
				.append(", requestsPendingApproval=")
				.append(requestsPendingApproval)
				.append(", requestsPendingApprovalAmount=")
				.append(requestsPendingApprovalAmount)
				.append("]");
		return builder.toString();
	}
	
}
