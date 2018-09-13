package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.pojos.EmailMessage;

public interface EmailService {

	public void sendEmail(EmailMessage email);
}
