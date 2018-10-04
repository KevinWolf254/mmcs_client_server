package co.ke.proaktiv.io.models;

import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Prefix.class)
public abstract class Prefix_ {
	public static volatile SingularAttribute<Prefix, Long> id;
	public static volatile SingularAttribute<Prefix, String> number;
	public static volatile SetAttribute<Prefix, Set<ServiceProvider>> serviceProviders;
	public static volatile SetAttribute<Prefix, Set<Organisation>> subscribers;

}
