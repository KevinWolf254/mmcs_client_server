package co.ke.proaktiv.io.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<DeliveryReport> findBtwnDates(final Date from, final Date to, final Long id) {
		final CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<DeliveryReport> query = builder.createQuery(DeliveryReport.class);
		
		final Root<DeliveryReport> requestRoot = query.from(DeliveryReport.class);			
		final Path<Date> date = requestRoot.get(DeliveryReport_.date);

		final Join<DeliveryReport, Organisation> join = requestRoot.join(DeliveryReport_.organisation);
		
		query = query.select(requestRoot).distinct(true)
				.where(builder.and(builder.between(date, from, to), 
						builder.equal(join.get(Organisation_.id), id)));
		
		return manager.createQuery(query).getResultList();
	}
}
