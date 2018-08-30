package co.ke.aeontech.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.User;
import co.ke.aeontech.models.UserCredentials;
import co.ke.aeontech.pojos._Administrator;
import co.ke.aeontech.pojos._Organisation;
import co.ke.aeontech.pojos.helpers.Role;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.Response;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.repository.OrganisationRepository;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.UserCredentialsService;
import co.ke.aeontech.services.UserService;

@Service
public class OrganisationServiceImpl implements OrganisationService{
	@Autowired
	private OrganisationRepository repository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserCredentialsService credService;
	@Autowired
	private AeonService aeonService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${mmcs.aeon.url}")
	private String aeonURI;

	@Override
	public Organisation findById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Organisation findBySchedulesId(Long id) {
		return repository.findBySchedulesId(id);
	}
	
	@Override
	public Response register(_Organisation org, _Administrator admin, String senderId) {
		
		if(userService.findByEmail(admin.getEmail()) != null) {
			return registrationError;
		}
		if(repository.findByName(org.getName()) != null) {
			return alreadyExists;
		}
		if(aeonService.foundSenderId(senderId)) {
			return senderIdError;
		}
		_Organisation _organisation = aeonService.registerOrganisation(org, admin, senderId);

		Organisation organisation = save(new Organisation(_organisation.getId(), _organisation.getName()));
		User user = userService.save(new User(admin.getSurname(), admin.getOtherNames(), admin.getEmail(), 
				organisation));
		
		credService.save(new UserCredentials(Role.ADMIN, passwordEncoder.encode(admin.getPassword()), user));
		
		return registrationSuccess;
	}
	
	@Override
	public Organisation findByEmployeesId(Long id) {
		return repository.findByEmployeesId(id);
	}

	@Override
	public Organisation save(Organisation org) {
		return repository.save(org);
	}
	
	private final static ResponseSuccess registrationSuccess = new ResponseSuccess("success", "registration was successful, check email to activate account");
	private final static ResponseError registrationError = new ResponseError("failed", "email already exists");
	private final static ResponseError alreadyExists = new ResponseError("failed", "organisation already exists");
	private final static ResponseError senderIdError = new ResponseError("failed", "the sender id has already been taken");

	@Override
	public Set<Organisation> findByContactsId(Long id) {
		// TODO Auto-generated method stub
		return repository.findByContactsId(id);
	}

	@Override
	public Organisation findByName(String orgName) {
		return repository.findByName(orgName);
	}
}
