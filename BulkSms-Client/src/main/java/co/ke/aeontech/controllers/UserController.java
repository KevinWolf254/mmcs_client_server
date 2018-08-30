package co.ke.aeontech.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos.helpers.Role;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.pojos.response._UserDetails;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AeonService aeonService;
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Value("${mmcs.aeon.url}")
	private String aeonURI;
	
	@GetMapping(value = "/secure/signin")
	public ResponseEntity<_UserDetails> signInUser() {
		User user = userService.getSignedInUser();
		user.getCredentials().setCurrentSignInDate(new Date());
		user.getCredentials().setSignedIn(true);
		userService.save(user);
		//set up the response
		_UserDetails response = new _UserDetails(user.getEmployer().getName(), user.getSurname(), 
				user.getOtherNames(), user.getEmail(), user.getCredentials().getRole(),
				user.getCredentials().isActive(), user.getCredentials().getCurrentSignInDate());
		return new ResponseEntity<_UserDetails>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/secure/users/create")
	public ResponseEntity<Object> createUser(
			@RequestParam("surname") String surname, 
			@RequestParam("otherNames") String otherNames,
			@RequestParam("email") String email,
			@RequestParam("role") String role, 
			@RequestParam("password") String password) {
		
		if(userService.findByEmail(email) != null)
			return new ResponseEntity<Object>(creationError, HttpStatus.BAD_REQUEST);
		
		User userDetails = userService.getSignedInUser();
		Organisation organisation = userDetails.getEmployer();
		userService.save(new User(surname, otherNames, email, organisation), role, password);
		return new ResponseEntity<>(creationSuccess, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/secure/users")
	public ResponseEntity<Object> getUsers() {
		final Long id = userService.getSignedInUser().getEmployer().getId();
		
		List<_UserDetails> response = new ArrayList<_UserDetails>();
		userService.findByEmployerId(id).parallelStream().forEach(user -> {
			response.add(new _UserDetails(user.getEmployer().getName(), user.getSurname(), 
				user.getOtherNames(), user.getEmail(), user.getCredentials().getRole(),
				user.getCredentials().isActive(), user.getCredentials().getCurrentSignInDate()));
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/secure/users")
	public ResponseEntity<Object> updateUser(
			@RequestParam("role") String role,
			@RequestParam("surname") String surname,
			@RequestParam("otherNames") String otherNames, 
			@RequestParam("email") String email, //not editable
			@RequestParam("isactive") Boolean status){
		
		User update = userService.findByEmail(email);

		if(update == null)
			return new ResponseEntity<Object>(updateError, HttpStatus.BAD_REQUEST);
		
		update.setSurname(surname);
		update.setOtherNames(otherNames);
		Role userRole = userService.getRole(role);
		update.getCredentials().setRole(userRole);
		update.getCredentials().setActive(status);
		User user = userService.save(update);
		
		//set up the response
		_UserDetails response = new _UserDetails(user.getEmployer().getName(), user.getSurname(), 
				user.getOtherNames(), user.getEmail(), user.getCredentials().getRole(),
				user.getCredentials().isActive(), user.getCredentials().getCurrentSignInDate());
		log.info("##### Updated: " +user);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/secure/users/{email}")
	public ResponseEntity<Object> deleteUser(@PathVariable("email") String email) {
		try {
			User exUser = userService.findByEmail(email);//.findById(id);
			log.info("##### Deleted: " +exUser);
			aeonService.deleteUser(exUser.getEmail());
			userService.delete(exUser);
		} catch (Exception e) {
			return new ResponseEntity<>(deletionError, HttpStatus.NOT_ACCEPTABLE);
		}		
		return new ResponseEntity<>(deletionSuccess, HttpStatus.OK);
	}


	private final static ResponseSuccess creationSuccess = new ResponseSuccess("success", "successfully created user and activation email send to them");
	private final static ResponseSuccess deletionSuccess = new ResponseSuccess("success","successfully deleted user");
	
	private final static ResponseError creationError = new ResponseError("failed", "user already exists");
	private final static ResponseError updateError = new ResponseError("failed", "could not update");
	private final static ResponseError deletionError = new ResponseError("failed", "could not delete user");
}
