package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

	public UserCredentials findByUser(User user);
	
	public UserCredentials findByUserId(Long id);
}
