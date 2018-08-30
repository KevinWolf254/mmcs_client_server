package co.ke.aeontech.services;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import co.ke.aeontech.models.Group;
import co.ke.aeontech.pojos._ContactsTotals;

public interface GroupService {
	
	public Group findById(Long id);
	
	public CompletableFuture<List<Group>> findAllById(Set<Long> groupIds);

	public Set<Group> findByContactsId(Long contact_id);

	public List<Group> findByOrganisationId();
	
	public List<Group> findBySchedulesId(Long id);

	public boolean search(String name);

	public _ContactsTotals getGroupedContacts(List<Long> groupIds);
	
	public Group save(Group group);

	public void saveGroup(String name);
	
	public void deleteGroup(Long id);
}
