package co.ke.proaktiv.io.pojos;

import co.ke.proaktiv.io.models.ServiceProvider;

public class ServiceProviderReport {

	private ServiceProvider provider;
	private long subscribers;
	public ServiceProviderReport() {
		super();
	}
	public ServiceProviderReport(ServiceProvider name, long subscribers) {
		super();
		this.provider = name;
		this.subscribers = subscribers;
	}
	public ServiceProvider getProvider() {
		return provider;
	}
	public void setProvider(ServiceProvider serviceProvider) {
		this.provider = serviceProvider;
	}
	public long getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(long subscribers) {
		this.subscribers = subscribers;
	}
	@Override
	public String toString() {
		return "ServiceProviderReport [provider=" + provider + ", subscribers=" + subscribers + "]";
	}
}
