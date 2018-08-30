package co.ke.aeontech.configurations.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import co.ke.aeontech.models.User;
import co.ke.aeontech.services.AeonService;

public class CustomUserDetails extends User implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AeonService aeonService;
	
	public CustomUserDetails(final User user, AeonService aeonService) {
		super(user);
		this.aeonService = aeonService;
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if(!aeonService.findOrganisationByEmail(getUsername()))
			return false;
		return super.getCredentials().isActive();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return AuthorityUtils.createAuthorityList(super.getCredentials().getRole().getRole());
	}

	@Override
	public String getPassword() {
		return super.getCredentials().getPassword();
	}

}
