package co.ke.proaktiv.io.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.proaktiv.io.pojos.helpers.ServiceProvider;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="subscriber")
public class Subscriber {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="code", length = 4, nullable = false)
	private String code;
	
	@Column(name="service_provider", length = 3, nullable = false)
	private String serviceProvider;
	
	@Column(name="number", length = 6, nullable = false)
	private String number;
	
	@Column(name="number",nullable = false)
	private ServiceProvider category;
	
	@ManyToMany(fetch = FetchType.LAZY, 
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="group_subscriber",
		inverseJoinColumns=@JoinColumn(name = "group_id"),
		joinColumns=@JoinColumn(name = "subscriber_id"))
	private Set<Group> groups = new HashSet<Group>();
	
	public Subscriber() {
		super();
	}
	
	public Subscriber(String code, String serviceProvider, String number, 
			ServiceProvider category, Group group) {
		super();
		this.code = code;
		this.serviceProvider = serviceProvider;
		this.number = number;
		this.category = category;
		getGroups().add(group);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String subscriberNumber) {
		this.number = subscriberNumber;
	}
	
	public ServiceProvider getCategory() {
		return category;
	}

	public void setCategory(ServiceProvider category) {
		this.category = category;
	}

	@JsonIgnore
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("MobilePhone [countryCode=")
				.append(code)
				.append(", serviceProvider=")
				.append(serviceProvider)
				.append(", number=")
				.append(number)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((serviceProvider == null) ? 0 : serviceProvider.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscriber other = (Subscriber) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (serviceProvider == null) {
			if (other.serviceProvider != null)
				return false;
		} else if (!serviceProvider.equals(other.serviceProvider))
			return false;
		return true;
	}	
}
