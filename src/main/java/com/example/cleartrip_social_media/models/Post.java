package com.example.cleartrip_social_media.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Post {
    private String id;
    private User user;
    private String content;
    private Long likes;
    private Long dislikes;
    private List<PostInteraction> postInteractions;
    private LocalDateTime postedAt;

}
