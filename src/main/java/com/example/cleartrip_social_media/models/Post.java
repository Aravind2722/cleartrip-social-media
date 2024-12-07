package com.example.cleartrip_social_media.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Post {
    private String id;
    private String userId;
    private String content;
    private List<PostInteraction> postInteractions;
    private LocalDateTime postedAt;
}
