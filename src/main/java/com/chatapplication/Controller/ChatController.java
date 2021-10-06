package com.chatapplication.Controller;

import com.chatapplication.Model.Chat;
import com.chatapplication.Services.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/chat")
public class ChatController {
	private static final Logger log = LogManager.getLogger(ChatController.class);

	private static final String DEFAULT_AUTH_VALUE = "chat12345";
	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	// check whether user is authorized or not
	public Boolean authorize(String authValue) {
		return DEFAULT_AUTH_VALUE.equals(authValue);
	}

	/**
	 *
	 * @param authValue
	 * @return
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
	 * @return
	 */
	@GetMapping("/get/id/{id}")
	public ResponseEntity<Object> getChatById(@RequestHeader(value = "Authorization") String authValue,
			@PathVariable Long id) {
		if (authorize(authValue)) {
			return chatService.getChatById(id);
		} else {
			return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 *
	 * @param authValue
	 * @param chats
	 * @return
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
	 * @return
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
