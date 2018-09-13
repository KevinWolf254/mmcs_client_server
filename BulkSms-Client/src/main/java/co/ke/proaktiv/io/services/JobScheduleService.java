package co.ke.proaktiv.io.services;

import java.util.Date;

import org.springframework.scheduling.quartz.QuartzJobBean;

public interface JobScheduleService {
	
	public boolean save(final String name,  final String schedule_group, 
			final Class<? extends QuartzJobBean> jobScheduleClass, final Date date);
	
	public boolean save(final String name,  final String schedule_group, 
			final Class<? extends QuartzJobBean> jobScheduleClass, final Date date,
			final String cronExpression);
	
	public boolean update(final String jobName, final Date date);
	public boolean update(final String jobName, final Date date, 
			final String cronExpression);	
	
	public boolean start(final String jobName);
	public boolean pause(final String jobName);
	public boolean resume(final String jobName);
	public boolean delete(final String jobName);
	public boolean stop(final String jobName);
}
