package co.ke.proaktiv.io.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="url")
public class RemoteServersProperties {

	private String apiServer;
	private String uiServer;
	private List<String> acceptedOrigins = new ArrayList<String>();
	public RemoteServersProperties() {
		super();
	}
	public String getApiServer() {
		return apiServer;
	}
	public String getUiServer() {
		return uiServer;
	}
	public List<String> getAcceptedOrigins() {
		return acceptedOrigins;
	}
	public void setApiServer(String apiServer) {
		this.apiServer = apiServer;
	}
	public void setUiServer(String uiServer) {
		this.uiServer = uiServer;
	}
	public void setAcceptedOrigins(List<String> acceptedOrigins) {
		this.acceptedOrigins = acceptedOrigins;
	}
}
