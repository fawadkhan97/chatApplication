package com.chatapplication.Services;


import com.chatapplication.Model.entity.Role;
import com.chatapplication.Repository.RoleRepository;
import com.chatapplication.Repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
	private static final Logger log = LogManager.getLogger(RoleService.class);

	@Autowired
	RoleRepository roleRepository;

	/**
	 * get list of all role available
	 * 
	 * @author Fawad khan
	 * @Created Date 11-0ct-2021
	 * @return Role object if any role
	 */
	public ResponseEntity<Object> getAllRoles() {
		try {

			List<Role> roles = roleRepository.findAllByStatus(true);

			// check if list is empty or not
			if (roles.isEmpty()) {
				return new ResponseEntity<>("No roles found... ", HttpStatus.NOT_FOUND);
			} else
				return new ResponseEntity<>(roles, HttpStatus.OK);
		} catch (Exception e) {
			log.debug(
					"some error has occurred trying to Fetch Roles, in Class  RoleService and its function getALLRoles ",
					e.getCause(), e.getMessage());
			System.out.println(e.getMessage() + " \n " + e.getCause());
			return new ResponseEntity<>("Roles could not fetched...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * add role to db
	 * 
	 * @author Fawad khan
	 * @Created Date 11-0ct-2021
	 * @param role
	 * @return Role object
	 */

	public ResponseEntity<Object> addRole(Role role) {

		try {
			String pattern = "dd-MM-yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			role.setCreatedDate(date);
			roleRepository.save(role);
			return new ResponseEntity<>(role, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Role already exist , Duplicates not allowed", HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.debug("some error has occurred trying to save role, in Class  RoleService and its function addRole ",
					e.getCause(), e.getMessage());
			System.out.println(e.getMessage() + " \n " + e.getCause());
			return new ResponseEntity<>("Roles could not added...", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<Object> getRoleById(Long id) {
		try {
			Optional<Role> role = roleRepository.findById(id);
			if (role.isPresent())
				return new ResponseEntity<>(role, HttpStatus.FOUND);
			else
				return new ResponseEntity<>("could not found role , Check id", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching Role by id , in class RoleService and its function getRoleById ",
					e);

			return new ResponseEntity<>("Unable to find Role, an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	public ResponseEntity<Object> updateRole(List<Role> categories) {
		try {
			for (Role role : categories) {
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				role.setCreatedDate(date);
				roleRepository.save(role);
				role.toString();
			}
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to update role,, in class RoleService and its function updateRole ",
					e.getMessage());
			return new ResponseEntity<>("Categories could not be Updated , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> deleteRole(Long id) {
		try {
			Optional<Role> role = roleRepository.findById(id);
			if (role.isPresent()) {

				// set status false
				role.get().setStatus(false);
				// set updated date
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				role.get().setUpdatedDate(date);
				roleRepository.save(role.get());
				return new ResponseEntity<>("Message: Role deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("Message: Role does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to Delete role,, in class RoleService and its function deleteRole ",
					e.getMessage(), e.getCause(), e);
			return new ResponseEntity<>("Role could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
