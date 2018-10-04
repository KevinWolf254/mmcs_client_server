package co.ke.proaktiv.io.services;

import java.util.Set;

import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;

public interface UserRoleService {

	public UserRole save(UserRole role);
	
	public Set<UserRole> findByUserCredentials(UserCredentials cred);
	
	public Set<UserRole>  findByUserCredentialsId(Long id);

	public void delete(UserRole role);
}
