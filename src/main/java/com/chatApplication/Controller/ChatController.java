package com.chatApplication.Controller;

import com.chatApplication.Model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @RequestMapping("/showUser")
    public String showUser(){
        return "User";
    }
}
