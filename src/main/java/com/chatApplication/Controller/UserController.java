package com.chatApplication.Controller;

import com.chatApplication.Model.User;
import com.chatApplication.Repository.UserRepository;
import com.chatApplication.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(" ")
    public List<User> userList (){
        return userService.listAllUser();
    }


}
