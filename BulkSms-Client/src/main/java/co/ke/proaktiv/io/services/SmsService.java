package co.ke.proaktiv.io.services;

import java.util.Set;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.response.SmsDeliveryResponse;

/**
 * 
 * @author Kevin
 * @version 1.0.0
 * @since 2018
 */
public interface SmsService {
	
	/**
	 * sends SMS to all subscribers for a particular organization
	 * @param user
	 * @param message
	 * @return SmsDeliveryResponse
	 */
	public SmsDeliveryResponse send(User user, String senderId, String message);
	
	/**
	 * sends SMS to a selected number of groups
	 * @param user
	 * @param message
	 * @param groupIds
	 * @return SmsDeliveryResponse
	 */
	public SmsDeliveryResponse send(User user, String senderId, String message, Set<Long> groupIds);
}
