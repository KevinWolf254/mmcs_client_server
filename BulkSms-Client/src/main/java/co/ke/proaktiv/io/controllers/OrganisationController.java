package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.pojos.AdminRole;
import co.ke.proaktiv.io.pojos.helpers.Country;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.response.SignUpResponse;
import co.ke.proaktiv.io.services.OrganisationService;

@RestController
public class OrganisationController {

	@Autowired
	private OrganisationService orgService;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<Object> save(	@RequestParam("surname") String surname,
			@RequestParam("otherNames") String otherNames, @RequestParam("organisation") String name,			
			@RequestParam("country") Country country, @RequestParam("code") Country code,	
			@RequestParam("phoneNo") String subscriberNo, @RequestParam("email") String email, 
			@RequestParam("shortCode") String sc_name, @RequestParam("password") String password){

		final Client client = new Client(country, name);
		
		final StringBuilder phoneNo = new StringBuilder("+")
				.append(code.getCountryCode())
				.append(subscriberNo);
		final AdminRole admin = new AdminRole(email, phoneNo.toString(), 
				surname, otherNames, password, Role.ADMIN);
		
		final SignUpResponse response = orgService.save(client, admin, sc_name);
		
		if(response.getCode() == 400) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}