package com.chatApplication.Services;

import com.chatApplication.Model.User;
import com.chatApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public List<User> listAllUser() {
		return userRepository.findAll();
	}

	public User get(Long id) {
		return userRepository.findById(id).get();
	}

	public User getbyName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

}
