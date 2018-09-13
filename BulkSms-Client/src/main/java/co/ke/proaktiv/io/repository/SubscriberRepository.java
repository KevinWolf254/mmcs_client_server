package co.ke.proaktiv.io.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.pojos.helpers.ServiceProvider;
import co.ke.proaktiv.io.repository.custom.SubscriberRepositoryCustom;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, SubscriberRepositoryCustom {
	
	public Set<Subscriber> findByCode(String code);

	public Set<Subscriber> findByServiceProvider(String number);
	
	public Set<Subscriber> findByNumber(String number);

	public Set<Subscriber> findByCategory(ServiceProvider category);
	
	public Set<Subscriber> findByGroupsId(Long id);

}