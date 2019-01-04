package co.ke.proaktiv.io.services;

import java.util.List;

import co.ke.proaktiv.io.models.Country;

public interface CountryService {

	public Country findByName(String name);

	public Country findByCode(String code);

	public List<Country> findAll();

}
