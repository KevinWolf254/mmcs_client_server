package co.ke.aeontech.services.impl;

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

import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.quartz.utils.JobScheduleUtil;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.JobScheduleService;
import co.ke.aeontech.services.UserService;

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
//	final static String groupKey = "Organization_Group";	

	@Override
	public boolean saveDateSchedule(final String name, final String schedule_group, 
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
	public boolean saveCronSchedule(final String name, final String schedule_group, 
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
			log.error("***** SchedulerException: "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateDateSchedule(final String jobName, final Date date) {
		return false;
	}

	@Override
	public boolean updateCronSchedule(final String jobName, final Date date, final String cronExpression) {
		return false;
	}
	
	@Override
	public boolean startJobSchedule(final String jobName) {
		final User user = userService.getSignedInUser();
		final String subject = "Job Schedule Start";
		final String body = "Job schedule: "+jobName+" was successfully STARTED on "+new Date();
		
		String jobKey = jobName;
//		String groupKey = "SampleGroup";
		final String groupKey = user.getEmployer().getName();

		JobKey jKey = new JobKey(jobKey, groupKey); 
		try {
			schedulerFactoryBean.getScheduler().triggerJob(jKey);
			emailService.sendEmail(new EmailMessage(user.getEmail(), subject, body));
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}		
		return false;
	}
	@Override
	public boolean pauseJobSchedule(final String jobName) {
		final User user = userService.getSignedInUser();
		final String subject = "Job Schedule Paused";
		final String body = "Job schedule: "+jobName+" was PAUSED on "+new Date();
		
		final String jobKey = jobName;
//		String groupKey = "SampleGroup";
		final String groupKey = user.getEmployer().getName();
		final JobKey jkey = new JobKey(jobKey, groupKey); 

		try {
			schedulerFactoryBean.getScheduler().pauseJob(jkey);
			emailService.sendEmail(new EmailMessage(user.getEmail(), subject, body));

			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean resumeJobSchedule(final String jobName) {
		final User user = userService.getSignedInUser();
		final String subject = "Job Schedule Paused";
		final String body = "Job schedule: "+jobName+" was RESUMED on "+new Date();
		
		final String jobKey = jobName;
//		String groupKey = "SampleGroup";
		final String groupKey = user.getEmployer().getName();

		final JobKey jKey = new JobKey(jobKey, groupKey); 
		try {
			schedulerFactoryBean.getScheduler().resumeJob(jKey);
			emailService.sendEmail(new EmailMessage(user.getEmail(), subject, body));
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteJobSchedule(final String jobName) {
		final User user = userService.getSignedInUser();
		final String subject = "Job Schedule Paused";
		final String body = "Job schedule: "+jobName+" was DELETED on "+new Date();
		
		final String jobKey = jobName;
//		String groupKey = "SampleGroup";
		final String groupKey = user.getEmployer().getName();

		final JobKey jkey = new JobKey(jobKey, groupKey); 

		try {
			final boolean status = schedulerFactoryBean.getScheduler().deleteJob(jkey);
			emailService.sendEmail(new EmailMessage(user.getEmail(), subject, body));
			return status;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean stopJobSchedule(final String jobName) {
		final User user = userService.getSignedInUser();
		final String subject = "Job Schedule Paused";
		final String body = "Job schedule: "+jobName+" was STOPPED on "+new Date();
		
		try{	
			final String jobKey = jobName;
//			String groupKey = "SampleGroup";
			final String groupKey = user.getEmployer().getName();

			final Scheduler scheduler = schedulerFactoryBean.getScheduler();
			final JobKey jkey = new JobKey(jobKey, groupKey);
			emailService.sendEmail(new EmailMessage(user.getEmail(), subject, body));

			return scheduler.interrupt(jkey);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	private static final Logger log = LoggerFactory.getLogger(JobScheduleServiceImpl.class);

}
