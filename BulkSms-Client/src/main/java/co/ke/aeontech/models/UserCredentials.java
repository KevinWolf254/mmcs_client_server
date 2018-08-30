package co.ke.aeontech.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.ke.aeontech.pojos.helpers.Role;

@Entity
@Table(name="user_credentials")
public class UserCredentials {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="is_active")
	private boolean isActive;	
	
	@Column(name="is_signed_in")
	private boolean isSignedIn;
	
	@Column(name="role", nullable=false)
	private Role role;

	@Column(name="password")
	private String password;
	
	@Column(name="last_sign_in_date", nullable=true)
	private Date lastSignInDate;
	
	@Column(name="current_sign_in_date", nullable=true)
	private Date currentSignInDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_profile", nullable=false)
	private User profile;
	
	public UserCredentials() {
	}
	
	public UserCredentials(Role role, String password, User profile) {
		super();
		this.isActive = true;
		this.isSignedIn = false;
		this.role = role;
		this.password = password;
		this.profile = profile;
	}

	public UserCredentials(boolean isActive, Role role, String password, User profile) {
		super();
		this.isActive = isActive;
		this.isSignedIn = false;
		this.role = role;
		this.password = password;
		this.profile = profile;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@JsonIgnore
	public boolean isSignedIn() {
		return isSignedIn;
	}

	public void setSignedIn(boolean isSignedIn) {
		this.isSignedIn = isSignedIn;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastSignInDate() {
		return lastSignInDate;
	}

	public void setLastSignInDate(Date lastSignInDate) {
		this.lastSignInDate = lastSignInDate;
	}

	@JsonIgnore
	public Date getCurrentSignInDate() {
		return currentSignInDate;
	}

	public void setCurrentSignInDate(Date currentSignInDate) {
		this.currentSignInDate = currentSignInDate;
	}

	@JsonIgnore
	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserCredentials [id=")
				.append(id)
				.append(", isActive=")
				.append(isActive)
				.append(", isSignedIn=")
				.append(isSignedIn)
				.append(", role=")
				.append(role)
				.append(", lastSignInDate=")
				.append(lastSignInDate)
				.append(", currentSignInDate=")
				.append(currentSignInDate)
				.append("]");
		return builder.toString();
	}
	
}
