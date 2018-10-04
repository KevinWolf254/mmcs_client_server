package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.repository.CountryRepository;
import co.ke.proaktiv.io.services.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository repository;
	
	@Override
	public Country findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Country findByCode(String code) {
		return repository.findByCode(code);
	}

}
