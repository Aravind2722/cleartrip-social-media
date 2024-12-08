package com.example.cleartrip_social_media.validators;

import com.example.cleartrip_social_media.dtos.FeedRequestDTO;
import com.example.cleartrip_social_media.exceptions.UserNotFoundException;
import com.example.cleartrip_social_media.exceptions.ValidationException;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.services.UserService;
import com.example.cleartrip_social_media.strategies.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FeedRequestDTOValidator implements Validator<FeedRequestDTO> {
    private final UserService userService;
    @Autowired
    public FeedRequestDTOValidator(UserService userService) {
        this.userService = userService;
    }
    @Override
    public void validate(FeedRequestDTO feedRequestDTO) throws ValidationException {
        if (feedRequestDTO == null) throw new IllegalArgumentException("user-id cannot be empty!");

        Optional<User> optionalUser = userService.getUserById(feedRequestDTO.getUserId());
        if (optionalUser.isEmpty()) throw new UserNotFoundException("user '" + feedRequestDTO.getUserId() + "' does not exist!");
    }
}
