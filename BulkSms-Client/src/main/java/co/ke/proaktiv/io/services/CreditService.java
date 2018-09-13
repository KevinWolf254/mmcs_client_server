package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.pojos.CreditCharges;
import co.ke.proaktiv.io.pojos.response.CreditResponse;

public interface CreditService {

	public CreditResponse findByEmail(String email);
	
	public CreditCharges findByOrganisationId(Long id);
}
