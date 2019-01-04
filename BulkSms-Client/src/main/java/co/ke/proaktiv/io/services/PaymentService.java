package co.ke.proaktiv.io.services;

import java.util.Date;
import java.util.List;

import co.ke.proaktiv.io.pojos.JR_Invoice;
import co.ke.proaktiv.io.pojos.pro._Payment;
import co.ke.proaktiv.io.pojos.response.Response;

public interface PaymentService {

	public Response confirm(_Payment payment);

	public List<JR_Invoice> findBtwnDates(Date from, Date to, Long id);

}
