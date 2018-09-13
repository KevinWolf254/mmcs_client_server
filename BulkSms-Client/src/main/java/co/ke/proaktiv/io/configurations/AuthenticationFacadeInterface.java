package co.ke.proaktiv.io.configurations;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeInterface {

	public Authentication getAuthentication();
}