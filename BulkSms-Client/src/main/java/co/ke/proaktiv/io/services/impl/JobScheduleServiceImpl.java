package co.ke.proaktiv.io.services.impl;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.EmailMessage;
import co.ke.proaktiv.io.quartz.utils.JobScheduleUtil;
import co.ke.proaktiv.io.services.EmailService;
import co.ke.proaktiv.io.services.JobScheduleService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class JobScheduleServiceImpl implements JobScheduleService {

	@Autowired
	@Lazy
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	
	@Autowired
	protected ApplicationContext context;
	private static final Logger log = LoggerFactory.getLogger(JobScheduleServiceImpl.class);

	@Override
	public boolean save(final String name, final String schedule_group, 
			final Class<? extends QuartzJobBean> jobScheduleClass, final Date date) {
		final String jobKey = name;
		final String triggerKey = name;
		
		final JobDetail jobDetail = JobScheduleUtil.createJob(jobScheduleClass, false, context, jobKey, schedule_group);
		//Create single trigger for the date_schedule job
		final Trigger cronTriggerBean = JobScheduleUtil.createSingleTrigger(triggerKey, date, 
				SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		try {
			final Scheduler scheduler = schedulerFactoryBean.getScheduler();
			//save the schedule
			scheduler.scheduleJob(jobDetail, cronTriggerBean);
			return true;
		} catch (SchedulerException e) {
			log.error("***** SchedulerException: "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean save(final String name, final String schedule_group, 
			final Class<? extends QuartzJobBean> jobScheduleClass, final Date date,
			String cronExpression) {
		final String jobKey = name;
		final String triggerKey = name;
		
		final JobDetail jobDetail = JobScheduleUtil.createJob(jobScheduleClass, false, context, jobKey, schedule_group);
		
		//Create cron_trigger for the cron_schedule job
		Trigger cronTriggerBean = JobScheduleUtil.createCronTrigger(triggerKey, date, cronExpression, 
				SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		try {
			final Scheduler scheduler = schedulerFactoryBean.getScheduler();
			//save the schedule
			scheduler.scheduleJob(jobDetail, cronTriggerBean);
			return true;
		} catch (SchedulerException e) {
			log.error("SchedulerException: "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean start(final String jobName) {
		final User user = userService.getSignedInUser();
		final String subject = "Sms Campaign Manually Started";
		final StringBuilder build = new StringBuilder("Sms Marketing Campaign: ")
				.append(jobName)
				.append(" was manually STARTED on ")
				.append(new Date());
		
		String jobKey = jobName;
		final String groupKey = user.getOrganisation().getName();

		final JobKey jKey = new JobKey(jobKey, groupKey); 
		try {
			schedulerFactoryBean.getScheduler().triggerJob(jKey);
			emailService.sendEmail(new EmailMessage(user.getEmail(), subject, build.toString()));
			return true;
		} catch (SchedulerException e) {
			log.error("SchedulerException: "+e.getMessage());
			return false;
		}catch (Exception e) {
			log.error("Exception: "+e.getMessage());
			return false;
		}		
	}

	@Override
	public boolean delete(final String jobName) {
		final User user = userService.getSignedInUser();
		final String subject = "Job Schedule Deleted";
		final StringBuilder build = new StringBuilder("Job schedule: ")
				.append(""+jobName)
				.append(" was DELETED on ")
				.append(""+new Date());
		
		final String jobKey = jobName;
		final String groupKey = user.getOrganisation().getName();

		final JobKey jkey = new JobKey(jobKey, groupKey); 

		try {
			final boolean status = schedulerFactoryBean.getScheduler().deleteJob(jkey);
			emailService.sendEmail(new EmailMessage(user.getEmail(), subject, build.toString()));
			return status;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
}
