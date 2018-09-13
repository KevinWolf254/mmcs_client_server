package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.UserCredentialsService;

@RestController
public class UserCredentialsController {
	@Autowired
	private UserCredentialsService credentialsService;

	@PutMapping(value = "/secure/credentials")
	public ResponseEntity<Object> update(
			@RequestParam("password") String password) {
		credentialsService.update(password);		
		return new ResponseEntity<Object>(new Response(200, "Success", 
				"successfully updated your credentials"), HttpStatus.OK);
	}
}
