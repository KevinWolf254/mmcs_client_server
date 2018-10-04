package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.repository.PrefixRepository;
import co.ke.proaktiv.io.services.PrefixService;

@Service
public class PrefixServiceImpl implements PrefixService {

	@Autowired
	private PrefixRepository repository;
	@Override
	public Prefix findByNumber(String provider) {
		// TODO Auto-generated method stub
		return repository.findByNumber(provider);
	}

}
