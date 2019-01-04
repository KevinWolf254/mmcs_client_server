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
import co.ke.proaktiv.io.models.DeliveryReport;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.SmsInfo;
import co.ke.proaktiv.io.pojos.ServiceProviderReport;
import co.ke.proaktiv.io.pojos.response.SmsDeliveryResponse;
import co.ke.proaktiv.io.services.DeliveryReportService;
import co.ke.proaktiv.io.services.SmsService;
import co.ke.proaktiv.io.services.SubscriberService;

@Service
public class SmsServiceImpl implements SmsService{
	@Autowired
	private SubscriberService subService;
	@Autowired
	private DeliveryReportService deliveryService;
	@Autowired
    private RemoteServersProperties properties;

	@Autowired
	private RestTemplate restTemplate;
	private HttpHeaders header;
	
	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Override
	public SmsDeliveryResponse send(final User user, final String senderId, final String message){
		final Organisation org = user.getOrganisation();
		final Long id = org.getId();
		final String senderEmail = user.getEmail();
		
		final Set<Subscriber> subs = subService.findAll(id);
		
		final String recipients = subService.toString(subs);
		final Set<ServiceProviderReport> report = subService.calculateTotalSubsPerProvider(subs);
		
		final SmsDeliveryResponse dReport = send(new SmsInfo(senderEmail, senderId, recipients, report, message));
		
		if(dReport.getCode() != 400)
			deliveryService.save(new DeliveryReport(dReport.getMessageId(), dReport.getReceived(), dReport.getRejected(),
					dReport.getRejectedNos(), org));
		
		return dReport;
	}
	
	@Override
	public SmsDeliveryResponse send(final User user, final String senderId, final String message, final Set<Long> groupIds) {

		final String senderEmail = user.getEmail();
		
		final Set<Subscriber> subs = subService.findByGroupsId(groupIds);
		final String recipients = subService.toString(subs);
		
		final Set<ServiceProviderReport> report = subService.calculateTotalSubsPerProvider(subs);
		
		final SmsDeliveryResponse dReport = send(new SmsInfo(senderEmail, senderId, recipients, report, message));
		
		if(dReport.getCode() != 400)
			deliveryService.save(new DeliveryReport(dReport.getMessageId(), dReport.getReceived(), dReport.getRejected(),
				dReport.getRejectedNos(), user.getOrganisation()));
		
		return dReport;
		
	}

	private SmsDeliveryResponse send(SmsInfo smsInfo) {
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		final HttpEntity<SmsInfo> body = new 
				HttpEntity<SmsInfo>(smsInfo, header);
		ResponseEntity<SmsDeliveryResponse> response = null;
		try {			
			response = restTemplate.exchange(properties.getApiServer() + "/sms", HttpMethod.POST, body, SmsDeliveryResponse.class);
		} catch (RestClientException e) {
			log.info("RestClientException: "+e.getMessage());
			return new SmsDeliveryResponse(400, "error", "something happened");
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new SmsDeliveryResponse(400, "error", "something happened");
		}
		return response.getBody();
	}
}
