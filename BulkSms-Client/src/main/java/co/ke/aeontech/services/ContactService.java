package co.ke.aeontech.services;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import co.ke.aeontech.models.Contact;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.pojos._Contact;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.pojos.response.Response;

public interface ContactService {
		
	public Contact findByCodePhoneNo(String countryCode, String phoneNo);
	
	public List<Contact> findBySuppliersId(Long Id);
	
	public List<Contact> findByGroupsId(Long id);

	public Set<Contact> validateContacts(MultipartFile csvfile, Organisation organisation)
			throws IOException, FileNotFoundException;

	public _ContactsTotals seperateContacts(Collection<Contact> contacts);
	
	public void uploadContacts(MultipartFile csvfile) throws IOException;

	public void uploadContacts(MultipartFile csvfile, Long groupId) throws IOException;

	public Contact save(Contact newClient, Long org_id);
	
	public Response saveNewContact(_Contact contactRequest, Organisation organisation);

	public Contact saveToGroup(Contact contact, Long org_id, Long group_id);
	
	public Response saveExistingContact(String country_code, String phoneNumber,Long organisation_id);

	public Response saveExistingContactToGroup(String country_code, String phoneNumber, Long organisation_id, 
			Long group_id);
	
	public Response removeContactFromGroup(final Long contactId, final Long groupId);
	
	public Contact determineContactLocality(Organisation organisation, _Contact contact);

}
