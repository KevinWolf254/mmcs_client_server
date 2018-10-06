package co.ke.proaktiv.io.services.impl;

import java.util.Optional;
import java.util.Set;

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

import co.ke.proaktiv.io.configurations.AuthenticationFacadeInterface;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.response.AdminResponse;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.repository.UserRepository;
import co.ke.proaktiv.io.services.UserCredentialsService;
import co.ke.proaktiv.io.services.UserRoleService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AuthenticationFacadeInterface authentication;
	@Autowired
	private UserRoleService roleService;
	@Autowired
	private UserCredentialsService credService;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private RestTemplate restTemplate;
	private HttpHeaders header;
	private MultiValueMap<String, Object> parameters;
	
	@Value("${mmcs.aeon.url}")
	private String URI;
	
	@Override
	public Optional<User> findByEmail(final String email) {
		final Optional<User> user = repository.findByEmail(email);
		return user;
	}
	
	@Override
	public User save(final User user) {
		final User user_ = repository.save(user);	
		log.info("##### saved: "+user_);
		return user_;
	}

	@Override
	public Response save(String surname, String otherNames, String email, Role role, String password) {

		final Optional<User> _user = findByEmail(email);
		if(_user.isPresent())
			return new Response(400, "failed", "user already exists");
		
		final User user = getSignedInUser();
		final Organisation org = user.getOrganisation();

		final Response response = saveToApi(org.getId(), email, password);
		if(response.getCode() == 400)
			return response;
		
		final User user_ = save(new User(surname, otherNames, email, org));
		final String encodedPass = encoder.encode(password);
		final UserCredentials credentials = credService.save(new UserCredentials(Boolean.TRUE,
				encodedPass, user_)); 
		roleService.save(new UserRole(role, credentials));
		
		return response;
	}
	
	private AdminResponse saveToApi(final Long orgId, final String email, final String password) {
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		parameters = new LinkedMultiValueMap<String, Object>();
		parameters.add("email", email);
		parameters.add("password", password);

		HttpEntity<MultiValueMap<String, Object>> body = new 
				HttpEntity<MultiValueMap<String, Object>>(parameters, header);
		
		return restTemplate.exchange(URI + "/user/" + orgId, 
				HttpMethod.POST, body, AdminResponse.class).getBody();
	}
	
	@Override
	public User findById(Long id) {
		return repository.findById(id).get();
	}
	
	@Override
	public Optional<User> findById(User employee) {
		return repository.findById(employee.getId());
	}

	@Override
	public User getSignedInUser() {
		final User principal = (User) authentication.getAuthentication().getPrincipal();
		final String email = principal.getEmail();
		final User user = repository.findByEmail(email).get();
		return user;
	}

	@Override
	public Set<User> findByOrganisationId(final Long id) {
		final Set<User> users = repository.findByOrganisationId(id);
		return users;
	}

	@Override
	public Response delete(User user) {
		final ResponseEntity<Response> response = 
				restTemplate.exchange(URI + "/user/" + user.getEmail(), 
				HttpMethod.DELETE, null, Response.class);
		final Response response_ = response.getBody();
		if(response_.getCode() == 400)
			return response_;
		final UserCredentials cred = credService.findByUser(user);
		final Set<UserRole> roles = roleService.findByUserCredentials(cred);
		roles.stream().forEach(role->{
			roleService.delete(role);
		});
		credService.delete(cred);
		repository.delete(user);
		return response_;
	}	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
}
