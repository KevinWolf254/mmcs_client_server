package co.ke.aeontech.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
    private JavaMailSender mailSender;

	@Async
	@Override
	public void sendEmail(EmailMessage email) {
		try {
			final SimpleMailMessage activationEmail = new SimpleMailMessage();
			activationEmail.setTo(email.getTo_address());
			activationEmail.setSubject(email.getSubject());
			activationEmail.setText(email.getBody());
			mailSender.send(activationEmail);
		} catch (MailException e) {
			log.info("##### Error sending email to " +email.getTo_address()+ ". Error message: "
					+e.getMessage());
		}
	}
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
}
