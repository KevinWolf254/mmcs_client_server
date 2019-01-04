package co.ke.proaktiv.io.services;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import co.ke.proaktiv.io.pojos.SenderId;
import co.ke.proaktiv.io.pojos.SenderIdRequest;
import co.ke.proaktiv.io.pojos.pro.ShortCode;
import co.ke.proaktiv.io.pojos.response.Response;

public interface SenderIdService {

	/**
	 * retrieves the sender id
	 * @param id is the client id
	 * @return SenderId
	 */
	public Set<SenderId> findAllByCompanyId(Long id);

	/**
	 * Retrieves senderId by name
	 * @param name
	 * @return ShortCode
	 */
	public ShortCode findByName(String name);

	/**
	 * 
	 * @param request
	 * @return Response
	 */
	public Response save(SenderIdRequest request);

	/**
	 * sends the application form (Multi-part file)
	 * through email and also sends invoice for the sender id payment
	 * 
	 * @param file its of ms word format
	 * @return response
	 */
	public Response send(MultipartFile file);

}
