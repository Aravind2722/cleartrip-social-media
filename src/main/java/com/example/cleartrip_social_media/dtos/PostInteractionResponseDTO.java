package com.example.cleartrip_social_media.dtos;

import com.example.cleartrip_social_media.enums.PostInteractionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostInteractionResponseDTO {
    private String postId;
    private String userId;
    private PostInteractionType action;
    private String time;

    @Override
    public String toString() {
        return "\n\t{" +
                "\n\t\tpost-id: " + this.getPostId() +
                "\n\t\tuser-id: " + this.getUserId() +
                "\n\t\taction: " + this.getAction() +
                "\n\t}";
    }
}
