package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import co.ke.proaktiv.io.pojos.CreditCharges;
import co.ke.proaktiv.io.pojos.response.CreditResponse;
import co.ke.proaktiv.io.services.CreditService;

@Service
public class CreditServiceImpl implements CreditService {
	@Autowired
	private RestTemplate restTemplate;	

	@Value("${mmcs.aeon.url}")
	private String URI;
	
	@Override
	public CreditResponse findByEmail(String email) {
		final HttpHeaders header = new HttpHeaders();
		final MultiValueMap<String, Object> parameters = 
				new LinkedMultiValueMap<String, Object>();
		
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);		
		parameters.add("email", email);

		final HttpEntity<MultiValueMap<String, Object>> body = 
				new HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		final ResponseEntity<CreditResponse> response =  
				restTemplate.exchange(URI + "/credit", 
				HttpMethod.POST, body, CreditResponse.class);
		return response.getBody();
	}

	@Override
	public CreditCharges findByOrganisationId(Long id) {
		final ResponseEntity<CreditCharges> response =  
				restTemplate.exchange(URI + "/charges/" + id, 
				HttpMethod.GET,	null, CreditCharges.class);
		return response.getBody();
	}

}
