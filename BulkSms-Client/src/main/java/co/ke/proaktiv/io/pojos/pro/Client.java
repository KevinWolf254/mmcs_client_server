package co.ke.proaktiv.io.pojos.pro;

import java.util.Date;

import co.ke.proaktiv.io.pojos.helpers.Country;

public class Client {
	
	private Long id;	
	private Country country;
	private String name;
    private boolean enabled;
    private Date createdOn;
	public Client() {
		super();
	}
	public Client(Country country, String name) {
		super();
		this.country = country;
		this.name = name;
		this.enabled = false;
		this.createdOn = new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
}
