package com.example.cleartrip_social_media.builders;

import com.example.cleartrip_social_media.dtos.UserInteractionResponseDTO;
import com.example.cleartrip_social_media.enums.UserInteractionType;
import lombok.Getter;

@Getter
public class UserInteractionResponseDTOBuilder {
    private String followerId;
    private String followeeId;
    private String followerName;
    private String followeeName;
    private UserInteractionType userInteractionType;

    public static UserInteractionResponseDTOBuilder getBuilder() {
        return new UserInteractionResponseDTOBuilder();
    }

    public UserInteractionResponseDTOBuilder setFollowerId(String followerId) {
        this.followerId = followerId;
        return this;
    }

    public UserInteractionResponseDTOBuilder setFolloweeId(String followeeId) {
        this.followeeId = followeeId;
        return this;
    }

    public UserInteractionResponseDTOBuilder setFollowerName(String followerName) {
        this.followerName = followerName;
        return this;
    }

    public UserInteractionResponseDTOBuilder setFolloweeName(String followeeName) {
        this.followeeName = followeeName;
        return this;
    }

    public UserInteractionResponseDTOBuilder setUserInteractionType(UserInteractionType userInteractionType) {
        this.userInteractionType = userInteractionType;
        return this;
    }

    public UserInteractionResponseDTO build() {
        UserInteractionResponseDTO userInteractionResponseDTO = new UserInteractionResponseDTO();
        userInteractionResponseDTO.setFolloweeId(this.getFolloweeId());
        userInteractionResponseDTO.setFollowerId(this.getFollowerId());
        userInteractionResponseDTO.setFolloweeName(this.getFolloweeName());
        userInteractionResponseDTO.setFollowerName(this.getFollowerName());
        userInteractionResponseDTO.setUserInteractionType(this.getUserInteractionType());
        return userInteractionResponseDTO;
    }
}
