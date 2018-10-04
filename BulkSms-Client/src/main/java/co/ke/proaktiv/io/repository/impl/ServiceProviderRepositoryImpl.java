package co.ke.proaktiv.io.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.models.ServiceProvider;
import co.ke.proaktiv.io.repository.custom.ServiceProviderRepositoryCustom;

public class ServiceProviderRepositoryImpl implements ServiceProviderRepositoryCustom {
	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public ServiceProvider find(Country country, Prefix prefix) {
		final EntityManager manager = factory.createEntityManager();
		ServiceProvider provider = null;
		try {
			final String sql = new StringBuilder("select distinct sp from ServiceProvider sp")
					.append(" join sp.countries c")
					.append(" join sp.prefixs p")
					.append(" where c.name = :country_name")
					.append(" and p.number = :prefix_number").toString();
			provider = manager.createQuery(sql, ServiceProvider.class)
					.setParameter("country_name", country.getName())
					.setParameter("prefix_number", prefix.getNumber())
					.getSingleResult();
			
		} catch(NoResultException nre){
			return new ServiceProvider();
		}finally {
			manager.close();
		}
		return provider;
	}

}
