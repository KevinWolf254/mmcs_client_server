package co.ke.proaktiv.io.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	public Set<UserRole> findByCredentials(UserCredentials cred);
	
	public Set<UserRole> findByCredentialsId(Long id);
}
