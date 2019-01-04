package co.ke.proaktiv.io.services.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.repository.UserRoleRepository;
import co.ke.proaktiv.io.services.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepository repository;
	private static final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	@Override
	public UserRole save(UserRole details) {
		UserRole userRole = null;
		try {
			userRole= repository.save(details);
		} catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new UserRole();

		}		
		return userRole;
	}

	@Override
	public Set<UserRole>   findByUserCredentials(UserCredentials cred) {
		final Set<UserRole> roles = repository.findByCredentials(cred);
		return roles;
	}

	@Override
	public Set<UserRole>  findByUserCredentialsId(Long id) {
		final Set<UserRole> roles = repository.findByCredentialsId(id);
		return roles;
	}

	@Override
	public void delete(UserRole role) {
		repository.delete(role);
	}
}
