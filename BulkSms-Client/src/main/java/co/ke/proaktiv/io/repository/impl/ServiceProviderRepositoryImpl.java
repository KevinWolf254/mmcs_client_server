package co.ke.proaktiv.io.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.models.ServiceProvider;
import co.ke.proaktiv.io.repository.custom.ServiceProviderRepositoryCustom;

public class ServiceProviderRepositoryImpl implements ServiceProviderRepositoryCustom {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public ServiceProvider find(Country country, Prefix prefix) {
		final String sql = new StringBuilder("select distinct sp from ServiceProvider sp")
				.append(" join sp.country c")
				.append(" join sp.prefixs p")
				.append(" where c.name = :country_name")
				.append(" and p.number = :prefix_number").toString();
		return manager.createQuery(sql, ServiceProvider.class)
				.setParameter("country_name", country.getName())
				.setParameter("prefix_number", prefix.getNumber())
				.getSingleResult();
	}

}
