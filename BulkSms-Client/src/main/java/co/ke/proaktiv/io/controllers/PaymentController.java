package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.pojos.pro._Payment;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.PaymentService;

@RestController
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@PutMapping(value = "/secure/payment")
	public ResponseEntity<Object> confirm(@RequestBody _Payment payment) {
		final Response response = paymentService.confirm(payment);
		if(response.getCode() == 400)
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
