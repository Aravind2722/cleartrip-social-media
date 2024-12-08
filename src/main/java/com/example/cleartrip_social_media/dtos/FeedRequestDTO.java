package com.example.cleartrip_social_media.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedRequestDTO {
    // Creating a class, to make sure that the requestDTO is scalable in future
    private String userId;

}
