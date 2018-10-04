package co.ke.proaktiv.io.repository.custom;

import co.ke.proaktiv.io.models.Subscriber;

public interface SubscriberRepositoryCustom {
	
	public Subscriber find(String code, String prefix, String number);
}
