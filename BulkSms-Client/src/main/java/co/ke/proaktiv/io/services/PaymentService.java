package co.ke.proaktiv.io.services;

import java.util.Date;
import java.util.List;

import co.ke.proaktiv.io.pojos.Sale;
import co.ke.proaktiv.io.pojos.pro._Payment;
import co.ke.proaktiv.io.pojos.response.Response;

public interface PaymentService {

	public Response confirm(_Payment payment);

	public List<Sale> search(Date from, Date to, Long id);
}
