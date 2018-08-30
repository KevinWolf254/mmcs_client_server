package co.ke.aeontech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Contact;
import co.ke.aeontech.repository.custom.ContactRepositoryCustom;

public interface ContactRepository extends JpaRepository<Contact, Long>, ContactRepositoryCustom {

	public List<Contact> findByPhoneNumber(String phoneNumber);

	public List<Contact> findBySuppliersId(Long id);

	public List<Contact> findByGroupsId(Long id);

}
