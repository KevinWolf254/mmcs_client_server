package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Text;

public interface TextRepository extends JpaRepository<Text, Long>{

	public Text findByScheduleId(Long id);

}
