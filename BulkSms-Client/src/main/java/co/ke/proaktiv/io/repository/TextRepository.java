package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Text;

public interface TextRepository extends JpaRepository<Text, Long>{

	public Text findByScheduleId(Long id);

}
