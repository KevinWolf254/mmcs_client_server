package co.ke.proaktiv.io.services.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Group;
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

	@Override
	public Group save(final Group group) {
		return repository.save(group);
	}
	
	@Override
	public Set<Group> findByOrganisationId(final Long id) {
		return repository.findByOrganisationId(id);
	}

	@Override
	public Optional<Group> findByName(final String name) {
		final Long id = userService.getSignedInUser().getOrganisation().getId();
		return repository.findByName(id+"_"+name);
	} 

	@Override
	public Response delete(Long id) {
		Optional<Group> group = repository.findById(id);
		if(!group.isPresent())
			return new Response(400, "failed", "group is non-existant");
		repository.delete(group.get());	
		return new Response(200, "success", "deleted group");
	}

	@Override
	public Optional<Group> findById(Long id) {
		return repository.findById(id);
	}
	
//	@Override
//	public void saveGroup(String name) {
//		Organisation organisation = userService.getSignedInUser().getEmployer();
//		save(new Group(name, organisation));
//	}
//
//
//	@Override
//	public _ContactsTotals getGroupedContacts(List<Long> groupIds) {
//		Set<Subscriber> allContacts = new HashSet<>();
//
//		groupIds.stream().forEach(id ->{
//			List<Subscriber> rawContacts = contactService.findByGroupsId(id);
//			rawContacts.parallelStream().forEach(contact ->{
//				allContacts.add(contact);
//			});
//		});
//		
//		List<Subscriber> contacts = new ArrayList<>();
//		allContacts.stream().forEach(contact ->{
//			contacts.add(contact);
//		});
//		
//		
//		return contactService.seperateContacts(contacts);
//	}
//
//	@Override
//	public Group findById(Long id) {
//		return repository.findById(id).get();
//	}
//	
//	@Override
//	public CompletableFuture<List<Group>> findAllById(Set<Long> groupIds){
//		List<Group> groups =  repository.findAllById(groupIds);
//		return CompletableFuture.completedFuture(groups);
//	}
//
//	@Override
//	public List<Group> findBySchedulesId(Long id) {
//		return repository.findBySchedulesId(id);
//	}
//
//	@Override
//	public Set<Group> findByContactsId(Long contact_id) {
//		return repository.findByContactsId(contact_id);
//	}

}
