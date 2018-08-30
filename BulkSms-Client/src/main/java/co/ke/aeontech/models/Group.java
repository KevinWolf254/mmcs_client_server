package co.ke.aeontech.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
//import java.util.Set;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="contact_group")
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="org_contact_groups",
				inverseJoinColumns=@JoinColumn(name = "org_fk"),
				joinColumns=@JoinColumn(name = "group_fk"))
	private Organisation organisation;
	
	@ManyToMany(mappedBy = "groups",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Contact> contacts = new HashSet<Contact>();
	
	@ManyToMany(mappedBy = "groups",
	fetch = FetchType.LAZY,
	cascade = CascadeType.ALL)
	private Set<Schedule> schedules = new HashSet<Schedule>();
	
//	@ManyToOne(fetch = FetchType.LAZY, 
//			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//			@JoinTable(name="ondemand_groups",
//				inverseJoinColumns=@JoinColumn(name = "sms_fk"),
//				joinColumns=@JoinColumn(name = "group_fk"))
//	private _Sms _sms;
		
	public Group() {
		super();
	}

	public Group(String name, Organisation organisation) {
		super();
		this.name = organisation.getNumber()+"_"+name;
		this.organisation = organisation;
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
		this.name = this.organisation.getNumber()+"_"+name;
	}

	@JsonIgnore
	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	@JsonIgnore
	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@JsonIgnore
	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Group [name=")
				.append(name)
				.append("]");
		return builder.toString();
	}
	
}
