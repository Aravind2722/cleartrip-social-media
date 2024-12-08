package com.example.cleartrip_social_media.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDTO {
    private String userId;
    private String content;
}
