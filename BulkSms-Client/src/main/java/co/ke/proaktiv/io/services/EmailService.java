package co.ke.proaktiv.io.services;

import org.springframework.web.multipart.MultipartFile;

import co.ke.proaktiv.io.pojos.EmailMessage;

public interface EmailService {

	/**
	 * sends email
	 * @param email
	 */
	public void sendEmail(EmailMessage email);
	
	/**
	 * sends an email with an attachment 
	 * @param email
	 * @param multipartFile
	 */
	public void sendEmail(EmailMessage email, MultipartFile msfile);}
