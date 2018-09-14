package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
		
		final ResponseEntity<CreditResponse> response =  
				restTemplate.exchange(URI + "/credit/" + email, 
				HttpMethod.GET,	null, CreditResponse.class);
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
