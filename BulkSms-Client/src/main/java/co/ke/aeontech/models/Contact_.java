package co.ke.aeontech.models;

import java.util.Set;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.aeontech.pojos.helpers.ServiceProvider;

@StaticMetamodel(Contact.class)
public abstract class Contact_ {
	public static volatile SingularAttribute<Contact, Long> id;
	public static volatile SingularAttribute<Contact, String> countryCode;
	public static volatile SingularAttribute<Contact, String> phoneNumber;
	public static volatile SingularAttribute<Contact, ServiceProvider> teleCom;
	public static volatile SingularAttribute<Contact, Set<Organisation>> suppliers;
	public static volatile SingularAttribute<Contact, Set<Group>> groups;
}
