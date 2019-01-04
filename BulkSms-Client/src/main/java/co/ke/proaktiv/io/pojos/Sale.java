package co.ke.proaktiv.io.pojos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.ke.proaktiv.io.pojos.helpers.PaymentType;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.pro.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sale {	
	
	private Long id;	
	private String invoiceNo;	
	private String code;
	private PaymentType type;
	private boolean successful;
	private String amountInfo;
	private Date date;
	private Product product;
	private Client client;
	public Sale() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public PaymentType getType() {
		return type;
	}
	public void setType(PaymentType type) {
		this.type = type;
	}
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public String getAmountInfo() {
		return amountInfo;
	}
	public void setAmountInfo(String amountInfo) {
		this.amountInfo = amountInfo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
