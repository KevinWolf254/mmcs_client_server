package co.ke.proaktiv.io.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.ke.proaktiv.io.models.DeliveryReport;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.SmsInfo;
import co.ke.proaktiv.io.pojos.SubscriberReport;
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
	private RestTemplate restTemplate;
	private HttpHeaders header;
	
	@Value("${mmcs.aeon.url}")
	private String URI;

	@Override
	public SmsDeliveryResponse send(final User user, final String message){
		final Organisation org = user.getOrganisation();
		final Long id = org.getId();
		final String senderEmail = user.getEmail();
		
		final Set<Subscriber> subs = subService.findAll(id);
		
		final String recipients = subService.convert(subs);
		final Set<SubscriberReport> report = subService.createReport(subs);
		
		final SmsDeliveryResponse dReport = send(new SmsInfo(senderEmail, recipients, report, message));
		
		deliveryService.save(new DeliveryReport(dReport.getMessageId(), dReport.getReceived(), dReport.getRejected(),
				dReport.getRejectedNos(), org));
		
		return dReport;
	}
	
	@Override
	public SmsDeliveryResponse send(final User user, final String message, final Set<Long> groupIds) {

		final String senderEmail = user.getEmail();
		
		final Set<Subscriber> subs = subService.findByGroupsIds(groupIds);
		final String recipients = subService.convert(subs);
		
		final Set<SubscriberReport> report = subService.createReport(subs);
		
		final SmsDeliveryResponse dReport = send(new SmsInfo(senderEmail, recipients, report, message));
		
		deliveryService.save(new DeliveryReport(dReport.getMessageId(), dReport.getReceived(), dReport.getRejected(),
				dReport.getRejectedNos(), user.getOrganisation()));
		
		return dReport;
		
	}

	private SmsDeliveryResponse send(SmsInfo smsInfo) {
		header.setContentType(MediaType.APPLICATION_JSON);
		
		final HttpEntity<SmsInfo> body = new 
				HttpEntity<SmsInfo>(smsInfo, header);
		
		return restTemplate.exchange(URI + "/sms", HttpMethod.POST, body, SmsDeliveryResponse.class).getBody();
	}
}
