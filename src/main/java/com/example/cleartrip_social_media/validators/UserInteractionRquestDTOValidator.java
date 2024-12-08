package com.example.cleartrip_social_media.validators;

import com.example.cleartrip_social_media.dtos.UserInteractionRequestDTO;
import com.example.cleartrip_social_media.enums.UserInteractionType;
import com.example.cleartrip_social_media.exceptions.InvalidUserInteractionRequestException;
import com.example.cleartrip_social_media.exceptions.UserNotFoundException;
import com.example.cleartrip_social_media.exceptions.ValidationException;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.services.UserService;
import com.example.cleartrip_social_media.strategies.Validator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Component
public class UserInteractionRquestDTOValidator implements Validator<UserInteractionRequestDTO> {
    private final UserService userService;
    @Autowired
    public UserInteractionRquestDTOValidator(UserService userService) {
        this.userService = userService;
    }

    public void validate(UserInteractionRequestDTO userInteractionRequestDTO) throws ValidationException {
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

        Optional<User> optionalfollower = userService.getUserById(userInteractionRequestDTO.getFollowerId());
        if (optionalfollower.isEmpty()) throw new UserNotFoundException("user-id '" + userInteractionRequestDTO.getFollowerId() + "' does not exist!");

        Optional<User> optionalFollowee = userService.getUserById(userInteractionRequestDTO.getFolloweeId());
        if (optionalFollowee.isEmpty()) throw new UserNotFoundException("user-id '" + userInteractionRequestDTO.getFolloweeId() + "' does not exist!");
        User follower = optionalfollower.get();
        User followee = optionalFollowee.get();
        if (
                (userInteractionRequestDTO.getUserInteractionType().equals(UserInteractionType.FOLLOW))
                && (follower.getFollowees().contains(followee))
        ) {
            throw new InvalidUserInteractionRequestException(
                    "user '" + followee.getId() + "' is already followed!"
            );
        }

        if (
                (userInteractionRequestDTO.getUserInteractionType().equals(UserInteractionType.UNFOLLOW))
                        && (!follower.getFollowees().contains(followee))
        ) {
            throw new InvalidUserInteractionRequestException(
                    "user '" + followee.getId() + "' is not followed!"
            );
        }
    }

}
