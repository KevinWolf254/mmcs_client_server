package co.ke.proaktiv.io.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import co.ke.proaktiv.io.models.DeliveryReport;
import co.ke.proaktiv.io.models.DeliveryReport_;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Organisation_;
import co.ke.proaktiv.io.repository.custom.DeliverReportRepositoryCustom;

public class DeliveryReportRepositoryImpl implements DeliverReportRepositoryCustom {

	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@Override
	public List<DeliveryReport> search(final Date from, final Date to, final Long id) {
		final EntityManager manager = factory.createEntityManager();
		List<DeliveryReport> reports = new ArrayList<DeliveryReport>();
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<DeliveryReport> query = builder.createQuery(DeliveryReport.class);
			
			Root<DeliveryReport> requestRoot = query.from(DeliveryReport.class);			
			Path<Date> date = requestRoot.get(DeliveryReport_.date);

			Join<DeliveryReport, Organisation> join = requestRoot.join(DeliveryReport_.organisation);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.between(date, from, to), 
							builder.equal(join.get(Organisation_.id), id)));
			
			reports = manager.createQuery(query).getResultList();
		} catch(NoResultException nre){
			return null;
		}finally {
			manager.close();
		}
		return reports;
	}

}
