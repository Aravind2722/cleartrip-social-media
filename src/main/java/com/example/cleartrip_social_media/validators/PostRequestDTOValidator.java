package com.example.cleartrip_social_media.validators;

import com.example.cleartrip_social_media.dtos.PostRequestDTO;
import com.example.cleartrip_social_media.services.PostService;
import com.example.cleartrip_social_media.strategies.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostRequestDTOValidator implements Validator<PostRequestDTO> {
    private final PostService postService;
    @Autowired
    public PostRequestDTOValidator(PostService postService) {
        this.postService = postService;
    }

    public void validate(PostRequestDTO postRequestDTO) {
        if (postRequestDTO == null) throw new IllegalArgumentException(
                "Post Request cannot be empty!"
        );
        if (postRequestDTO.getContent().isBlank()) throw new IllegalArgumentException("Content of the post cannot be empty!");
    }

}
