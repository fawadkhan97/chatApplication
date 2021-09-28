package com.chatApplication.Controller;

import com.chatApplication.Model.User;
import com.chatApplication.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(" ")
    public List<User> userList() {
        return userService.listAllUser();
    }

    @PostMapping("/adduser")
    public void addUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateuser")
    public ResponseEntity<?> update(@RequestBody User user) {

        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("deleteuser/{id}")
    public void delete(@PathVariable Long id) {


        userService.delete(id);
    }


}
