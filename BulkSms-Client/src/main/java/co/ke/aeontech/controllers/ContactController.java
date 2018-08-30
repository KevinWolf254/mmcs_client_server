package co.ke.aeontech.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import co.ke.aeontech.configurations.AuthenticationFacadeInterface;
import co.ke.aeontech.models.Contact;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos._Contact;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.pojos.response.AvailabilityResponse;
import co.ke.aeontech.pojos.response.Response;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.services.ContactService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.UserService;

@RestController
public class ContactController {

	@Autowired
	private UserService userService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private OrganisationService orgService;
	
	@Autowired
	private AuthenticationFacadeInterface authentication;
	
	@PostMapping(value = "/secure/contact")
	public ResponseEntity<Object> saveContact(@RequestBody final _Contact contactRequest){
				
		if(contactRequest.getCountryCode().isEmpty() || contactRequest.getCountryCode() == null || 
				contactRequest.getPhoneNumber().isEmpty() || contactRequest.getPhoneNumber() == null)
			return new ResponseEntity<Object>(errorIsNull, HttpStatus.BAD_REQUEST);
		if(contactRequest.getCountryCode().length() != 4)
			return new ResponseEntity<Object>(errorCCFormat, HttpStatus.BAD_REQUEST);
		if(!contactRequest.getCountryCode().startsWith("+2"))
			return new ResponseEntity<Object>(errorCCFormat, HttpStatus.BAD_REQUEST);
		if(contactRequest.getPhoneNumber().trim().length() < 8 || 
				contactRequest.getPhoneNumber().trim().length() > 9)
			return new ResponseEntity<Object>(errorPNFormat, HttpStatus.BAD_REQUEST);
		if (!(contactRequest.getPhoneNumber().trim().startsWith("7") || 
				contactRequest.getPhoneNumber().trim().startsWith("6")))
			return new ResponseEntity<Object>(errorPNFormat, HttpStatus.BAD_REQUEST);
		
		final Long user_id = ((User) authentication.getAuthentication().getPrincipal()).getId();
		final Organisation organisation = orgService.findByEmployeesId(user_id);
		
		final String code = contactRequest.getCountryCode();
		final String phoneNumber = contactRequest.getPhoneNumber();
		Response response = null;
		
		if(contactService.findByCodePhoneNo(code, phoneNumber) != null) {						
			response = contactService.saveExistingContact(code, phoneNumber, organisation.getId());
		}else
			response = contactService.saveNewContact(contactRequest, organisation);	
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/contacts")
	public ResponseEntity<Object> saveContacts(
			@RequestPart("file") final MultipartFile csvfile) throws IOException{
		try {
			contactService.uploadContacts(csvfile);
		} catch (MultipartException e) {
			log.error("##### multi-part error: "+e.getMessage());
			return new ResponseEntity<Object>(errorMultiPart, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(successAddingClients, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/contact/{groupId}")
	public ResponseEntity<Object> saveContactToGroup( 
							@PathVariable("groupId") final Long group_id,
							@RequestBody final _Contact request){
		final Long user_id = ((User) authentication.getAuthentication().getPrincipal()).getId();
		final Organisation organisation = orgService.findByEmployeesId(user_id);
		final Contact contact = contactService.findByCodePhoneNo(request.getCountryCode(), 
				request.getPhoneNumber());
		
		if(contact != null) {
			contactService.saveExistingContactToGroup(request.getCountryCode(), 
					request.getPhoneNumber(), organisation.getId(), group_id);
		}else {
			Contact new_contact = contactService.determineContactLocality(organisation, request);
			contactService.saveToGroup(new_contact, organisation.getId(), group_id);
		}
		return new ResponseEntity<Object>(successAddingClients, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/contacts/{groupId}")
	public ResponseEntity<Object> saveContactsToGroup(
			@RequestPart("file") final MultipartFile csvfile, 
			@PathVariable("groupId") final Long id) throws IOException{
		try {
			contactService.uploadContacts(csvfile, id);
		} catch (MultipartException e) {
			log.info("##### multi-part error: "+e.getMessage());
			return new ResponseEntity<Object>(errorMultiPart, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(successAddingClients, HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/contact")
	public ResponseEntity<Object> getContacts(){
		Long id = userService.getSignedInUser().getEmployer().getId();
		List<Contact> contacts = contactService.findBySuppliersId(id);
		_ContactsTotals seperatedContacts = contactService.seperateContacts(contacts);
		return new ResponseEntity<Object>(seperatedContacts, HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/contact/{code}/{phone}")
	public ResponseEntity<Object> checkContactExists(
			@PathVariable("code") final String country_code, 
			@PathVariable("phone") final String phone_no){
		final Contact contact = contactService.findByCodePhoneNo(country_code, phone_no);
		if(contact != null)
			return new ResponseEntity<Object>(new AvailabilityResponse(true), HttpStatus.OK);
		return new ResponseEntity<Object>(new AvailabilityResponse(false), HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/contacts/{id}")
	public ResponseEntity<Object> findByGroupId(@PathVariable("id") Long id){
		final List<Contact> contacts = contactService.findByGroupsId(id);
		return new ResponseEntity<Object>(contacts, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/contact/remove")
	public ResponseEntity<Object> removeContactFromGroup(
			@RequestParam("ContactId") Long contact_id,
			@RequestParam("GroupId") Long group_id){
		final Response response = contactService.removeContactFromGroup(contact_id, group_id);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	private static final Logger log = LoggerFactory.getLogger(ContactController.class);
	
	private final static ResponseSuccess successAddingClients = new ResponseSuccess("success",
			"successfully added clients");
	private final static ResponseError errorIsNull = new ResponseError("failed",
			"country code or phone number can't be empty");
	private final static ResponseError errorCCFormat = new ResponseError("failed",
			"country code is invalid");
	private final static ResponseError errorPNFormat = new ResponseError("failed",
			"phone number is invalid");
	private final static ResponseError errorMultiPart = new ResponseError("failed",
			"request is not a multipart request");

}
