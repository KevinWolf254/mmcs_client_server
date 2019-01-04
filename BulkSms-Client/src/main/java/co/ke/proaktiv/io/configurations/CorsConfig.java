package co.ke.proaktiv.io.configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public FilterRegistrationBean<CorsFilter> myCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("*")));
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.setAllowedMethods(getAllowedMethods());
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config);
		
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
	private List<String> getAllowedMethods() {
		List<String> allowedMethods = new ArrayList<String>();
		allowedMethods.add(HttpMethod.HEAD.name());
		allowedMethods.add(HttpMethod.GET.name());
		allowedMethods.add(HttpMethod.POST.name());
		allowedMethods.add(HttpMethod.PUT.name());
		allowedMethods.add(HttpMethod.DELETE.name());
		allowedMethods.add(HttpMethod.OPTIONS.name());
		return Collections.unmodifiableList(allowedMethods);
	}
}
