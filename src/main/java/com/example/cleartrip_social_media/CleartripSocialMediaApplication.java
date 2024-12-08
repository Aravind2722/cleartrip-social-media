package com.example.cleartrip_social_media;

import com.example.cleartrip_social_media.controllers.PostController;
import com.example.cleartrip_social_media.controllers.UserController;
import com.example.cleartrip_social_media.dtos.*;
import com.example.cleartrip_social_media.enums.UserInteractionType;
import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public List <UserResponseDTO> registerMultipleUsers() {
		UserRequestDTO userRequestDTO1 = new UserRequestDTO();
		userRequestDTO1.setFirstName("Alice");
		userRequestDTO1.setLastName("");
		userRequestDTO1.setEmail("abc@gmail.com");
		userRequestDTO1.setContact("9999999999");
		userRequestDTO1.setDateOfBirthDTO(
				new DateOfBirthDTO(27, "july", 2004)
		);
		userRequestDTO1.setPassword("1234");

		UserRequestDTO userRequestDTO2 = new UserRequestDTO();
		userRequestDTO2.setFirstName("Bob");
		userRequestDTO2.setLastName("");
		userRequestDTO2.setEmail("def@gmail.com");
		userRequestDTO2.setContact("8888888888");
		userRequestDTO2.setDateOfBirthDTO(
				new DateOfBirthDTO(22, "12", 2001)
		);
		userRequestDTO2.setPassword("5678");

		ResponseDTO<UserResponseDTO> responseDTO1 = userController.registerUser(userRequestDTO1);
		ResponseDTO<UserResponseDTO> responseDTO2 = userController.registerUser(userRequestDTO2);
		System.out.println(responseDTO1);
		System.out.println(responseDTO2);

        return new ArrayList<>(Arrays.asList(responseDTO1.getEntity(), responseDTO2.getEntity()));

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
	@Override
	public void run(String... ags) throws InvalidDateOfBirthException {
		// Creating random users
		UserResponseDTO user1 = registerUser("Alice", "Walker", 27, "12", 2001, "abc@gmail.com", "1234", "9999999999");
		UserResponseDTO user2 = registerUser("Bob", "Parker", 22, "july", 2004, "def@gmail.com", "5678", "8888888888");
		UserResponseDTO user3 = registerUser("Ron", "Mosby", 12, "2", 2002, "ghi@gmail.com", "9012", "7777777777");
		UserResponseDTO user4 = registerUser("Barney", "Stinson", 28, "10", 1997, "jkl@gmail.com", "3456", "6666666666");

		// Checking Follow requests
		sendUserInteractionRequest(user1.getId(), user2.getId(), UserInteractionType.FOLLOW);
		sendUserInteractionRequest(user1.getId(), user3.getId(), UserInteractionType.FOLLOW);
		sendUserInteractionRequest(user1.getId(), user4.getId(), UserInteractionType.FOLLOW);

		// Checking if redundant follow request works
		sendUserInteractionRequest(user1.getId(), user2.getId(), UserInteractionType.FOLLOW);

		// Checking Unfollow requests
		sendUserInteractionRequest(user1.getId(), user4.getId(), UserInteractionType.UNFOLLOW);

		// Checking if redundant unfollow request works
		sendUserInteractionRequest(user1.getId(), user4.getId(), UserInteractionType.UNFOLLOW);


		// Checking if follow/unfollow request works on anonymous users
		sendUserInteractionRequest(user1.getId(), "anonymousUserId", UserInteractionType.FOLLOW);
		sendUserInteractionRequest(user1.getId(), "anonymousUserId", UserInteractionType.UNFOLLOW);

		PostResponseDTO post1 = uploadPost(user1.getId(), "Writing code for Thinkify Labs");
		PostResponseDTO post2 = uploadPost(user2.getId(), "Working as an instructor at Scaler academy");
		PostResponseDTO post3 = uploadPost(user2.getId(), "Secured Very good ratings on teaching sessions");
		PostResponseDTO post4 = uploadPost(user3.getId(), "Solved more than 1000 DSA problems");
		PostResponseDTO post5 = uploadPost(user3.getId(), "Maintaining over 500 days of streak in Leetcode");
		PostResponseDTO post6 = uploadPost(user4.getId(), "Half the way through Masters in Computer Sciende part time");
		PostResponseDTO post7 = uploadPost(user4.getId(), "Actively Learning system design concepts");
	}

}
