package com.example.cleartrip_social_media.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.cleartrip_social_media.builders.DateOfBirthBuilder;
import com.example.cleartrip_social_media.builders.UserInteractionResponseDTOBuilder;
import com.example.cleartrip_social_media.builders.UserResponseDTOBuilder;
import com.example.cleartrip_social_media.dtos.*;
import com.example.cleartrip_social_media.enums.UserInteractionType;
import com.example.cleartrip_social_media.exceptions.*;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.repositories.UserRepository;
import com.example.cleartrip_social_media.strategies.Validator;
import com.example.cleartrip_social_media.validators.UserInteractionRquestDTOValidator;
import com.example.cleartrip_social_media.validators.UserRequestDTOValidator;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Validator<UserRequestDTO> userRequestDTOValidator;
    private final Validator<UserInteractionRequestDTO> userInteractionRquestDTOValidator;

    @Autowired
    public UserService(UserRepository userRepository,
                       @Lazy UserRequestDTOValidator userRequestDTOValidator,
                       @Lazy UserInteractionRquestDTOValidator userInteractionRquestDTOValidator
    ) {
        this.userRepository = userRepository;
        this.userRequestDTOValidator = userRequestDTOValidator;
        this.userInteractionRquestDTOValidator = userInteractionRquestDTOValidator;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) throws ValidationException {

        userRequestDTOValidator.validate(userRequestDTO);

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
        user.setPosts(new ArrayList<>());
        user.setActivity(new ArrayList<>());

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

    public UserInteractionResponseDTO interactWithUser(UserInteractionRequestDTO userInteractionRequestDTO) throws ValidationException {
        userInteractionRquestDTOValidator.validate(userInteractionRequestDTO);

        Optional<User> optionalfollower = userRepository.getUserById(userInteractionRequestDTO.getFollowerId());
        Optional<User> optionalFollowee = userRepository.getUserById(userInteractionRequestDTO.getFolloweeId());

        if ((optionalfollower.isEmpty()) || (optionalFollowee.isEmpty())) throw new UserNotFoundException("user-id '" + userInteractionRequestDTO.getFollowerId() + "' does not exist!");
        User follower = optionalfollower.get();
        User followee = optionalFollowee.get();

        if (userInteractionRequestDTO.getUserInteractionType().equals(UserInteractionType.FOLLOW)) {
            follower.getFollowees().add(followee);
            followee.getFollowers().add(follower);
        } else if (userInteractionRequestDTO.getUserInteractionType().equals(UserInteractionType.UNFOLLOW)) {
            follower.getFollowees().remove(followee);
            followee.getFollowers().remove(follower);
        }

        User savedFollower = userRepository.save(follower);
        User savedFollowee = userRepository.save(followee);

        return UserInteractionResponseDTOBuilder.getBuilder()
                .setFolloweeId(savedFollowee.getId())
                .setFollowerId(savedFollower.getId())
                .setFolloweeName(savedFollowee.getFirstName().trim() + " " + savedFollowee.getLastName().trim())
                .setFollowerName(savedFollower.getFirstName().trim() + " " + savedFollower.getLastName().trim())
                .setUserInteractionType(userInteractionRequestDTO.getUserInteractionType())
                .build();
    }


    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
    public Optional<User> getUserById(String id) { return userRepository.getUserById(id); }

}
