package co.ke.proaktiv.io.pojos;

import co.ke.proaktiv.io.pojos.helpers.Currency;
import co.ke.proaktiv.io.pojos.helpers.PaymentType;

public class Payment {
	private Long id;
	private PaymentType type;
	//paymentSource -phoneNo or payPalAcnt_no
	private String account;
	private Currency currency;
	private double amount;
	
	public Payment() {
		super();
	}

	public Payment(PaymentType type, String account, 
			Currency currency, double amount) {
		super();
		this.type = type;
		this.account = account;
		this.currency = currency;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Payment [type=").append(type)
				.append(", account=").append(account)
				.append(", currency=").append(currency)
				.append(", amount=").append(amount)
				.append("]");
		return builder.toString();
	}
}
