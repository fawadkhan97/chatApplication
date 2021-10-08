package com.chatapplication.Model.Interface;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Model.entity.Chat;

import java.util.ArrayList;
import java.util.List;

public class UserChatsAndCategories {

	List<Chat> chats = new ArrayList<>();
	List<Category> categories = new ArrayList<>();

	public UserChatsAndCategories(List<Chat> chats, List<Category> categories) {
		this.chats = chats;
		this.categories = categories;
	}

	public List<Chat> getChats() {

		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
