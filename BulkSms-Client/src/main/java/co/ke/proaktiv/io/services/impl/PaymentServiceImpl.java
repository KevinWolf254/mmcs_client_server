package co.ke.proaktiv.io.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import co.ke.proaktiv.io.configurations.RemoteServersProperties;
import co.ke.proaktiv.io.pojos.JR_Invoice;
import co.ke.proaktiv.io.pojos.Sale;
import co.ke.proaktiv.io.pojos.pro.PurchaseRequest;
import co.ke.proaktiv.io.pojos.pro._Payment;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private RestTemplate restTemplate;	
	@Autowired
    private RemoteServersProperties properties;
	private HttpHeaders header;

	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Override
	public Response confirm(final _Payment payment) {
		Response response = null;
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<_Payment> body = new HttpEntity<_Payment>(payment, header);
		
		ResponseEntity<Response> responseEntity = null;
		try {			
			responseEntity = restTemplate.exchange(properties.getApiServer() + "/sale", HttpMethod.PUT, body, Response.class);
			response = responseEntity.getBody();
		} catch (RestClientException e) {
			log.info("RestClientException: "+e.getMessage());
			return new Response(400, "error", "something happened");
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new Response(400, "error", "something happened");
		} 
		return response;
	}

	@Override
	public List<JR_Invoice> findBtwnDates(final Date from, final Date to, final Long id) {		
		header = new HttpHeaders();		
		header.setContentType(MediaType.APPLICATION_JSON);
		
		final HttpEntity<PurchaseRequest> body = new HttpEntity<PurchaseRequest>(new PurchaseRequest(id, from, to), header);

		
		ResponseEntity<List<Sale>> response = null;
		List<JR_Invoice> invoices = new ArrayList<JR_Invoice>();
		try {
			response = restTemplate.exchange(properties.getApiServer() + "/sale/find", HttpMethod.POST, body, new ParameterizedTypeReference<List<Sale> >() {});
			
			invoices = response
						.getBody()
						.parallelStream()
						.map(result -> {
							final JR_Invoice invoice = new JR_Invoice();
							invoice.setDate(result.getDate());
							invoice.setInvoiceNo(result.getInvoiceNo());
							invoice.setProductName(result.getProduct().getName());
							invoice.setTransactionId(result.getCode());
							invoice.setAmount(result.getAmountInfo());
							return invoice;
						})
						.collect(Collectors.toList());
		} catch (RestClientException e) {
			log.info("RestClientException: "+e.getMessage());
			return new ArrayList<JR_Invoice>();
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new ArrayList<JR_Invoice>();
		} 	
		return invoices;
	}
}
