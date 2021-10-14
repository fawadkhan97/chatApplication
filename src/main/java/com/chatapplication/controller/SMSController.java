package com.chatapplication.controller;

import com.chatapplication.Model.entity.SMS;
import com.chatapplication.Services.SMSService;
import com.twilio.exception.ApiException;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class SMSController {

	final private SMSService smsService;
	final private SimpMessagingTemplate webSocket;

	private final String TOPIC_DESTINATION = "/topic/sms";

	public SMSController(SMSService smsService, SimpMessagingTemplate webSocket) {
		this.smsService = smsService;
		this.webSocket = webSocket;
	}

	@RequestMapping(value = "/sms", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void smsSubmit(@RequestBody SMS sms) {
		try {
			smsService.send(sms);
		} catch (ApiException e) {
			webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: " + e.getMessage());
			throw e;
		}
		webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: " + sms.getTo());

	}

	@RequestMapping(value = "/smscallback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void smsCallback(@RequestBody MultiValueMap<String, String> map) {
		smsService.receive(map);
		webSocket.convertAndSend(TOPIC_DESTINATION,
				getTimeStamp() + ": Twilio has made a callback request! Here are the contents: " + map.toString());
	}

	private String getTimeStamp() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}
}
