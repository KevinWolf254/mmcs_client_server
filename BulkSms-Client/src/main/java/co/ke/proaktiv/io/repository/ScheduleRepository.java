package co.ke.proaktiv.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	public Optional<Schedule> findByName(String schedule_name);
}
