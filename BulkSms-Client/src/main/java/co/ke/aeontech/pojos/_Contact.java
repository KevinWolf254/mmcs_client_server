package co.ke.aeontech.pojos;

import com.opencsv.bean.CsvBindByName;

public class _Contact {
	
	@CsvBindByName
	private String countryCode;
	
	@CsvBindByName
	private String phoneNumber;

	public _Contact() {
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "_Contact [countryCode=" + countryCode + ", phoneNumber=" + phoneNumber + "]";
	}
	
}
