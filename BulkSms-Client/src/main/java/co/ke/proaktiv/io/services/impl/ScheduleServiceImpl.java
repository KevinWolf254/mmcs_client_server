package co.ke.proaktiv.io.services.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.models.Text;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.builders.ScheduleBuilder;
import co.ke.proaktiv.io.pojos.ChargesReport;
import co.ke.proaktiv.io.pojos.ScheduleReport;
import co.ke.proaktiv.io.pojos.Sms;
import co.ke.proaktiv.io.pojos._Schedule;
import co.ke.proaktiv.io.pojos._ScheduleDetails;
import co.ke.proaktiv.io.pojos.helpers.ScheduleStatus;
import co.ke.proaktiv.io.pojos.helpers.ScheduleType;
import co.ke.proaktiv.io.quartz.jobs.CronJob;
import co.ke.proaktiv.io.quartz.jobs.SimpleJob;
import co.ke.proaktiv.io.repository.ScheduleRepository;
import co.ke.proaktiv.io.services.ChargesService;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.JobScheduleService;
import co.ke.proaktiv.io.services.OrganisationService;
import co.ke.proaktiv.io.services.ScheduleService;
import co.ke.proaktiv.io.services.SmsService;
import co.ke.proaktiv.io.services.TextService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository repository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private TextService textService;
	@Autowired
	private JobScheduleService jobService;
	@Autowired
	private OrganisationService orgService;
	@Autowired
	private ChargesService chargesService;
	@Autowired
	@Lazy
	SchedulerFactoryBean schedulerFactoryBean;
	private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Override
	public Optional<Schedule> findByName(final String name) {
		return repository.findByName(name);
	}
	
	@Override
	public Schedule save(final String orgName, final _Schedule _schedule, final String message, final Group_ group){
		final Organisation org = orgService.findByName(orgName).get();
		final ScheduleBuilder schedule = build(_schedule);
		final Schedule schedule_ = schedule.build();
		schedule_.getGroups().add(group);
		
		final Date date = _schedule.getDate();		
		final Schedule sche =  repository.save(schedule_);
		textService.save(new Text(message, sche));
		save(_schedule.getName(), org.getName(), date, _schedule.getCronExpression());
		
		return sche;
	}

	private ScheduleBuilder build(final _Schedule schedule) {
		ScheduleBuilder schedule_ = null;
		if(schedule.getType().equals(ScheduleType.DAILY))
			schedule_ = new ScheduleBuilder()
								.setName(schedule.getName())
								.setSenderId(schedule.getSenderId())
								.setCreatedBy(schedule.getCreatedBy())
								.setType(ScheduleType.DAILY)
								.setDate(schedule.getDate());
		else if(schedule.getType().equals(ScheduleType.DATE)) 
			schedule_ = new ScheduleBuilder()
								.setName(schedule.getName())
								.setSenderId(schedule.getSenderId())
								.setCreatedBy(schedule.getCreatedBy())
								.setType(ScheduleType.DATE)
								.setDate(schedule.getDate());	
		else if(schedule.getType().equals(ScheduleType.WEEKLY))
			schedule_ = new ScheduleBuilder()
								.setName(schedule.getName())
								.setSenderId(schedule.getSenderId())
								.setCreatedBy(schedule.getCreatedBy())
								.setDate(schedule.getDate())
								.setDayOfWeek(schedule.getDayOfWeek())
								.setCronExpression(schedule.getCronExpression());	
		else if(schedule.getType().equals(ScheduleType.MONTHLY))
			schedule_ = new ScheduleBuilder()
								.setName(schedule.getName())
								.setSenderId(schedule.getSenderId())
								.setCreatedBy(schedule.getCreatedBy())
								.setDate(schedule.getDate())
								.setDayOfMonth(schedule.getDayOfMonth())
								.setCronExpression(schedule.getCronExpression());
		return schedule_;
	}
	
	@Override
	public Schedule save(final String orgName, final _Schedule _schedule, final String message, final Set<Group_> groups){
		final User user = userService.getSignedInUser();
		final Organisation org = orgService.findByName(orgName).get();
		final ScheduleBuilder schedule = build(_schedule);
		schedule.setGroups(groups);
		
		final Date date = _schedule.getDate();
		try {
			save(_schedule.getName(), org.getName(), date, _schedule.getCronExpression());
		} catch (Exception e) {
			log.error("Exception: "+e.getMessage());
		}
		final Schedule sche =  repository.save(schedule.setCreatedBy(user.getEmail()).build());
		textService.save(new Text(message, sche));
		
		return sche;
	}

	private void save(final String scheduleName, final String scheduleGroup, final Date date, final String cronExpression) {
		//save the jobSchedule
		if(cronExpression == null || cronExpression.trim().equals(""))
			//save a date_Schedule since it does not have a cron_expression
			jobService.save(scheduleName, scheduleGroup, SimpleJob.class, date);
		else
			//save schedule which have cron_expressions
			jobService.save(scheduleName, scheduleGroup, CronJob.class, date, cronExpression);
	}
	
	@Override
	public void send(final String schedule_name) {
		final Optional<Schedule> _schedule = repository.findByName(schedule_name);
		if(_schedule.isPresent()) {
			final Schedule schedule = _schedule.get();
			final Text smsText = textService.findByScheduleId(schedule.getId());
			final String message = smsText.getMessage();
			final User user = userService.findByEmail(schedule.getCreatedBy()).get();
			
			final Set<Group_> groups = groupService.findBySchedule(schedule);
			final Set<Long> groupIds = groups.stream()
					.map(group -> group.getId())
					.collect(Collectors.toSet());
			
			smsService.send(user, schedule.getSenderId(), message, groupIds);
		}
		log.info("Schedule: " + schedule_name + " could not be found for delivery");
	}
	
	@Override
	public Set<_ScheduleDetails> findAll(final String scheduleGroup) {
		final Organisation org = userService.getSignedInUser().getOrganisation();
		final Set<Group_> groups = groupService.findByOrganisationId(org.getId());
		
		final Set<Schedule> schedules = groups.stream()
				.map(group -> group.getSchedules())
				.flatMap(sche -> sche.stream())
				.collect(Collectors.toSet());
		
		final Scheduler scheduler = schedulerFactoryBean.getScheduler();
		final Set<_ScheduleDetails> details = new HashSet<_ScheduleDetails>();
		
			schedules.stream()
				.forEach(jobSchedule -> {
					final JobKey jobKey = new JobKey(jobSchedule.getName(), scheduleGroup);
					try {
						if(scheduler.getJobDetail(jobKey) != null) {
							final JobDetail jobDetail = scheduler.getJobDetail(jobKey);							
							final List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
							
							triggers.stream()
								.filter(trigger -> (triggers != null && triggers.size() > 0))
								.forEach(trigger -> {
								try {
									final TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
	
									final Date schedule = trigger.getStartTime();
									final Date nextFire = trigger.getNextFireTime();
									final Date lastFired = trigger.getPreviousFireTime();
									final ScheduleType type = jobSchedule.getType();
									ScheduleStatus status = null;
									
									if(isJobRunning(jobSchedule.getName(), scheduleGroup))
										status = ScheduleStatus.RUNNING;
									else{		
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
									
								}catch (SchedulerException e) {
									e.printStackTrace();
								}
							});
						}
					}catch(SchedulerException e){
						log.info("SchedulerException: "+ e.getMessage());
						return;
					}
				});		
		return details;
	}
	
	public boolean isJobRunning(final String jobName, final String groupKey) {
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

	@Override
	public ScheduleReport getScheduleDetails(final String name) {
		final Optional<Schedule> schedule = findByName(name);		
		if(schedule.isPresent()) 
			return processReport(schedule.get());		
		return new ScheduleReport(400, "Bad request", "Campaign does not exist");
	}

	private ScheduleReport processReport(final Schedule schedule) {
		final Set<Group_> groups = groupService.findBySchedule(schedule);
		final ChargesReport charges = chargesService.calculate(processSms(schedule, groups));
		return new ScheduleReport(200, "Success", "Campaign report populated", schedule, groups, charges);
	}

	private Sms processSms(final Schedule schedule, final Set<Group_> groups) {
		final Text text = textService.findByScheduleId(schedule.getId());
		final Sms request = new Sms();
		request.setEmail(schedule.getCreatedBy());
		request.setSenderId(schedule.getSenderId());
		request.setGroupIds(groups.stream().map(group -> group.getId()).collect(Collectors.toSet()));
		request.setMessage(text.getMessage());
		return request;
	}
	
}