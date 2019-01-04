package co.ke.proaktiv.io.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import co.ke.proaktiv.io.configurations.RemoteServersProperties;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.EmailMessage;
import co.ke.proaktiv.io.pojos.SenderId;
import co.ke.proaktiv.io.pojos.SenderIdRequest;
import co.ke.proaktiv.io.pojos.pro.ShortCode;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.EmailService;
import co.ke.proaktiv.io.services.SenderIdService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class SenderIdServiceImpl implements SenderIdService {
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
    private RemoteServersProperties properties;
	@Autowired
	private RestTemplate restTemplate;	
	private HttpHeaders header;

	@Value("${spring.mail.username}")
	private String EMAIL;
	private static final Logger log = LoggerFactory.getLogger(SenderIdServiceImpl.class);

	@Override
	public ShortCode findByName(final String name) {		
		ResponseEntity<ShortCode> response = null;
		try {		
			response = restTemplate.exchange(properties.getApiServer() + "/senderId/byName/"+name, HttpMethod.GET, null, 
					new ParameterizedTypeReference<ShortCode>(){});
		} catch (RestClientException e) {
			log.info("RestClientException: "+e.getMessage());
			return new ShortCode();
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new ShortCode();
		} 
		return response.getBody();
	}
	
	@Override
	public Set<SenderId> findAllByCompanyId(final Long id) {
		ResponseEntity<Set<SenderId>> response = null;
		try {			
			response = restTemplate.exchange(properties.getApiServer() + "/senderId/"+id, HttpMethod.GET, null, 
					new ParameterizedTypeReference<Set<SenderId>>(){});
		} catch (RestClientException e) {
			log.info("RestClientException: "+e.getMessage());
			return new HashSet<SenderId>();
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new HashSet<SenderId>();
		} 
		return response.getBody();
	}

	@Override
	public Response save(final SenderIdRequest request) {
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Response> response = null;
		
		final HttpEntity<SenderIdRequest> body = new HttpEntity<SenderIdRequest>(request, header);
		try {			
			response = restTemplate.exchange(properties.getApiServer() + "/senderId", HttpMethod.POST, body, 
					new ParameterizedTypeReference<Response>(){});
			log.info("response: "+response.getBody());
		} catch (RestClientException e) {
			log.info("RestClientException: "+e.getMessage());
			return new Response(400, "", "Something happened");
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new Response(400, "", "Something happened");
		} 
		return response.getBody();
	}

	@Override
	public Response send(final MultipartFile msfile) {
		
		final User sender = userService.getSignedInUser();
		
		final EmailMessage email = new EmailMessage(sender.getEmail(), "SENDER ID APPLICATION", 
				"Find attached the sender id application form from: "+sender);
	    emailService.sendEmail(email, msfile);
	    return null;
	}

}
