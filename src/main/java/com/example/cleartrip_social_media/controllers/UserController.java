package com.example.cleartrip_social_media.controllers;

import com.example.cleartrip_social_media.dtos.*;
import com.example.cleartrip_social_media.enums.ResponseStatus;
import com.example.cleartrip_social_media.exceptions.UserNotFoundException;
import com.example.cleartrip_social_media.exceptions.InvalidContactNumberException;
import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import com.example.cleartrip_social_media.exceptions.InvalidUserInteractionRequestException;
import com.example.cleartrip_social_media.exceptions.UserAlreadyExistsException;
import com.example.cleartrip_social_media.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseDTO<UserResponseDTO> registerUser(UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.registerUser(userRequestDTO);
            return new ResponseDTO<>(
                    userResponseDTO,
                    ResponseStatus.CREATED,
                    userResponseDTO.getFirstName().trim() + " " + userResponseDTO.getLastName().trim() + " Registered!!"
            );
        } catch (
                InvalidDateOfBirthException |
                InvalidContactNumberException |
                UserAlreadyExistsException |
                IllegalArgumentException exception
        ) {

            return new ResponseDTO<>(
                    null,
                    ResponseStatus.BAD_REQUEST,
                    exception.getMessage()
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
    public ResponseDTO<UserInteractionResponseDTO> interactWithUser(UserInteractionRequestDTO userInteractionRequestDTO) {

        try {
            UserInteractionResponseDTO userInteractionResponseDTO = userService.interactWithUser(userInteractionRequestDTO);
            return new ResponseDTO<>(
                    userInteractionResponseDTO,
                    ResponseStatus.ACCEPTED,
                    (userInteractionRequestDTO.getUserInteractionType().name().equalsIgnoreCase("follow")) ?
                            "Follow successful!" :
                            "Unfollow successful!"
            );
        } catch (UserNotFoundException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.NOT_FOUND,
                    (userInteractionRequestDTO.getUserInteractionType().name().equalsIgnoreCase("follow")) ?
                            (exception.getMessage() + ", Follow unsuccessful!") :
                           (exception.getMessage() + ", Unfollow unsuccessful!")
            );
        } catch (InvalidUserInteractionRequestException exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.BAD_REQUEST,
                    (userInteractionRequestDTO.getUserInteractionType().name().equalsIgnoreCase("follow")) ?
                            (exception.getMessage() + ", Follow unsuccessful!") :
                            (exception.getMessage() + ", Unfollow unsuccessful!")
            );
        } catch (Exception exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong, Please try again later!"
            );
        }
    }
}
