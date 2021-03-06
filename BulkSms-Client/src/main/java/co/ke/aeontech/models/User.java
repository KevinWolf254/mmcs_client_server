package co.ke.aeontech.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="user")
public class User {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="surname")
	private String surname;
	
	@Column(name="other_names")
	private String otherNames;	
	
	@Column(name="email", nullable=false, unique=true)
	private String email;	
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="org_users",
				inverseJoinColumns=@JoinColumn(name = "org_fk"),
				joinColumns=@JoinColumn(name = "user_fk"))
	private Organisation employer;
	
	@OneToOne(mappedBy = "profile",
	        cascade = CascadeType.ALL, orphanRemoval = true)
	private UserCredentials credentials;
	
	public User() {
		super();
	}

	public User(User user) {
		super();
		this.id = user.getId();
		this.surname = user.getSurname();
		this.otherNames = user.getOtherNames();
		this.email = user.getEmail();
		this.credentials = user.getCredentials();
		this.employer = user.getEmployer();
	}
	
	public User(String surname, String otherNames, String email) {
		super();
		this.surname = surname;
		this.otherNames = otherNames;
		this.email = email;
	}

	public User(String surname, String otherNames, String email, Organisation employer,
			UserCredentials credentials) {
		super();
		this.surname = surname;
		this.otherNames = otherNames;
		this.email = email;
		this.employer = employer;
		this.credentials = credentials;
	}
	
	public User(String surname, String otherNames, String email, Organisation employer) {
		super();
		this.surname = surname;
		this.otherNames = otherNames;
		this.email = email;
		this.employer = employer;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String firstName) {
		this.surname = firstName;
	}

	public String getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(String lastName) {
		this.otherNames = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public Organisation getEmployer() {
		return employer;
	}

	public void setEmployer(Organisation employer) {
		this.employer = employer;
	}
	@JsonIgnore
	public UserCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(UserCredentials credentials) {
		this.credentials = credentials;
	}
	
//	public boolean index(String name, String password) {
//		return this.email.equals(name) && this.credentials.getPassword().equals(password);
//	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("User [id=")
				.append(id)
				.append(", surname=")
				.append(surname)
				.append(", otherNames=")
				.append(otherNames)
				.append(", email=")
				.append(email)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}	
}
