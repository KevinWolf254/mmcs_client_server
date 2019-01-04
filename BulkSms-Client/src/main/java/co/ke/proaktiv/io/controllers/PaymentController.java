package co.ke.proaktiv.io.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.pojos.pro._Payment;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.PaymentService;

@RestController
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping(value = "/secure/payment")
	public ResponseEntity<Object> confirm(@RequestBody final _Payment payment) {
		final Response response = paymentService.confirm(payment);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
