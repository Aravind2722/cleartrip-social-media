package com.example.cleartrip_social_media.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.cleartrip_social_media.builders.DateOfBirthBuilder;
import com.example.cleartrip_social_media.builders.UserResponseDTOBuilder;
import com.example.cleartrip_social_media.dtos.DateOfBirthDTO;
import com.example.cleartrip_social_media.dtos.UserRequestDTO;
import com.example.cleartrip_social_media.dtos.UserResponseDTO;
import com.example.cleartrip_social_media.exceptions.InvalidContactNumberException;
import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import com.example.cleartrip_social_media.exceptions.UserAlreadyExistsException;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.repositories.UserRepository;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistsException,
            InvalidDateOfBirthException, InvalidContactNumberException {

        if (userRepository.getUserByEmail(userRequestDTO.getEmail()).isPresent()) throw new UserAlreadyExistsException(
                userRequestDTO.getEmail() + "already exists!"
        );
        if (userRequestDTO.getContact().length() != 10) throw new InvalidContactNumberException(
                "Invalid contact number provided " + userRequestDTO.getContact() + " !"
        );

        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());

        DateOfBirthDTO dateOfBirthDTO = userRequestDTO.getDateOfBirthDTO();
        user.setDateOfBirth(
                DateOfBirthBuilder.getBuilder()
                        .setDay(dateOfBirthDTO.getDay())
                        .setMonth(dateOfBirthDTO.getMonth())
                        .setYear(dateOfBirthDTO.getYear())
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

}
