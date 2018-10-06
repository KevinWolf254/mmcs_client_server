package co.ke.proaktiv.io.controllers;

import java.util.Optional;
import java.util.Set;

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
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.ServiceProviderReport;
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
	private SubscriberService subscriberService;
	@Autowired
	private GroupService groupService;
	
	@GetMapping(value = "/secure/subscriber/{id}")
	public ResponseEntity<Object> findByGroupId(@PathVariable("id") final Long id){
		final Set<Subscriber> subscribers = subscriberService.findByGroupsId(id);
		return new ResponseEntity<Object>(subscribers, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber/groups")
	public ResponseEntity<Object> findByGroupsId(@RequestBody final GroupIds groupIds) {
		final Set<Subscriber> subs = subscriberService.findByGroupsId(groupIds.getGroupIds());
		final Set<ServiceProviderReport> report = subscriberService.createReport(subs);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber")
	public ResponseEntity<Object> save(@RequestBody final Subscriber_ subscriber){
		if(!subscriberService.isValid(subscriber))
			return new ResponseEntity<Object>(new Response(400, "failed", "invalid format"), 
					HttpStatus.BAD_REQUEST);
		final Subscriber response = subscriberService.save(subscriber);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber/file")
	public ResponseEntity<Object> save(@RequestPart("file") final MultipartFile csvfile){
		final Set<Subscriber> subs = subscriberService.save(csvfile);
		return new ResponseEntity<Object>(subs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber/{groupId}")
	public ResponseEntity<Object> save(@RequestBody final Subscriber_ sub, 
			@PathVariable("groupId") final Long id){

		final Optional<Group_> group = groupService.findById(id);
		if(!subscriberService.isValid(sub) || !group.isPresent())
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		final Subscriber response = subscriberService.save(sub, group.get());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber/file/{groupId}")
	public ResponseEntity<Object> save(@RequestPart("file") final MultipartFile csvfile, 
			@PathVariable("groupId") final Long id){
		
		final Optional<Group_> group = groupService.findById(id);
		if(!group.isPresent())
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		final Set<Subscriber>  response = subscriberService.save(csvfile, group.get());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/subscriber")
	public ResponseEntity<Object> findAll(){
		final User user = userService.getSignedInUser();
		final Set<ServiceProviderReport> report = subscriberService.findAllByUser(user);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/subscriber/{code}/{phone}")
	public ResponseEntity<Object> exists(@PathVariable("code") final String code, 
			@PathVariable("phone") final String phone_no){		
		
		final String provider = phone_no.substring(0, 3);
		final String number = phone_no.substring(3);
		final StringBuilder builder = new StringBuilder(code)
				.append(provider).append(number);
		final Optional<Subscriber> sub = subscriberService.findByFullPhoneNo(builder.toString());
		final boolean exists = sub.isPresent();
		return new ResponseEntity<Object>(exists, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/subscriber/remove")
	public ResponseEntity<Object> delete(@RequestParam("ContactId") final Long contact_id,
			@RequestParam("GroupId") final Long group_id){
		
		final Response response = subscriberService.delete(contact_id, group_id);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
