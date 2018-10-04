package co.ke.proaktiv.io.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.proaktiv.io.pojos.helpers.Role;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="user_role")
public class UserRole {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="role")
	private Role role;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="credentials_id", nullable = false)
	private UserCredentials credentials;

	public UserRole() {
		super();
	}

	public UserRole(Role role) {
		super();
		this.role = role;
	}

	public UserRole(String role) {
		super();
		SetRole(role);
	}
	
	public UserRole(Role role, UserCredentials credentials) {
		super();
		this.role = role;
		this.credentials = credentials;
	}
	public UserRole(String role, UserCredentials credentials) {
		super();
		SetRole(role);
		this.credentials = credentials;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public void SetRole(String role) {
		if(role.equals(Role.ADMIN.getRole()))
			this.role = Role.ADMIN;
		else if(role.equals(Role.USER.getRole()))
			this.role = Role.USER;
	}
	
	@JsonIgnore
	public UserCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(UserCredentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((credentials == null) ? 0 : credentials.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		UserRole other = (UserRole) obj;
		if (credentials == null) {
			if (other.credentials != null)
				return false;
		} else if (!credentials.equals(other.credentials))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserRole [id=")
				.append(id)
				.append(", role=")
				.append(role)
				.append(", credentials=")
				.append(credentials)
				.append("]");
		return builder.toString();
	}	
}
