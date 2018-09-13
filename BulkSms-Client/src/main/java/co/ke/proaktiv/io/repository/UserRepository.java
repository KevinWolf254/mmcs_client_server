package co.ke.proaktiv.io.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public  Optional<User>  findByEmail(String email);

	public Set<User> finByOrganisationId(Long id);
}
