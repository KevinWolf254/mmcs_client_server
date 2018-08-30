package co.ke.aeontech.models;

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

import co.ke.aeontech.pojos.helpers.ServiceProvider;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="contact")
public class Contact {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="country_code", nullable = false)
	private String countryCode;
	
	@Column(name="phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name="tele_com", nullable = false)
	private ServiceProvider teleCom;
	
	@ManyToMany(fetch = FetchType.LAZY, 
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="org_contacts",
		inverseJoinColumns=@JoinColumn(name = "org_fk"),
		joinColumns=@JoinColumn(name = "client_fk"))
	private Set<Organisation> suppliers = new HashSet<Organisation>();
	
	@ManyToMany(fetch = FetchType.LAZY, 
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="contacts_grouped",
		inverseJoinColumns=@JoinColumn(name = "group_fk"),
		joinColumns=@JoinColumn(name = "contact_fk"))
	private Set<Group> groups = new HashSet<Group>();
	
	public void addNewOrganisation(Organisation organisation) {
		this.suppliers.add(organisation);
	}
	
	public Contact() {
		super();
	}

	public Contact(String countryCode, String phoneNumber, Organisation supplier,
			ServiceProvider teleCom) {
		super();
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.suppliers.add(supplier);
		this.teleCom = teleCom;
	}

	public Contact(String countryCode, String phoneNumber, ServiceProvider teleCom, Organisation supplier,
			Group group) {
		super();
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.teleCom = teleCom;
		this.suppliers.add(supplier);
		this.groups.add(group);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public ServiceProvider getTeleCom() {
		return teleCom;
	}

	public void setTeleCom(ServiceProvider teleCom) {
		this.teleCom = teleCom;
	}

	@JsonIgnore
	public Set<Organisation> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Set<Organisation> supplier) {
		this.suppliers = supplier;
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
		builder.append("Client [id=")
				.append(id)
				.append(", countryCode=")
				.append(countryCode)
				.append(", phoneNumber=")
				.append(phoneNumber)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
		Contact other = (Contact) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}
	
}
