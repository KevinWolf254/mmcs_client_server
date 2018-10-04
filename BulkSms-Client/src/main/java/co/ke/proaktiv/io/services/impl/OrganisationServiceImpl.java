package co.ke.proaktiv.io.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.pojos.AdminRole;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.response.Credit;
import co.ke.proaktiv.io.pojos.response.SignUp;
import co.ke.proaktiv.io.repository.OrganisationRepository;
import co.ke.proaktiv.io.services.CountryService;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.OrganisationService;
import co.ke.proaktiv.io.services.UserCredentialsService;
import co.ke.proaktiv.io.services.UserRoleService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class OrganisationServiceImpl implements OrganisationService{
	@Autowired
	private OrganisationRepository repository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private CountryService countryService;	
	@Autowired
	private UserCredentialsService credService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RestTemplate restTemplate;
	private MultiValueMap<String, Object> parameters;
	private HttpHeaders header;
	private HttpEntity<MultiValueMap<String, Object>> body;

	@Value("${mmcs.aeon.url}")
	private String URI;

	@Override
	public Optional<Organisation> findById(final Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Optional<Organisation> findByName(final String orgName) {
		return repository.findByName(orgName);
	}
	
	@Override
	public SignUp save(final Client client, final AdminRole user) {
		
		final Optional<User> user_ = userService.findByEmail(user.getEmail());
		if(user_.isPresent()) 
			return new SignUp(400, "failed", "user already exist");
		
		final Optional<Organisation> client_ = repository.findByName(client.getName());
		if(client_.isPresent()) 
			return new SignUp(400, "failed", "Organization already exist");
	
		final SignUp report = saveToApi(client, user);
		if(report.getCode() == 400)
			return report;
		
		log.info("response: "+report);
		
		final Client savedClient = report.getClient();
		
		final String countryName = report.getClient().getCountry().getName();
		final Country country = countryService.findByName(countryName);
		
		final Organisation org = save(new Organisation(savedClient.getId(), 
				savedClient.getName(), country));

		groupService.save(new Group_("All_Contacts", org));
		
		final User newUser = userService.save(new User(user.getSurname(), user.getOtherNames(), 
				user.getEmail(), org));
		
		final UserCredentials credentials = new UserCredentials(passwordEncoder
				.encode(user.getPassword()), newUser);
		credentials.setEnabled(Boolean.TRUE);
		final UserCredentials cred = credService.save(credentials);
		
		userRoleService.save(new UserRole(Role.ADMIN, cred));
		
		return report;
	}

	private SignUp saveToApi(final Client client, final AdminRole user) {

		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("client", client.getName());
		parameters.add("country", client.getCountry().getName());
		parameters.add("admin", user.getEmail());
		parameters.add("phoneNo", user.getPhoneNo());
				
		body = new HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		final ResponseEntity<SignUp> response = restTemplate.exchange(URI + "/client", 
				HttpMethod.POST, body, SignUp.class);
		return response.getBody();
	}
	
	@Override
	public Organisation save(final Organisation organisation) {
		final Organisation org = repository.save(organisation);
		log.info("##### saved: "+org);
		return org;
	}
	
	@Override
	public boolean isEnabled(final String email) {	
		final Credit client = getClient(email);
		if(client.getCode() == 400)
			return false;
		
		return client.getClient().isEnabled();
	}
	
	@Override
	public Credit getClient(final String email) {
		header = new HttpHeaders();		
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("email", email);
		
		body = new HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		final ResponseEntity<Credit> response = restTemplate
				.exchange(URI + "/user", 
				HttpMethod.POST, body, Credit.class);
		final Credit client = response.getBody();
		return client;
	}	
	private static final Logger log = LoggerFactory.getLogger(OrganisationServiceImpl.class);
}
