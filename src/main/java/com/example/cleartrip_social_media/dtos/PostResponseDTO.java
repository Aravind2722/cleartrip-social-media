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
        return "\n\t{" +
                "\n\t\tpost-id: " + this.getId() +
                "\n\t\tuser-name: " + this.getUserName() +
                "\n\t\tcontent: " + this.getContent() +
                "\n\t\tlikes: " + this.getLikes() +
                "\n\t\tdislikes: " + this.getDislikes() +
                "\n\t\tposted-at: " + this.getPostedAt() +
                "\n\t}\n";
    }
}
