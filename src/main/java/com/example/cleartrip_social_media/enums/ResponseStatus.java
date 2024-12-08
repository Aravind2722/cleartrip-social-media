package com.example.cleartrip_social_media.enums;

import lombok.Getter;

@Getter
public enum ResponseStatus {


    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),


    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getCode() + " " + this.getMessage();
    }
}