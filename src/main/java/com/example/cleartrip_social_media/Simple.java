package com.example.cleartrip_social_media;

import java.util.Objects;

public class Simple {
    private String userId;
    public Simple(String userId) {
        this.userId = userId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Simple user = (Simple) obj;
        return Objects.equals(this.userId, user.userId);
    }
}
