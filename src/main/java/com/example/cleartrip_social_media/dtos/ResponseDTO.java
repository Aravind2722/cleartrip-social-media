package com.example.cleartrip_social_media.dtos;

import com.example.cleartrip_social_media.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO <T> {
    private T entity;
    private ResponseStatus responseStatus;
    private String message;

    @Override
    public String toString() {
        return "\nresponse-status: " + this.getResponseStatus() +
                "\nresponse-message: " + this.getMessage() +
                "\nresponse-data: " +
                this.getEntity() +
                "\n";
    }
}
