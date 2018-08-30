package co.ke.aeontech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.services.UserCredentialsService;

@RestController
public class UserCredentialsController {
	@Autowired
	private UserCredentialsService credentialsService;

	@PostMapping(value = "/secure/credentials/change")
	public ResponseEntity<Object> changePassword(
			@RequestParam("NewPassword") String newPassword) {
		credentialsService.changePassword(newPassword);		
		return new ResponseEntity<Object>(passwordChanged, HttpStatus.OK);
	}
	
	private final static ResponseSuccess passwordChanged = new ResponseSuccess("success","successfully changed password");
}
