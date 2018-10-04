package co.ke.proaktiv.io.pojos;

import com.opencsv.bean.CsvBindByName;

public class Subscriber_ {
	
	@CsvBindByName
	private String code;	
	@CsvBindByName
	private String number;

	public Subscriber_() {
	}
	public String getCode() {
		return code;
	}
	public void setCode(String countryCode) {
		this.code = countryCode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String phoneNumber) {
		this.number = phoneNumber;
	}
	@Override
	public String toString() {
		return "Subscriber_ [code=" + code + ", number=" + number + "]";
	}	
}
