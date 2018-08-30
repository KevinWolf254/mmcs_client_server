package co.ke.aeontech.controllers;

import java.util.Set;
import java.util.concurrent.ExecutionException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.pojos._ScheduleDetails;
import co.ke.aeontech.pojos.helpers.ScheduleStatus;
import co.ke.aeontech.pojos.requests.Sms;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.services.JobScheduleService;
import co.ke.aeontech.services.ScheduleService;
import co.ke.aeontech.services.UserService;

@RestController
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private UserService userService;
	@Autowired
	private JobScheduleService jobService;
	
//	@GetMapping(value = "/secure/schedule")
//	public ResponseEntity<Object> getSchedule(){
//		final List<Schedule> response = scheduleService.findByOrganisationId();
//		return new ResponseEntity<Object>(response, HttpStatus.OK);
//	}	
	@GetMapping(value = "/secure/schedule")
	public ResponseEntity<Object> getAllSchedules(){
		final Organisation organisation = userService.getSignedInUser().getEmployer();

		final Set<_ScheduleDetails> list = scheduleService.getAllSchedules(organisation.getName());
		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}
	
//	@GetMapping(value = "/secure/schedule/{name}")
//	public ResponseEntity<AvailabilityResponse> isAvailable(@PathVariable("name") String name){
//		final Organization organization = userService.getSignedInUser().getEmployer();
//		final String scheduleName = organisation.getNumber()+"_"+name;
//		Schedule schedule = scheduleService.findByName(scheduleName);
//		if(schedule != null) {
//			return new ResponseEntity<AvailabilityResponse>(new AvailabilityResponse(false), HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<AvailabilityResponse>(new AvailabilityResponse(true), HttpStatus.OK);
//	}
	
	@PostMapping(value = "/secure/schedule")
	public ResponseEntity<Object> saveSchedule(@RequestBody Sms sms) throws 
	InterruptedException, ExecutionException{
		//check if schedule_name is empty
		if(sms.getSchedule().getName().isEmpty() || sms.getSchedule().getName() == null)
			return new ResponseEntity<Object>(nullError, HttpStatus.BAD_REQUEST);
		//retrieve the organization
		final Organisation organisation = userService.getSignedInUser().getEmployer();
		final String scheduleName = organisation.getNumber()+"_"+sms.getSchedule().getName();
		//check if schedule_name already exists
		if(scheduleService.findByName(scheduleName) != null)
			return new ResponseEntity<Object>(existsError, HttpStatus.BAD_REQUEST);
		scheduleService.saveSchedule(sms, scheduleName, organisation);		
		return new ResponseEntity<Object>(creationSuccess, HttpStatus.OK);
	}
	
	@PutMapping(value = "/secure/schedule/{name}/{status}")
	public ResponseEntity<Object> changeScheduleStatus(@PathVariable("name") String name,
			@PathVariable("status") ScheduleStatus status){
		Boolean success = null;
		
		//NB: should receive email 
		if(status.equals(ScheduleStatus.SCHEDULED))//start now job
			success = jobService.startJobSchedule(name);
		else if(status.equals(ScheduleStatus.PAUSED))//pause job
			success = jobService.pauseJobSchedule(name);
		else if(status.equals(ScheduleStatus.RUNNING))//resume job
			success = jobService.resumeJobSchedule(name);
		else if(status.equals(ScheduleStatus.BLOCKED))//stop job
			success = jobService.stopJobSchedule(name);
		else if(status.equals(ScheduleStatus.NONE))//delete job
			success = jobService.deleteJobSchedule(name);
		if(!success)
			return new ResponseEntity<Object>(changingStatusSError, HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<Object>(changingStatusSuccess, HttpStatus.OK);
	}
	private final static ResponseSuccess creationSuccess = new 
			ResponseSuccess("success", "successfully created schedule");
	private final static ResponseSuccess changingStatusSuccess = new 
			ResponseSuccess("success", "successfully changed schedule");
	private final static ResponseError existsError = new 
			ResponseError("failed", "schedule already exists");
	private final static ResponseError nullError = new 
			ResponseError("failed", "schedule name is required");
	private final static ResponseError changingStatusSError = new 
			ResponseError("failed", "something went wrong with request");
//	private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

}
