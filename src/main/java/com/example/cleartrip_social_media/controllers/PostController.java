package com.example.cleartrip_social_media.controllers;

import com.example.cleartrip_social_media.dtos.*;
import com.example.cleartrip_social_media.enums.ResponseStatus;
import com.example.cleartrip_social_media.exceptions.InvalidPostInteractionRequestException;
import com.example.cleartrip_social_media.exceptions.PostNotFoundException;
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
        } catch (IllegalArgumentException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.BAD_REQUEST,
                    exception.getMessage() + ", Post upload unsuccessful!"
            );
        } catch (UserNotFoundException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.NOT_FOUND,
                    exception.getMessage() + ", Post upload unsuccessful!"
            );
        }
        catch (Exception exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went Wrong, Please try again later!"
            );
        }
    }

    public ResponseDTO<FeedResponseDTO> showFeed(FeedRequestDTO feedRequestDTO) {
        try {
            return new ResponseDTO<>(
                    postService.getFeed(feedRequestDTO),
                    ResponseStatus.OK,
                    "Feed fetch successful!"
            );
        } catch (UserNotFoundException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.NOT_FOUND,
                    exception.getMessage() + ", Feed fetch unsuccessful!"
            );
        } catch (IllegalArgumentException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.BAD_REQUEST,
                    exception.getMessage() + ", Feed fetch unsuccessful!"
            );
        } catch (Exception exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong, Please try again later!"
            );
        }
    }

    public ResponseDTO<PostInteractionResponseDTO> interactWithPost(PostInteractionRequestDTO postInteractionRequestDTO) {
        try {
            return new ResponseDTO<>(
                    postService.interactWithPost(postInteractionRequestDTO),
                    ResponseStatus.ACCEPTED,
                    postInteractionRequestDTO.getAction() + " posted successfully!"
            );
        } catch (InvalidPostInteractionRequestException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.BAD_REQUEST,
                    exception.getMessage() + ", " + postInteractionRequestDTO.getAction() + " unsuccessful!"
            );
        }
        catch (UserNotFoundException | PostNotFoundException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.NOT_FOUND,
                    exception.getMessage() + " " + postInteractionRequestDTO.getAction() + " unsuccessful!"
            );
        }
        catch (Exception exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong, Please try again later!"
            );
        }
    }

}
