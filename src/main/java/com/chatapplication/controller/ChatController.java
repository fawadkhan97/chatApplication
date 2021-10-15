package com.chatapplication.controller;

import com.chatapplication.Model.entity.Chat;
import com.chatapplication.Services.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * * @author fawad khan * @createdDate 12-oct-2021
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
	private static final Logger log = LogManager.getLogger(ChatController.class);

	private static final String DEFAULT_AUTH_VALUE = "chat12345";
	private final ChatService chatService;

	/**
	 * @author fawad khan
	 * @createdDate 10-oct-2021
	 * 
	 * @param chatService
	 */
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	/**
	 * @author fawad khan
	 * @createdDate 12-oct-2021
	 * @param authValue
	 * @return
	 */
	// check whether user is authorized or not
	public Boolean authorize(String authValue) {
		return DEFAULT_AUTH_VALUE.equals(authValue);
	}

	/**
	 *
	 * @param authValue
	 * @author fawad khan
	 * @createdDate 12-oct-2021
	 */
	@GetMapping("/all")
	public ResponseEntity<Object> chatList(@RequestHeader(required = false, value = "authorization") String authValue) {
		// check whether user is authorized or not
		if (authorize(authValue)) {
			return chatService.listAllChat();
		} else
			return new ResponseEntity<>("Not authorize", HttpStatus.UNAUTHORIZED);
	}

	/**
	 *
	 * @param authValue
	 * @param id
	 * @author fawad khan
	 * @createdDate 12-oct-2021
	 */
	@GetMapping("/get/id/{id}")
	public ResponseEntity<Object> getChatById(@RequestHeader(value = "Authorization") String authValue,
			@PathVariable Long id) {
		if (authorize(authValue)) {
			return chatService.getChatById(id);
		} else {
			return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 *
	 * @param authValue
	 * @param chats
	 * @author fawad khan
	 * @createdDate 12-oct-2021
	 */
	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestHeader(value = "Authorization", required = false) String authValue,
			@RequestBody List<Chat> chats) {
		if (authorize(authValue)) {
			return chatService.updateChat(chats);
		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

	/**
	 *
	 * @param authValue
	 * @param id
	 * @author fawad khan
	 * @createdDate 12-oct-2021
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = false) String authValue,
			@PathVariable Long id) {
		if (authorize(authValue)) {
			if (!(null == id)) {
				return chatService.deleteChat(id);
			} else
				return new ResponseEntity<>("please enter Chat id. ", HttpStatus.METHOD_NOT_ALLOWED);

		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

}
