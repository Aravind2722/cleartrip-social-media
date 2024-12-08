package com.example.cleartrip_social_media;

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
	private UserController userController;
	@Autowired
	public CleartripSocialMediaApplication(UserController userController) {
		this.userController = userController;
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
	@Override
	public void run(String... ags) throws InvalidDateOfBirthException {
		// Creating random users
		List<UserResponseDTO> users = registerMultipleUsers();

		// Following each other
		for (UserResponseDTO user1 : users) {
			for (UserResponseDTO user2 : users) {
				if (user1.equals(user2)) continue;
				sendUserInteractionRequest(user1.getId(), user2.getId(), UserInteractionType.FOLLOW);
			}
		}
		// Checking if redundant follow request works
		sendUserInteractionRequest(users.get(0).getId(), users.get(1).getId(), UserInteractionType.FOLLOW);

		// Unfollowing each other
		for (UserResponseDTO user1 : users) {
			for (UserResponseDTO user2 : users) {
				if (user1.equals(user2)) continue;
				sendUserInteractionRequest(user1.getId(), user2.getId(), UserInteractionType.UNFOLLOW);
			}
		}
		// Checking if redundant unfollow request works
		sendUserInteractionRequest(users.get(0).getId(), users.get(1).getId(), UserInteractionType.UNFOLLOW);

		// Checking if follow/unfollow request works on anonymous users
		sendUserInteractionRequest("abc", "efg", UserInteractionType.FOLLOW);
		sendUserInteractionRequest("abc", "efg", UserInteractionType.UNFOLLOW);
	}

}
