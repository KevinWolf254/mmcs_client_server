package co.ke.proaktiv.io.pojos;

import java.util.Date;

import co.ke.proaktiv.io.pojos.helpers.SaleType;
import co.ke.proaktiv.io.pojos.helpers.Status;

public class Sale {

	private Long id;	
	//this is the unique transaction code
	//could be mpesaTransNo or payPalTransNo
	private String code;
	private SaleType type;
	private Status status;
	private boolean creditDisbursed;
	private Date date;
	private Payment payment;
	
	public Sale() {
		super();
	}

	public Sale(String code, SaleType type, Status status, Payment payment) {
		super();
		this.code = code;
		this.type = type;
		this.status = status;
		this.creditDisbursed = Boolean.FALSE;
		this.payment = payment;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SaleType getType() {
		return type;
	}

	public void setType(SaleType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isCreditDisbursed() {
		return creditDisbursed;
	}

	public void setCreditDisbursed(boolean creditDisbursed) {
		this.creditDisbursed = creditDisbursed;
	}

}
