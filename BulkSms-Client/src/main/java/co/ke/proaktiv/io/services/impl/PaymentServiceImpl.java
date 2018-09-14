package co.ke.proaktiv.io.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import co.ke.proaktiv.io.pojos.Sale;
import co.ke.proaktiv.io.pojos.pro._Payment;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private RestTemplate restTemplate;	
	
	private HttpHeaders header;
	private MultiValueMap<String, Object> parameters;

	@Value("${mmcs.aeon.url}")
	private String URI;

	@Override
	public Response confirm(final _Payment payment) {
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<_Payment> body = new HttpEntity<_Payment>(payment, header);
		
		final ResponseEntity<Response> response = restTemplate
				.exchange(URI + "/sale", HttpMethod.POST, 
						body, Response.class);
		return response.getBody();
	}

	@Override
	public List<Sale> search(Date from, Date to, Long id) {		
		header = new HttpHeaders();		
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("from", from);
		parameters.add("to", to);
		parameters.add("id", id);

		final HttpEntity<MultiValueMap<String, Object>> body = 
				new HttpEntity<MultiValueMap<String, Object>>(parameters, header);

		
		return restTemplate.exchange(URI + "/sale/find", HttpMethod.POST,	
				body, new ParameterizedTypeReference<List<Sale> >() {}).getBody();	
	}
}
