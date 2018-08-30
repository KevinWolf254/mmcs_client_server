package co.ke.aeontech.services;

import java.util.List;
import java.util.Optional;

import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos.helpers.Role;

public interface UserService {

	public User findById(Long id);

	public Optional<User> findById(User employee);

	public User findByEmail(String email);

	public List<User> findByEmployerId(Long id);

	public void delete(User exUser);
	
	public User getSignedInUser();

	public User save(User user, String role, String password);

	public Role getRole(String role);

	public User save(User user);
}
