package co.ke.aeontech.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import co.ke.aeontech.models.Contact;
import co.ke.aeontech.models.Contact_;
import co.ke.aeontech.repository.custom.ContactRepositoryCustom;

public class ContactRepositoryImpl implements ContactRepositoryCustom{
	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@Override
	public Contact findByCodePhoneNo(String countryCode, String phoneNo) {
		final EntityManager manager = factory.createEntityManager();
		Contact result_contact = new Contact();
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Contact> query = builder.createQuery(Contact.class);
			Root<Contact> contact = query.from(Contact.class);
			query.where(builder.equal(contact.get(Contact_.countryCode), countryCode),
					(builder.and(builder.equal(contact.get(Contact_.phoneNumber), phoneNo))));
			result_contact = manager.createQuery(query).getSingleResult();
			
		} catch(NoResultException nre){
			return null;
		}finally {
			manager.close();
		}
		return result_contact;
	}

}
