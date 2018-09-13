package co.ke.proaktiv.io.quartz.jobs;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import co.ke.proaktiv.io.services.ScheduleService;

public class CronJob extends QuartzJobBean implements InterruptableJob {
	@Autowired
	private ScheduleService scheduleService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		//retrieve job key/schedule name
		final JobKey key = jobExecutionContext.getJobDetail().getKey();
		final String schedule_name = key.getName();
		
		//process the schedule
		scheduleService.send(schedule_name);	
	}
	
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		log.debug("***** Cron job was interrupted");
	}
	private static final Logger log = LoggerFactory.getLogger(CronJob.class);
}
