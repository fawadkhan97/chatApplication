package com.chatapplication.Model.Interface;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Model.entity.Chat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserChatsAndCategories {
        List<Chat> chats = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
    }


