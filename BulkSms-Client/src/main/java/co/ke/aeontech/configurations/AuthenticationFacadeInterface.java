package co.ke.aeontech.configurations;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeInterface {

	Authentication getAuthentication();
}