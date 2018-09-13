package co.ke.proaktiv.io.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{

	public Set<Group> findByOrganisationId(Long id);

	public Optional<Group> findByName(String name);
	
	public Set<Group> findBySubscribersId(Long id);
	
	public Set<Group> findBySchedulesId(Long id);
}
