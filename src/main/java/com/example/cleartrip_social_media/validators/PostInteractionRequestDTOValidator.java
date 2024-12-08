package com.example.cleartrip_social_media.validators;

import com.example.cleartrip_social_media.dtos.PostInteractionRequestDTO;
import com.example.cleartrip_social_media.exceptions.InvalidPostInteractionRequestException;
import com.example.cleartrip_social_media.exceptions.PostNotFoundException;
import com.example.cleartrip_social_media.exceptions.UserNotFoundException;
import com.example.cleartrip_social_media.exceptions.ValidationException;
import com.example.cleartrip_social_media.models.Post;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.services.PostService;
import com.example.cleartrip_social_media.services.UserService;
import com.example.cleartrip_social_media.strategies.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostInteractionRequestDTOValidator implements Validator<PostInteractionRequestDTO> {
    private final PostService postService;
    private final UserService userService;
    @Autowired
    public PostInteractionRequestDTOValidator(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public void validate(PostInteractionRequestDTO postInteractionRequestDTO) throws ValidationException {
        Optional<User> optionalUser = userService.getUserById(postInteractionRequestDTO.getUserId());
        if (optionalUser.isEmpty()) throw new UserNotFoundException(
                "user '" + postInteractionRequestDTO.getUserId() + "' does not exist!"
        );

        Optional<Post> optionalPost = postService.getPostById(postInteractionRequestDTO.getPostId());
        if (optionalPost.isEmpty()) throw new PostNotFoundException(
                "post '" + postInteractionRequestDTO.getPostId() + "' does not exist!"
        );

        User user = optionalUser.get();
        Post post = optionalPost.get();

        if (postService.checkIfInteractionAlreadyExists(post, user.getId(), postInteractionRequestDTO.getAction())) throw new InvalidPostInteractionRequestException(
                "post '" + postInteractionRequestDTO.getPostId() + "' already " + postInteractionRequestDTO.getAction() + "D"
        );
    }

}
