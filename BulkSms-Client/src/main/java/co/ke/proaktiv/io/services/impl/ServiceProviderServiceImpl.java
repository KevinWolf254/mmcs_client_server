package co.ke.proaktiv.io.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.models.ServiceProvider;
import co.ke.proaktiv.io.repository.ServiceProviderRepository;
import co.ke.proaktiv.io.services.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

	@Autowired
	private ServiceProviderRepository repository;
	
	@Override
	public List<ServiceProvider> findAll() {
		return repository.findAll();
	}

	@Override
	public ServiceProvider find(Country country, Prefix prefix) {
		return repository.find(country, prefix);
	}

	@Override
	public Optional<ServiceProvider> findById(Long id) {
		return repository.findById(id);
	}

}
