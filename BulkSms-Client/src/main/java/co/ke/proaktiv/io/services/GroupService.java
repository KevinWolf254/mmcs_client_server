package co.ke.proaktiv.io.services;

import java.util.Optional;
import java.util.Set;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.pojos.response.Response;

public interface GroupService {
	
	public Group_ save(Group_ group);

	public Optional<Group_> findByName(String name);
	
	public Set<Group_> findByOrganisationId(Long id);
	
	public Response delete(Long id);

	public Optional<Group_> findById(Long id);
}
