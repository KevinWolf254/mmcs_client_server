package co.ke.aeontech.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Contact;
import co.ke.aeontech.models.Group;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.repository.GroupRepository;
import co.ke.aeontech.services.ContactService;
import co.ke.aeontech.services.GroupService;
import co.ke.aeontech.services.UserService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository repository;

	@Autowired
	private UserService userService;
	@Autowired
	private ContactService contactService;
	
	@Override
	public List<Group> findByOrganisationId() {
		Long id = userService.getSignedInUser().getEmployer().getId();
		return repository.findByOrganisationId(id);
	}

	@Override
	public Group save(Group group) {
		return repository.save(group);
	}

	@Override
	public void saveGroup(String name) {
		Organisation organisation = userService.getSignedInUser().getEmployer();
		save(new Group(name, organisation));
	}

	@Override
	public boolean search(String name) {
		Long org_no = userService.getSignedInUser().getEmployer().getNumber();
		Group found = repository.findByName(org_no+"_"+name);
		if(found != null)
			return true;
		return false;
	}

	@Override
	public _ContactsTotals getGroupedContacts(List<Long> groupIds) {
		Set<Contact> allContacts = new HashSet<>();

		groupIds.stream().forEach(id ->{
			List<Contact> rawContacts = contactService.findByGroupsId(id);
			rawContacts.parallelStream().forEach(contact ->{
				allContacts.add(contact);
			});
		});
		
		List<Contact> contacts = new ArrayList<>();
		allContacts.stream().forEach(contact ->{
			contacts.add(contact);
		});
		
		
		return contactService.seperateContacts(contacts);
	}

	@Override
	public Group findById(Long id) {
		return repository.findById(id).get();
	}
	
	@Override
	public CompletableFuture<List<Group>> findAllById(Set<Long> groupIds){
		List<Group> groups =  repository.findAllById(groupIds);
		return CompletableFuture.completedFuture(groups);
	}

	@Override
	public List<Group> findBySchedulesId(Long id) {
		return repository.findBySchedulesId(id);
	}

	@Override
	public Set<Group> findByContactsId(Long contact_id) {
		return repository.findByContactsId(contact_id);
	}

	@Override
	public void deleteGroup(Long id) {
		Optional<Group> group = repository.findById(id);
		if(group.isPresent())
			repository.delete(group.get());		
	}

}
