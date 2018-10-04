package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.Country;

public interface CountryService {

	public Country findByName(String name);

	public Country findByCode(String code);

}
