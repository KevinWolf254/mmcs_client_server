package co.ke.proaktiv.io.services;

import java.util.Optional;
import java.util.Set;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.pojos.ScheduleReport;
import co.ke.proaktiv.io.pojos._Schedule;
import co.ke.proaktiv.io.pojos._ScheduleDetails;

public interface ScheduleService {

	public Optional<Schedule> findByName(String name);

	public Schedule save(String orgName, _Schedule schedule, String message, Group_ group);
	
	public Schedule save(String orgName, _Schedule schedule, String message, Set<Group_> groups);
		
	public void send(String schedule_name);
	
	public Set<_ScheduleDetails> findAll(String schedule_group);

	public ScheduleReport getScheduleDetails(String name);
}
