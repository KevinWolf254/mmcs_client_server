package co.ke.aeontech.repository;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import co.ke.aeontech.models.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long>{

	@Transactional
	public Organisation findByEmployeesId(Long id);

	public Organisation findByName(String name);

	public Organisation findBySchedulesId(Long id);

	public Set<Organisation> findByContactsId(Long id);

}
