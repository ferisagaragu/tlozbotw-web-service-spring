package org.neurobrain.tlozbotw.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Mail {
 	
	@Value("${app.auth.mail-user}")
	private String user;
	
	@Value("${app.auth.mail-password}")
	private String password;
	
	public boolean send(String subject, String content, String... emails) {
		boolean out = true;
	
		for (String email : emails) {
			out = out && sendIndividual(email, subject, content); 
		}
		
		return out;
	}
	
	private boolean sendIndividual(String email, String subject, String content) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			
			Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
							user, 
							password
						);
					}
				}
			);
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("javabrain.email@gmail.com", false));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject(subject);
			msg.setContent(content, "text/html");
			msg.setSentDate(new Date());
			
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
