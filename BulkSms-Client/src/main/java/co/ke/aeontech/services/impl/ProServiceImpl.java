package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import co.ke.aeontech.pojos.MpesaConfirmationBy;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.Payment;
import co.ke.aeontech.pojos.SenderIdentifier;
import co.ke.aeontech.pojos._Organisation;
import co.ke.aeontech.pojos.helpers.Reply;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.pojos.response.UnitsDetails;
import co.ke.aeontech.pojos.response.UnitsResponse;
import co.ke.aeontech.services.ProService;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.AdminCredentials;
import co.ke.proaktiv.io.pojos.CreditCharges;
import co.ke.proaktiv.io.pojos.SmsInfo;
import co.ke.proaktiv.io.pojos.pro.ShortCode;
import co.ke.proaktiv.io.pojos.pro._Payment;
import co.ke.proaktiv.io.pojos.reports.ReportRequest;
import co.ke.proaktiv.io.pojos.response.ClientResponse;
import co.ke.proaktiv.io.pojos.response.ShortCodeResponse;
import co.ke.proaktiv.io.pojos.response.SignUpResponse;

@Service
public class ProServiceImpl implements ProService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${mmcs.aeon.url}")
	private String URI;
//
//	@Override
//	public _Organisation getOrganizationInfo(Long org_no) {
//		return restTemplate.exchange(aeonURI + "/org/" + org_no, 
//				HttpMethod.GET,	null, _Organisation.class).getBody();
//	}
//	
//	@Override
//	public SenderIdentifier findSenderId(Long org_no) {
//		return restTemplate.exchange(aeonURI + "/senderId/" + org_no, 
//				HttpMethod.GET,	null, SenderIdentifier.class).getBody();
//	}
//
//	@Override
//	public UnitsDetails findOrgUnitsById(Long id) {		
//		return restTemplate.exchange(aeonURI + "/units/info/id" + id, 
//				HttpMethod.GET,	null, UnitsDetails.class).getBody();
//	}
//	
//	@Override
//	public UnitsDetails findOrgUnitsByEmail(String email) {		
//		return restTemplate.exchange(aeonURI + "/units/info/" + email, 
//				HttpMethod.GET,	null, UnitsDetails.class).getBody();
//	}
//
//	
//	/*
//	 * Warning: do not change 
//	 * being used by CustomUserDetails
//	 * during sign in to confirm 
//	 * if organization has been activated
//	 * 
//	 * @param email 
//	 */
	
//	
//	
//	@Override
//	public _Charges getCharges(Long org_number) {
//		return restTemplate.exchange(aeonURI + "/charges/" + org_number, 
//				HttpMethod.GET,	null, new ParameterizedTypeReference<_Charges>() {}).getBody();
//	}	
//	
//	@Async
//	@Override
//	public void subtractOrganisationUnits(final Long org_number, final BigDecimal expense) {		
//		restTemplate.exchange(aeonURI + "/units/subtract/" +expense+ "/" +org_number, HttpMethod.POST, 
//				null, new ParameterizedTypeReference<Object>() {});		
//	}
//	
//	@Override
//	public OnSendDeliveryReport sendSms(final SmsInfo smsInfo) {
//		header.setContentType(MediaType.APPLICATION_JSON);
//		
//		HttpEntity<SmsInfo> body = new 
//				HttpEntity<SmsInfo>(smsInfo, header);
//		
//		return restTemplate.exchange(aeonURI + "/senderId/contacts", HttpMethod.POST, 
//				body, new ParameterizedTypeReference<OnSendDeliveryReport>() {}).getBody();	 
//	}
//	
//	@Async
//	@Override
//	public void deleteUser(String email) {
//		restTemplate.exchange(aeonURI + "/delete/user/" + email, HttpMethod.DELETE, 
//				null, new ParameterizedTypeReference<Object>() {});			
//	}
//	
//	@Override
//	public List<Payment> getPurchases(Long org_no) {		
//		return restTemplate.exchange(aeonURI + "/payment/" + org_no, HttpMethod.GET,	
//				null, new ParameterizedTypeReference<List<Payment> >() {}).getBody();
//	}
//
//	@Override
//	public List<Payment> SearchBtw(Date from, Date to, Long id) {
//
//		final Dates searchParams = new Dates(from, to);
//		
//		header.setContentType(MediaType.APPLICATION_JSON);
//		
//		HttpEntity<Dates> body = new 
//				HttpEntity<Dates>(searchParams, header);
//		
//		return restTemplate.exchange(aeonURI + "/payment/btwn/" + id, HttpMethod.POST,	
//				body, new ParameterizedTypeReference<List<Payment> >() {}).getBody();
//	}
//
//	private static final Logger log = LoggerFactory.getLogger(ProServiceImpl.class);

}
