package co.ke.proaktiv.io.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Country_;
import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.models.Prefix_;
import co.ke.proaktiv.io.models.ServiceProvider;
import co.ke.proaktiv.io.models.ServiceProvider_;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.models.Subscriber_;
import co.ke.proaktiv.io.repository.custom.SubscriberRepositoryCustom;

public class SubscriberRepositoryImpl implements SubscriberRepositoryCustom{
	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@Override
	public Subscriber find(final String code, final String prefix, final String number) {
		final EntityManager manager = factory.createEntityManager();
		Subscriber result_contact = null;
		try {
			final CriteriaBuilder builder = manager.getCriteriaBuilder();
			final CriteriaQuery<Subscriber> query = builder.createQuery(Subscriber.class);
			final Root<Subscriber> root = query.from(Subscriber.class);
			
			final Join<Subscriber, Prefix> joinPrefix = root.join(Subscriber_.prefix);
			
			final Join<Subscriber, ServiceProvider> joinSP = root.join(Subscriber_.serviceProvider);
			final Join<ServiceProvider, Country> joinCountry = joinSP.join(ServiceProvider_.country);

			query.where(builder.equal(joinCountry.get(Country_.code), code),
					(builder.and(builder.equal(joinPrefix.get(Prefix_.number), code))),
					builder.and(builder.equal(root.get(Subscriber_.number), number)));
			
			result_contact = manager.createQuery(query).getSingleResult();
			
		} catch(NoResultException nre){
			return new Subscriber();
		}finally {
			manager.close();
		}
		return result_contact;
	}
}
