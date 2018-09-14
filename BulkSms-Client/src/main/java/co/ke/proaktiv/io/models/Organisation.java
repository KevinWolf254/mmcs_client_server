package co.ke.proaktiv.io.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
	@Column(name="id")
	private Long id;
	
	@Column(name="name", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<User> users  = new HashSet<User>();
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Group> groups = new HashSet<Group>();
	
	@OneToMany(mappedBy = "organisation", orphanRemoval=true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<DeliveryReport> deliveryReports = new HashSet<DeliveryReport>();
	
	public Organisation() {
		super();
	}

	public Organisation(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@JsonIgnore
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
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
//				.append(", number=")
//				.append(number)
				.append(", name=")
				.append(name)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}
