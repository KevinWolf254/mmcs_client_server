package co.ke.proaktiv.io.controllers;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.pojos.SubscriberReport;
import co.ke.proaktiv.io.pojos.Subscriber_;
import co.ke.proaktiv.io.pojos.reports.GroupIds;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.SubscriberService;
import co.ke.proaktiv.io.services.UserService;

@RestController
public class SubscriberController {

	@Autowired
	private UserService userService;
	@Autowired
	private SubscriberService contactService;
	@Autowired
	private GroupService groupService;
	
	@GetMapping(value = "/secure/subscriber/{id}")
	public ResponseEntity<Object> findByGroupId(@PathVariable("id") final Long id){
		final Set<Subscriber> contacts = contactService.findByGroupsId(id);
		return new ResponseEntity<Object>(contacts, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscribers/")
	public ResponseEntity<Object> findByGroupIds(@RequestBody final GroupIds groupIds) {
		final Set<Subscriber> response = contactService.findByGroupsIds(groupIds.getGroupIds());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber")
	public ResponseEntity<Object> save(@RequestBody final Subscriber_ subscriber){
		if(!contactService.validate(subscriber))
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		final Subscriber response = contactService.save(subscriber);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscribers")
	public ResponseEntity<Object> save(@RequestPart("file") final MultipartFile csvfile){
		final Set<Subscriber> subs = contactService.save(csvfile);
		return new ResponseEntity<Object>(subs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber/{groupId}")
	public ResponseEntity<Object> save(@RequestBody final Subscriber_ sub, 
			@PathVariable("groupId") final Long id){

		final Optional<Group_> group = groupService.findById(id);
		if(!contactService.validate(sub) || !group.isPresent())
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		final Subscriber response = contactService.save(sub, group.get());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscribers/{groupId}")
	public ResponseEntity<Object> save(@RequestPart("file") final MultipartFile csvfile, 
			@PathVariable("groupId") final Long id){
		
		final Optional<Group_> group = groupService.findById(id);
		if(!group.isPresent())
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		final Set<Subscriber>  response = contactService.save(csvfile, group.get());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/subscriber")
	public ResponseEntity<Object> findAll(){
		final Organisation org = userService.getSignedInUser().getOrganisation();
		final Set<Group_> groups = groupService.findByOrganisationId(org.getId());
		final Set<Long> groupIds = groups.stream()
				.map(group -> group.getId())
				.collect(Collectors.toSet());
		final Set<Subscriber> subs = contactService.findByGroupsIds(groupIds);
		final Set<SubscriberReport> report = contactService.createReport(subs);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/contact/{code}/{phone}")
	public ResponseEntity<Object> exists(@PathVariable("code") final String code, 
			@PathVariable("phone") final String phone_no){		
		
		final String provider = phone_no.substring(0, 2);
		final String number = phone_no.substring(3);
		final boolean exists = contactService.exists(code, provider, number);
		return new ResponseEntity<Object>(exists, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/contact/remove")
	public ResponseEntity<Object> delete(@RequestParam("ContactId") final Long contact_id,
			@RequestParam("GroupId") final Long group_id){
		
		final Response response = contactService.delete(contact_id, group_id);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
