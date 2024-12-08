package com.example.cleartrip_social_media.builders;


import com.example.cleartrip_social_media.dtos.PostInteractionResponseDTO;
import com.example.cleartrip_social_media.enums.PostInteractionType;
import com.example.cleartrip_social_media.utilities.RelativeTimeFormatter;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostInteractionResponseDTOBuilder {
    private String postId;
    private String userId;
    private PostInteractionType action;
    private LocalDateTime time;

    public static PostInteractionResponseDTOBuilder getBuilder() {
        return new PostInteractionResponseDTOBuilder();
    }

    public PostInteractionResponseDTOBuilder setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public PostInteractionResponseDTOBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public PostInteractionResponseDTOBuilder setAction(PostInteractionType action) {
        this.action = action;
        return this;
    }

    public PostInteractionResponseDTOBuilder setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public PostInteractionResponseDTO build() {
        PostInteractionResponseDTO postInteractionResponseDTO = new PostInteractionResponseDTO();
        postInteractionResponseDTO.setPostId(this.getPostId());
        postInteractionResponseDTO.setUserId(this.getUserId());
        postInteractionResponseDTO.setAction(this.getAction());
        postInteractionResponseDTO.setTime(RelativeTimeFormatter.getRelativeTime(this.getTime()));

        return postInteractionResponseDTO;
    }
}
