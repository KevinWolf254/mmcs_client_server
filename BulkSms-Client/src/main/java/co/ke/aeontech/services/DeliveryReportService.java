package co.ke.aeontech.services;

import java.util.Date;
import java.util.List;

import co.ke.aeontech.models.DeliveryReport;

public interface DeliveryReportService {
	
	public List<DeliveryReport> findByOrganisationId(Long id);
	
	public DeliveryReport findByMessageId(String messageId);

	public DeliveryReport save(DeliveryReport deliveryReport);

	public List<DeliveryReport> SearchBtw(Date from, Date to, Long id);
}
