package com.chatApplication.Controller;
import com.chatApplication.Model.User;
import com.chatApplication.Services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	UserService userService;
	private static final String defaultAuthValue = "user12345";
	private static boolean isLogin = false;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// check user is authorized or not

	/**
	 *
	 * @param authValue
	 * @return
	 */
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	/**
	 * @author Fawad Khan
	 * @param userName
	 * @param password
	 * @return
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
				List<User> userList = userService.listAllUser();
				// check if database is empty
				if (userList.isEmpty()) {
					return new ResponseEntity<>("Message: No data available", HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<>(userList, HttpStatus.OK);
				}

			} else
				return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestHeader("Authorization") String authValue, @RequestBody User user) {
		// check authorization
		if (authorize(authValue)) {
			try {
				userService.save(user);
				return new ResponseEntity<>("Message: User added successfully", HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>("Either username or email already exist .  ", HttpStatus.CONFLICT);
			}
		} else
			return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Object> get(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
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
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestHeader("authorization") String authValue, @RequestBody User user) {
		if (authorize(authValue)) {
			try {

				userService.save(user);
				return new ResponseEntity<>("Message: User updated successfully ", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>("Message: incorrect id or Duplicate entry", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			userService.delete(id);
			return new ResponseEntity<>("Message: User deleted successfully", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Message: User not found", HttpStatus.NOT_FOUND);
		}
	}

}
