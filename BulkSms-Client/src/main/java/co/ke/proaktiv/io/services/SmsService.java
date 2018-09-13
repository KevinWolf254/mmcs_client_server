package co.ke.proaktiv.io.services;

import java.util.Set;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.response.SmsDeliveryResponse;

public interface SmsService {
	
	public SmsDeliveryResponse send(final User user, final String message);
	
	public SmsDeliveryResponse send(final User user, final String message, final Set<Long> groupIds);
	
//	public double calculateSmsCost(final _ContactsTotals contactNos, final _Charges charges);
}
