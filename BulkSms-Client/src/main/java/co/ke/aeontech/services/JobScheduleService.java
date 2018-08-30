package co.ke.aeontech.services;

import java.util.Date;

import org.springframework.scheduling.quartz.QuartzJobBean;

public interface JobScheduleService {
	
	public boolean saveDateSchedule(final String name,  final String schedule_group, 
			final Class<? extends QuartzJobBean> jobScheduleClass, final Date date);
	
	public boolean saveCronSchedule(final String name,  final String schedule_group, 
			final Class<? extends QuartzJobBean> jobScheduleClass, final Date date,
			final String cronExpression);
	
	public boolean updateDateSchedule(final String jobName, final Date date);
	public boolean updateCronSchedule(final String jobName, final Date date, 
			final String cronExpression);	
	
	public boolean startJobSchedule(final String jobName);
	public boolean pauseJobSchedule(final String jobName);
	public boolean resumeJobSchedule(final String jobName);
	public boolean deleteJobSchedule(final String jobName);
	public boolean stopJobSchedule(final String jobName);
}
