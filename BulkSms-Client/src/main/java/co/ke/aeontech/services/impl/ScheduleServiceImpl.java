package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Contact;
import co.ke.aeontech.models.DeliveryReport;
import co.ke.aeontech.models.Group;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Schedule;
import co.ke.aeontech.models.Text;
import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.SmsInfo;
import co.ke.aeontech.pojos._Charges;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.pojos._ScheduleDetails;
import co.ke.aeontech.pojos.helpers.Country;
import co.ke.aeontech.pojos.helpers.ScheduleStatus;
import co.ke.aeontech.pojos.helpers.ScheduleType;
import co.ke.aeontech.pojos.requests.Sms;
import co.ke.aeontech.pojos.response.UnitsDetails;
import co.ke.aeontech.quartz.jobs.CronJob;
import co.ke.aeontech.quartz.jobs.CronToGroupJob;
import co.ke.aeontech.quartz.jobs.SimpleJob;
import co.ke.aeontech.quartz.jobs.SimpleToGroupJob;
import co.ke.aeontech.repository.ScheduleRepository;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.ContactService;
import co.ke.aeontech.services.DeliveryReportService;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.GroupService;
import co.ke.aeontech.services.JobScheduleService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.ScheduleService;
import co.ke.aeontech.services.SmsService;
import co.ke.aeontech.services.TextService;
import co.ke.aeontech.services.UserService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository repository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private TextService textService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private JobScheduleService jobScheduleService;
	@Autowired
	private OrganisationService orgService;
	@Autowired
	private AeonService aeonService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private DeliveryReportService reportService;
	
	@Autowired
	@Lazy
	SchedulerFactoryBean schedulerFactoryBean;
	
	@Override
	public Schedule findByName(final String schedule_name) {
		return repository.findByName(schedule_name);
	}

	@Override
	public List<Schedule> findByOrganisationId() {
		Long id = userService.getSignedInUser().getEmployer().getId();
		return repository.findByOrganisationId(id);
	}

	@Override
	public void saveSchedule(final Sms sms, final String scheduleName, final Organisation organisation) throws 
	InterruptedException, ExecutionException {
		//retrieve the schedule
		final Schedule schedule;
		if(sms.getGroupIds().isEmpty() || sms.getGroupIds() == null) {
			schedule = processToAllSchedules(sms, scheduleName, organisation);
		}else {
			schedule = processToGroupSchedules(sms, scheduleName, organisation);
		}		
		//retrieve the schedule's date
		final Date date = schedule.getDate();	
		//save the text
		textService.saveText(new Text(sms.getMessage(), schedule));
		//check if schedule is send to all or to group
		if(schedule.getGroups() == null || schedule.getGroups().isEmpty()) {
			saveToAllSchedule(schedule.getName(), organisation.getName(), date, schedule.getCronExpression());
		}else {
			saveToGroupSchedule(schedule.getName(), organisation.getName(), date, schedule.getCronExpression());
		}		
	}
	
	private Schedule processToGroupSchedules(final Sms sms, final String scheduleName, final Organisation organisation) 
			throws InterruptedException, ExecutionException {
		final Set<Long> groupIds = sms.getGroupIds();
		//retrieve list of groups and add to new schedule
		final CompletableFuture<List<Group>> groups = groupService.findAllById(groupIds);
		//send to grouped contacts schedules
		if(sms.getSchedule().getType() == ScheduleType.DAILY) {
			return repository.save(new Schedule(scheduleName, ScheduleType.DAILY, sms.getSchedule().getDate(), 
					organisation, groups.get()));
		}
		if(sms.getSchedule().getType() == ScheduleType.DATE) {
			return repository.save(new Schedule(scheduleName, ScheduleType.DATE, sms.getSchedule().getDate(), 
					organisation, groups.get()));
		}
		if(sms.getSchedule().getType() == ScheduleType.WEEKLY) {
			return repository.save(new Schedule(scheduleName, sms.getSchedule().getDate(), sms.getSchedule().getDayOfWeek(), 
					 sms.getSchedule().getCronExpression(), organisation, groups.get()));
		}
		if(sms.getSchedule().getType() == ScheduleType.MONTHLY) {
			return repository.save(new Schedule(scheduleName, sms.getSchedule().getDate(), sms.getSchedule().getDayOfMonth(), 
					sms.getSchedule().getCronExpression(), organisation, groups.get()));
		}
		return null;
	}

	private Schedule processToAllSchedules(final Sms sms, final String scheduleName, final Organisation organisation) {
		//send to all schedules
		if(sms.getSchedule().getType() == ScheduleType.DAILY) {
			return repository.save(new Schedule(scheduleName, ScheduleType.DAILY, sms.getSchedule().getDate(), 
					organisation));
		}
		if(sms.getSchedule().getType() == ScheduleType.DATE) {
			return repository.save(new Schedule(scheduleName, ScheduleType.DATE, sms.getSchedule().getDate(), 
					organisation));
		}
		if(sms.getSchedule().getType() == ScheduleType.WEEKLY) {
			return repository.save(new Schedule(scheduleName, sms.getSchedule().getDate(), sms.getSchedule().getDayOfWeek(), 
					 sms.getSchedule().getCronExpression(), organisation));
		}
		if(sms.getSchedule().getType() == ScheduleType.MONTHLY) {
			return repository.save(new Schedule(scheduleName, sms.getSchedule().getDate(), sms.getSchedule().getDayOfMonth(), 
					sms.getSchedule().getCronExpression(), organisation));
		}
		return null;
	}
	
	private void saveToGroupSchedule(final String scheduleName, final String scheduleGroup, final Date date, final String cronExpression) {
		//save the jobSchedule
		if(cronExpression == null || cronExpression.trim().equals(""))
			//save a date_Schedule since it does not have a cron_expression
			jobScheduleService.saveDateSchedule(scheduleName, scheduleGroup, SimpleToGroupJob.class, date);
		else
			//save schedule which have cron_expressions
			jobScheduleService.saveCronSchedule(scheduleName, scheduleGroup, CronToGroupJob.class, date, 
					cronExpression);
	}

	private void saveToAllSchedule(final String scheduleName, final String scheduleGroup, final Date date, final String cronExpression) {
		//save the jobSchedule
		if(cronExpression == null || cronExpression.trim().equals(""))
			//save a date_Schedule since it does not have a cron_expression
			jobScheduleService.saveDateSchedule(scheduleName, scheduleGroup, SimpleJob.class, date);
		else
			//save schedule which have cron_expressions
			jobScheduleService.saveCronSchedule(scheduleName, scheduleGroup, CronJob.class, date, 
					cronExpression);
	}

	@Override
	public void sendToAll(String schedule_name) {
		final Schedule schedule = repository.findByName(schedule_name);
		final String message = schedule.getText().getMessage();
		
		//retrieve organization units available from _aeon
		final Organisation organisation = orgService.findBySchedulesId(schedule.getId());
		final Long org_id = organisation.getId();
		final Long org_number = organisation.getNumber();
		//get administrators email
		final List<User> users = userService.findByEmployerId(org_id);
		final String email = users.get(0).getEmail();
		final UnitsDetails unitsDetails = aeonService.findOrgUnitsById(org_number);
		//retrieve all the contacts for the organization
		
		//set up the multiplier which will depend on the length of the message
		int multiplier = 1;
		if(message.length() > 160)
			multiplier = 2;	
		//retrieve the number of contacts
		final List<Contact> contacts = contactService.findBySuppliersId(organisation.getId());

		//separate the number of clients according to:
		//1: country_code
			//1.1: service provider
		final _ContactsTotals contactNos = contactService.seperateContacts(contacts);
		//retrieve the charges per service provider from _aeon
		final _Charges charges = aeonService.getCharges(org_number);
		//calculate the total cost
		final BigDecimal cost = BigDecimal.valueOf(smsService.calculateSmsCost(contactNos, charges) * multiplier);
		//if total cost is less than the units available send ErrorResponse
		if(BigDecimal.valueOf(unitsDetails.getUnitsAvailable()).compareTo(cost) == -1) {
			log.info("#### Schedule could not send sms. Credit Units are insufficient");
			//send email to notify organization units are not available
			emailService.sendEmail(new EmailMessage(email, "Scheduled sms not sent. Insufficient funds!", 
				"Scheduled sms: "+schedule.getName()+", could not be sent. Avalilable funds are: "
				+unitsDetails.getUnitsAvailable()+", while needed units are: "+cost+", kindly top up."));
		}else {
			//else send to africa's talking
			final StringBuffer buffer = new StringBuffer();
			//convert the contacts to string(format required by africa's talking)
			contacts.parallelStream()
				.forEach(contact -> {
					buffer.append(contact.getCountryCode())
						.append(contact.getPhoneNumber())
						.append(",");				
				});
			//send the all_contacts list and sender_name to aeon_ in order to send it to africa's talking
				//also should check if organization has paid for sender_id
			final OnSendDeliveryReport report =
						aeonService.sendSms(new SmsInfo(organisation.getName(), buffer.toString(), message));
			if(!report.getMessageId().equals("") || report.getMessageId() != null) {
				//update the organization's units available and update aeon_units at the same time
				aeonService.subtractOrganisationUnits(org_number, cost);
				//save sms_cost to database
//				costService.save(new Cost(cost, orgService.findById(org_id)));
				//save delivery report to database
				final StringBuffer phoneNoBuffer = new StringBuffer();
				report.getRejected().parallelStream()
				.forEach(details -> {
					phoneNoBuffer.append(details.getPhoneNo())
						.append(",");				
				});
				//retrieve response from africa's talking to see if there was some numbers 
				//which did not receive the _sms
				//get organization country
				final Country org_country = aeonService.getOrganizationInfo(org_number).getCountry();
				reportService.save(new DeliveryReport(report.getMessageId(), report.getSent().size(), 
						report.getRejected().size(), phoneNoBuffer.toString(), org_country, cost.doubleValue(), organisation));		
				//send email notifying organization _sms was sent
				emailService.sendEmail(new EmailMessage(email, "Scheduled sms was sent successfully.", 
						"Scheduled sms: "+schedule.getName()+", scheduled for: "+schedule.getDate()+
						", was sent successfully."));
			}else {
				log.error("##### Error: sms was not sent");
				emailService.sendEmail(new EmailMessage(email, "Scheduled sms was not sent.", 
						"Scheduled sms: "+schedule.getName()+", could not be sent. "
								+ "Kindly reschedule for a later date"));
			}
		}	
	}
	
	@Override
	public void sendToGroup(String schedule_name) {
		final Schedule schedule = repository.findByName(schedule_name);
		final Long schedule_id = schedule.getId();
		final String message = schedule.getText().getMessage();
		//retrieve all the groups assigned to the schedule
		final List<Group> groups = groupService.findBySchedulesId(schedule_id);
		
		//retrieve units available from _aeon
		final Organisation organisation = orgService.findBySchedulesId(schedule_id);
		final Long org_id = organisation.getId();
		final Long org_number = organisation.getNumber();
		//get administrators email
		final List<User> users = userService.findByEmployerId(org_id);
		final String email = users.get(0).getEmail();
		final UnitsDetails unitsDetails = aeonService.findOrgUnitsById(org_number);
		
		//set up the multiplier which will depend on the length of the message
		int multiplier = 1;
		if(message.length() > 160)
			multiplier = 2;	
		
		//retrieve the number of contacts for each group
		final Set<Contact> contacts = new HashSet<Contact>();
		groups.parallelStream().forEach(group ->{
			//get all the contacts of the group
			List<Contact> group_Contacts = contactService.findByGroupsId(group.getId());
			group_Contacts.stream().forEach(contact -> {
				contacts.add(contact);
			});
		});
		
		//separate the number of clients according to:
		//1: country_code
			//1.1: service provider
		final _ContactsTotals contactNos = contactService.seperateContacts(contacts);
		//retrieve the charges per service provider from _aeon
		final _Charges charges = aeonService.getCharges(org_number);
		//calculate the total cost
		final BigDecimal cost = BigDecimal.valueOf(smsService.calculateSmsCost(contactNos, charges) * multiplier);
//		final double cost = smsService.calculateSmsCost(contactNos, charges) * multiplier;
		//if total cost is less than the units available send ErrorResponse
		if(BigDecimal.valueOf(unitsDetails.getUnitsAvailable()).compareTo(cost) == -1) {
			log.info("#### Schedule could not send sms. Credit Units are insufficient");
			//send email to notify organization units are not available
			emailService.sendEmail(new EmailMessage(email, "Scheduled sms not sent. Insufficient funds!", 
				"Scheduled sms: "+schedule.getName()+", could not be sent. Avalilable funds are: "
				+unitsDetails.getUnitsAvailable()+", while needed units are: "+cost+", kindly top up."));
		}else {
			//else send to africa's talking
			final StringBuffer buffer = new StringBuffer();
			//convert the contacts to string(format required by africa's talking)
			contacts.parallelStream()
				.forEach(contact -> {
					buffer.append(contact.getCountryCode())
						.append(contact.getPhoneNumber())
						.append(",");				
			});
			//send the all_contacts list and sender_name to aeon_ in order to send it to africa's talking
				//also should check if organization has paid for sender_id
			final OnSendDeliveryReport report =
						aeonService.sendSms(new SmsInfo(organisation.getName(), buffer.toString(), message));
			if(!report.getMessageId().equals("") || report.getMessageId() != null) {
				//update the organization's units available and update aeon_units at the same time
				aeonService.subtractOrganisationUnits(org_number, cost);
				//save sms_cost to database
	//			costService.save(new Cost(cost, orgService.findById(org_id)));
				//save delivery report to database
				final StringBuffer phoneNoBuffer = new StringBuffer();
				report.getRejected().parallelStream()
				.forEach(phoneNo -> {
					phoneNoBuffer.append(phoneNo)
						.append(",");				
				});
				//retrieve response from africa's talking to see if there was some numbers 
				//which did not receive the _sms
				//get organization country
				final Country org_country = aeonService.getOrganizationInfo(org_number).getCountry();
				reportService.save(new DeliveryReport(report.getMessageId(), report.getSent().size(), 
						report.getRejected().size(), phoneNoBuffer.toString(), org_country, cost.doubleValue(), organisation));		
				//send email notifying organization _sms was sent
				emailService.sendEmail(new EmailMessage(email, "Scheduled sms was sent successfully.", 
						"Scheduled sms: "+schedule.getName()+", scheduled for: "+schedule.getDate()+
						", was sent successfully."));
			}else {
				log.error("##### Error: sms was not sent");
				emailService.sendEmail(new EmailMessage(email, "Scheduled sms was not sent.", 
						"Scheduled sms: "+schedule.getName()+", could not be sent. "
								+ "Kindly reschedule for a later date"));
			}
		}
	}

	@Override
	public Set<_ScheduleDetails> getAllSchedules(final String scheduleGroup) {
		
		final List<Schedule> schedules = findByOrganisationId();
		
		final Scheduler scheduler = schedulerFactoryBean.getScheduler();
		final Set<_ScheduleDetails> details = new HashSet<_ScheduleDetails>();
			log.info("JobGroup: "+scheduleGroup);
			schedules.parallelStream()
				.forEach(jobSchedule -> {
					final JobKey jobKey = new JobKey(jobSchedule.getName(), scheduleGroup);
					try {
						if(scheduler.getJobDetail(jobKey) != null) {
							final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
							
							final List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
							
							triggers.stream()
								.filter(trigger -> {
									return (triggers != null && triggers.size() > 0);
								}
							).forEach(trigger -> {
								try {
									final TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
	
									final Date schedule = trigger.getStartTime();
									final Date nextFire = trigger.getNextFireTime();
									final Date lastFired = trigger.getPreviousFireTime();
									final ScheduleType type = jobSchedule.getType();
									ScheduleStatus status = null;
									
									if(isJobRunning(jobSchedule.getName(), scheduleGroup)){
										status = ScheduleStatus.RUNNING;
									}else{		
										if (TriggerState.NORMAL.equals(triggerState))
											status = ScheduleStatus.SCHEDULED;							
										else if (TriggerState.PAUSED.equals(triggerState))
											status = ScheduleStatus.PAUSED;
										else if (TriggerState.COMPLETE.equals(triggerState))
											status = ScheduleStatus.COMPLETE;
										else if (TriggerState.BLOCKED.equals(triggerState))
											status = ScheduleStatus.BLOCKED;
										else if (TriggerState.NONE.equals(triggerState))
											status = ScheduleStatus.NONE;
									}
									final _ScheduleDetails detail = 
											new _ScheduleDetails(jobSchedule.getName(), type, schedule, nextFire, lastFired, status);
									
									details.add(detail);
									
								} catch (SchedulerException e) {
									e.printStackTrace();
								}
							});
						}
					} catch (SchedulerException e) {
						e.printStackTrace();
					}

				});		
		return details;
	}
	
	public boolean isJobRunning(String jobName, String groupKey) {
		String jobKey = jobName;

		try {

			List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
			if(currentJobs!=null){
				for (JobExecutionContext jobCtx : currentJobs) {
					String jobNameDB = jobCtx.getJobDetail().getKey().getName();
					String groupNameDB = jobCtx.getJobDetail().getKey().getGroup();
					if (jobKey.equalsIgnoreCase(jobNameDB) && groupKey.equalsIgnoreCase(groupNameDB)) {
						return true;
					}
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

}