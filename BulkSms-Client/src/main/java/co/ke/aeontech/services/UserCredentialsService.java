package co.ke.aeontech.services;

import org.springframework.scheduling.annotation.Async;

import co.ke.aeontech.models.UserCredentials;

public interface UserCredentialsService {

	@Async
	public void save(UserCredentials credentials);

	public void changePassword(String newPassword);

}
