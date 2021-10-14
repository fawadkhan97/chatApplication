package com.chatapplication.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {
	final private JavaMailSender javaMailSender;

	private final String body = " A new account was created using your email ,  please enter following code to verify: ";
	private final String subject = " User verification email";

	public EmailUtil(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public ResponseEntity<Object> sendMail(String toEmail, int token) {

		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(toEmail);
			msg.setSubject(subject);
			msg.setText(body + token);
			javaMailSender.send(msg);
			return new ResponseEntity<>("user added and email sent successfully please verify it", HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getCause());
			return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}