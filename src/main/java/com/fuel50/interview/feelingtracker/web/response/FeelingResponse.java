package com.fuel50.interview.feelingtracker.web.response;

public class FeelingResponse extends AbstractResponse {

    private Double averageForDay;

    public FeelingResponse() {
    }

    public FeelingResponse(String message, Double averageForDay) {
        super(message);
        this.averageForDay = averageForDay;
    }

    public Double getAverageForDay() {
        return averageForDay;
    }

    public FeelingResponse setAverageForDay(Double averageForDay) {
        this.averageForDay = averageForDay;
        return this;
    }
}
