package com.chatapplication.Services;

import com.chatapplication.Model.entity.Permission;
import com.chatapplication.Repository.PermissionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author fawad khan
 * @createdDate 13-oct-2021
 */
@Service
public class PermissionService {

	final private PermissionRepository permissionRepository;
	private static final Logger log = LogManager.getLogger(PermissionService.class);

	// autowiring permissionRepository
	public PermissionService(PermissionRepository permissionRepository) {
		this.permissionRepository = permissionRepository;
	}

	/**
	 * @author fawad khan
	 * @createdDate 13-oct-2021
	 * @return list of permissions available
	 */
	public ResponseEntity<Object> getAllPermission() {

		try {
			List<Permission> permissions = permissionRepository.findAllByStatus(true);
			if (!permissions.isEmpty()) {
				return new ResponseEntity<>(permissions, HttpStatus.OK);
			} else
				return new ResponseEntity<>(" no permission is available ", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			System.out.println(e.getMessage() + " \n " + e.getCause());
			return new ResponseEntity<>(" Could not fetch permissions due to some error",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @author fawad khan
	 * @createdDate 13-oct-2021
	 * @param id
	 * @return specific permission object as specify by id
	 */
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

	/**
	 * @author fawad khan
	 * @createdDate 13-oct-2021
	 * @param permissions
	 * @return saved permission object
	 */
	public ResponseEntity<Object> savePermission(List<Permission> permissions) {
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
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Could not add new permission , Permission already exist or ", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " \n " + e.getCause());
			return new ResponseEntity<>("Could not add permissions due to some  error",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @author fawad khan
	 * @createdDate 13-oct-2021
	 * @param permissions
	 * @return
	 */
	public ResponseEntity<Object> updatePermission(List<Permission> permissions) {
		try {
			for (Permission permission : permissions) {
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				permission.setUpdatedDate(date);
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

	/**
	 * @author fawad khan
	 * @createdDate 13-oct-2021
	 * @param id
	 * @return
	 */
	public ResponseEntity<String> deletePermission(Long id) {
		try {
			Optional<Permission> permission = permissionRepository.findById(id);
			if (permission.isPresent()) {

				// set status false
				permission.get().setStatus(false);
				// set updated date
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				permission.get().setUpdatedDate(date);
				permissionRepository.save(permission.get());
				return new ResponseEntity<>("SMS: Permission deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("SMS: Permission does not exists ", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>("Permission could not be Deleted..Due to some error.....",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}