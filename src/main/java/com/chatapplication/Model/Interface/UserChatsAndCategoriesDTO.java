package com.chatapplication.Model.Interface;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserChatsAndCategoriesDTO {
	List<ChatDTO> chats = new ArrayList<>();
	List<CategoryDTO> categories = new ArrayList<>();
}
