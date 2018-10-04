package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.ServiceProvider;
import co.ke.proaktiv.io.repository.custom.ServiceProviderRepositoryCustom;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long>, ServiceProviderRepositoryCustom {

}
