package com.chatApplication.Controller;

import com.chatApplication.Model.Chat;
import com.chatApplication.Model.User;
import com.chatApplication.Services.ChatService;
import com.chatApplication.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/chat")
public class ChatController {


    @Autowired
    ChatService chatService;


    @GetMapping(" ")
    public List<Chat> chatList() {
        return chatService.listAllChat();
    }

    @GetMapping("/get")
    public ResponseEntity<Chat> get(@RequestParam ("question") Long id) {
        try {
           Chat chat =  chatService.getChat(id);
           return new ResponseEntity<Chat>(chat,HttpStatus.FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/addchat")
    public void add(@RequestBody Chat chat) {
        chatService.save(chat);
    }

    @PutMapping("/updatechat")
    public ResponseEntity<?> update(@RequestBody Chat chat) {

        try {
            chatService.save(chat);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletechat/{id}")
    public void delete(@PathVariable Long id) {
        chatService.delete(id);
    }

}
