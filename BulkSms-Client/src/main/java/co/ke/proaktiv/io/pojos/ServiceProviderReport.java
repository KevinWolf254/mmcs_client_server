package co.ke.proaktiv.io.pojos;

import co.ke.proaktiv.io.models.ServiceProvider;

public class ServiceProviderReport {

	private ServiceProvider name;
	private long totalSubscribers;
	public ServiceProviderReport() {
		super();
	}
	public ServiceProviderReport(ServiceProvider name, long totalSubscribers) {
		super();
		this.name = name;
		this.totalSubscribers = totalSubscribers;
	}
	public ServiceProvider getName() {
		return name;
	}
	public void setName(ServiceProvider serviceProvider) {
		this.name = serviceProvider;
	}
	public long getTotalSubscribers() {
		return totalSubscribers;
	}
	public void setTotalSubscribers(long totals) {
		this.totalSubscribers = totals;
	}	
}
