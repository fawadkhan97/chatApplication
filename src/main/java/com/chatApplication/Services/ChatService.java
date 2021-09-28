package com.chatApplication.Services;

import com.chatApplication.Model.Chat;
import com.chatApplication.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;
    Date date;

    public List<Chat> listAllChat(){
        return chatRepository.findAll();
    }

    public Chat get(Long id){

       return chatRepository.findById(id).get();
    }

    public  void save(Chat chat){

        chat.setCreatedDate(date = new Date());
        System.out.println("date is "+date);
        chatRepository.save(chat);

    }


    public void delete(Long id ){
        chatRepository.deleteById(id);
    }


}
