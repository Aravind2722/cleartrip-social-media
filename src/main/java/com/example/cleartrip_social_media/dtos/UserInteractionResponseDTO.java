package com.example.cleartrip_social_media.dtos;

import com.example.cleartrip_social_media.enums.UserInteractionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInteractionResponseDTO {
    private String followerId;
    private String followeeId;
    private String followerName;
    private String followeeName;
    private UserInteractionType userInteractionType;

    @Override
    public String toString() {
        return "\n{" +
                "\n\tfollower: " + this.getFollowerName() +
                "\n\tfollowee: " + this.getFolloweeName() +
                "\n\trequest-type: " + this.getUserInteractionType() +
                "\n}\n";
    }
}
