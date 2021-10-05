package com.chatapplication.Services;
import com.chatapplication.Controller.UserController;
import com.chatapplication.Model.Chat;
import com.chatapplication.Model.User;
import com.chatapplication.Repository.ChatRepository;
import com.chatapplication.Repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	final private UserRepository userRepository;
	final private ChatRepository chatRepository;

	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	// Autowiring through constructor
	public UserService(UserRepository userRepository, ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
		this.userRepository = userRepository;
	}

	// Get list of all users
	public ResponseEntity<Object> listAllUser() {
		List<User> userList = userRepository.findAll();
		LOGGER.info(userList);
		// check if database is empty
		if (userList.isEmpty()) {
			return new ResponseEntity<>("Message: No data available", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}

	}

	public Boolean addChatByID(Long userid, List<Chat> chatlist) {
		// find user in db and store in User object
		Optional<User> user = userRepository.findById(userid);

		// add created Date to chats
		for (Chat chat : chatlist) {
			String pattern = "dd-M-yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			chat.setCreatedDate(date);
		}

		if (user.isPresent()) {
			// get all chats from user
			List<Chat> userChatlist = user.get().getChats();

			// add new chats to existing chatlist
			userChatlist.addAll(chatlist);
			String userResult = user.get().toString();

			// save updated user object to chats
			userRepository.save(user.get());
			System.out.println(userResult + " ");
			return true;
		}

		return false;

	}

	public User get(Long id) {
		return userRepository.findById(id).get();
	}

	public User getbyName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public User save(User user) {

		user.toString();

		return userRepository.save(user);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

}
