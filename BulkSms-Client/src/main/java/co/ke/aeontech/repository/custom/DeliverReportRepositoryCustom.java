package co.ke.aeontech.repository.custom;

import java.util.Date;
import java.util.List;

import co.ke.aeontech.models.DeliveryReport;

public interface DeliverReportRepositoryCustom {

	public List<DeliveryReport> SearchBtw(Date from, Date to, Long id);
}
