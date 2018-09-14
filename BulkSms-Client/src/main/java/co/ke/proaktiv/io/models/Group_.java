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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="group_")
public class Group_ {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;
	
	@ManyToMany(mappedBy = "groups",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Subscriber> subscribers = new HashSet<Subscriber>();
	
	@ManyToMany(mappedBy = "groups",
	fetch = FetchType.LAZY,
	cascade = CascadeType.ALL)
	private Set<Schedule> schedules = new HashSet<Schedule>();
		
	public Group_() {
		super();
	}

	public Group_(String name, Organisation organisation) {
		super();
		final StringBuilder build = new StringBuilder(""+organisation.getId())
				.append("_")
				.append(name);
		this.name = build.toString();
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
		this.name = this.organisation.getId()+"_"+name;
	}

	@JsonIgnore
	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}	
	
	@JsonIgnore
	public Set<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<Subscriber> subscribers) {
		this.subscribers = subscribers;
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
		Group_ other = (Group_) obj;
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
