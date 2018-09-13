package co.ke.proaktiv.io.services;

import java.util.Optional;
import java.util.Set;

import co.ke.proaktiv.io.models.Group;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.pojos._Schedule;
import co.ke.proaktiv.io.pojos._ScheduleDetails;

public interface ScheduleService {

	public Optional<Schedule> findByName(String name);

	public Schedule save(final String orgName, final _Schedule schedule, final String message, final Group group);
	
	public Schedule save(final String orgName, final _Schedule schedule, final String message, final Set<Group> groups);
		
	public void send(String schedule_name);
	
	public Set<_ScheduleDetails> findAll(final String schedule_group);
}
