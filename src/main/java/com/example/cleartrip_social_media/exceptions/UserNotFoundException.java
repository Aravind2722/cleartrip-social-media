package com.example.cleartrip_social_media.exceptions;

public class UserNotFoundException extends ValidationException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
