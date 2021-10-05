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

	@GetMapping(" ")
	public ResponseEntity<Object> chatList(@RequestHeader(required = false, value = "authorization") String authValue) {
		// check whether user is authorized or not
		if (authorize(authValue)) {
			List<Chat> chatList = chatService.listAllChat();
			// check if database is empty
			if (chatList.isEmpty()) {
				return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(chatList, HttpStatus.OK);
			}

		} else
			return new ResponseEntity<>("Not authorize", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Object> get(@RequestHeader(required = false, value = "Authorization") String authValue, @RequestParam("question") @PathVariable Long id) {
		if (authorize(authValue)) {
		try {
			Chat chat = chatService.get(id);
			log.info(chat);
			return new ResponseEntity<>(chat, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	} else {
		return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);

	}}

	@PostMapping("/add")
	public ResponseEntity<String> addUser(@RequestHeader(required = false, value = "Authorization") String authValue,
			@RequestBody Chat chat) {
		// check authorization
		if (authorize(authValue)) {
			String pattern = "dd-M-yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			chat.setCreatedDate(date);
			chatService.save(chat);
			return new ResponseEntity<>("chat added successfully", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(" not authorized ", HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestHeader(value = "Authorization", required = false) String authValue,
			@RequestBody Chat chat) {
		if (authorize(authValue)) {
			try {
				String pattern = "dd-M-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				chat.setUpdatedDate(date);
				chatService.save(chat);
				return new ResponseEntity<>("updated successfully", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("User not found ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader(value = "Authorization", required = false) String authValue,
			@PathVariable Long id) {
		if (authorize(authValue)) {
			try {
				chatService.delete(id);
				return new ResponseEntity<>("User deleted successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("User not found ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

}
