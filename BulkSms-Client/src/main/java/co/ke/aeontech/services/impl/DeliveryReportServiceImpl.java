package co.ke.aeontech.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.DeliveryReport;
import co.ke.aeontech.repository.DeliveryReportRepository;
import co.ke.aeontech.services.DeliveryReportService;

@Service
public class DeliveryReportServiceImpl implements DeliveryReportService {

	@Autowired
	private DeliveryReportRepository repository;
	
	@Override
	public DeliveryReport findByMessageId(String messageId) {
		return repository.findByMessageId(messageId);
	}
	@Override
	public DeliveryReport save(DeliveryReport deliveryReport) {
		return repository.save(deliveryReport);
	}
	@Override
	public List<DeliveryReport> findByOrganisationId(Long id) {
		return repository.findByOrganisationId(id);
	}
	@Override
	public List<DeliveryReport> SearchBtw(Date from, Date to, Long id) {
		return repository.SearchBtw(from, to, id);
	}
}
