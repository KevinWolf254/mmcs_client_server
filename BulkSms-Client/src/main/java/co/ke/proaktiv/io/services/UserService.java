package co.ke.proaktiv.io.services;

import java.util.Optional;
import java.util.Set;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.reports.SignInReport;
import co.ke.proaktiv.io.pojos.response.Response;

public interface UserService {

	/**
	 * 
	 * @return SignInReport
	 */
	public SignInReport signIn();
	/**
	 * 
	 * @param id
	 * @return User
	 */
	public User findById(Long id);
	/**
	 * 
	 * @param employee
	 * @return
	 */
	public Optional<User> findById(User employee);
	/**
	 * 
	 * @param email
	 * @return Optional<User>
	 */
	public Optional<User> findByEmail(String email);
	/**
	 * 
	 * @param id
	 * @return Set<User>
	 */
	public Set<User> findByOrganisationId(Long id);
	/**
	 * 
	 * @return User
	 */
	public User getSignedInUser();
	/**
	 * 
	 * @param user
	 * @return User
	 */
	public User save(User user);
	/**
	 * 
	 * @param surname
	 * @param otherNames
	 * @param email
	 * @param role
	 * @param password
	 * @return Response
	 */
	public Response save(String surname, String otherNames, 
			String email, Role role, String password);
	/**
	 * 
	 * @param user
	 * @return Response
	 */ 
	public Response delete(User user);
}
