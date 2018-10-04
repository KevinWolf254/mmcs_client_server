package co.ke.proaktiv.io.models;

import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SingularAttribute<Country, Long> id;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile SingularAttribute<Country, String> code;	
	public static volatile SingularAttribute<Country, String> currency;
	public static volatile SetAttribute<Country, Set<ServiceProvider>> serviceProviders;
	public static volatile SetAttribute<Country, Set<Organisation>> organisations;
}
