package co.ke.proaktiv.io.models;

import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ServiceProvider.class)
public abstract class ServiceProvider_ {

	public static volatile SingularAttribute<ServiceProvider, Long> id;
	public static volatile SingularAttribute<ServiceProvider, String> name;
	public static volatile SingularAttribute<ServiceProvider, Country> country;
	public static volatile SetAttribute<ServiceProvider, Set<Subscriber>> subscribers;
	public static volatile SetAttribute<ServiceProvider, Set<Prefix>> prefixs;
}
