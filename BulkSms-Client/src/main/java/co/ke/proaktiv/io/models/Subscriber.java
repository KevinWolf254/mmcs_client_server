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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="subscriber")
public class Subscriber {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="number", length = 9, nullable = false)
	private String number;
	
	@Column(name="full_phone_no", length = 13, nullable = false, unique=true)
	private String fullPhoneNo;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})	
	@JoinColumn(name = "service_provider_id", nullable = false)
	private ServiceProvider serviceProvider;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})	
	@JoinColumn(name = "prefix_id", nullable = false)
	private Prefix prefix;
	
	@ManyToMany(fetch = FetchType.LAZY, 
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="group_subscriber",
		inverseJoinColumns=@JoinColumn(name = "group_id"),
		joinColumns=@JoinColumn(name = "subscriber_id"))
	private Set<Group_> groups = new HashSet<Group_>();
	
	public Subscriber() {
		super();
	}
	public Subscriber(String code, String number, Prefix prefix, ServiceProvider sp, Group_ group) {
		super();
		this.serviceProvider = sp;
		this.prefix = prefix;
		this.number = number;
		this.fullPhoneNo = combine(code, number);
		this.groups.add(group);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getFullPhoneNo() {
		return fullPhoneNo;
	}
	public void setFullPhoneNo(String fullPhoneNo) {
		this.fullPhoneNo = fullPhoneNo;
	}
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public Prefix getPrefix() {
		return prefix;
	}
	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}
	@JsonIgnore
	public Set<Group_> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group_> groups) {
		this.groups = groups;
	}
	private String combine(String code, String number) {
		final StringBuilder builder = new StringBuilder(code).append(number);
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullPhoneNo == null) ? 0 : fullPhoneNo.hashCode());
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
		if (fullPhoneNo == null) {
			if (other.fullPhoneNo != null)
				return false;
		} else if (!fullPhoneNo.equals(other.fullPhoneNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Subscriber [id=").append(id)
				.append(", number=").append(number)
				.append(", fullPhoneNo=").append(fullPhoneNo)
				.append("]");
		return builder.toString();
	}
}
