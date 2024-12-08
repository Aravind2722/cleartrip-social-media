package com.example.cleartrip_social_media.dtos;

import com.example.cleartrip_social_media.enums.UserInteractionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInteractionRequestDTO {
    private String followerId;
    private String followeeId;
    private UserInteractionType userInteractionType;
}
