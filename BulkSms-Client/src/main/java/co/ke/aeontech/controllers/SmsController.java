package co.ke.aeontech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.pojos._Charges;
import co.ke.aeontech.pojos.requests.Sms;
import co.ke.aeontech.pojos.response.Response;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.SmsService;
import co.ke.aeontech.services.UserService;

@RestController
public class SmsController {
	@Autowired
	private UserService userService;
	@Autowired
	private AeonService aeonService;
	@Autowired
	private SmsService smsService;
	
	@PostMapping(value = "/secure/sms")
	public ResponseEntity<Object> sendSms(@RequestBody Sms sms){
		Response response;
		//check if groupIds are present to determine if its a send to all 
		//or a send to group sms_
		if(sms.getGroupIds().isEmpty() || sms.getGroupIds() == null) {
			//send to all contacts for that organization
			response = smsService.sendSmsToAll(sms);
			if(response.getClass().equals(ResponseError.class))
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		else {
			response = smsService.sendSmsToGroup(sms);
			if(response.getClass().equals(ResponseError.class))
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	

	@GetMapping(value = "/secure/charges")
	public ResponseEntity<Object> getCharges(){
		final Long org_number = userService.getSignedInUser().getEmployer().getNumber();
		final _Charges charges = aeonService.getCharges(org_number);
		return new ResponseEntity<Object>(charges, HttpStatus.OK);
	}
}
