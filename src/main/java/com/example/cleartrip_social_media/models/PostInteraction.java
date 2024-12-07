package com.example.cleartrip_social_media.models;

import com.example.cleartrip_social_media.enums.PostInteractionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostInteraction {
    private String id;
    private String postId;
    private String userId;
    private PostInteractionType type;
    private LocalDateTime time;
}
