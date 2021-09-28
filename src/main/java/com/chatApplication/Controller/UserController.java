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
    private static final String defaultAuthValue = "fawad12345";

    public Boolean authorize(String authValue) {

        if (defaultAuthValue.equals(authValue)) {
            return true;

        } else return false;

    }


    @GetMapping(" ")
    public ResponseEntity<List<User>> userList(@RequestHeader("Authorization") String authValue) {

        if (authorize(authValue)) {
            List<User> userList = userService.listAllUser();
            // check if database is empty
            if (userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {

                return new ResponseEntity<>(userList, HttpStatus.OK);
            }

        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestHeader("Authorization") String authValue, @RequestBody User user) {
        if (authorize(authValue)) {
            userService.save(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        } else return new ResponseEntity<>("not authorize ", HttpStatus.FORBIDDEN);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> get(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
        if (authorize(authValue)) {
            try {
                User user = userService.get(id);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else return new ResponseEntity<>( HttpStatus.FORBIDDEN);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody User user) {

        try {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }


}
