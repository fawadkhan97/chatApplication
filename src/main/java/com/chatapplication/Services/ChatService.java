package com.chatapplication.Services;

import com.chatapplication.Model.Chat;
import com.chatapplication.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;
    Date date;

    public List<Chat> listAllChat() {
        return chatRepository.findAll();
    }

    public Chat get(Long id) {

        return chatRepository.findById(id).get();
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }


    public void delete(Long id) {
        chatRepository.deleteById(id);
    }


}
