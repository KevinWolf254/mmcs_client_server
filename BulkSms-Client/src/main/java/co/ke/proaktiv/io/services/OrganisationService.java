package co.ke.proaktiv.io.services;

import java.util.Optional;

import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.pojos.AdminRole;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.response.Credit;
import co.ke.proaktiv.io.pojos.response.SignUp;

public interface OrganisationService {
	
	public Optional<Organisation> findById(Long id);
	
	public Optional<Organisation> findByName(String name);
	
	public SignUp save(Client client, AdminRole admin);

	public Organisation save(Organisation organisation);

	public boolean isEnabled(String email);
	
	public Credit getClient(String email);
}
