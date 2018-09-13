package co.ke.proaktiv.io.pojos.pro;

import co.ke.proaktiv.io.pojos.helpers.Currency;

public class Credit {

	private Long id;
	private Currency currency;
	private double amount;
	public Credit() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}	
}
