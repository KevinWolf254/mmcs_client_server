package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import co.ke.aeontech.models.SenderIdentifier;
import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos._Charges;
import co.ke.aeontech.pojos.MpesaConfirmation;
import co.ke.aeontech.pojos.MpesaConfirmationBy;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.Payment;
import co.ke.aeontech.pojos.SmsInfo;
import co.ke.aeontech.pojos._Administrator;
import co.ke.aeontech.pojos._Organisation;
import co.ke.aeontech.pojos.helpers.Reply;
import co.ke.aeontech.pojos.requests.Dates;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.pojos.response.UnitsDetails;
import co.ke.aeontech.pojos.response.UnitsResponse;
import co.ke.aeontech.services.AeonService;

@Service
public class AeonServiceImpl implements AeonService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${mmcs.aeon.url}")
	private String aeonURI;
	
	@Override
	public _Organisation registerOrganisation(final _Organisation new_org, final _Administrator admin, final String senderId) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("name", new_org.getName());
		parameters.add("country", new_org.getCountry().toString());
		parameters.add("admin", admin.getEmail());
		parameters.add("senderId", senderId);
		
		parameters.add("code", admin.getCountryCode().toString());
		parameters.add("phoneNo", admin.getPhoneNo());
				
		HttpEntity<MultiValueMap<String, Object>> body = new 
				HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		ResponseEntity<_Organisation> response = restTemplate.exchange(aeonURI + "/register", 
				HttpMethod.POST, body, _Organisation.class);
		log.info("***** Successfully retrieved from Aeon API: "+response.getBody());
		return response.getBody();
	}

	@Override
	public _Organisation getOrganizationInfo(Long org_no) {
		return restTemplate.exchange(aeonURI + "/org/" + org_no, 
				HttpMethod.GET,	null, _Organisation.class).getBody();
	}
	
	@Override
	public SenderIdentifier findSenderId(Long org_no) {
		return restTemplate.exchange(aeonURI + "/senderId/" + org_no, 
				HttpMethod.GET,	null, SenderIdentifier.class).getBody();
	}
	
	@Override
	public boolean foundSenderId(String senderId) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("senderId", senderId);
		HttpEntity<MultiValueMap<String, Object>> body = new 
				HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		ResponseEntity<Boolean> response = restTemplate.exchange(aeonURI + "/senderId", 
				HttpMethod.POST, body, Boolean.class);
		return response.getBody();
	}

	@Override
	public UnitsDetails findOrgUnitsById(Long id) {		
		return restTemplate.exchange(aeonURI + "/units/info/id" + id, 
				HttpMethod.GET,	null, UnitsDetails.class).getBody();
	}
	
	@Override
	public UnitsDetails findOrgUnitsByEmail(String email) {		
		return restTemplate.exchange(aeonURI + "/units/info/" + email, 
				HttpMethod.GET,	null, UnitsDetails.class).getBody();
	}

	@Override
	public ResponseEntity<Object> confirmMpesaPayment(final MpesaConfirmationBy request) {
		
		final HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		final HttpEntity<MpesaConfirmation> body = new 
				HttpEntity<MpesaConfirmation>(request, header);
		
		final ResponseEntity<UnitsResponse> response = restTemplate
				.exchange(aeonURI + "/units", 
							HttpMethod.POST, body, UnitsResponse.class);
		
		if(response.getBody().getStatus() == Reply.FAILED)
			return new ResponseEntity<Object>(new ResponseError("failed", 
					response.getBody().getMessage()), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(new ResponseSuccess("success", 
				response.getBody().getMessage()), HttpStatus.OK);
	}
	
	/*
	 * Warning: do not change 
	 * being used by CustomUserDetails
	 * during sign in to confirm 
	 * if organization has been activated
	 * 
	 * @param email 
	 */
	@Override
	public boolean findOrganisationByEmail(String email) {		
		ResponseEntity<_Organisation> details = restTemplate.exchange(aeonURI + "/signin?email="+email, 
				HttpMethod.POST, null, _Organisation.class);
		return details.getBody().getEnabled();
	}
	
	@Async
	@Override
	public void saveUser(User user, String rawPassword) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("email", user.getEmail());
		parameters.add("password", rawPassword);

		HttpEntity<MultiValueMap<String, Object>> body = new 
				HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		restTemplate.exchange(aeonURI + "/add/user/" + user.getEmployer().getNumber(), HttpMethod.POST, 
				body, new ParameterizedTypeReference<Object>() {});		
	}
	
	@Override
	public _Charges getCharges(Long org_number) {
		return restTemplate.exchange(aeonURI + "/charges/" + org_number, 
				HttpMethod.GET,	null, new ParameterizedTypeReference<_Charges>() {}).getBody();
	}	
	
	@Async
	@Override
	public void subtractOrganisationUnits(final Long org_number, final BigDecimal expense) {		
		restTemplate.exchange(aeonURI + "/units/subtract/" +expense+ "/" +org_number, HttpMethod.POST, 
				null, new ParameterizedTypeReference<Object>() {});		
	}
	
	@Override
	public OnSendDeliveryReport sendSms(final SmsInfo smsInfo) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<SmsInfo> body = new 
				HttpEntity<SmsInfo>(smsInfo, header);
		
		return restTemplate.exchange(aeonURI + "/senderId/contacts", HttpMethod.POST, 
				body, new ParameterizedTypeReference<OnSendDeliveryReport>() {}).getBody();	 
	}
	
	@Async
	@Override
	public void deleteUser(String email) {
		restTemplate.exchange(aeonURI + "/delete/user/" + email, HttpMethod.DELETE, 
				null, new ParameterizedTypeReference<Object>() {});			
	}
	
	@Override
	public List<Payment> getPurchases(Long org_no) {		
		return restTemplate.exchange(aeonURI + "/payment/" + org_no, HttpMethod.GET,	
				null, new ParameterizedTypeReference<List<Payment> >() {}).getBody();
	}

	@Override
	public List<Payment> SearchBtw(Date from, Date to, Long id) {

		final Dates searchParams = new Dates(from, to);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Dates> body = new 
				HttpEntity<Dates>(searchParams, header);
		
		return restTemplate.exchange(aeonURI + "/payment/btwn/" + id, HttpMethod.POST,	
				body, new ParameterizedTypeReference<List<Payment> >() {}).getBody();
	}

	private static final Logger log = LoggerFactory.getLogger(AeonServiceImpl.class);

}
