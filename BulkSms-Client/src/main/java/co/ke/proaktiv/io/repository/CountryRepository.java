package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	public Country findByName(String name);

	public Country findByCode(String code);

}
