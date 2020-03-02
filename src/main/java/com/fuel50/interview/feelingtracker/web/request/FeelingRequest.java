package com.fuel50.interview.feelingtracker.web.request;

import javax.validation.constraints.*;

public class FeelingRequest {

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer feelingIndicator;
    @Size(max = 350)
    private String message;

    public FeelingRequest() {
    }

    public FeelingRequest(@NotBlank Integer feelingIndicator, @Size(max = 350) String message) {
        this.feelingIndicator = feelingIndicator;
        this.message = message;
    }

    public Integer getFeelingIndicator() {
        return feelingIndicator;
    }

    public void setFeelingIndicator(Integer feelingIndicator) {
        this.feelingIndicator = feelingIndicator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
