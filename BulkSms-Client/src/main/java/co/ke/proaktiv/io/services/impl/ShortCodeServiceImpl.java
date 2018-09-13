package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import co.ke.proaktiv.io.pojos.response.ShortCodeResponse;
import co.ke.proaktiv.io.services.ShortCodeService;

public class ShortCodeServiceImpl implements ShortCodeService{
	
	@Autowired
	private RestTemplate restTemplate;	
	
	private HttpHeaders header;
	private MultiValueMap<String, Object> parameters;

	@Value("${mmcs.aeon.url}")
	private String URI;
	
	@Override
	public ShortCodeResponse findByName(String name) {
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("name", name);
		
		final HttpEntity<MultiValueMap<String, Object>> body = new 
				HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		ResponseEntity<ShortCodeResponse> api_response = restTemplate.exchange(URI + "/shortcode", 
				HttpMethod.POST, body, ShortCodeResponse.class);
		
		final ShortCodeResponse response = api_response.getBody();
		return response;
	}

	@Override
	public boolean exists(String name) {
		final ShortCodeResponse response = findByName(name);
		if(response.getCode() == 400)
			return false;
		return true;
	}

}
