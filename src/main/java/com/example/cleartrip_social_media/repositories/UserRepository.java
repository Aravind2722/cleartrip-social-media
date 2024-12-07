package com.example.cleartrip_social_media.repositories;

import com.example.cleartrip_social_media.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private Map<String, User> users;
    public UserRepository () {
        users = new HashMap<>();
    }
    public User save(User user) {
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    public Optional<User> getUserByEmail(String email) {

        for (User user : users.values()) {
            if (email.equals(user.getEmail())) return Optional.of(user);
        }

        return Optional.empty();
    }

}
