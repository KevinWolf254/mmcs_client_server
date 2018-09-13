package co.ke.proaktiv.io.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.models.Subscriber_;
import co.ke.proaktiv.io.repository.custom.SubscriberRepositoryCustom;

public class SubscriberRepositoryImpl implements SubscriberRepositoryCustom{
	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@Override
	public Subscriber findByCodePhoneNo(final String code, final String serviceProvider, 
			final String number) {
		final EntityManager manager = factory.createEntityManager();
		Subscriber result_contact = null;
		try {
			final CriteriaBuilder builder = manager.getCriteriaBuilder();
			final CriteriaQuery<Subscriber> query = builder.createQuery(Subscriber.class);
			final Root<Subscriber> contact = query.from(Subscriber.class);
			query.where(builder.equal(contact
							.get(Subscriber_.code), code),
					(builder.and(builder.equal(contact
							.get(Subscriber_.serviceProvider), serviceProvider))),
					builder.and(builder.equal(contact
							.get(Subscriber_.number), number)));
			
			result_contact = manager.createQuery(query).getSingleResult();
			
		} catch(NoResultException nre){
			return result_contact;
		}finally {
			manager.close();
		}
		return result_contact;
	}

	@Override
	public boolean exists(String code, String serviceProvider, String number) {
		final Subscriber subscriber = findByCodePhoneNo(code, serviceProvider, number);
		if(subscriber.equals(null))
			return false;
		return true;
	}
}
