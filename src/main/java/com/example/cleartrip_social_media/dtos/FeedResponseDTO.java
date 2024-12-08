package com.example.cleartrip_social_media.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedResponseDTO {
    private List<PostResponseDTO> postResponseDTOS;
    private int totalPosts;

    @Override
    public String toString() {
        return "\n{" +
                "\n\tfeed: " + this.getPostResponseDTOS() +
                "\n\ttotal-posts: " + this.getTotalPosts() +
                "\n}\n";
    }
}
