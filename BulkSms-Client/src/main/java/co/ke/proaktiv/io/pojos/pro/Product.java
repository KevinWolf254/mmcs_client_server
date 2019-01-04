package co.ke.proaktiv.io.pojos.pro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.ke.proaktiv.io.models.Country;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
	
	private Long id;	
	private String name;
	private Country country;	
	public Product() {
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
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
}
