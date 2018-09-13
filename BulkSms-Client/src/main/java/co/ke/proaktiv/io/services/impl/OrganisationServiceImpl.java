package co.ke.proaktiv.io.services.impl;

import java.util.Optional;

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

import co.ke.proaktiv.io.models.Group;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.pojos.AdminRole;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.response.ClientResponse;
import co.ke.proaktiv.io.pojos.response.SignUpResponse;
import co.ke.proaktiv.io.repository.OrganisationRepository;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.OrganisationService;
import co.ke.proaktiv.io.services.ShortCodeService;
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
	private ShortCodeService shortCodeService;	
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
	public Optional<Organisation> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Optional<Organisation> findByName(String orgName) {
		return repository.findByName(orgName);
	}
	
	@Override
	public SignUpResponse save(final Client client, final AdminRole user, final String sc_name) {
		
		final Optional<User> user_ = userService.findByEmail(user.getEmail());
		if(!user_.isPresent()) 
			return new SignUpResponse(400, "failed", "user already exist");
		
		final Optional<Organisation> client_ = repository.findByName(client.getName());
		if(!client_.isPresent()) 
			return new SignUpResponse(400, "failed", "Organization already exist");
		
		if(shortCodeService.exists(sc_name)) 
			return new SignUpResponse(400, "failed", "Sender Id already exist");
	
		final SignUpResponse report = create(client, user, sc_name);
		if(report.getCode() == 400)
			return report;
		
		final Client savedClient = report.getClient();
		final Organisation org = save(new Organisation(savedClient.getId(), 
				savedClient.getName()));
		
		groupService.save(new Group(org.getId()+"_All_Contacts", org));
		
		final User newUser = userService.save(new User(user.getSurname(), user.getOtherNames(), 
				user.getEmail(), org));
		
		final UserCredentials cred = credService.save(new UserCredentials(passwordEncoder
				.encode(user.getPassword()), newUser));
		
		userRoleService.save(new UserRole(Role.ADMIN, cred));
		
		return report;
	}

	private SignUpResponse create(Client client, AdminRole user, String sc_name) {

		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("client", client.getName());
		parameters.add("country", client.getCountry().toString());
		parameters.add("admin", user.getEmail());
		parameters.add("shortCode", sc_name);		
		parameters.add("phoneNo", user.getPhoneNo());
				
		body = new HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		final ResponseEntity<SignUpResponse> response = restTemplate.exchange(URI + "/client", 
				HttpMethod.POST, body, SignUpResponse.class);
		return response.getBody();
	}
	
	@Override
	public Organisation save(final Organisation organisation) {
		final Organisation org = repository.save(organisation);
		return org;
	}
	
	@Override
	public boolean isEnabled(final String email) {	
		header = new HttpHeaders();
		parameters = new LinkedMultiValueMap<String, Object>();
		
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		parameters.add("email", email);
		
		body = new HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		final ResponseEntity<ClientResponse> response = restTemplate
				.exchange(URI + "/admin/signin", 
				HttpMethod.POST, body, ClientResponse.class);
		final ClientResponse client = response.getBody();
		if(client.getCode() == 400)
			return false;
		
		return client.getClient().isEnabled();
	}	
}
