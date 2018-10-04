package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Prefix;

public interface PrefixRepository extends JpaRepository<Prefix, Long> {

	public Prefix findByNumber(String provider);
}
