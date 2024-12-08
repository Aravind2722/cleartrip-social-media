package com.example.cleartrip_social_media.exceptions;

public class UserAlreadyExistsException extends ValidationException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
