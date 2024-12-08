package com.example.cleartrip_social_media.dtos;

import com.example.cleartrip_social_media.models.DateOfBirth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private DateOfBirth dateOfBirth;
    private String email;
    private String contact;

    @Override
    public String toString() {
        return "{" +
                "\n\tid: " + this.getId() +
                "\n\tname: " + this.getFirstName().trim() + " " + this.getLastName().trim() +
                "\n\temail: " + this.getEmail() +
                "\n\tcontact: " + this.getContact() +
                "\n\tdate-of-birth: " + this.getDateOfBirth() +
                "\n}\n";
    }

}
