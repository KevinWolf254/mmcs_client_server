package co.ke.proaktiv.io.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.repository.UserRoleRepository;
import co.ke.proaktiv.io.services.UserRoleService;

public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepository repository;
	
	@Override
	public UserRole save(UserRole role) {
		final UserRole userRole = repository.save(role);
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

}
