package com.example.cleartrip_social_media.repositories;

import com.example.cleartrip_social_media.models.Post;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public List<Post> getAllPosts() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getPostById(String id) {
        if (posts.containsKey(id)) return Optional.of(posts.get(id));

        return Optional.empty();
    }

}
