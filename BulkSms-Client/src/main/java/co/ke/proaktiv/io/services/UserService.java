package co.ke.proaktiv.io.services;

import java.util.Optional;
import java.util.Set;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.response.Response;

public interface UserService {

	public User findById(Long id);

	public Optional<User> findById(User employee);

	public Optional<User> findByEmail(String email);

	public Set<User> findByOrganisationId(Long id);
	
	public User getSignedInUser();

	public User save(User user);
	
	public Response save(String surname, String otherNames, 
			String email, Role role, String password);

	public Response delete(User exUser);
}
