package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.response.CreditResponse;
import co.ke.proaktiv.io.services.CreditService;
import co.ke.proaktiv.io.services.UserService;

@RestController
public class CreditController {
	
	@Autowired
	private CreditService creditService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/secure/credit")
	public ResponseEntity<Object> getUnits() {	
		final User user = userService.getSignedInUser();
		final CreditResponse response = creditService.findByEmail(user.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
