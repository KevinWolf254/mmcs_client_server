package co.ke.proaktiv.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.DeliveryReport;
import co.ke.proaktiv.io.repository.custom.DeliverReportRepositoryCustom;

public interface DeliveryReportRepository extends JpaRepository<DeliveryReport, Long>, DeliverReportRepositoryCustom {

	public List<DeliveryReport> findByOrganisationId(Long id);

	public DeliveryReport findByMessageId(String messageId);
}
