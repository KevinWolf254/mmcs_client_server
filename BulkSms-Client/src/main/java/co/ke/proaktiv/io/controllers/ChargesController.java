package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.pojos.ChargesReport;
import co.ke.proaktiv.io.pojos.Sms;
import co.ke.proaktiv.io.services.ChargesService;

@RestController
public class ChargesController {
	@Autowired
	private ChargesService chargesServices;
	
	@PostMapping(value = "/secure/charges")
	public ResponseEntity<Object> calculateCharges(@RequestBody final Sms sms){
		final ChargesReport report = chargesServices.calculate(sms);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
}
