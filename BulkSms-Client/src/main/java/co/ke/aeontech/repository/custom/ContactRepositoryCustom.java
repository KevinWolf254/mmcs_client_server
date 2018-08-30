package co.ke.aeontech.repository.custom;

import javax.transaction.Transactional;

import co.ke.aeontech.models.Contact;

public interface ContactRepositoryCustom {
	
	@Transactional
	public Contact findByCodePhoneNo(String countryCode, String phoneNo);
}
