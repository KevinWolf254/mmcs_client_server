package co.ke.aeontech.configurations;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig extends DefaultResponseErrorHandler {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		log.error("RestTemplate Error: "+response);
		super.handleError(response);
	}
	private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);

}
