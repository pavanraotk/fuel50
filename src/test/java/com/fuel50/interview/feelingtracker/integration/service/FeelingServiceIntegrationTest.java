package com.fuel50.interview.feelingtracker.integration.service;

import com.fuel50.interview.feelingtracker.builder.FeelingBuilder;
import com.fuel50.interview.feelingtracker.builder.FeelingRequestBuilder;
import com.fuel50.interview.feelingtracker.db.model.Feeling;
import com.fuel50.interview.feelingtracker.integration.AbstractIT;
import com.fuel50.interview.feelingtracker.service.FeelingService;
import com.fuel50.interview.feelingtracker.service.exception.FeelingServiceException;
import com.fuel50.interview.feelingtracker.web.request.FeelingRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class FeelingServiceIntegrationTest extends AbstractIT {

    @Autowired
    private FeelingService feelingService;

    @Test
    public void testFeelingServicce() throws FeelingServiceException {
        FeelingRequest feelingRequest = new FeelingRequestBuilder().withData().build();
        Double feelingIndicator = feelingService.saveFeeling("127.0.0.1", feelingRequest);
        assertThat(5.0D).isEqualTo(feelingIndicator);

        feelingRequest = new FeelingRequestBuilder().withData().setFeelingIndicator(3).build();
        feelingIndicator = feelingService.saveFeeling("127.0.0.2", feelingRequest);
        assertThat(4.0D).isEqualTo(feelingIndicator);

        try {
            feelingService.saveFeeling("127.0.0.1", feelingRequest);
            fail("Exception not thrown");
        } catch (FeelingServiceException e) {
            assertThat("error.persisting.feeling").isEqualTo(e.getCode());
            assertThat("Error persisting feeling").isEqualTo(e.getMessage());
        }

        assertThat(4.0D).isEqualTo(feelingService.getAverageFeelingForDay());
    }

}