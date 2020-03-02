package com.fuel50.interview.feelingtracker.web.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes({
        @JsonSubTypes.Type(value = FeelingResponse.class, name = "success"),
        @JsonSubTypes.Type(value = ErrorResponse.class, name = "error")
})
public class AbstractResponse {
    private String message;

    AbstractResponse() {
    }

    AbstractResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public AbstractResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}