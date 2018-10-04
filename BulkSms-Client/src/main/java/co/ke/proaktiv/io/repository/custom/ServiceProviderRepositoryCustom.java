package co.ke.proaktiv.io.repository.custom;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.models.ServiceProvider;

public interface ServiceProviderRepositoryCustom {

	public ServiceProvider find(Country country, Prefix prefix);
}
