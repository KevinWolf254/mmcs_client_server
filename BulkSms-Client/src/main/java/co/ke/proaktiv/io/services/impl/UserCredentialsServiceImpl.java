package co.ke.proaktiv.io.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.repository.UserCredentialsRepository;
import co.ke.proaktiv.io.services.UserCredentialsService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{

	@Autowired
	private UserCredentialsRepository repository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserCredentials save(UserCredentials credentials) {
		final UserCredentials cred = repository.save(credentials);
		log.info("##### saved: "+cred);
		return cred;
	}
	@Override
	public UserCredentials findByUser(final User user) {
		final UserCredentials cred = repository.findByUser(user);	
		return cred;
	}
	@Override
	public UserCredentials findById(final Long id){
		final UserCredentials cred = repository.findByUserId(id);	
		return cred;
	}
	
	@Override
	public UserCredentials update(final String newPassword) {
		final User user = userService.getSignedInUser();
		//retrieve user's credentials
		final UserCredentials cred = findByUser(user);
		final String encodedNewPass = encoder.encode(newPassword);
		//change password
		cred.setPassword(encodedNewPass);
		return save(cred);
	}
private static final Logger log = LoggerFactory.getLogger(UserCredentialsService.class);

}
