package com.chatapplication.controller;

import com.chatapplication.Model.entity.Role;
import com.chatapplication.Services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
	final private String adminToken = "admin12345";
	final private RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getAllRoles(@RequestHeader(required = false, value = "Authorization") String token) {

		if (token != null) {
			if (token.equals(adminToken)) {
				return roleService.getAllRoles();
			} else
				return new ResponseEntity<>("Message: Not authorize, you dont have privilege ",
						HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/add")
	public ResponseEntity<Object> saveRole(@RequestHeader(required = false, value = "Auhtorization") String token,
	@RequestBody Role role)  {

		if (token != null) {
			return roleService.addRole(role);
		} else
			return new ResponseEntity<>("Could not Authrize incorrect Token", HttpStatus.UNAUTHORIZED);

	}

}
