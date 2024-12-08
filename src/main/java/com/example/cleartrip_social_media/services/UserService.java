package com.example.cleartrip_social_media.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.cleartrip_social_media.builders.DateOfBirthBuilder;
import com.example.cleartrip_social_media.builders.UserInteractionResponseDTOBuilder;
import com.example.cleartrip_social_media.builders.UserResponseDTOBuilder;
import com.example.cleartrip_social_media.dtos.UserInteractionRequestDTO;
import com.example.cleartrip_social_media.dtos.UserInteractionResponseDTO;
import com.example.cleartrip_social_media.dtos.UserRequestDTO;
import com.example.cleartrip_social_media.dtos.UserResponseDTO;
import com.example.cleartrip_social_media.enums.UserInteractionType;
import com.example.cleartrip_social_media.enums.UserNotFoundException;
import com.example.cleartrip_social_media.exceptions.InvalidContactNumberException;
import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import com.example.cleartrip_social_media.exceptions.InvalidUserInteractionRequestException;
import com.example.cleartrip_social_media.exceptions.UserAlreadyExistsException;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.repositories.UserRepository;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistsException,
            InvalidDateOfBirthException, InvalidContactNumberException {

        validateUserRequestDTO(userRequestDTO);

        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());

        user.setDateOfBirth(
                DateOfBirthBuilder.getBuilder()
                        .setDay(userRequestDTO.getDateOfBirthDTO().getDay())
                        .setMonth(userRequestDTO.getDateOfBirthDTO().getMonth())
                        .setYear(userRequestDTO.getDateOfBirthDTO().getYear())
                        .build()
        );

        user.setEmail(userRequestDTO.getEmail());
        user.setContact(userRequestDTO.getContact());

        String hashedPassword = BCrypt.withDefaults().hashToString(
                12, userRequestDTO.getPassword().toCharArray()
        );
        user.setPassword(hashedPassword);

        user.setId(
                Generators.timeBasedEpochGenerator().generate().toString()
        );
        user.setFollowers(new HashSet<>());
        user.setFollowees(new HashSet<>());

        User savedUser = userRepository.save(user);
        return UserResponseDTOBuilder.getBuilder()
                .setId(savedUser.getId())
                .setFirstName(savedUser.getFirstName())
                .setLastName(savedUser.getLastName())
                .setDateOfBirth(savedUser.getDateOfBirth())
                .setEmail(savedUser.getEmail())
                .setContact(savedUser.getContact())
                .build();
    }

    public UserInteractionResponseDTO interactWithUser(UserInteractionRequestDTO userInteractionRequestDTO) throws UserNotFoundException, InvalidUserInteractionRequestException {
        validateUserInteractionRequestDTO(userInteractionRequestDTO);

        Optional<User> optionalfollower = userRepository.getUserById(userInteractionRequestDTO.getFollowerId());
        if (optionalfollower.isEmpty()) throw new UserNotFoundException("user-id '" + userInteractionRequestDTO.getFollowerId() + "' does not exist!");

        Optional<User> optionalFollowee = userRepository.getUserById(userInteractionRequestDTO.getFolloweeId());
        if (optionalFollowee.isEmpty()) throw new UserNotFoundException("user-id '" + userInteractionRequestDTO.getFolloweeId() + "' does not exist!");

        User follower = optionalfollower.get();
        User followee = optionalFollowee.get();

        if (userInteractionRequestDTO.getUserInteractionType().equals(UserInteractionType.FOLLOW)) {
            if (follower.getFollowees().contains(followee)) throw new InvalidUserInteractionRequestException(
                    "user '" + followee.getId() + "' is already followed!"
            );
            follower.getFollowees().add(followee);
            followee.getFollowers().add(follower);
        }

        if (userInteractionRequestDTO.getUserInteractionType().equals(UserInteractionType.UNFOLLOW)) {
            if (!follower.getFollowees().contains(followee)) throw new InvalidUserInteractionRequestException(
                    "user '" + followee.getId() + "' is not followed!"
            );
            follower.getFollowees().remove(followee);
            followee.getFollowers().remove(follower);
        }
        return UserInteractionResponseDTOBuilder.getBuilder()
                .setFolloweeId(followee.getId())
                .setFollowerId(follower.getId())
                .setFolloweeName(followee.getFirstName().trim() + " " + followee.getLastName().trim())
                .setFollowerName(follower.getFirstName().trim() + " " + follower.getLastName().trim())
                .setUserInteractionType(userInteractionRequestDTO.getUserInteractionType())
                .build();
    }

    public void validateUserInteractionRequestDTO(UserInteractionRequestDTO userInteractionRequestDTO) {
        if (userInteractionRequestDTO == null) throw new IllegalArgumentException("user interaction cannot be empty!");
        if ((userInteractionRequestDTO.getFollowerId() == null) ||(userInteractionRequestDTO.getFollowerId().isBlank())) throw new IllegalArgumentException(
                "follower-id cannot be empty!"
        );
        if ((userInteractionRequestDTO.getFolloweeId() == null) ||(userInteractionRequestDTO.getFolloweeId().isBlank())) throw new IllegalArgumentException(
                "follower-id cannot be empty!"
        );
        if (userInteractionRequestDTO.getUserInteractionType() == null) throw new IllegalArgumentException(
                "interaction type cannot be empty!"
        );

    }

    public void validateUserRequestDTO(UserRequestDTO userRequestDTO) throws UserAlreadyExistsException, InvalidContactNumberException {

        if (userRequestDTO == null) throw new IllegalArgumentException("User details cannot be empty!");
        if (userRequestDTO.getFirstName() == null) throw new IllegalArgumentException("First name cannot be empty!");
        if (userRequestDTO.getLastName() == null) throw new IllegalArgumentException("Last name cannot be empty!");
        if (userRequestDTO.getDateOfBirthDTO() == null) throw new IllegalArgumentException("Date of birth cannot be empty!");
        if (userRequestDTO.getContact() == null) throw new IllegalArgumentException("Contact cannot be Empty!");

        if ((userRequestDTO.getFirstName() + userRequestDTO.getLastName()).isBlank()) throw new IllegalArgumentException(
                "User name cannot be empty!"
        );
        if ((userRequestDTO.getEmail() == null) || (userRequestDTO.getEmail().isBlank())) throw new IllegalArgumentException(
                "Email cannot be Empty!"
        );
        if ((userRequestDTO.getPassword() == null) || (userRequestDTO.getPassword().isBlank())) throw new IllegalArgumentException(
                "Password cannot be Empty!"
        );
        if (userRequestDTO.getContact().length() != 10) throw new InvalidContactNumberException(
                "Invalid contact number provided '" + userRequestDTO.getContact() + "' !"
        );
        if (userRepository.getUserByEmail(userRequestDTO.getEmail()).isPresent()) throw new UserAlreadyExistsException(
                "user '" + userRequestDTO.getEmail() + "' already exists!"
        );
    }

}
