package com.fuel50.interview.feelingtracker.integration.web;

import com.fuel50.interview.feelingtracker.builder.FeelingRequestBuilder;
import com.fuel50.interview.feelingtracker.integration.AbstractIT;
import com.fuel50.interview.feelingtracker.service.exception.FeelingServiceException;
import com.fuel50.interview.feelingtracker.web.FeelingController;
import com.fuel50.interview.feelingtracker.web.request.FeelingRequest;
import com.fuel50.interview.feelingtracker.web.response.AbstractResponse;
import com.fuel50.interview.feelingtracker.web.response.ErrorResponse;
import com.fuel50.interview.feelingtracker.web.response.FeelingResponse;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class FeelingControllerIntegrationTest extends AbstractIT {

    @Autowired
    private FeelingController feelingController;

    @Test
    public void testFeelingController() throws FeelingServiceException {
        ResponseEntity<AbstractResponse> responseEntity = feelingController.recordFeelings("127.0.0.1", new FeelingRequestBuilder().withData().build());
        FeelingResponse feelingResponse = (FeelingResponse) responseEntity.getBody();
        assertThat(feelingResponse.getMessage()).isEqualTo("Successfully saved your response");
        assertThat(feelingResponse.getAverageForDay()).isEqualTo(5.0D);

        responseEntity = feelingController.recordFeelings("127.0.0.1", new FeelingRequestBuilder().withData().build());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertThat(errorResponse.getMessage()).isEqualTo("Sorry, you have already submitted your response for today, try again tomorrow!");
        assertThat(errorResponse.getCode()).isEqualTo("feeling.already.exists");

        responseEntity = feelingController.getAverageForDay();
        feelingResponse = (FeelingResponse) responseEntity.getBody();
        assertThat(feelingResponse.getMessage()).isEqualTo("Average feeling for day");
        assertThat(feelingResponse.getAverageForDay()).isEqualTo(5.0D);
    }
}