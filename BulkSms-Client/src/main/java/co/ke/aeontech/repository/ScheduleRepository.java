package co.ke.aeontech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	public Schedule findByName(String schedule_name);

	public List<Schedule> findByOrganisationId(Long id);
}
