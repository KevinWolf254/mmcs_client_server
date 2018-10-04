package co.ke.proaktiv.io.models;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="user_credentials")
public class UserCredentials {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="enabled")
	private boolean enabled;	

	@Column(name="password")
	private String password;
	
	@Column(name="sign_in")
	private Date signIn;
	
	@OneToMany(mappedBy = "credentials", orphanRemoval = true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<UserRole> roles = new HashSet<UserRole>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable=false)
	private User user;
	
	public UserCredentials() {			
		super();
	}
	public UserCredentials(String password, User user) {
		super();
		this.password = password;
		this.user = user;
	}
	public UserCredentials(boolean enabled, String password, User user) {
		super();
		this.enabled = enabled;
		this.password = password;
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getSignIn() {
		return signIn;
	}
	public void setSignIn(Date signIn) {
		this.signIn = signIn;
	}	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserCredentials [id=")
				.append(id)
				.append(", enabled=")
				.append(enabled)
				.append(", signIn=")
				.append(signIn)
				.append("]");
		return builder.toString();
	}	
}
