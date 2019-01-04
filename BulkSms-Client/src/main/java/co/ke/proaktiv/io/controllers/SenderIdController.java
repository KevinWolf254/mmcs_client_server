package co.ke.proaktiv.io.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.ke.proaktiv.io.pojos.SenderId;
import co.ke.proaktiv.io.pojos.SenderIdRequest;
import co.ke.proaktiv.io.pojos.pro.ShortCode;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.SenderIdService;

@RestController
public class SenderIdController {
	@Autowired
	private SenderIdService senderIdService;

	@GetMapping(value = "/secure/senderId/{id}")
	public ResponseEntity<Object> sendSms(@PathVariable("id") final Long id){
		Set<SenderId> response = new HashSet<SenderId>();
		response = senderIdService.findAllByCompanyId(id);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	@GetMapping(value = "/secure/senderId/byName/{name}")
	public ResponseEntity<ShortCode> findByName(@PathVariable("name") final String name){
		final ShortCode shortCode = senderIdService.findByName(name);
		return new ResponseEntity<ShortCode>(shortCode, HttpStatus.OK);
	}
	@PostMapping(value = "/secure/senderId")
	public ResponseEntity<Object> save(@RequestBody final SenderIdRequest request){
		final Response response = senderIdService.save(request);
		if(response.getCode() != 200 || response.getCode() == 400)
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	@PostMapping(value = "/secure/senderId/form")
	public ResponseEntity<Object> submit(@RequestPart("file") final MultipartFile msWordDoc){
		final Response response = senderIdService.send(msWordDoc);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
