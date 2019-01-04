package co.ke.proaktiv.io.pojos;

import java.util.Date;

public class JR_Invoice {

	private String invoiceNo;
	private String productName;
	private Date date;
	private String transactionId;
	private String amount;
	public JR_Invoice() {
		super();
	}
	public JR_Invoice(String invoiceNo, String productName, Date date, String transactionId, String amount) {
		super();
		this.invoiceNo = invoiceNo;
		this.productName = productName;
		this.date = date;
		this.transactionId = transactionId;
		this.amount = amount;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
