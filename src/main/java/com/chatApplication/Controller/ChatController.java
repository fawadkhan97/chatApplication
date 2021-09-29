package com.chatApplication.Controller;

import com.chatApplication.Model.Chat;
import com.chatApplication.Services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/chat")
public class ChatController {
	private static final String defaultAuthValue = "chat12344";
	@Autowired
	ChatService chatService;

	// check whether user is authorized or not
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	@GetMapping(" ")
	public ResponseEntity<Object> chatList(@RequestHeader(required = false,value = "authorization"  )  String authValue ) {

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

	@GetMapping("/get")
	public ResponseEntity<Chat> get(@RequestParam("question") Long id) {
		try {
			Chat chat = chatService.get(id);
			return new ResponseEntity<Chat>(chat, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<String> addUser(@RequestHeader(required = false,value = "Authorization") String authValue, @RequestBody Chat chat) {
		// check authorization
		if (authorize(authValue)) {
			chatService.save(chat);
			return new ResponseEntity<>("chat added successfully", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestHeader(value = "Authorization" , required = false) String authValue, @RequestBody Chat chat) {
		if (authorize(authValue)) {
			try {
				chatService.save(chat);
				return new ResponseEntity<>("updated successfully", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("User not found ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader(value = "Authorization", required = false) String authValue, @PathVariable Long id) {
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
