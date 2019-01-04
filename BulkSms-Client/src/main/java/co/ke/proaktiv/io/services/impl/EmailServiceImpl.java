package co.ke.proaktiv.io.services.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.ke.proaktiv.io.pojos.EmailMessage;
import co.ke.proaktiv.io.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
    private JavaMailSender mailSender;
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Value("${spring.mail.username}")
	private String EMAIL;
	@Async
	@Override
	public void sendEmail(final EmailMessage email) {
		try {
			final SimpleMailMessage activationEmail = new SimpleMailMessage();
			activationEmail.setTo(email.getTo_address());
			activationEmail.setSubject(email.getSubject());
			activationEmail.setText(email.getBody());
			mailSender.send(activationEmail);
		} catch (MailException e) {
			log.info("MailException: " +email.getTo_address()+ ". Error message: "
					+e.getMessage());
		}catch (Exception e) {
			log.info("Exception: " +email.getTo_address()+ ". Error message: "
					+e.getMessage());
		}
	}
	@Override
	public void sendEmail(final EmailMessage email, final MultipartFile msfile) {//final DataSource attachment) {
        
		try {
			final MimeMessage message = mailSender.createMimeMessage();

			message.setFrom(new InternetAddress(email.getTo_address()));
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL));
			message.setSubject(email.getSubject());
						
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(email.getBody(), "text/html");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
						
			MimeBodyPart attachPart = new MimeBodyPart();

	        attachPart.setContent(msfile.getBytes(), msfile.getContentType());
	        attachPart.setFileName(msfile.getOriginalFilename());
	        attachPart.setDisposition(Part.ATTACHMENT);
		
            multipart.addBodyPart(attachPart);

            message.setContent(multipart);
					
	        mailSender.send(message);
		} catch (MessagingException e) {
			log.info("MessagingException: "+e);
		}catch (Exception e) {
			log.info("Exception: "+e);
		}
	}
}
