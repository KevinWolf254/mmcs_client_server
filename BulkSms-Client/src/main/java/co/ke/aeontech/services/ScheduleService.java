package co.ke.aeontech.services;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Schedule;
import co.ke.aeontech.pojos._ScheduleDetails;
import co.ke.aeontech.pojos.requests.Sms;

public interface ScheduleService {

	public Schedule findByName(String schedule_name);

	public List<Schedule> findByOrganisationId();

	public void saveSchedule(Sms sms, String scheduleName, Organisation organisation) throws 
	InterruptedException, ExecutionException;
	
	public void sendToAll(String schedule_name);
	
	public void sendToGroup(String schedule_name);
	
	public Set<_ScheduleDetails> getAllSchedules(final String schedule_group);
}
