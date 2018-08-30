package co.ke.aeontech.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.ke.aeontech.configurations.AuthenticationFacadeInterface;
import co.ke.aeontech.models.User;
import co.ke.aeontech.models.UserCredentials;
import co.ke.aeontech.pojos.helpers.Role;
import co.ke.aeontech.repository.UserRepository;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.UserCredentialsService;
import co.ke.aeontech.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AuthenticationFacadeInterface authentication;
	@Autowired
	private UserCredentialsService credService;
	@Autowired
	private AeonService aeonService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findById(Long id) {
		return repository.findById(id).get();
	}
	
	@Override
	public Optional<User> findById(User employee) {
		return repository.findById(employee.getId());
	}

	@Override
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public User save(User _user, String role, String password) {
		
		User user = repository.save(_user);		
		String encryptedPassword = passwordEncoder.encode(password);
		Role userRole = getRole(role);
		credService.save(new UserCredentials(userRole, encryptedPassword, user));
		
		aeonService.saveUser(user, password);
		return user;
	}
	
	@Override
	public Role getRole(String role) {
		Role userRole;
		if(role.trim().equals(Role.ADMIN.getRole().trim())) {
			userRole = Role.ADMIN;
		}else {
			userRole = Role.USER;
		}
		return userRole;
	}

	@Override
	public List<User> findByEmployerId(Long id) {
		return repository.findByEmployerId(id);
	}

	@Async
	@Override
	public void delete(User user) {
		repository.delete(user);		
	}

	@Override
	public User getSignedInUser() {
		User userDetails = repository
				.findByEmail(((User) authentication.getAuthentication().getPrincipal()).getEmail());
		return userDetails;
	}
}
