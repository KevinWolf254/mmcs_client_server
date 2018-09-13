package co.ke.proaktiv.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long>{

	public Optional<Organisation> findById(Long id);
	
	public Optional<Organisation> findByName(String name);

}
