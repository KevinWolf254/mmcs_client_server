package co.ke.aeontech.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import co.ke.aeontech.pojos.MpesaConfirmationBy;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.Payment;
import co.ke.aeontech.pojos.SenderIdentifier;
import co.ke.aeontech.pojos._Organisation;
import co.ke.aeontech.pojos.response.UnitsDetails;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.AdminCredentials;
import co.ke.proaktiv.io.pojos.CreditCharges;
import co.ke.proaktiv.io.pojos.SmsInfo;
import co.ke.proaktiv.io.pojos.pro.ShortCode;
import co.ke.proaktiv.io.pojos.response.SignUpResponse;

public interface ProService {
		
//	public SignUpResponse save(_Organisation new_org, 
//			_Administrator admin, String shortCode);
//	
//	public boolean findOrganisationByEmail(String email);
//	
//	public UnitsDetails findOrgUnitsById(Long id);
//	
//	public UnitsDetails findOrgUnitsByEmail(String email);
//
//	public SenderIdentifier findSenderId(Long org_no);
//	
//	@Async
//	public void saveUser(User user, String rawPassword);
//
//	public _Charges getCharges(Long org_number);
//
//	@Async
//	public void subtractOrganisationUnits(Long org_number, BigDecimal expense);
//
//	public ResponseEntity<Object> confirmMpesaPayment(MpesaConfirmationBy request);
//	
//	public OnSendDeliveryReport sendSms(SmsInfo smsInfo);
//	
//	@Async
//	public void deleteUser(String email);
//
//	public _Organisation getOrganizationInfo(Long org_no);
//	
//	public List<Payment> getPurchases(Long org_no);
//
//	public List<Payment> SearchBtw(Date from, Date to, Long id);

}
