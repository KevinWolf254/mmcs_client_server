package co.ke.proaktiv.io.pojos.response;

import java.math.BigDecimal;

import co.ke.proaktiv.io.pojos.helpers.Currency;

/**
 * pending disbursement report
 * @author kevin
 *
 */
public class PendingDisbursementResponse extends Response {

	private Currency currency;
	private BigDecimal amount;
	private int number;
	
	public PendingDisbursementResponse() {
		super();
	}

	public PendingDisbursementResponse(int code, String title, 
			String message, Currency currency, BigDecimal amount, 
			int number) {
		super(code, title, message);
		this.currency = currency;
		this.amount = amount;
		this.number = number;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
