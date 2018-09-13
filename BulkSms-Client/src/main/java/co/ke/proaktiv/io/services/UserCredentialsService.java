package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;

public interface UserCredentialsService {

	public UserCredentials save(UserCredentials credentials);

	public UserCredentials findByUser(User user);

	public UserCredentials findById(Long id);

	public UserCredentials update(String newPassword);

}
