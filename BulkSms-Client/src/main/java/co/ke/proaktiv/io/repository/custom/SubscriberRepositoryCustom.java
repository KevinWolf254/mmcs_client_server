package co.ke.proaktiv.io.repository.custom;

import co.ke.proaktiv.io.models.Subscriber;

public interface SubscriberRepositoryCustom {
	
	public boolean exists(String code, String serviceProvider,
			String number);
	
	public Subscriber findByCodePhoneNo(String code, String serviceProvider,
			String number);
}
