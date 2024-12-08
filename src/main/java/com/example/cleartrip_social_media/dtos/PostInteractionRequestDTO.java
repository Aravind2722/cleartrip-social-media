package com.example.cleartrip_social_media.dtos;

import com.example.cleartrip_social_media.enums.PostInteractionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostInteractionRequestDTO {
    private String postId;
    private String userId;
    private PostInteractionType action;
}
