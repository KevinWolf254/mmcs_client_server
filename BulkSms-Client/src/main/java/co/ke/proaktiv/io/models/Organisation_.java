package co.ke.proaktiv.io.models;

import java.util.Set;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Organisation.class)
public abstract class Organisation_ {
	public static volatile SingularAttribute<Organisation, Long> id;
	public static volatile SingularAttribute<Organisation, String> customerId;
	public static volatile SingularAttribute<Organisation, String> name;
	public static volatile SingularAttribute<Organisation, Set<User>> users;
	public static volatile SingularAttribute<Organisation, Set<Group_>> groups;	
	public static volatile SingularAttribute<Organisation, Set<DeliveryReport>> deliveryReports;
	public static volatile SingularAttribute<Organisation, Country> country;
}
