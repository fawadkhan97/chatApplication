package com.chatapplication.Services;

import com.chatapplication.Model.entity.Permission;
import com.chatapplication.Repository.PermissionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

	final private PermissionRepository permissionRepository;
	private static final Logger log = LogManager.getLogger(PermissionService.class);


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

	public ResponseEntity<Object> getPermissionById(Long id) {
		try {
			Optional<Permission> permission = permissionRepository.findById(id);
			if (permission.isPresent())
				return new ResponseEntity<>(permission, HttpStatus.FOUND);
			else
				return new ResponseEntity<>("could not found permission , Check id", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching Permission by id , in class PermissionService and its function getPermissionById ",
					e);

			return new ResponseEntity<>("Unable to find Permission, an error has occurred",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	public ResponseEntity<Object> savePermission(List<Permission> permissions) {
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

	public ResponseEntity<Object> updatePermission(List<Permission> permissions) {
		try {
			for (Permission permission : permissions) {
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				permission.setCreatedDate(date);
				permissionRepository.save(permission);
				permission.toString();
			}
			return new ResponseEntity<>(permissions, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to update permission,, in class PermissionService and its function updatePermission ",
					e.getMessage());
			return new ResponseEntity<>("Permissions could not be Updated , Data maybe incorrect",
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