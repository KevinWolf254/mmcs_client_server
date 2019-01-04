package co.ke.proaktiv.io.pojos;

public class SenderIdRequest {
	private String product;
	private String type;
	private String email;
    private String senderId;
    private String country;
    private String transNo;
    private String currency;
    private double amount;
	public SenderIdRequest() {
		super();
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
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
		return "SenderIdRequest [product=" + product + ", type=" + type + ", email=" + email + ", senderId=" + senderId
				+ ", country=" + country + ", transNo=" + transNo + ", currency=" + currency + ", amount=" + amount
				+ "]";
	}
}
