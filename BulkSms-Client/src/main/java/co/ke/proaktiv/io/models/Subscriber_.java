package co.ke.proaktiv.io.models;

import java.util.Set;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.proaktiv.io.pojos.helpers.ServiceProvider;

@StaticMetamodel(Subscriber.class)
public abstract class Subscriber_ {
	public static volatile SingularAttribute<Subscriber, Long> id;
	public static volatile SingularAttribute<Subscriber, String> code;
	public static volatile SingularAttribute<Subscriber, String> serviceProvider;
	public static volatile SingularAttribute<Subscriber, String> number;
	public static volatile SingularAttribute<Subscriber, ServiceProvider> category;
	public static volatile SingularAttribute<Subscriber, Set<Group>> groups;
}
