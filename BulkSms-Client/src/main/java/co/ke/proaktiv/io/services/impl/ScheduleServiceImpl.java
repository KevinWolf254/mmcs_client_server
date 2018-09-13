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

import co.ke.proaktiv.io.models.Group;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.models.Text;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos._Schedule;
import co.ke.proaktiv.io.pojos._ScheduleDetails;
import co.ke.proaktiv.io.pojos.helpers.ScheduleStatus;
import co.ke.proaktiv.io.pojos.helpers.ScheduleType;
import co.ke.proaktiv.io.quartz.jobs.CronJob;
import co.ke.proaktiv.io.quartz.jobs.SimpleJob;
import co.ke.proaktiv.io.repository.ScheduleRepository;
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
	@Lazy
	SchedulerFactoryBean schedulerFactoryBean;
	
	@Override
	public Optional<Schedule> findByName(final String name) {
		return repository.findByName(name);
	}
	
	@Override
	public Schedule save(final String orgName, final _Schedule _schedule, final String message, final Group group){
		final Organisation org = orgService.findByName(orgName).get();
		final Schedule schedule = convert(_schedule);
		schedule.getGroups().add(group);
		
		final Date date = _schedule.getDate();		
		final Schedule sche =  repository.save(schedule);
		textService.save(new Text(message, sche));
		save(_schedule.getName(), org.getName(), date, _schedule.getCronExpression());
		
		return sche;
	}

	private Schedule convert(final _Schedule schedule) {
		 Schedule schedule_ = null;
		if(schedule.getType().equals(ScheduleType.DAILY))
			schedule_ = new Schedule(schedule.getName(), schedule.getCreatedBy(), ScheduleType.DAILY, 
					schedule.getDate());
		else if(schedule.getType().equals(ScheduleType.DATE)) 
			schedule_ = new Schedule(schedule.getName(), schedule.getCreatedBy(), ScheduleType.DATE, 
					schedule.getDate());
		else if(schedule.getType().equals(ScheduleType.WEEKLY))
			schedule_ = new Schedule(schedule.getName(), schedule.getCreatedBy(), schedule.getDate(), 
					schedule.getDayOfWeek(), schedule.getCronExpression());
		else if(schedule.getType().equals(ScheduleType.MONTHLY))
			schedule_ = new Schedule(schedule.getName(), schedule.getCreatedBy(), schedule.getDate(), 
					schedule.getDayOfMonth(), schedule.getCronExpression());
		return schedule_;
	}
	
	@Override
	public Schedule save(final String orgName, final _Schedule _schedule, final String message, final Set<Group> groups){
		final Organisation org = orgService.findByName(orgName).get();
		final Schedule schedule = convert(_schedule);
		schedule.setGroups(groups);
		
		final Date date = _schedule.getDate();		
		final Schedule sche =  repository.save(schedule);
		textService.save(new Text(message, sche));
		save(_schedule.getName(), org.getName(), date, _schedule.getCronExpression());
		
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
		final Schedule schedule = repository.findByName(schedule_name).get();
		
		final String message = schedule.getText().getMessage();
		final User user = userService.findByEmail(schedule.getCreatedBy()).get();
		
		final Set<Group> groups = schedule.getGroups();
		final Set<Long> groupIds = groups.stream()
				.map(group -> group.getId())
				.collect(Collectors.toSet());
		
		smsService.send(user, message, groupIds);
	}
	
	@Override
	public Set<_ScheduleDetails> findAll(final String scheduleGroup) {
		final Organisation org = userService.getSignedInUser().getOrganisation();
		final Set<Group> groups = groupService.findByOrganisationId(org.getId());
		
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
						}else
							log.info("#### job details are null");
					}catch(SchedulerException e){e.printStackTrace();}
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