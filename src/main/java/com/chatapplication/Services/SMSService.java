package com.chatapplication.Services;

import com.chatapplication.Model.entity.SMS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.xml.ws.Response;

@Component
public class SMSService {

	private final String ACCOUNT_SID = "AC41c995234967e84c7dd650af8ab28d0f";

	private final String AUTH_TOKEN = "b7ddc15fe1d1250a331a0c6ad055e07e";

	private final String FROM_NUMBER = "+13158402662";

	public ResponseEntity<Object> send(SMS sms) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		System.out.println(" data sms is " + sms.toString());

		Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
				.create();
		System.out.println("here is my id:" + message.getSid());// Unique resource ID created to manage this transaction
//		return new ResponseEntity<>(message.getSid(), HttpStatus.OK);
		return ResponseEntity.ok.(message.getSid(), HttpStatus.OK);
	}

	public void receive(MultiValueMap<String, String> smscallback) {
	}

}