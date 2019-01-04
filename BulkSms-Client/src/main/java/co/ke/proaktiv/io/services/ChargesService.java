package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.pojos.ChargesReport;
import co.ke.proaktiv.io.pojos.Sms;

public interface ChargesService {

	/**
	 * Calculates the charges for sending an sms
	 * @param id for the organization
	 * @param sms
	 * @return
	 */
	public ChargesReport calculate(Sms sms);

}
