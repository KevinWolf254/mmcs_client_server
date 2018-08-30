package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Contact;
import co.ke.aeontech.models.DeliveryReport;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.SmsInfo;
import co.ke.aeontech.pojos._Charges;
import co.ke.aeontech.pojos._ContactsTotals;
import co.ke.aeontech.pojos.helpers.Country;
import co.ke.aeontech.pojos.requests.Sms;
import co.ke.aeontech.pojos.response.ResponseError;
import co.ke.aeontech.pojos.response.Response;
import co.ke.aeontech.pojos.response.ResponseSuccess;
import co.ke.aeontech.pojos.response.UnitsDetails;
import co.ke.aeontech.services.AeonService;
import co.ke.aeontech.services.ContactService;
import co.ke.aeontech.services.DeliveryReportService;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.SmsService;
import co.ke.aeontech.services.UserService;

@Service
public class SmsServiceImpl implements SmsService{
	@Autowired
	private UserService userService;
	@Autowired
	private OrganisationService orgService;
	@Autowired
	private AeonService aeonService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private DeliveryReportService reportService;
	
	@Override
	public Response sendSmsToAll(final Sms sms) {
		final User user = userService.getSignedInUser();
		
		//retrieve units available from _aeon
		final Organisation organisation = orgService.findByEmployeesId(user.getId());
		final Long org_number = organisation.getNumber();
		final String email = user.getEmail();
		final UnitsDetails unitsDetails = aeonService.findOrgUnitsByEmail(email);
		
		//set up the multiplier which will depend on the length of the message
		int multiplier = 1;
		if(sms.getMessage().length() > 160)
			multiplier = 2;		
		//retrieve the number of contacts
		final List<Contact> contacts = contactService.findBySuppliersId(organisation.getId());
		
		//separate the number of clients according to:
			//1: country_code
				//1.1: service provider
		final _ContactsTotals contactNos = contactService.seperateContacts(contacts);
		//retrieve the charges per service provider from _aeon
		final _Charges charges = aeonService.getCharges(org_number);
		//calculate the total cost
		final BigDecimal cost = BigDecimal.valueOf(calculateSmsCost(contactNos, charges) * multiplier);
		//if total cost is less than the units available send ErrorResponse
		if(BigDecimal.valueOf(unitsDetails.getUnitsAvailable()).compareTo(cost) == -1) {
			//send email to notify organization units are not available
			emailService.sendEmail(new EmailMessage(email, "sms can not be sent. Insufficient funds!", 
					"sms could not be sent. Avalilable funds are: "	+unitsDetails.getUnitsAvailable()+
					", while needed units are: "+cost+", kindly top up."));			
			return new ResponseError("failed", "sms can not be sent. Insufficient funds. top up to send sms");
		}//else send to africa's talking
		final StringBuffer buffer = new StringBuffer();
		//convert the contacts to string(format required by africa's talking)
		contacts.parallelStream()
			.forEach(contact -> {
				buffer.append(contact.getCountryCode())
					.append(contact.getPhoneNumber())
					.append(",");				
			});
		//send the all_contacts list and sender_name to aeon_ in order to send it to africa's talking
		//also should check if organization has paid for sender_id
		final OnSendDeliveryReport report = 
				aeonService.sendSms(new SmsInfo(organisation.getName(), buffer.toString(), sms.getMessage()));
		if(!report.getMessageId().equals("") || report.getMessageId() != null) {
			//update the organization's units available and update aeon_units at the same time
			aeonService.subtractOrganisationUnits(org_number, cost);
			//save sms_cost to database
	//		costService.save(new Cost(cost, organisation));
			//save delivery report to database
			final StringBuffer phoneNoBuffer = new StringBuffer();
			report.getRejected().parallelStream()
			.forEach(phoneNo -> {
				phoneNoBuffer.append(phoneNo)
					.append(",");				
			});
			//retrieve response from africa's talking to see if there was some numbers 
			//which did not receive the _sms
			//get organization country
			final Country org_country = aeonService.getOrganizationInfo(org_number).getCountry();
			reportService.save(new DeliveryReport(report.getMessageId(), report.getSent().size(), 
					report.getRejected().size(), phoneNoBuffer.toString(), org_country, cost.doubleValue(), organisation));
			//send email notifying organization _sms was sent
			emailService.sendEmail(new EmailMessage(email, "Sms was sent successfully.", 
					"Sms was sent successfully."));
			//send SuccessResponse	
			return new ResponseSuccess("success", "successfully sent sms.");
		}
		log.error("##### Error: sms was not sent");
		return new ResponseError("failed", "Could not send sms at this time. kindly try again later.");
	}

	@Override
	public Response sendSmsToGroup(final Sms sms) {
		final User user = userService.getSignedInUser();
		//retrieve units available from _aeon
		final Organisation organisation = orgService.findByEmployeesId(user.getId());
		final Long org_number = organisation.getNumber();
		final String email = user.getEmail();
		final UnitsDetails unitsDetails = aeonService.findOrgUnitsByEmail(email);
		
		//set up the multiplier which will depend on the length of the message
		int multiplier = 1;
		if(sms.getMessage().length() > 160)
			multiplier = 2;
		
		//retrieve the number of contacts for each group
		final Set<Contact> contacts = new HashSet<Contact>();
		sms.getGroupIds().parallelStream().forEach(groupId ->{
			//get all the contacts of the group
			List<Contact> group_Contacts = contactService.findByGroupsId(groupId);
			group_Contacts.stream().forEach(contact -> {
				contacts.add(contact);
			});
		});
		
		final _ContactsTotals contactNos = contactService.seperateContacts(contacts);
		//retrieve the charges per service provider from _aeon
		final _Charges charges = aeonService.getCharges(org_number);
		//calculate the total cost
		final BigDecimal cost = BigDecimal.valueOf(calculateSmsCost(contactNos, charges) * multiplier);

		//if total cost is less than the units available send ErrorResponse
		if(BigDecimal.valueOf(unitsDetails.getUnitsAvailable()).compareTo(cost) == -1) {
			//send email to notify organization units are not available
			emailService.sendEmail(new EmailMessage(email, "sms can not be sent. Insufficient funds!", 
					"sms could not be sent. Avalilable funds are: "	+unitsDetails.getUnitsAvailable()+
					", while needed units are: "+cost+", kindly top up."));	
			return new ResponseError("failed", "sms can not be sent. Insufficient funds. top up to send sms");
		}
		//else send to africa's talking
		final StringBuffer buffer = new StringBuffer();
		//convert the contacts to string(format required by africa's talking)
		contacts.parallelStream()
			.forEach(contact -> {
				buffer.append(contact.getCountryCode())
					.append(contact.getPhoneNumber())
					.append(",");				
		});
		//send the all_contacts list and sender_name to aeon_ in order to send it to africa's talking
			//also should check if organization has paid for sender_id
		final OnSendDeliveryReport report =
					aeonService.sendSms(new SmsInfo(organisation.getName(), buffer.toString(), sms.getMessage()));
		if(!report.getMessageId().equals("") || report.getMessageId() != null) {
			//update the organization's units available and update aeon_units at the same time
			aeonService.subtractOrganisationUnits(org_number, cost);
			//save sms_cost to database
	//		costService.save(new Cost(cost, orgService.findById(org_id)));
			//save delivery report to database
			final StringBuffer phoneNoBuffer = new StringBuffer();
			report.getRejected().parallelStream()
			.forEach(phoneNo -> {
				phoneNoBuffer.append(phoneNo)
					.append(",");				
			});
			//retrieve response from africa's talking to see if there was some numbers 
			//which did not receive the _sms
			//get organization country
			final Country org_country = aeonService.getOrganizationInfo(org_number).getCountry();
			reportService.save(new DeliveryReport(report.getMessageId(), report.getSent().size(), 
					report.getRejected().size(), phoneNoBuffer.toString(), org_country, cost.doubleValue(), organisation));		
			//send email notifying organization _sms was sent
			emailService.sendEmail(new EmailMessage(email, "Sms was sent successfully.", 
					"Sms was sent successfully."));
			//send SuccessResponse	
			return new ResponseSuccess("success", "successfully sent sms.");
		}
		log.error("##### Error: sms was not sent");
		return new ResponseError("failed", "Could not send sms at this time. kindly try again later.");
	}

	public double calculateSmsCost(final _ContactsTotals contactNos, final _Charges charges) {
		return ((contactNos.getRwf() * charges.getRwf()) + 
				(contactNos.getRwfAir() * charges.getRwfAir()) +
				(contactNos.getKes() * charges.getKes()) +
				(contactNos.getKesAir() * charges.getKesAir()) +
				(contactNos.getTzs() * charges.getTzs()) +
				(contactNos.getTzsAir() * charges.getTzsAir()) +
				(contactNos.getUgx() * charges.getUgx()) +
				(contactNos.getUgxAir() * charges.getUgxAir()) +
				(contactNos.getOther() * charges.getOther()));
	}
	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

}
