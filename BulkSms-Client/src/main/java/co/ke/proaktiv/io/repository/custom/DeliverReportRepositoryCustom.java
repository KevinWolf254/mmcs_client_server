package co.ke.proaktiv.io.repository.custom;

import java.util.Date;
import java.util.List;

import co.ke.proaktiv.io.models.DeliveryReport;

public interface DeliverReportRepositoryCustom {

	public List<DeliveryReport> findBtwnDates(Date from, Date to, Long id);
}
