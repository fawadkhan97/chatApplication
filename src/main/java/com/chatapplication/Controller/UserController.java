package com.chatapplication.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatapplication.Model.Chat;
import com.chatapplication.Model.User;
import com.chatapplication.Services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	UserService userService;
	private static final String defaultAuthValue = "user12345";

	private static final Logger log = LogManager.getLogger(ChatController.class);

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * check user is authorized or not
	 * 
	 * @param authValue
	 * @return
	 */
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	/**
	 * @param userName
	 * @param password
	 * @author Fawad Khan
	 */
	@GetMapping("/login")
	public ResponseEntity<Object> login(@RequestParam(value = "username") String userName,
			@RequestParam(value = "password") String password) {
		return userService.getUserByNameAndPassword(userName, password);
	}

	/**
	 * 
	 * @param authValue
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<Object> users(@RequestHeader(value = "Authorization") String authValue) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.listAllUser();
			} else
				return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * 
	 * @param authValue
	 * @param user
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<Object> addUser(@RequestHeader("Authorization") String authValue, @RequestBody User user) {
		// check authorization
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.saveUser(user);
			} else
				return new ResponseEntity<>("Message: not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * 
	 * @param authValue
	 * @param userid
	 * @param chatList
	 * @return
	 */
	@PostMapping("/add/{userid}/chat")
	public ResponseEntity<Object> addUserChats(@RequestHeader("Authorization") String authValue,
			@PathVariable Long userid, @RequestBody List<Chat> chatList) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.addChatsInUser(userid, chatList);
			} else
				return new ResponseEntity<>("Message: not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @param authValue
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getUser(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.getUserById(id);
			} else {
				return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @param authValue
	 * @param user
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<Object> updateUser(@RequestHeader("Authorization") String authValue, @RequestBody User user) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.updateUser(user);
			} else
				return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @param authValue
	 * @param id
	 * @return
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteUser(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return userService.deleteUser(id);
			} else
				return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}
}
