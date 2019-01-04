package co.ke.proaktiv.io.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.pojos.response.GroupResponse;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.UserService;

@RestController
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/secure/group")
	public ResponseEntity<Object> getGroups(){
		final Long id = userService.getSignedInUser().getOrganisation().getId();
		final Set<Group_> groups = groupService.findByOrganisationId(id);
		return new ResponseEntity<Object>(groups, HttpStatus.OK);
	}
	@PostMapping(value = "/secure/group")
	public ResponseEntity<Object> save(@RequestParam("name") final String name) {
		final Optional<Group_> group = groupService.findByName(name);
		if(group.isPresent())
			return new ResponseEntity<Object>(new Response(400, "failed", "kindly try another name"), 
					HttpStatus.BAD_REQUEST);
		final Organisation organisation = userService.getSignedInUser().getOrganisation();
		final Group_ group_ = groupService.save(new Group_(name, organisation));
		return new ResponseEntity<>(new GroupResponse(200, "success", "created group successfully", 
				group_), HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/group/{name}")
	public ResponseEntity<Object> getGroup(@PathVariable("name") final String name) {
		Group_ group_ = null;
		final Optional<Group_> group = groupService.findByName(name);
		if(group.isPresent())
			group_ = group.get();
		return new ResponseEntity<Object>(group_, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/secure/group/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id){
		final Response response = groupService.delete(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}