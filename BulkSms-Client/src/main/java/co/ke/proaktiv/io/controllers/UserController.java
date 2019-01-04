package co.ke.proaktiv.io.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.pojos.helpers.Role;
import co.ke.proaktiv.io.pojos.reports.UserReport;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.UserCredentialsService;
import co.ke.proaktiv.io.services.UserRoleService;
import co.ke.proaktiv.io.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserCredentialsService credService;
	@Autowired
	private UserRoleService roleService;
	
	@GetMapping(value = "/secure/user/signin")
	public ResponseEntity<Object> signIn() {
		return new ResponseEntity<Object>(userService.signIn(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/user/isauth")
	public ResponseEntity<Object> isAuth() {
		final Map<String, Boolean> isAuth = new HashMap<>();
		isAuth.put("isAuth", true);
		return new ResponseEntity<Object>(isAuth, HttpStatus.OK);
	}	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/secure/user")
	public ResponseEntity<Object> save(
			@RequestParam("surname") String surname, 
			@RequestParam("otherNames") String otherNames,
			@RequestParam("email") String email,
			@RequestParam("role") Role role, 
			@RequestParam("password") String password) {
		final Response response = userService.save(surname, otherNames, email, role, password);
		if(response.getCode() == 400)
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(new Response(200, "success", "successfully created user"), 
				HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/secure/user")
	public ResponseEntity<Object> findAll() {
		final Long id = userService.getSignedInUser().getOrganisation().getId();
		
		final List<UserReport> response = new ArrayList<UserReport>();
		userService.findByOrganisationId(id).stream().forEach(user -> {
			final UserCredentials cred = credService.findByUser(user);
			final Set<UserRole> roles = roleService.findByUserCredentials(cred);
			response.add(new UserReport(user, cred, roles));
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/secure/user")
	public ResponseEntity<Object> update(
			@RequestParam("roles") String role[],
			@RequestParam("surname") String surname,
			@RequestParam("otherNames") String otherNames, 
			@RequestParam("email") String email, //not editable
			@RequestParam("status") Boolean enabled){
		
		final Optional<User> _user = userService.findByEmail(email);

		if(!_user.isPresent())
			return new ResponseEntity<Object>(new Response(400, "failed", "user does not exist"), 
					HttpStatus.BAD_REQUEST);
		final User user = _user.get();
		user.setSurname(surname);
		user.setOtherNames(otherNames);
		final UserCredentials cred = credService.findByUser(user);	
		cred.setEnabled(enabled);
		
		final Set<UserRole> roles = roleService.findByUserCredentials(cred);
		for(int i = 0; i<= role.length; i++)
			roles.add(new UserRole(role[i]));
		userService.save(user);
		return new ResponseEntity<Object>(new Response(200, "success", "successfully updated user"), 
				HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/secure/user/{email}")
	public ResponseEntity<Object> delete(@PathVariable("email") String email) {
		final Optional<User> user = userService.findByEmail(email);
		if(!user.isPresent())
			return new ResponseEntity<>(new Response(400, "failed", "user may already be deleted"), 
					HttpStatus.BAD_REQUEST);
		final Response response = userService.delete(user.get());	
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
