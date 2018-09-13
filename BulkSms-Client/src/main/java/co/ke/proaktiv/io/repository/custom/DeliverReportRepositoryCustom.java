package co.ke.proaktiv.io.repository.custom;

import java.util.Date;
import java.util.List;

import co.ke.proaktiv.io.models.DeliveryReport;

public interface DeliverReportRepositoryCustom {

	public List<DeliveryReport> search(Date from, Date to, Long id);
}
