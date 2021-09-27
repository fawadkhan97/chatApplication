package com.chatApplication.Services;

import com.chatApplication.Model.User;
import com.chatApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
     UserRepository userRepository;

    public void saveUser (User user){
        userRepository.save(user);
    }

    public List<User> listAllUser(){
        return userRepository.findAll();
    }
}
