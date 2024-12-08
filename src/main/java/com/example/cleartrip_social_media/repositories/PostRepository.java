package com.example.cleartrip_social_media.repositories;

import com.example.cleartrip_social_media.models.Post;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PostRepository {
    private final Map<String, Post> posts;
    public PostRepository() {
        posts = new HashMap<>();
    }
    public Post save(Post post) {
        posts.put(post.getId(), post);
        return posts.get(post.getId());
    }

}
