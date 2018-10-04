package co.ke.proaktiv.io.services;

import java.util.List;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.models.ServiceProvider;


public interface ServiceProviderService {

	public List<ServiceProvider> findAll();

	public ServiceProvider find(Country country, Prefix prefix);
}
