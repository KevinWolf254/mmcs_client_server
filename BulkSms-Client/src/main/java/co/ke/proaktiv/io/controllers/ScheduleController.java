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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.pojos.CampaignRequest;
import co.ke.proaktiv.io.pojos.ScheduleReport;
import co.ke.proaktiv.io.pojos.Sms;
import co.ke.proaktiv.io.pojos._Schedule;
import co.ke.proaktiv.io.pojos._ScheduleDetails;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.JobScheduleService;
import co.ke.proaktiv.io.services.ScheduleService;
import co.ke.proaktiv.io.services.UserService;

@RestController
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private JobScheduleService jobService;

	@GetMapping(value = "/secure/schedule")
	public ResponseEntity<Object> getAllSchedules(){
		final Organisation organisation = userService.getSignedInUser().getOrganisation();

		final Set<_ScheduleDetails> list = scheduleService.findAll(organisation.getName());
		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/schedule/{name}")
	public ResponseEntity<Object> getSchedule(@PathVariable("name")final String name){
		final Organisation organisation = userService.getSignedInUser().getOrganisation();
		
		final StringBuilder name_ = new StringBuilder(""+organisation.getId())
				.append("_")
				.append(name);
		
		final Optional<Schedule> schedule = scheduleService.findByName(name_.toString());
		
		if(schedule.isPresent())
			return new ResponseEntity<Object>(new Response(200,"success","exists"), HttpStatus.OK);
		return new ResponseEntity<Object>(new Response(400,"failed","non existant"), HttpStatus.OK);
	}
	
	@GetMapping(value = "/secure/schedule/details/{name}")
	public ResponseEntity<Object> getScheduleDetails(@PathVariable("name")final String name){	
		final ScheduleReport report = scheduleService.getScheduleDetails(name);
		if(report.getCode() == 400)
			return new ResponseEntity<Object>( report, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>( report, HttpStatus.OK);
	}
	
	@PostMapping(value = "/secure/schedule")
	public ResponseEntity<Object> save(@RequestBody final Sms sms) {
		//check if schedule_name is empty
		if(sms.getSchedule().getName().isEmpty())
			return new ResponseEntity<Object>(new Response(400, "failed", "invalid request"),
					HttpStatus.BAD_REQUEST);
		//retrieve the organization
		final Organisation org = userService.getSignedInUser().getOrganisation();
		
		final StringBuilder name = new StringBuilder(""+org.getId())
				.append("_")
				.append(sms.getSchedule().getName());
		
		final _Schedule schedule = sms.getSchedule();
		schedule.setName(name.toString());
		//check if schedule_name already exists
		final Optional<Schedule> schedule_ = scheduleService.findByName(name.toString());
		if(schedule_.isPresent())
			return new ResponseEntity<Object>(new Response(400, "failed", "schedule name already exists"),
					HttpStatus.BAD_REQUEST);
		
		Schedule sched = null;
		if(sms.getGroupIds().isEmpty()) {
			final StringBuilder gName = new StringBuilder(""+org.getId())
				.append("_All_Contacts");
			final Group_ group = groupService.findByName(gName.toString()).get();
			sched = scheduleService.save(org.getName(), schedule, sms.getMessage(), group);
		}else {
			final Set<Group_> groups = sms.getGroupIds().stream()
			.map(id->groupService.findById(id).get())
			.collect(Collectors.toSet());
			sched = scheduleService.save(org.getName(), schedule, sms.getMessage(), groups);
		}
		return new ResponseEntity<Object>(sched, HttpStatus.OK);
	}
	
	@PutMapping(value = "/secure/schedule")
	public ResponseEntity<Object> update(@RequestBody final CampaignRequest request){
		final Boolean isSuccessful = runCommand(request);
		if(!isSuccessful)
			return new ResponseEntity<Object>(new Response(400, "failed","failed to run campaign manully"), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(new Response(200, "success","successfully run campaign manully"), HttpStatus.OK);
	}

	private Boolean runCommand(final CampaignRequest request) {
		if(request.getCommand().equals("START"))
			return jobService.start(request.getCampaignName());
		if(request.getCommand().equals("DELETE"))
			return jobService.delete(request.getCampaignName());
		return false;
	}
}
