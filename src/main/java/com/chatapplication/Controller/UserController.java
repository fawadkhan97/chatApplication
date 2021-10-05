package com.chatapplication.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
	private Boolean isLogin=false;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// check user is authorized or not

	/**
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
	public ResponseEntity<String> login(@RequestParam(value = "username") String userName,
			@RequestParam(value = "password") String password) {
		User user = userService.getbyName(userName);
		// check if password and username from parameter is same as db
		if (user != null) {
			if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
				isLogin = true;
				return new ResponseEntity<>("Message: login successfully", HttpStatus.OK);
			}
			System.out.println(user.getUserName() + "  email is " + user.getEmail() + "id is " + user.getId());
		}
		return new ResponseEntity<>("Message: Incorrect login details ", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/logout")
	public ResponseEntity<Object> logout() {
		isLogin = false;
		return new ResponseEntity<>("Message: logout success", HttpStatus.OK);

	}

	@GetMapping("")
	public ResponseEntity<Object> userList(@RequestHeader(value = "Authorization") String authValue) {

		if (authValue != null) {
			if (authorize(authValue)) {
				try {
					return userService.listAllUser();
				} catch (Exception e){
					return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
				}
			} else
				return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/add")
	public ResponseEntity<Object> addUser(@RequestHeader("Authorization") String authValue, @RequestBody User user) {
		// check authorization
		if (authValue != null) {
			if (authorize(authValue)) {
				try {
					List<Chat> chatList = user.getChats();
					// insert created date to chats
					if (!chatList.isEmpty()) {
						for (Chat chat : chatList) {
							String pattern = "dd-M-yyyy hh:mm:ss";
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
							String date = simpleDateFormat.format(new Date());
							chat.setCreatedDate(date);
						}
					}
				User user1=	userService.save(user);
					return new ResponseEntity<>( user1,HttpStatus.CREATED);
				} catch (Exception e) {
					return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
				}
			} else
				return new ResponseEntity<>("Message: not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/add/{userid}/chat")
	public ResponseEntity<Object> addUserChat(@RequestHeader("Authorization") String authvalue,
			@PathVariable Long userid, @RequestBody List<Chat> chatList) {

		Boolean addResponse;
		if (chatList.isEmpty()) {
			return new ResponseEntity<>("Empty chat list provided", HttpStatus.METHOD_NOT_ALLOWED);
		}

		addResponse = userService.addChatByID(userid, chatList);
		if (addResponse) {
			return new ResponseEntity<>("Chat added succesfully", HttpStatus.OK);
		} else
			return new ResponseEntity<>(" User doesnot exist or incorrect id", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Object> get(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				try {
					User user = userService.get(id);
					return new ResponseEntity<>(user, HttpStatus.CREATED);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>("Message: User not found incorrect id ", HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestHeader("authorization") String authValue, @RequestBody User user) {
		if (authValue != null) {
			if (authorize(authValue)) {
				try {

					userService.save(user);
					return new ResponseEntity<>("Message: User updated successfully ", HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>("Message: incorrect id or Duplicate entry", HttpStatus.NOT_FOUND);
				}
			} else
				return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader("authorization") String authValue, @PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				try {
					userService.delete(id);
					return new ResponseEntity<>("Message: User deleted successfully", HttpStatus.OK);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>("Message: User not found", HttpStatus.NOT_FOUND);
				}
			} else
				return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

}
