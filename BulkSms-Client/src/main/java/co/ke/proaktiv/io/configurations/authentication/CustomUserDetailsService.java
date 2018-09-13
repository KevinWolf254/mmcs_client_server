package co.ke.proaktiv.io.configurations.authentication;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.models.UserCredentials;
import co.ke.proaktiv.io.models.UserRole;
import co.ke.proaktiv.io.services.OrganisationService;
import co.ke.proaktiv.io.services.UserCredentialsService;
import co.ke.proaktiv.io.services.UserRoleService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	private OrganisationService orgService;
	@Autowired
	private UserCredentialsService credService;
	@Autowired
	private UserRoleService roleService;
	
	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		final Optional<User> user = userService.findByEmail(email);	
		if(!user.isPresent())
			throw new UsernameNotFoundException("invalid username or password");
		else {
			final UserCredentials cred = credService.findByUser(user.get());
			final Set<UserRole> roles = roleService.findByUserCredentials(cred);
			return new CustomUserDetails(user.get(),roles, orgService);
		}
	}

}
