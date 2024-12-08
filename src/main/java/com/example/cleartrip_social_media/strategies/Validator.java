package com.example.cleartrip_social_media.strategies;

import com.example.cleartrip_social_media.exceptions.ValidationException;

public interface Validator<T> {
    public void validate(T object) throws ValidationException;
}
