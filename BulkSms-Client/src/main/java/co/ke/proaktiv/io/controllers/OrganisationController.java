package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.pojos.AdminRole;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.response.SignUp;
import co.ke.proaktiv.io.services.CountryService;
import co.ke.proaktiv.io.services.OrganisationService;

@RestController
public class OrganisationController {

	@Autowired
	private OrganisationService orgService;
	@Autowired
	private CountryService countryService;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<Object> save(	@RequestParam("surname") String surname,
			@RequestParam("otherNames") String otherNames, @RequestParam("organisation") String name,			
			@RequestParam("country") String c_name, @RequestParam("code") String code,	
			@RequestParam("phoneNo") String phoneNo, @RequestParam("email") String email, 
			@RequestParam("password") String password){

		final Country country = countryService.findByName(c_name);
		final Client client = new Client(country, name);
		
		final StringBuilder phoneNo_ = new StringBuilder()
				.append(code.trim())
				.append(phoneNo.trim());
		final AdminRole admin = new AdminRole(email, phoneNo_.toString().trim(), 
				surname, otherNames, password, Role.ADMIN);
		
		final SignUp response = orgService.save(client, admin);
		
		if(response.getCode() == 400) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}