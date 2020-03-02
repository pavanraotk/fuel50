package com.fuel50.interview.feelingtracker.service;

import com.fuel50.interview.feelingtracker.builder.FeelingBuilder;
import com.fuel50.interview.feelingtracker.builder.FeelingRequestBuilder;
import com.fuel50.interview.feelingtracker.db.FeelingRepository;
import com.fuel50.interview.feelingtracker.service.exception.FeelingServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeelingServiceTest {

    private FeelingService feelingService;
    private FeelingRepository feelingRepository;

    @Before
    public void before() {
        feelingRepository = mock(FeelingRepository.class);
        feelingService = new FeelingService(feelingRepository);
    }

    @After
    public void tearDown() {
        feelingService = null;
        feelingRepository = null;
    }

    @Test
    public void addFeelingServiceSuccess() throws FeelingServiceException {
        when(feelingRepository.save(any())).thenReturn(new FeelingBuilder().withData().build());
        when(feelingRepository.findAverageForDay(any())).thenReturn(1.0D);
        feelingService.saveFeeling("127.0.0.1", new FeelingRequestBuilder().withData().build());
    }

    @Test
    public void addFeelingServiceAlreadyExists() {
        try {
            when(feelingRepository.save(any())).thenThrow(new RuntimeException());
            feelingService.saveFeeling("127.0.0.1", new FeelingRequestBuilder().withData().build());
        } catch (FeelingServiceException ex) {
            assertThat("error.persisting.feeling").isEqualTo(ex.getCode());
            assertThat("Error persisting feeling").isEqualTo(ex.getMessage());
        }
    }

    @Test
    public void getAverageFeelingForDaySuccess() throws FeelingServiceException {
        when(feelingRepository.findAverageForDay(any())).thenReturn(4.0D);
        Double averageFeelingForDay = feelingService.getAverageFeelingForDay();
        assertThat(4.0D).isEqualTo(averageFeelingForDay);
    }

    @Test
    public void getAverageFeelingThrowsError() {
        try {
            when(feelingRepository.findByFeelingDateAndIpAddress(any(), any())).thenThrow(new RuntimeException());
            feelingService.getAverageFeelingForDay();
        } catch (FeelingServiceException ex) {
            assertThat("error.checking.feeling.exists").isEqualTo(ex.getCode());
            assertThat("Error checking if feeling exists").isEqualTo(ex.getMessage());
        }
    }

}