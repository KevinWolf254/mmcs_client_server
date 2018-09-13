package co.ke.proaktiv.io.services;

import java.util.Optional;
import java.util.Set;

import co.ke.proaktiv.io.models.Group;
import co.ke.proaktiv.io.pojos.response.Response;

public interface GroupService {
	
	public Group save(Group group);

	public Optional<Group> findByName(String name);
	
	public Set<Group> findByOrganisationId(Long id);
	
	public Response delete(Long id);

	public Optional<Group> findById(Long id);
}
