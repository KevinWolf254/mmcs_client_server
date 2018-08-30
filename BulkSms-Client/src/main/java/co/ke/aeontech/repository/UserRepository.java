package co.ke.aeontech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

	public List<User> findByEmployerId(Long id);
}
