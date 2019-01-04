package co.ke.proaktiv.io.services.impl;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.repository.GroupRepository;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository repository;

	@Autowired
	private UserService userService;	
	private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Override
	public Group_ save(final Group_ group) {
		Group_ response;
		try {
			response = repository.save(group);
		} catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new Group_();
		}
		return response;
	}
	
	@Override
	public Set<Group_> findByOrganisationId(final Long id) {
		return repository.findByOrganisationId(id);
	}

	@Override
	public Optional<Group_> findByName(final String name) {
		final Long id = userService.getSignedInUser().getOrganisation().getId();
		final String groupName = id+"_"+name;
		
		log.info("Searching for: "+groupName);
		
		return repository.findByName(groupName);
	} 

	@Override
	public Response delete(Long id) {
		Optional<Group_> group = repository.findById(id);
		if(!group.isPresent())
			return new Response(400, "failed", "group is non-existant");
		repository.delete(group.get());	
		return new Response(200, "success", "deleted group");
	}

	@Override
	public Optional<Group_> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Set<Group_> findBySchedule(Schedule schedule) {
		return repository.findBySchedulesId(schedule.getId());
	}
}
