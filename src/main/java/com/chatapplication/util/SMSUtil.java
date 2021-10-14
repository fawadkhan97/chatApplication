package com.chatapplication.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SMSUtil {

	private final String ACCOUNT_SID = "AC41c995234967e84c7dd650af8ab28d0f";

	private final String AUTH_TOKEN = "08a6f98e79e0a553b63bce5ebec8c6db";

	private final String FROM_NUMBER = "+13158402662";

	private final String sms = "otp is : ";

	// send general sms (get sms body from user)
	public ResponseEntity<Object> sendSMS(String phoneNumber, String sms) {
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			System.out.println(" data sms is " + sms + "  phone is :  " + phoneNumber);

			Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(FROM_NUMBER), sms).create();
			System.out.println(" message sent successfully here is my id: " + message.getSid());
//		return new ResponseEntity<>(message.getSid(), HttpStatus.OK);
			return new ResponseEntity("message sent successfully ", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}

	// send otp sms
	public ResponseEntity<Object> sendSMS(String phoneNumber, int token) {
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			final String otpmsg = sms + token;
			System.out.println(" data sms is " + otpmsg + "  phone is :  " + phoneNumber);

			Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(FROM_NUMBER), otpmsg)
					.create();
			System.out.println(" message sent successfully here is my id: " + message.getSid());
//		return new ResponseEntity<>(message.getSid(), HttpStatus.OK);
			return new ResponseEntity("message sent successfully ", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}
}
