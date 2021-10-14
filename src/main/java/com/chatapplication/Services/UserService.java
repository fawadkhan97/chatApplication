package com.chatapplication.Services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.chatapplication.Model.Interface.UserChatsAndCategoriesDTO;
import com.chatapplication.Model.entity.User;
import com.chatapplication.util.EmailUtil;
import com.chatapplication.util.SMSUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chatapplication.Model.Interface.UserChatsAndCategories;
import com.chatapplication.Model.Interface.UserDTO;
import com.chatapplication.Model.entity.Chat;
import com.chatapplication.Repository.ChatRepository;
import com.chatapplication.Repository.UserRepository;

/**
 * @author Fawad khan Created Date : 08-October-2021 A service class of user
 *         connected with repository which contains user CRUD operations
 */
@Service
public class UserService {
	final private UserRepository userRepository;
	final private ChatRepository chatRepository;
	final private EmailUtil emailUtil;
	final private RestTemplate restTemplate = new RestTemplate();
	final private SMSUtil smsUtil = new SMSUtil();

	private static final Logger log = LogManager.getLogger(UserService.class);

	// Autowiring through constructor
	public UserService(UserRepository userRepository, ChatRepository chatRepository, EmailUtil emailUtil) {
		this.userRepository = userRepository;
		this.chatRepository = chatRepository;
		this.emailUtil = emailUtil;
	}

	/**
	 * @author Fawad khan
	 * @return List of users
	 */
	// Get list of all users
	public ResponseEntity<Object> listAllUser() {
		try {

			List<User> users = userRepository.findAllByStatus(true);
			log.info("list of  users fetch from db are ", users);
			// check if list is empty
			if (users.isEmpty()) {
				return new ResponseEntity<>("  Users are empty", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(users, HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error(
					"some error has occurred trying to Fetch users, in Class  UserService and its function listAllUser ",
					e.getMessage());
			return new ResponseEntity<>("User could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 *
	 * @param id
	 * @return
	 */
	// get user by specific id
	public ResponseEntity<Object> getUserById(Long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				log.info("user fetch and found from db by id  : ", user.toString());
				return new ResponseEntity<>(user, HttpStatus.FOUND);
			} else
				log.info("no user found with id:", user.get().getId());
			return new ResponseEntity<>("could not found user with given details....", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching User by id , in class UserService and its function getUserById ",
					e.getMessage());

			return new ResponseEntity<>("Unable to find User, an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	// send user sms
	public ResponseEntity<Object> sendSms(Long id, String message) {
		UserDTO userDTO = new UserDTO();
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				log.info("user fetch and found from db by id  : ", user.toString());
				return smsUtil.sendSMS(user.get().getPhoneNumber(), message);
			} else {
				String url = "http://192.168.10.13:8080/user/" + id;
				HttpHeaders requestHeaders = new HttpHeaders();
				// add Authorization to headers
				requestHeaders.set("Authorization", "40dc498b-e837-4fa9-8e53-c1d51e01af15");
				HttpEntity<UserDTO> requestEntity = new HttpEntity(requestHeaders);

				System.out.println("url is " + url);
				// store request response in response entity object
				ResponseEntity<UserDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
						UserDTO.class);
				log.error(responseEntity.getBody());

				// store body from response entity in userDTO object
				userDTO = responseEntity.getBody();
				String phoneNumber = userDTO.getPhoneNumber();
				// String mymessage = jsonObj;
				System.out
						.println("response is " + responseEntity.getBody().getPhoneNumber() + " message is " + message);
				return smsUtil.sendSMS(phoneNumber, message);
			}
		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching User by id , in class UserService and its function sendSms ",
					e.getMessage());
			System.out.println(e.getMessage() + e.getCause());
			return new ResponseEntity<>("Unable to find User, an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * get all chats and categories of specific
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> getUserChatsAndCategories(Long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				// save chats and categories from user object
				UserChatsAndCategories userChatsAndCategories = new UserChatsAndCategories();
				userChatsAndCategories.setCategories(user.get().getCategories());
				userChatsAndCategories.setChats(user.get().getChats());
				log.info("user chats and categories from db by id  : ", user.get().getId());

				return new ResponseEntity<>(userChatsAndCategories, HttpStatus.FOUND);
			} else {
				String url = "http://192.168.10.13:8080/user/" + id;
				HttpHeaders requestHeaders = new HttpHeaders();
				// add Authorization to headers
				requestHeaders.set("Authorization", "40dc498b-e837-4fa9-8e53-c1d51e01af15");
				HttpEntity<UserDTO> requestEntity = new HttpEntity(requestHeaders);

				// store request response in response entity object
				ResponseEntity<UserDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
						UserDTO.class);
				log.error(responseEntity.getBody());

				UserChatsAndCategoriesDTO userChatsAndCategoriesDTO = new UserChatsAndCategoriesDTO();
				System.out.println("response is " + responseEntity.getBody());
				UserDTO userDTO = new UserDTO();
				userChatsAndCategoriesDTO.setCategories(responseEntity.getBody().getCategories());
				userChatsAndCategoriesDTO.setChats(responseEntity.getBody().getChat());

				// log.info("user fetch and found from 3rd party db by id : ",
				// user.get().getId());

				return new ResponseEntity<>(userChatsAndCategoriesDTO, responseEntity.getStatusCode());

			}
			// return new ResponseEntity<>("could not get user with given details....",
			// HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching User by id , in class UserService and its function getUserById ",
					e.getMessage());
			System.out.println(e.getMessage() + " " + e.getCause());
			return new ResponseEntity<>("Unable to find User, an error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/**
	 *
	 * @param userid
	 * @param chats
	 * @return
	 */
	// add chats for specific user
	public ResponseEntity<Object> addChatsInUser(Long userid, List<Chat> chats) {
		try {
			// find user in db and store in User object
			Optional<User> user = userRepository.findById(userid);
			if (user.isPresent()) {

				// get all chats from user
				List<Chat> userChats = user.get().getChats();

				// add created date to chats\
				for (Chat chat : chats) {
					String pattern = "dd-M-yyyy hh:mm:ss";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					String date = simpleDateFormat.format(new Date());
					chat.setCreatedDate(date);
				}

				// add new chats to existing chatlist
				userChats.addAll(chats);
				String userResult = user.get().toString();

				// save updated user object to chats
				userRepository.save(user.get());
				System.out.println(userResult + " ");
				return new ResponseEntity<>(userChats, HttpStatus.OK);
			} else
				return new ResponseEntity<>("Could not find user please correct userid", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred trying to save user,  Class  UserService and its function addChatsInUser ",
					e.getMessage());
			System.out.println(" error is " + e.getCause() + " " + e.getMessage());
			return new ResponseEntity<>("chats could not be added , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	public ResponseEntity<Object> getUserByNameAndPassword(String userName, String password) {
		try {
			Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);
			if (user.isPresent())
				return new ResponseEntity<>("login success", HttpStatus.OK);
			else
				return new ResponseEntity<>("incorrect login details, Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.error(
					"some error has occurred during fetching User by username , in class UserService and its function getUserByName ",
					e.getMessage());
			return new ResponseEntity<>("Unable to Login either password or username might be incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 *
	 * @param user
	 * @return
	 */
	public ResponseEntity<Object> saveUser(User user) {
		try {
			String pattern = "dd-MM-yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			user.setCreatedDate(date);
			user.setStatus(false);
			Random rndkey = new Random(); // Generating a random number
			int emailToken = rndkey.nextInt(999999); // Generating a random email token of 6 digits
			int smsToken = rndkey.nextInt(999999); // Generating a random email token of 6 digits
			// send email token to user email and save in db
			emailUtil.sendMail(user.getEmail(), emailToken);
			user.setEmailToken(emailToken);

			// send sms token to user email and save in db
			smsUtil.sendSMS(user.getPhoneNumber(), smsToken);
			user.setSmsToken(smsToken);

			// save user to db
			userRepository.save(user);
			user.toString();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Data already exists .. duplicates not allowed ", HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to save user,, in class UserService and its function saveUser ",
					e.getMessage());
			System.out.println("error is " + e.getMessage() + "  " + e.getCause());
			return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 *
	 * @param user
	 * @return
	 */
	public ResponseEntity<Object> updateUser(User user) {
		try {
			String pattern = "dd-MM-yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			user.setUpdatedDate(date);
			userRepository.save(user);
			user.toString();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "  " + e.getCause());
			log.error(
					"some error has occurred while trying to update user,, in class UserService and its function updateUser ",
					e.getMessage());
			return new ResponseEntity<>("Chats could not be added , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> deleteUser(Long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {

				// set status false
				user.get().setStatus(false);
				// set updated date
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				user.get().setUpdatedDate(date);
				userRepository.save(user.get());
				return new ResponseEntity<>("SMS: User deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("SMS: User does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to Delete user,, in class UserService and its function deleteUser ",
					e.getMessage(), e.getCause(), e);
			return new ResponseEntity<>("User could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	// verify user
	public ResponseEntity<Object> verifyUser(Long id, int emailToken, int smsToken) {
		try {
			Optional<User> user = userRepository.findByIdAndEmailTokenAndSmsToken(id, emailToken, smsToken);
			if (user.isPresent()) {
				System.out.println("user is : " + user.toString());
				user.get().setStatus(true);
				userRepository.save(user.get());
				return new ResponseEntity<>("user has been verified ", HttpStatus.OK);
			} else
				return new ResponseEntity<>("incorrect verification details were entered", HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			System.out.println(e.getCause());
			return new ResponseEntity<>("could not verify user please try again later",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
