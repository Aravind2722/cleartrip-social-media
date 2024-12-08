package com.example.cleartrip_social_media.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDTO {
    private String id;
    private String userName;
    private String content;
    private Long likes;
    private Long dislikes;
    private String postedAt;

    @Override
    public String toString() {
        return "\n{" +
                "\n\tpost-id: " + this.getId() +
                "\n\tuser-name: " + this.getUserName() +
                "\n\tcontent: " + this.getContent() +
                "\n\tlikes: " + this.getLikes() +
                "\n\tdislikes: " + this.getDislikes() +
                "\n\tposted-at: " + this.getPostedAt() +
                "\n}\n";
    }
}
