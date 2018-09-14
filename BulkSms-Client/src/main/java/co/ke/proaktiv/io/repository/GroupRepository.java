package co.ke.proaktiv.io.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Group_;

public interface GroupRepository extends JpaRepository<Group_, Long>{

	public Set<Group_> findByOrganisationId(Long id);

	public Optional<Group_> findByName(String name);
	
	public Set<Group_> findBySubscribersId(Long id);
	
	public Set<Group_> findBySchedulesId(Long id);
}
