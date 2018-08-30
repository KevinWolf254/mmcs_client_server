package co.ke.aeontech.services;

import co.ke.aeontech.pojos._Charges;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.pojos.requests.Sms;
import co.ke.aeontech.pojos.response.Response;

public interface SmsService {

	public Response sendSmsToAll(Sms sms);

	public Response sendSmsToGroup(Sms sms);
	
	public double calculateSmsCost(final _ContactsTotals contactNos, final _Charges charges);

}
