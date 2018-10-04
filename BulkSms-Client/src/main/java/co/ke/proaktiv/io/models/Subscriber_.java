package co.ke.proaktiv.io.models;

import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Subscriber.class)
public abstract class Subscriber_ {
	public static volatile SingularAttribute<Subscriber, Long> id;
	public static volatile SingularAttribute<Subscriber, String> number;
	public static volatile SingularAttribute<Subscriber, String> fullPhoneNo;
	public static volatile SingularAttribute<Subscriber, ServiceProvider> serviceProvider;
	public static volatile SingularAttribute<Subscriber, Prefix> prefix;
	public static volatile SetAttribute<Subscriber, Set<Group_>> groups;
}
