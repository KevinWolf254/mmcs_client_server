package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.Sms;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.SmsService;
import co.ke.proaktiv.io.services.UserService;

@RestController
public class SmsController {
	@Autowired
	private UserService userService;
	@Autowired
	private SmsService smsService;
	
	@PostMapping(value = "/secure/sms")
	public ResponseEntity<Object> send(@RequestBody Sms sms){
		Response response;
		final User user = userService.getSignedInUser();
		if(!sms.getGroupIds().isEmpty() || sms.getGroupIds() != null) 
			response = smsService.send(user, sms.getMessage(), sms.getGroupIds());
		else
			response = smsService.send(user, sms.getMessage());
		
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}	
}
