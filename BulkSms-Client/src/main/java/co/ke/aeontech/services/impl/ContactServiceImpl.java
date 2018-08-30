package co.ke.aeontech.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import co.ke.aeontech.configurations.AuthenticationFacadeInterface;
import co.ke.aeontech.models.Contact;
import co.ke.aeontech.models.Group;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos._Contact;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.pojos.helpers.ServiceProvider;
import co.ke.aeontech.pojos.response.Response;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.repository.ContactRepository;
import co.ke.aeontech.services.ContactService;
import co.ke.aeontech.services.GroupService;
import co.ke.aeontech.services.OrganisationService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository repository;
	
	@Autowired
	private AuthenticationFacadeInterface authentication;
	@Autowired
	private OrganisationService orgService;
	@Autowired
	private GroupService groupService;

	@Override
	public Response saveNewContact(final _Contact contact, final Organisation organisation) {
		Contact new_contact = determineContactLocality(organisation, contact);
		repository.save(new_contact);
		return new ResponseSuccess("success", "successfully added new client");
	}
	
	@Override
	public Response saveExistingContact(final String country_code, final String phoneNumber, 
			final Long organisation_id) {
		final Organisation organisation = orgService.findById(organisation_id);

		final Contact contact = findByCodePhoneNo(country_code, phoneNumber);
		final Set<Organisation> contact_orgs = orgService.findByContactsId(contact.getId());
		contact_orgs.add(organisation);
		contact.setSuppliers(contact_orgs);
		repository.save(contact);
		return new ResponseSuccess("success", "successfully added new client");
	} 
	
	@Override
	public Response saveExistingContactToGroup(final String country_code, final String phoneNumber, 
			final Long organisation_id, final Long group_id) {
		final Organisation organisation = orgService.findById(organisation_id);
		final Group group = groupService.findById(group_id);
		
		final Contact contact = findByCodePhoneNo(country_code, phoneNumber);
		
		final Set<Organisation> contact_orgs = orgService.findByContactsId(contact.getId());
		final Set<Group> contact_groups = groupService.findByContactsId(contact.getId());
		contact_orgs.add(organisation);
		contact.setSuppliers(contact_orgs);
		contact_groups.add(group);
		contact.setGroups(contact_groups);
		repository.save(contact);
		return new ResponseSuccess("success", "successfully added client to group");
	}
		
	@Override
	public void uploadContacts(final MultipartFile csvfile) throws IOException {		
		
		final Long user_id = ((User) authentication.getAuthentication().getPrincipal()).getId();
		final Organisation organisation = orgService.findByEmployeesId(user_id);
		
		final Set<Contact> all_contacts = validateContacts(csvfile, organisation);
		
		//save the set of contacts to the database
		all_contacts.stream()
			.forEach(contact ->{
				final String phone = contact.getPhoneNumber();
				//check if contact already exists
				if(repository.findByCodePhoneNo(contact.getCountryCode(), phone) != null)				
					saveExistingContact(contact.getCountryCode(), phone, organisation.getId());
				else
					save(contact, organisation.getId());
			});
	}

	@Override
	public void uploadContacts(final MultipartFile csvfile, final Long groupId) throws IOException {
		final Long user_id = ((User) authentication.getAuthentication().getPrincipal()).getId();
		final Organisation organisation = orgService.findByEmployeesId(user_id);
		final Group group = groupService.findById(groupId);
		
		final Set<Contact> all_contacts = validateContacts(csvfile, organisation);
		
		//save the set of contacts to the database
		all_contacts.stream()
			.forEach(contact ->{
				final String phone = contact.getPhoneNumber();
				//check if contact already exists
				if(repository.findByCodePhoneNo(contact.getCountryCode(), phone) != null)				
					saveExistingContactToGroup(contact.getCountryCode(), phone, organisation.getId(), 
							group.getId());
				else
					saveToGroup(contact, organisation.getId(), group.getId());
			});
	}
	
	@Override
	public Set<Contact> validateContacts(final MultipartFile csvfile, final Organisation organisation)
			throws IOException, FileNotFoundException {
		final Set<Contact> all_contacts = new HashSet<Contact>();
		final File file = convertMultiPartToFile(csvfile);
		try (Reader reader = new FileReader(file);) {
			CsvToBean<_Contact> csvToBean = new CsvToBeanBuilder<_Contact>(reader)
					.withType(_Contact.class)
					.withFieldAsNull(CSVReaderNullFieldIndicator.NEITHER)
					.withIgnoreLeadingWhiteSpace(true)
					.withOrderedResults(false)
					.withSkipLines(0)
					.build();
			
			//If memory is not a problem, read using CsvToBean.parse(), 
			//which will read all beans at once and is multi_threaded.
			//If your memory is limited, use CsvToBean.iterator() and iterate over the input. 
			//Only one bean is read at a time, making multi_threading impossible and slowing down reading,

			List<_Contact> contactList = csvToBean.parse();			

			contactList.parallelStream()
				.filter(contact -> {
					if(!contact.getCountryCode().isEmpty() || contact.getCountryCode() != null || 
							!contact.getPhoneNumber().isEmpty() || contact.getPhoneNumber() != null)
						return true;
					return false;})
				.filter(contact -> {
					if(contact.getCountryCode().length() == 3)
						return true;
					return false;})
				.filter(contact -> {
					return contact.getCountryCode().startsWith("2");})
				.filter(contact -> {
					if(contact.getPhoneNumber().length() >= 8 && contact.getPhoneNumber().length() <=9)
						return true;
					return false;})
				.filter(contact ->{
					return (contact.getPhoneNumber().startsWith("7") || contact.getPhoneNumber().startsWith("6"));})
				.map(contact -> {
					final StringBuilder new_Country_Code = new StringBuilder();
					new_Country_Code.append("+")
							.append(contact.getCountryCode());
					contact.setCountryCode(new_Country_Code.toString());
					return contact;})
				.map(contact -> {
					return determineContactLocality(organisation, contact);
					})
				.forEach(new_contact ->{
					all_contacts.add(new_contact);
				}
			);
		}
		return all_contacts;
	}

	@Override
	public Contact determineContactLocality(final Organisation organisation, _Contact contact) {
		final String service_provider =  contact.getPhoneNumber().substring(0, 2).trim();
		final Contact new_contact;
		if(contact.getCountryCode().trim().equals("+250")) {
			if(service_provider.startsWith("73")) {
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
						organisation, ServiceProvider.AIRTEL_RW);
			}else
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
					organisation, ServiceProvider.GENERIC_RW);
		}
		else if(contact.getCountryCode().trim().equals("+254")) {
			if(service_provider.startsWith("73") || service_provider.startsWith("75") || 
					service_provider.startsWith("78")) {
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
						organisation, ServiceProvider.AIRTEL_KE);
			}else
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
						organisation, ServiceProvider.GENERIC_KE);
		}
		else if(contact.getCountryCode().trim().equals("+255")) {
			if(service_provider.startsWith("68") || service_provider.startsWith("69")) {
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
						organisation, ServiceProvider.AIRTEL_TZ);
			}else
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
					organisation, ServiceProvider.GENERIC_TZ);
		}
		else if(contact.getCountryCode().trim().equals("+256")) {
			if(service_provider.startsWith("75")) {
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
						organisation, ServiceProvider.AIRTEL_UG);
			}else
				new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
						organisation, ServiceProvider.GENERIC_UG);

		}else {
			new_contact = new Contact(contact.getCountryCode(),contact.getPhoneNumber().trim(), 
					organisation, ServiceProvider.OTHER);
		}
		return new_contact;
	} 
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
	/**
	 * to save new contacts with detached organization
	 */
	@Override
	public Contact save(Contact newClient, Long org_id) {
		final Organisation org = orgService.findById(org_id);
		return repository.save(new Contact(newClient.getCountryCode(), newClient.getPhoneNumber(), 
				org, newClient.getTeleCom()));
	}
	
	@Override
	public Contact saveToGroup(final Contact contact, final Long org_id, final Long group_id) {	
		final Organisation org = orgService.findById(org_id);
		final Group group = groupService.findById(group_id);
		return repository.save(new Contact(contact.getCountryCode(), contact.getPhoneNumber(), 
				contact.getTeleCom(), org, group));
	}
	
	@Override
	public Contact findByCodePhoneNo(String countryCode, String phoneNo) {
		return repository.findByCodePhoneNo(countryCode, phoneNo);
	}

	@Override
	public List<Contact> findBySuppliersId(Long org_id) {
		List<Contact> contacts = repository.findBySuppliersId(org_id);
		return contacts;
	}

	@Override
	public _ContactsTotals seperateContacts(Collection<Contact> contacts) {
		Set<Contact> rwf_Contacts = new HashSet<Contact>();
		Set<Contact> rwfAir_Contacts = new HashSet<Contact>();
		Set<Contact> kes_Contacts = new HashSet<Contact>();
		Set<Contact> kesAir_Contacts = new HashSet<Contact>();
		Set<Contact> tzs_Contacts = new HashSet<Contact>();
		Set<Contact> tzsAir_Contacts = new HashSet<Contact>();
		Set<Contact> ugx_Contacts = new HashSet<Contact>();
		Set<Contact> ugxAir_Contacts = new HashSet<Contact>();
		Set<Contact> other_Contacts = new HashSet<Contact>();
		
		contacts.parallelStream().forEach(contact -> {
			if(contact.getTeleCom().equals(ServiceProvider.GENERIC_RW))
				rwf_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.AIRTEL_RW))
				rwfAir_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.GENERIC_KE))
				kes_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.AIRTEL_KE))
				kesAir_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.GENERIC_TZ))
				tzs_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.AIRTEL_TZ))
				tzsAir_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.GENERIC_UG))
				ugx_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.AIRTEL_UG))
				ugxAir_Contacts.add(contact);
			else if(contact.getTeleCom().equals(ServiceProvider.OTHER))
				other_Contacts.add(contact);				
		});
		
		final long rwfContacts = rwf_Contacts.size();
		final long rwfAirContacts = rwfAir_Contacts.size();
		final long kesContacts = kes_Contacts.size();
		final long kesAirContacts = kesAir_Contacts.size();
		final long tzsContacts = tzs_Contacts.size();
		final long tzsAirContacts = tzsAir_Contacts.size();
		final long ugxContacts = ugx_Contacts.size();
		final long ugxAirContacts = ugxAir_Contacts.size();
		final long otherContacts = other_Contacts.size();
		_ContactsTotals response = new _ContactsTotals(rwfContacts, rwfAirContacts,
				kesContacts, kesAirContacts, tzsContacts, tzsAirContacts, ugxContacts, ugxAirContacts, 
				otherContacts);
		
		return response;
	}

	@Override
	public List<Contact> findByGroupsId(Long id) {
		return repository.findByGroupsId(id);
	}

	@Override
	public Response removeContactFromGroup(Long contactId, Long groupId) {
		final Contact contact = repository.findById(contactId).get();
		final Set<Group> groups = groupService.findByContactsId(contactId);
		final Set<Group> newGroups = groups.parallelStream()
			.filter(group -> {
				return (group.getId() != groupId);
			})
			.collect(Collectors.toSet()
		);
		contact.setGroups(newGroups);
		repository.save(contact);
		return new ResponseSuccess("success", "successfully removed client from group");
	}
}
