package com.example.cleartrip_social_media;

import com.example.cleartrip_social_media.controllers.PostController;
import com.example.cleartrip_social_media.controllers.UserController;
import com.example.cleartrip_social_media.dtos.*;
import com.example.cleartrip_social_media.enums.PostInteractionType;
import com.example.cleartrip_social_media.enums.UserInteractionType;
import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CleartripSocialMediaApplication implements CommandLineRunner {
	private final UserController userController;
	private final PostController postController;
	@Autowired
	public CleartripSocialMediaApplication(UserController userController, PostController postController) {
		this.userController = userController;
		this.postController = postController;
	}

	public static void main(String[] args) {
		SpringApplication.run(CleartripSocialMediaApplication.class, args);
	}

	public void sendUserInteractionRequest(String userId1, String userId2, UserInteractionType userInteractionType) {
		UserInteractionRequestDTO userInteractionRequestDTO = new UserInteractionRequestDTO(
				userId1,
				userId2,
				userInteractionType
		);
		System.out.println(userController.interactWithUser(userInteractionRequestDTO));
	}
	public UserResponseDTO registerUser(String firstName, String lastName, int day, String month, int year, String email, String password, String contact) {

		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setFirstName(firstName);
		userRequestDTO.setLastName(lastName);
		userRequestDTO.setEmail(email);
		userRequestDTO.setContact(contact);
		userRequestDTO.setDateOfBirthDTO(
				new DateOfBirthDTO(day, month, year)
		);
		userRequestDTO.setPassword(password);

		ResponseDTO<UserResponseDTO> responseDTO = userController.registerUser(userRequestDTO);
		System.out.println(responseDTO);
		return responseDTO.getEntity();
	}

	public PostResponseDTO uploadPost(String userId, String content) {
		ResponseDTO<PostResponseDTO> responseDTO = postController.uploadPost(
				new PostRequestDTO(userId, content)
		);
		System.out.println(responseDTO);
		return responseDTO.getEntity();
	}

	public void showFeed(String userId) {
		System.out.println(postController.showFeed(new FeedRequestDTO(userId)));
	}

	public PostInteractionResponseDTO interactWithPost(String userId, String postId, PostInteractionType action) {
		ResponseDTO <PostInteractionResponseDTO> responseDTO = postController.interactWithPost(
				new PostInteractionRequestDTO(postId, userId, action)
		);
		System.out.println(responseDTO);
		return responseDTO.getEntity();
	}
	@Override
	public void run(String... ags) throws InvalidDateOfBirthException {
		// Creating random users
		System.out.println("\n-----------------------------registering users-----------------------------------");
		UserResponseDTO user1 = registerUser("Alice", "Walker", 27, "12", 2001, "abc@gmail.com", "1234", "9999999999");
		UserResponseDTO user2 = registerUser("Bob", "Parker", 22, "july", 2004, "def@gmail.com", "5678", "8888888888");
		UserResponseDTO user3 = registerUser("Ron", "Mosby", 12, "2", 2002, "ghi@gmail.com", "9012", "7777777777");
		UserResponseDTO user4 = registerUser("Barney", "Stinson", 28, "10", 1997, "jkl@gmail.com", "3456", "6666666666");

		if ((user1 == null) || (user2 == null) || (user3 == null) || (user4 == null)) return;

		// Checking Follow requests
		System.out.println("----------------------sending follow/unfollow requests----------------------------");
		sendUserInteractionRequest(user1.getId(), user2.getId(), UserInteractionType.FOLLOW);
		sendUserInteractionRequest(user1.getId(), user3.getId(), UserInteractionType.FOLLOW);
		sendUserInteractionRequest(user1.getId(), user4.getId(), UserInteractionType.FOLLOW);

		// Checking if redundant follow request works
		sendUserInteractionRequest(user1.getId(), user2.getId(), UserInteractionType.FOLLOW);

		// Checking Unfollow requests
		sendUserInteractionRequest(user1.getId(), user4.getId(), UserInteractionType.UNFOLLOW);

		// Checking if redundant unfollow request works
		sendUserInteractionRequest(user1.getId(), user4.getId(), UserInteractionType.UNFOLLOW);


		// Checking if follow/unfollow request handles anonymous users
		sendUserInteractionRequest(user1.getId(), "anonymousUserId", UserInteractionType.FOLLOW);
		sendUserInteractionRequest(user1.getId(), "anonymousUserId", UserInteractionType.UNFOLLOW);

		// Checking if upload posts requests work
		System.out.println("--------------------------------uploading posts-----------------------------------");
		PostResponseDTO post1 = uploadPost(user1.getId(), "Writing code for Thinkify Labs");
		PostResponseDTO post2 = uploadPost(user2.getId(), "Working as an instructor at Scaler academy");
		PostResponseDTO post3 = uploadPost(user2.getId(), "Secured Very good ratings on teaching sessions");
		PostResponseDTO post4 = uploadPost(user3.getId(), "Solved more than 1000 DSA problems");
		PostResponseDTO post5 = uploadPost(user3.getId(), "Maintaining over 500 days of streak in Leetcode");
		PostResponseDTO post6 = uploadPost(user4.getId(), "Half the way through Masters in Computer Sciende part time");
		PostResponseDTO post7 = uploadPost(user4.getId(), "Actively Learning system design concepts");

		// Checking if upload post requests handle anonymous inputs
		PostResponseDTO post8 = uploadPost("anonymousUserId", "Checking with Anonymous user Id");
		PostResponseDTO post9 = uploadPost(user1.getId(), "");

		// Checking if LIKE/DISLIKE requests work
		System.out.println("--------------------------sending LIKE/DISLIKE requests------------------------------");
		interactWithPost(user1.getId(), post2.getId(), PostInteractionType.DISLIKE);
		interactWithPost(user1.getId(), post2.getId(), PostInteractionType.LIKE);
		interactWithPost(user1.getId(), post2.getId(), PostInteractionType.LIKE);
		interactWithPost(user1.getId(), post3.getId(), PostInteractionType.DISLIKE);
		interactWithPost(user1.getId(), post4.getId(), PostInteractionType.LIKE);
		interactWithPost(user2.getId(), post2.getId(), PostInteractionType.LIKE);
		interactWithPost(user2.getId(), post3.getId(), PostInteractionType.LIKE);
		interactWithPost(user3.getId(), post1.getId(), PostInteractionType.DISLIKE);

		// Checking if Feed generating
		System.out.println("--------------------------------generating feed-----------------------------------");
		showFeed(user1.getId());
		System.out.println(user1.getId());
	}

}
