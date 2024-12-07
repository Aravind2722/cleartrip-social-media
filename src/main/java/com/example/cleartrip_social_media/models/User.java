package com.example.cleartrip_social_media.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private DateOfBirth dateOfBirth;
    private String email;
    private String password;
    private String contact;
    private List<User> followers;
    private List<User> following;
    private List<Post> posts;
    private List<PostInteraction> activity;
}
