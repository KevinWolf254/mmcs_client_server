package co.ke.aeontech.pojos;

import co.ke.aeontech.pojos.helpers.Reply;
import co.ke.proaktiv.io.pojos.helpers.Country;

public class Payment {

//	private Long id;
//	
//	private String transactionId;
//	
//	private String category;
//	
//	private String source;
//	
//	private String provider;
//	
//	private String providerRefId;
//	
//	private Country currency;
//	
//	private double amount;
//	
//	private double transactionFee;
//	
//	private double providerFee;
//	
//	private Reply response;
//
//	public Payment() {
//		super();
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getTransactionId() {
//		return transactionId;
//	}
//
//	public void setTransactionId(String transactionId) {
//		this.transactionId = transactionId;
//	}
//
//	public String getCategory() {
//		return category;
//	}
//
//	public void setCategory(String category) {
//		this.category = category;
//	}
//
//	public String getSource() {
//		return source;
//	}
//
//	public void setSource(String source) {
//		this.source = source;
//	}
//
//	public String getProvider() {
//		return provider;
//	}
//
//	public void setProvider(String provider) {
//		this.provider = provider;
//	}
//
//	public String getProviderRefId() {
//		return providerRefId;
//	}
//
//	public void setProviderRefId(String providerRefId) {
//		this.providerRefId = providerRefId;
//	}
//
//	public Country getCurrency() {
//		return currency;
//	}
//
//	public void setCurrency(Country currency) {
//		this.currency = currency;
//	}
//
//	public double getAmount() {
//		return amount;
//	}
//
//	public void setValue(double amount) {
//		this.amount = amount;
//	}
//
//	public double getTransactionFee() {
//		return transactionFee;
//	}
//
//	public void setTransactionFee(double transactionFee) {
//		this.transactionFee = transactionFee;
//	}
//
//	public double getProviderFee() {
//		return providerFee;
//	}
//
//	public void setProviderFee(double providerFee) {
//		this.providerFee = providerFee;
//	}
//
//	public Reply getResponse() {
//		return response;
//	}
//
//	public void setResponse(Reply response) {
//		this.response = response;
//	}
//	
//	@Override
//	public String toString() {		
//		final StringBuilder builder = new StringBuilder();
//		builder.append("Payment Notification [id=")
//				.append(id)
//				.append(", transactionId=").append(transactionId)
//				.append(", category=").append(category)
//				.append(", sourceType=").append(source)
//				.append(", provider=").append(provider)
//				.append(", providerRefId=").append(providerRefId)
//				.append(", currency=").append(currency)
//				.append(", value=").append(amount)
//				.append(", transactionFee=").append(transactionFee)
//				.append(", providerFee=").append(providerFee)
//				.append(", response=").append(response)
//				.append("]");
//		return builder.toString();
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((providerRefId == null) ? 0 : providerRefId.hashCode());
//		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Payment other = (Payment) obj;
//		if (providerRefId == null) {
//			if (other.providerRefId != null)
//				return false;
//		} else if (!providerRefId.equals(other.providerRefId))
//			return false;
//		if (transactionId == null) {
//			if (other.transactionId != null)
//				return false;
//		} else if (!transactionId.equals(other.transactionId))
//			return false;
//		return true;
//	}	
}
