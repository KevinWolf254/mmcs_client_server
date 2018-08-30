package co.ke.aeontech.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import co.ke.aeontech.models.SenderIdentifier;
import co.ke.aeontech.models.User;
import co.ke.aeontech.pojos._Charges;
import co.ke.aeontech.pojos.MpesaConfirmationBy;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.Payment;
import co.ke.aeontech.pojos.SmsInfo;
import co.ke.aeontech.pojos._Administrator;
import co.ke.aeontech.pojos._Organisation;
import co.ke.aeontech.pojos.response.UnitsDetails;

public interface AeonService {
	public _Organisation registerOrganisation(_Organisation new_org, _Administrator admin, String senderId);
	
	public boolean findOrganisationByEmail(String email);
	
	public UnitsDetails findOrgUnitsById(Long id);
	
	public UnitsDetails findOrgUnitsByEmail(String email);

	public SenderIdentifier findSenderId(Long org_no);
	
	public boolean foundSenderId(String senderId);
	
	@Async
	public void saveUser(User user, String rawPassword);

	public _Charges getCharges(Long org_number);

	@Async
	public void subtractOrganisationUnits(Long org_number, BigDecimal expense);

	public ResponseEntity<Object> confirmMpesaPayment(MpesaConfirmationBy request);
	
	public OnSendDeliveryReport sendSms(SmsInfo smsInfo);
	
	@Async
	public void deleteUser(String email);

	public _Organisation getOrganizationInfo(Long org_no);
	
	public List<Payment> getPurchases(Long org_no);

	public List<Payment> SearchBtw(Date from, Date to, Long id);

}
