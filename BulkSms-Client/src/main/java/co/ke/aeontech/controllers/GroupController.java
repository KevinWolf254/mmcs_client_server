package co.ke.aeontech.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.Group;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.pojos.requests.GroupedContacts;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.services.GroupService;

@RestController
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@GetMapping(value = "/secure/group")
	public ResponseEntity<Object> getGroups(){
		List<Group> groups = groupService.findByOrganisationId();
		return new ResponseEntity<Object>(groups, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/group")
	public ResponseEntity<Object> createGroup(@RequestParam("name") String name) {
		final boolean found = groupService.search(name);
		if(found)
			return new ResponseEntity<Object>(alreadyExistsError, HttpStatus.BAD_REQUEST);
		groupService.saveGroup(name);
		return new ResponseEntity<>(creationSuccess, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/group/search")
	public ResponseEntity<Object> searchGroup(@RequestParam("name") String name) {
		final boolean found = groupService.search(name);
		if(found)
			return new ResponseEntity<Object>(alreadyExistsError, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(canCreateSuccess, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/group/contacts")
	public ResponseEntity<_ContactsTotals> getGroupedContacts(@RequestBody GroupedContacts request) {
		final _ContactsTotals response = groupService.getGroupedContacts(request.getGroupIds());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/secure/group/{id}")
	public ResponseEntity<Object> deleteGroup(@PathVariable("id") Long id){
		groupService.deleteGroup(id);
		return new ResponseEntity<>(deleteSuccess, HttpStatus.OK);
	}
	
	private final static ResponseSuccess creationSuccess = new ResponseSuccess("success",
			"successfully created group");	
	private final static ResponseSuccess canCreateSuccess = new ResponseSuccess("success",
			"group can be created");	
	private final static ResponseSuccess deleteSuccess = new ResponseSuccess("success",
			"group was deleted");
	private final static ResponseError alreadyExistsError = new ResponseError("failed", "group already exists");


}
