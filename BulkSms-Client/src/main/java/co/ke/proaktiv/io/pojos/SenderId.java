package co.ke.proaktiv.io.pojos;

import java.util.Set;

import co.ke.proaktiv.io.models.Country;

public class SenderId {

	private Long id;
	private String name;
	private Set<Country> countries;
	
	public SenderId() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Country> getCountries() {
		return countries;
	}
	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}
}
