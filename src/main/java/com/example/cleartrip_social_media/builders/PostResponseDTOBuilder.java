package com.example.cleartrip_social_media.builders;

import com.example.cleartrip_social_media.dtos.PostResponseDTO;
import com.example.cleartrip_social_media.utilities.RelativeTimeFormatter;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class PostResponseDTOBuilder {
    private String id;
    private String userName;
    private String content;
    private Long likes;
    private Long dislikes;
    private LocalDateTime postedAt;

    public static PostResponseDTOBuilder getBuilder() {
        return new PostResponseDTOBuilder();
    }

    public PostResponseDTOBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public PostResponseDTOBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public PostResponseDTOBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public PostResponseDTOBuilder setLikes(Long likes) {
        this.likes = likes;
        return this;
    }

    public PostResponseDTOBuilder setDislikes(Long dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public PostResponseDTOBuilder setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
        return this;
    }

    public PostResponseDTO build() {
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setId(this.getId());
        postResponseDTO.setUserName(this.getUserName());
        postResponseDTO.setContent(this.getContent());
        postResponseDTO.setLikes(this.getLikes());
        postResponseDTO.setDislikes(this.getDislikes());
        postResponseDTO.setPostedAt(RelativeTimeFormatter.getRelativeTime(this.getPostedAt()));

        return postResponseDTO;
    }
}
