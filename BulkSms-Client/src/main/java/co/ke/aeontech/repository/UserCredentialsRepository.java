package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.UserCredentials;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

}
