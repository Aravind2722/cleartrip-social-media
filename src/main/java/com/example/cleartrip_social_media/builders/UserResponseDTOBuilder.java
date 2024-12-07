package com.example.cleartrip_social_media.builders;

import com.example.cleartrip_social_media.dtos.UserResponseDTO;
import com.example.cleartrip_social_media.models.DateOfBirth;
import lombok.Getter;

@Getter
public class UserResponseDTOBuilder {
    private String id;
    private String firstName;
    private String lastName;
    private DateOfBirth dateOfBirth;
    private String email;
    private String contact;

    public static UserResponseDTOBuilder getBuilder() {
        return new UserResponseDTOBuilder();
    }
    public UserResponseDTOBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public UserResponseDTOBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserResponseDTOBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserResponseDTOBuilder setDateOfBirth(DateOfBirth dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserResponseDTOBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserResponseDTOBuilder setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public UserResponseDTO build() {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(this.getId());
        userResponseDTO.setFirstName(this.getFirstName());
        userResponseDTO.setLastName(this.getLastName());
        userResponseDTO.setDateOfBirth(this.getDateOfBirth());
        userResponseDTO.setEmail(this.getEmail());
        userResponseDTO.setContact(this.getContact());

        return userResponseDTO;
    }
}
