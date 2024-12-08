package com.example.cleartrip_social_media.controllers;

import com.example.cleartrip_social_media.dtos.PostRequestDTO;
import com.example.cleartrip_social_media.dtos.PostResponseDTO;
import com.example.cleartrip_social_media.dtos.ResponseDTO;
import com.example.cleartrip_social_media.enums.ResponseStatus;
import com.example.cleartrip_social_media.exceptions.UserNotFoundException;
import com.example.cleartrip_social_media.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    public ResponseDTO<PostResponseDTO> uploadPost(PostRequestDTO postRequestDTO) {
        try {
            PostResponseDTO postResponseDTO = postService.uploadPost(postRequestDTO);
            return new ResponseDTO<>(
                    postResponseDTO,
                    ResponseStatus.CREATED,
                    "Post upload successful!"
            );
        } catch (UserNotFoundException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.NOT_FOUND,
                    exception.getMessage() + ", Post upload unsuccessful!"
            );
        }
    }
}
