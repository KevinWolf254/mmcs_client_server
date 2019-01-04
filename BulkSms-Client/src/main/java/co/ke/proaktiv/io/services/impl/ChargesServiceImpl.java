package co.ke.proaktiv.io.services.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import co.ke.proaktiv.io.configurations.RemoteServersProperties;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.pojos.ChargesReport;
import co.ke.proaktiv.io.pojos.ServiceProviderReport;
import co.ke.proaktiv.io.pojos.Sms;
import co.ke.proaktiv.io.pojos.SmsInfo;
import co.ke.proaktiv.io.services.ChargesService;
import co.ke.proaktiv.io.services.SubscriberService;

@Service
public class ChargesServiceImpl implements ChargesService{
	@Autowired
	private SubscriberService subService;

	@Autowired
    private RemoteServersProperties properties;
	
	@Autowired
	private RestTemplate restTemplate;
	private HttpHeaders header;
	
//	@Value("${proaktivio.url}")
//	private String URI;
	private static final Logger log = LoggerFactory.getLogger(ChargesServiceImpl.class);

	@Override
	public ChargesReport calculate(final Sms sms) {

		final Set<Subscriber> subs = subService.findByGroupsId(sms.getGroupIds());	
		
		final Set<ServiceProviderReport> subsPerProviderReport = subService.calculateTotalSubsPerProvider(subs);
				
		final ChargesReport report = getCost(new SmsInfo(sms.getEmail(), sms.getSenderId(), "", subsPerProviderReport, sms.getMessage()));
		
		report.setTotalContacts(subs.size());
		return report;
	}
	
	private ChargesReport getCost(final SmsInfo smsInfo) {
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		final HttpEntity<SmsInfo> body = new HttpEntity<SmsInfo>(smsInfo, header);
		ResponseEntity<ChargesReport> response = null;
		try {			
			response = restTemplate.exchange(properties.getApiServer() + "/charges", HttpMethod.POST, body, 
					ChargesReport.class);
		} catch (RestClientException e) {
			log.info("RestClientException: "+e.getMessage());
			return new ChargesReport(400, "error", "something happened");
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new ChargesReport(400, "error", "something happened");
		} 
		return response.getBody();
	}

}
