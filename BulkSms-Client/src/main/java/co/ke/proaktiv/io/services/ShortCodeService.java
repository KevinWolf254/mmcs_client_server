package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.pojos.response.ShortCodeResponse;

public interface ShortCodeService {

	public ShortCodeResponse findByName(String name);
	
	public boolean exists(String name);
}
