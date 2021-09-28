package com.chatApplication.Controller;

import com.chatApplication.Model.User;
import com.chatApplication.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	private static final String defaultAuthValue = "user12345";

	// check user is authorize or not
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	@GetMapping("/login")
	public boolean userLogin(@RequestParam(value = "username", required = true) String paramUserName,
			@RequestParam(value = "password", required = false) String paramPassword) {
		User user = userService.getbyName(paramUserName);
		System.out.println(user.getUserName() + "  email is " + user.getEmail() + "id is " + user.getId());
		return false;

	}

	@GetMapping(" ")
	public ResponseEntity<Object> userList(@RequestHeader("Authorization") String authValue) {

		if (authorize(authValue)) {
			List<User> userList = userService.listAllUser();
			// check if database is empty
			if (userList.isEmpty()) {
				return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(userList, HttpStatus.OK);
			}

		} else
			return new ResponseEntity<>("Not authorize", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addUser(@RequestHeader("Authorization") String authValue, @RequestBody User user) {
		// check authorization
		if (authorize(authValue)) {
			userService.save(user);
			return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);

		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Object> get(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
		if (authorize(authValue)) {
			try {
				User user = userService.get(id);
				return new ResponseEntity<>(user, HttpStatus.CREATED);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("User not found incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("Not authorize", HttpStatus.UNAUTHORIZED);

		}
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestHeader("authorization") String authValue, @RequestBody User user) {
		if (authorize(authValue)) {
			try {
				userService.save(user);
				return new ResponseEntity<>("User updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("User not found incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(" not authorize ", HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			userService.delete(id);
			return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}

}
