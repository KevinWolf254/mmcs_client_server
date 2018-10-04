package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import co.ke.proaktiv.io.pojos.CreditCharges;
import co.ke.proaktiv.io.services.CreditService;
import co.ke.proaktiv.io.services.UserService;

public class ChargesController {
	@Autowired
	private UserService userService;
	@Autowired
	private CreditService creitService;
	
	@GetMapping(value = "/secure/charges")
	public ResponseEntity<Object> getCharges(){
		final Long id = userService.getSignedInUser().getOrganisation().getId();
		final CreditCharges charges = creitService.findByOrganisationId(id);
		return new ResponseEntity<Object>(charges, HttpStatus.OK);
	}
}
