package co.ke.proaktiv.io.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.DeliveryReport;
import co.ke.proaktiv.io.repository.DeliveryReportRepository;
import co.ke.proaktiv.io.services.DeliveryReportService;

@Service
public class DeliveryReportServiceImpl implements DeliveryReportService {

	@Autowired
	private DeliveryReportRepository repository;
	
	@Override
	public DeliveryReport save(final DeliveryReport deliveryReport) {
		return repository.save(deliveryReport);
	}
	
	@Override
	public DeliveryReport findByMessageId(final String messageId) {
		return repository.findByMessageId(messageId);
	}
	@Override
	public List<DeliveryReport> findByOrganisationId(final Long id) {
		return repository.findByOrganisationId(id);
	}
	@Override
	public List<DeliveryReport> search(final Date from, final Date to, final Long id) {
		return repository.search(from, to, id);
	}
}
