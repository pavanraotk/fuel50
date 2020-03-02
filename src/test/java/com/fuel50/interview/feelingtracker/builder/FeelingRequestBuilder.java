package com.fuel50.interview.feelingtracker.builder;

import com.fuel50.interview.feelingtracker.web.request.FeelingRequest;

import java.time.LocalDate;

public class FeelingRequestBuilder {
    private Integer feelingIndicator;
    private String message;

    public FeelingRequestBuilder setFeelingIndicator(Integer feelingIndicator) {
        this.feelingIndicator = feelingIndicator;
        return this;
    }

    public FeelingRequestBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public FeelingRequestBuilder withData() {
        this.feelingIndicator = 5;
        this.message = "This is a good feeling";
        return this;
    }

    public FeelingRequest build() {
        return new FeelingRequest(feelingIndicator, message);
    }
}
