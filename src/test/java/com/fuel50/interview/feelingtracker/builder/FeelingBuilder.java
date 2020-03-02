package com.fuel50.interview.feelingtracker.builder;

import com.fuel50.interview.feelingtracker.db.model.Feeling;

import java.time.LocalDate;
import java.util.Random;

public class FeelingBuilder {
    private Integer feelingValue;
    private String ipAddress;
    private LocalDate feelingDate;
    private String message;

    public FeelingBuilder setFeelingValue(Integer feelingValue) {
        this.feelingValue = feelingValue;
        return this;
    }

    public FeelingBuilder setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public FeelingBuilder setFeelingDate(LocalDate feelingDate) {
        this.feelingDate = feelingDate;
        return this;
    }

    public FeelingBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public FeelingBuilder withData() {
        this.feelingValue = 1;
        this.ipAddress = "127.0.0.1";
        this.feelingDate = LocalDate.now();
        this.message = "This is a good feeling";
        return this;
    }

    public Feeling build() {
        return new Feeling(feelingValue, ipAddress, feelingDate, message);
    }
}
