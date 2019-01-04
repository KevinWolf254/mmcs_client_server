package co.ke.proaktiv.io.pojos;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.pojos.response.Response;

public class ChargesReport extends Response{

	private Country currency;
	private double estimatedCost;
	private int totalContacts;
	public Country getCurrency() {
		return currency;
	}
	public ChargesReport() {
		super();
	}
	public ChargesReport(int code, String title, String message) {
		super(code, title, message);
	}
	public ChargesReport(int code, String title, String message, Country currency, double estimatedCost, int totalContacts) {
		super(code, title, message);
		this.currency = currency;
		this.estimatedCost = estimatedCost;
		this.totalContacts = totalContacts;
	}
	public void setCurrency(Country currency) {
		this.currency = currency;
	}
	public double getEstimatedCost() {
		return estimatedCost;
	}
	public void setEstimatedCost(double charge) {
		this.estimatedCost = charge;
	}
	public int getTotalContacts() {
		return totalContacts;
	}
	public void setTotalContacts(int totalContacts) {
		this.totalContacts = totalContacts;
	}
}
