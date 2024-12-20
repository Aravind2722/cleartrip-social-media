package com.example.cleartrip_social_media.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private DateOfBirthDTO dateOfBirthDTO;
    private String email;
    private String password;
    private String contact;
}
