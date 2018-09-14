package co.ke.proaktiv.io.configurations.authentication;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.services.OrganisationService;
import co.ke.proaktiv.io.services.UserCredentialsService;
import co.ke.proaktiv.io.services.UserRoleService;

public class CustomUserDetails extends User implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrganisationService orgService;
	private UserCredentialsService credService;
	private UserRoleService roleService;
	private int i;
	
	public CustomUserDetails(final User user, final UserCredentialsService credService,  
			final UserRoleService roleService, final OrganisationService orgService) {
		super(user);
		this.roleService = roleService;
		this.orgService = orgService;
		this.credService = credService;
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
		if(!orgService.isEnabled(getUsername()))
			return false;
		return credService.findByUser(this).isEnabled();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final UserCredentials cred = credService.findByUser(this);
		final Set<UserRole> roles = roleService.findByUserCredentials(cred);
		
		final String[] userRoles = new String[roles.size()];
		i = 0;
		roles.stream().forEach(role ->{
			i = i++;
			userRoles[i] = role.getRole().getRole();
		});		
		return AuthorityUtils.createAuthorityList(userRoles);
	}

	@Override
	public String getPassword() {
		return credService.findByUser(this).getPassword();
	}

}
