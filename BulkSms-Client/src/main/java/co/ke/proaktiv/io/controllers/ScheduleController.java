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

import co.ke.proaktiv.io.models.Group;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.pojos.Sms;
import co.ke.proaktiv.io.pojos._Schedule;
import co.ke.proaktiv.io.pojos._ScheduleDetails;
import co.ke.proaktiv.io.pojos.helpers.ScheduleStatus;
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
		
	@PostMapping(value = "/secure/schedule")
	public ResponseEntity<Object> save(@RequestBody Sms sms) {
		//check if schedule_name is empty
		if(sms.getSchedule().getName().isEmpty() || sms.getSchedule().getName() == null)
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		//retrieve the organization
		final Organisation org = userService.getSignedInUser().getOrganisation();
		
		final StringBuilder name = new StringBuilder(""+org.getId())
				.append("_")
				.append(sms.getSchedule().getName());
		
		final _Schedule schedule = sms.getSchedule();
		schedule.setName(name.toString());
		//check if schedule_name already exists
		final Optional<Schedule> schedule_ = scheduleService.findByName(name.toString());
		if(!schedule_.isPresent())
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		
		Schedule sched = null;
		if(sms.getGroupIds() == null) {
			final StringBuilder gName = new StringBuilder(""+org.getId())
				.append("_All_Contacts");
			final Group group = groupService.findByName(gName.toString()).get();
			sched = scheduleService.save(org.getName(), schedule, sms.getMessage(), group);
		}else {
			final Set<Group> groups = sms.getGroupIds().stream()
			.map(id->groupService.findById(id).get())
			.collect(Collectors.toSet());
			sched = scheduleService.save(org.getName(), schedule, sms.getMessage(), groups);
		}
		return new ResponseEntity<Object>(sched, HttpStatus.OK);
	}
	
	@PutMapping(value = "/secure/schedule/{name}/{status}")
	public ResponseEntity<Object> changeScheduleStatus(@PathVariable("name") String name,
			@PathVariable("status") ScheduleStatus status){
		Boolean success = null;
		
		if(status.equals(ScheduleStatus.SCHEDULED))
			success = jobService.start(name);
		else if(status.equals(ScheduleStatus.PAUSED))
			success = jobService.pause(name);
		else if(status.equals(ScheduleStatus.RUNNING))
			success = jobService.resume(name);
		else if(status.equals(ScheduleStatus.BLOCKED))
			success = jobService.stop(name);
		else if(status.equals(ScheduleStatus.NONE))
			success = jobService.delete(name);

		return new ResponseEntity<Object>(success, HttpStatus.OK);
	}
}
