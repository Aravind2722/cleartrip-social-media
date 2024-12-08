package com.example.cleartrip_social_media.repositories;

import com.example.cleartrip_social_media.models.PostInteraction;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PostInteractionRepository {
    private final Map<String, PostInteraction> postInteractions;
    public PostInteractionRepository() {
        this.postInteractions = new HashMap <>();
    }

    public PostInteraction save(PostInteraction postInteraction) {
        postInteractions.put(postInteraction.getId(), postInteraction);
        return postInteractions.get(postInteraction.getId());
    }
}
