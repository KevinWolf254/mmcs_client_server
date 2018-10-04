package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.Prefix;

public interface PrefixService {

	public Prefix findByNumber(String provider);

}
