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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="organisation")
public class Organisation {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="number", unique = true)
	private Long number;
	
	@Column(name="name", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "employer", orphanRemoval=true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<User> employees  = new HashSet<User>();
	
	@ManyToMany(mappedBy = "suppliers",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Contact> contacts = new HashSet<Contact>();
	
//	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
//			fetch = FetchType.LAZY,
//			cascade = CascadeType.ALL)
//	private Set<Cost> costs = new HashSet<Cost>();
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Group> groups = new HashSet<Group>();
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Schedule> schedules = new HashSet<Schedule>();
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<DeliveryReport> deliveryReports = new HashSet<DeliveryReport>();
	
	public Organisation() {
		super();
	}

	public Organisation(Long number, String name) {
		super();
		this.number = number;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Set<User> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<User> employees) {
		this.employees = employees;
	}

	@JsonIgnore
	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> clients) {
		this.contacts = clients;
	}

	@JsonIgnore
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@JsonIgnore
	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	@JsonIgnore
	public Set<DeliveryReport> getDeliveryReports() {
		return deliveryReports;
	}

	public void setDeliveryReports(Set<DeliveryReport> deliveryReport) {
		this.deliveryReports = deliveryReport;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Organisation [id=")
				.append(id)
				.append(", number=")
				.append(number)
				.append(", name=")
				.append(name)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Organisation other = (Organisation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}	
	
}
