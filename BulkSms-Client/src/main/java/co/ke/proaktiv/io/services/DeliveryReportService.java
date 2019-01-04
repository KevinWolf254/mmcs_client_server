package co.ke.proaktiv.io.services;

import java.util.Date;
import java.util.List;

import co.ke.proaktiv.io.models.DeliveryReport;

public interface DeliveryReportService {
	
	public DeliveryReport save(DeliveryReport report);
	
	public List<DeliveryReport> findByOrganisationId(Long id);
	
	public DeliveryReport findByMessageId(String messageId);

	public List<DeliveryReport> findBtwnDates(Date from, Date to, Long id);
}
