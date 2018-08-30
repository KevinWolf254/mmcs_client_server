package co.ke.aeontech.configurations.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.User;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService service;
	@Autowired
	private AeonService aeonService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = service.findByEmail(email);	
		if(user == null)
			throw new UsernameNotFoundException("invalid username or password");
		else
			return new CustomUserDetails(user, aeonService);
	}

}
