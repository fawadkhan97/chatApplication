package com.chatapplication.Services;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Model.entity.Permission;
import com.chatapplication.Repository.PermissionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

	final private PermissionRepository permissionRepository;

	public PermissionService(PermissionRepository permissionRepository) {
		this.permissionRepository = permissionRepository;
	}

	public ResponseEntity<Object> getAllPermission() {

		try {

			List<Permission> permissions = permissionRepository.findAll();
			if (permissions.isEmpty()) {
				return new ResponseEntity<>(" no permission is available", HttpStatus.NOT_FOUND);

			} else
				return new ResponseEntity<>(permissions, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage() + " \n " + e.getCause());
			return new ResponseEntity<>("Could not fetch permissions due to some  error",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> addPermission(List<Permission> permissions) {
		try {
			for (Permission permission : permissions) {
				permissionRepository.save(permission);
			}
			return new ResponseEntity<>(permissions, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Could not add new permission , Permission already exist", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " \n " + e.getCause());
			return new ResponseEntity<>("Could not add permissions due to some  error",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> deletePermission(Long id) {
		try {
			permissionRepository.deleteById(id);
			return new ResponseEntity<>("Message: Permission deleted successfully", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<>("Message: Permission does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {

			return new ResponseEntity<>("Permission could not be Deleted..Due to some error.....",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
}