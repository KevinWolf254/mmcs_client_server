package co.ke.proaktiv.io.services;

import java.util.Optional;

import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.pojos.AdminRole;
import co.ke.proaktiv.io.pojos.pro.Client;
import co.ke.proaktiv.io.pojos.response.SignUpResponse;

public interface OrganisationService {
	
	public Optional<Organisation> findById(Long id);
	
	public Optional<Organisation> findByName(String name);
	
	public SignUpResponse save(Client client, AdminRole admin, String senderId);

	public Organisation save(Organisation organisation);

	public boolean isEnabled(String email);
}
