package com.example.cleartrip_social_media.validators;

import com.example.cleartrip_social_media.dtos.UserRequestDTO;
import com.example.cleartrip_social_media.exceptions.InvalidContactNumberException;
import com.example.cleartrip_social_media.exceptions.UserAlreadyExistsException;
import com.example.cleartrip_social_media.exceptions.ValidationException;
import com.example.cleartrip_social_media.services.UserService;
import com.example.cleartrip_social_media.strategies.Validator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserRequestDTOValidator implements Validator<UserRequestDTO> {
    private final UserService userService;
    @Autowired
    public UserRequestDTOValidator (UserService userService) {
        this.userService = userService;
    }

    public void validate(UserRequestDTO userRequestDTO) throws ValidationException {

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
        if (userService.getUserByEmail(userRequestDTO.getEmail()).isPresent()) throw new UserAlreadyExistsException(
                "user '" + userRequestDTO.getEmail() + "' already exists!"
        );
    }

}
