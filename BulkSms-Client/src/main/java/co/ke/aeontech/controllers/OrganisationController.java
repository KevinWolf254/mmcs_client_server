package co.ke.aeontech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos.MpesaConfirmation;
import co.ke.aeontech.pojos.MpesaConfirmationBy;
import co.ke.aeontech.pojos._Administrator;
import co.ke.aeontech.pojos._Organisation;
import co.ke.aeontech.pojos.helpers.Country;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.Response;
import co.ke.aeontech.pojos.response.UnitsDetails;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.UserService;

@RestController
public class OrganisationController {

	@Autowired
	private OrganisationService orgService;
	@Autowired
	private AeonService aeonService;
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<Object> register( 
			@RequestParam("surname") String surname, 
			@RequestParam("otherNames") String otherNames,
			@RequestParam("organisation") String org_name,			
			@RequestParam("country") Country country,		
			@RequestParam("code") Country country_code,	
			@RequestParam("phoneNo") String phoneNo, 
			@RequestParam("email") String email, 
			@RequestParam("senderId") String senderId, 
			@RequestParam("password") String password){

		_Organisation org = new _Organisation(country, org_name);
		_Administrator admin = new _Administrator(surname, otherNames, email, country_code, phoneNo, password);
		
		Response response = orgService.register(org, admin, senderId);
		if(response.getClass() == ResponseError.class) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/secure/units")
	public ResponseEntity<Object> getUnits() {	
		User user = userService.getSignedInUser();
		UnitsDetails unitsDetails = aeonService.findOrgUnitsByEmail(user.getEmail());
		return new ResponseEntity<>(unitsDetails, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/units")
	public ResponseEntity<Object> addUnits(@RequestBody MpesaConfirmation request) {
		User user = userService.getSignedInUser();

		return aeonService.confirmMpesaPayment(new MpesaConfirmationBy(user.getEmail(), request));
	}

}