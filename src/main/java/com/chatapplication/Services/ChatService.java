package com.chatapplication.Services;

import com.chatapplication.Model.entity.Chat;
import com.chatapplication.Repository.ChatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

	private final ChatRepository chatRepository;
	private static final Logger log = LogManager.getLogger(ChatService.class);

	public ChatService(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	/**
	 *
	 * @return
	 */
	public ResponseEntity<Object> listAllChat()
	{
		try {
			List<Chat> chats = chatRepository.findAll();
			// check if db has return chats
			if (!chats.isEmpty()) {
				return new ResponseEntity<>(chats, HttpStatus.OK);
			} else
				return new ResponseEntity<>(" Chats are empty ", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching all chats list in class ChatService and its function ListAllChat ",
					e.getMessage());
			return new ResponseEntity<>("Unable to find list an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> getChatById(Long id) {
		try {
			Optional<Chat> chat = chatRepository.findById(id);
			if (chat.isPresent()) {
				return new ResponseEntity<>(chat, HttpStatus.FOUND);
			} else
				return new ResponseEntity<>("Chat not found. Incorrect id!!", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching Chat by id  in class ChatService and its function getChatById ",
					e.getMessage());
			return new ResponseEntity<>("Unable to get chat an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/**
	 *
	 * @param chats
	 * @return
	 */
	public ResponseEntity<Object> updateChat(List<Chat> chats) {
		try {
			for (Chat chat : chats) {
				String pattern = "dd-M-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				chat.setUpdatedDate(date);
				chatRepository.save(chat);
			}
			return new ResponseEntity<>(chats, HttpStatus.OK);
		} catch (Exception e) {
			log.error("some error has occurred during Chat Update in class ChatService and its function updateChat ",
					e.getMessage());
			return new ResponseEntity<>("Chats could no be Updated , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> deleteChat(Long id) {
		try {
			chatRepository.deleteById(id);
			return new ResponseEntity<>("chat has been Deleted successfully", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<>("Message: Chat does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("some error has occurred during Chat Deletion in class ChatService and its function DeleteChat ",
					e.getMessage());
			return new ResponseEntity<>("Chats could not be deleted due to some error",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
