package co.ke.aeontech.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{

	public List<Group> findByOrganisationId(Long id);
	
	public List<Group> findBySchedulesId(Long id);

	public Group findByName(String name);

	public Set<Group> findByContactsId(Long contact_id);

}
