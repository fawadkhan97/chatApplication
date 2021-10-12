package com.chatapplication.controller;

import com.chatapplication.Model.entity.Permission;
import com.chatapplication.Services.PermissionService;
import com.chatapplication.Services.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
	final private PermissionService permissionService;
	private static final String defaultAuthValue = "permission12345";
	private static final Logger log = LogManager.getLogger(PermissionService.class);

	public PermissionController(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	/**
	 * check permission is authorized or not
	 *
	 * @param authValue
	 * @return
	 */
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getAllPermission(@RequestHeader("Authorization") String authValue) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return permissionService.getAllPermission();
			} else {
				return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getPermission(@RequestHeader("Authorization") String authValue,
			@PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return permissionService.getPermissionById(id);
			} else {
				return new ResponseEntity<>("Message: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Object> addPermission(@RequestHeader("Authorization") String authValue,
			@RequestBody List<Permission> permissions) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return permissionService.savePermission(permissions);
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
	 * @param permission
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<Object> updatePermission(@RequestHeader("Authorization") String authValue,
			@RequestBody List<Permission> permission) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return permissionService.updatePermission(permission);
			} else
				return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePermission(@RequestHeader("Authorization") String authValue,
			@PathVariable long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return permissionService.deletePermission(id);
			} else
				return new ResponseEntity<>("Message:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

}
