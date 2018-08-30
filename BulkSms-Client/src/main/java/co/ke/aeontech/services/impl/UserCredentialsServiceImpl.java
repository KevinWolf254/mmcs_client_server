package co.ke.aeontech.services.impl;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.ke.aeontech.configurations.AuthenticationFacade;
import co.ke.aeontech.models.User;
import co.ke.aeontech.models.UserCredentials;
import co.ke.aeontech.repository.UserCredentialsRepository;
import co.ke.aeontech.services.UserCredentialsService;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{

	@Autowired
	private UserCredentialsRepository userCredRepository;
//	private static final Logger log = LoggerFactory.getLogger(UserCredentialsService.class);

	@Autowired
	private AuthenticationFacade authentication;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void save(UserCredentials credentials) {
		userCredRepository.save(credentials);		
	}
	
	@Override
	public void changePassword(final String newPassword) {
		final User currentUser = (User) authentication.getAuthentication().getPrincipal();
		//retrieve user's credentials
		final UserCredentials cred = currentUser.getCredentials();
		final String encodedNewPass = encoder.encode(newPassword);
		//change password
		cred.setPassword(encodedNewPass);
		userCredRepository.save(cred);
	}
		
}
