package com.example.cleartrip_social_media.services;

import com.example.cleartrip_social_media.builders.PostResponseDTOBuilder;
import com.example.cleartrip_social_media.dtos.PostRequestDTO;
import com.example.cleartrip_social_media.dtos.PostResponseDTO;
import com.example.cleartrip_social_media.exceptions.UserNotFoundException;
import com.example.cleartrip_social_media.models.Post;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.repositories.PostRepository;
import com.example.cleartrip_social_media.repositories.UserRepository;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    public PostResponseDTO uploadPost(PostRequestDTO postRequestDTO) throws UserNotFoundException {
        validatePostRequestDTO(postRequestDTO);

        Optional<User> optionalUser = userRepository.getUserById(postRequestDTO.getUserId());
        if (optionalUser.isEmpty()) throw new UserNotFoundException("User '"+ postRequestDTO.getUserId() +"' does not exist!");
        User user = optionalUser.get();
        Post post = new Post();
        post.setId(Generators.timeBasedEpochGenerator().generate().toString());
        post.setUser(user);
        post.setContent(postRequestDTO.getContent());
        post.setLikes(0L);
        post.setDislikes(0L);
        post.setPostInteractions(new ArrayList<>());
        post.setPostedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        return PostResponseDTOBuilder.getBuilder()
                .setId(post.getId())
                .setUserName(user.getFirstName().trim() + " " + user.getLastName().trim())
                .setContent(post.getContent())
                .setLikes(post.getLikes())
                .setDislikes(post.getDislikes())
                .setPostedAt(post.getPostedAt())
                .build();
    }
    public void validatePostRequestDTO(PostRequestDTO postRequestDTO) {
        if (postRequestDTO == null) throw new IllegalArgumentException(
                "Post Request cannot be empty!"
        );
    }

}
