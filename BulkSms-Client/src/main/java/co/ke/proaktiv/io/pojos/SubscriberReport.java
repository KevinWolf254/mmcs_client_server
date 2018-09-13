package co.ke.proaktiv.io.pojos;

import co.ke.proaktiv.io.pojos.helpers.ServiceProvider;

public class SubscriberReport {

	private ServiceProvider serviceProvider;
	private long totals;
	public SubscriberReport() {
		super();
	}
	public SubscriberReport(ServiceProvider serviceProvider, long totals) {
		super();
		this.serviceProvider = serviceProvider;
		this.totals = totals;
	}
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public long getTotals() {
		return totals;
	}
	public void setTotals(long totals) {
		this.totals = totals;
	}	
}
