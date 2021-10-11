package com.chatapplication.controller;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Model.entity.Permission;
import com.chatapplication.Services.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
	final private PermissionService permissionService;

	public PermissionController(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getAllPermission() {
		return permissionService.getAllPermission();
	}

	@PostMapping("/add")
	public ResponseEntity<Object> addPermission(@RequestBody List<Permission> permissions) {
		return permissionService.addPermission(permissions);
	}

	@GetMapping("/delete")
	public ResponseEntity<String> deletePermission(@PathVariable long id) {
		return permissionService.deletePermission(id);
	}

}
