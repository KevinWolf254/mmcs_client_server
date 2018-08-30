package co.ke.aeontech.models;

import java.util.Set;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Organisation.class)
public abstract class Organisation_ {
	public static volatile SingularAttribute<Organisation, Long> id;
	
	public static volatile SingularAttribute<Organisation, Long> number;
	
	public static volatile SingularAttribute<Organisation, Set<User>> employees;
	
	public static volatile SingularAttribute<Organisation, Set<Contact>> contacts;
	
	public static volatile SingularAttribute<Organisation, Set<Group>> groups;
	
	public static volatile SingularAttribute<Organisation, Set<Schedule>> schedules;
	
	public static volatile SingularAttribute<Organisation, Set<DeliveryReport>> deliveryReports;
}
