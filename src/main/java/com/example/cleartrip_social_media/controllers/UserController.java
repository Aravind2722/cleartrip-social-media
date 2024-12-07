package com.example.cleartrip_social_media.controllers;

import com.example.cleartrip_social_media.dtos.ResponseDTO;
import com.example.cleartrip_social_media.dtos.UserRequestDTO;
import com.example.cleartrip_social_media.dtos.UserResponseDTO;
import com.example.cleartrip_social_media.enums.ResponseStatus;
import com.example.cleartrip_social_media.exceptions.InvalidContactNumberException;
import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
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
                    userResponseDTO.getFirstName() + userResponseDTO.getLastName() + "Registered!!"
            );
        } catch (InvalidDateOfBirthException | InvalidContactNumberException exception) {

            return new ResponseDTO<>(
                    null,
                    ResponseStatus.BAD_REQUEST,
                    exception.getMessage()
            );
        } catch (Exception exception) {
            return new ResponseDTO<>(
                    null,
                    ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong. Please try again later"
            );
        }
    }
}
