package com.fuel50.interview.feelingtracker.web;

import com.fuel50.interview.feelingtracker.builder.FeelingRequestBuilder;
import com.fuel50.interview.feelingtracker.service.FeelingService;
import com.fuel50.interview.feelingtracker.service.exception.FeelingServiceException;
import com.fuel50.interview.feelingtracker.web.request.FeelingRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static java.util.Locale.ENGLISH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class FeelingControllerTest extends AbstractTestController {

    private MockMvc mockMvc;
    private FeelingController feelingController;
    private FeelingService feelingService;
    private MessageSource messageSource;
    private FeelingRequest feelingRequest;

    @Before
    public void setUp() {
        feelingService = mock(FeelingService.class);
        messageSource = mock(MessageSource.class);
        feelingController = new FeelingController(messageSource, feelingService);
        feelingRequest = new FeelingRequestBuilder().withData().build();

        mockMvc = MockMvcBuilders.standaloneSetup(feelingController)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testAddFeelingSuccess() throws Exception {
        when(feelingService.feelingExists(any())).thenReturn(false);
        when(feelingService.saveFeeling(any(), any())).thenReturn(5.0D);
        Cookie cookie = new Cookie("ipAddress", "127.0.0.1");
        MvcResult mvcResult = performPostRequest(mockMvc, "/feeling/record", feelingRequest, cookie);
        assertSuccessResponse(mvcResult, "Successfully saved your response");
    }

    @Test
    public void testAddFeelingWithFeelingIndicatorValueSix() throws Exception {
        when(messageSource.getMessage("feelingIndicator.should.be.between.values", null, ENGLISH)).thenReturn("Feeling Indicator should be between 1-5");
        when(feelingService.feelingExists(any())).thenReturn(false);
        when(feelingService.saveFeeling(any(), any())).thenReturn(5.0D);
        Cookie cookie = new Cookie("ipAddress", "127.0.0.1");
        feelingRequest.setFeelingIndicator(6);
        MvcResult mvcResult = performPostRequest(mockMvc, "/feeling/record", feelingRequest, cookie);
        assertBadRequestErrorResponse(mvcResult, "feelingIndicator.should.be.between.values", "Feeling Indicator should be between 1-5");
    }

    @Test
    public void testAddFeelingInvalidIPAddress() throws Exception {
        Cookie cookie = new Cookie("ipAddress", "127.0.0");
        MvcResult mvcResult = performPostRequest(mockMvc, "/feeling/record", feelingRequest, cookie);
        assertBadRequestErrorResponse(mvcResult, "ipAddress.format.is.wrong", "IP Address format is wrong");
    }

    @Test
    public void testAddFeelingWithFeelingIndicatorZero() throws Exception {
        when(messageSource.getMessage("feelingIndicator.should.be.between.values", null, ENGLISH)).thenReturn("Feeling Indicator should be between 1-5");
        when(feelingService.feelingExists(any())).thenReturn(false);
        when(feelingService.saveFeeling(any(), any())).thenReturn(5.0D);
        Cookie cookie = new Cookie("ipAddress", "127.0.0.1");
        feelingRequest.setFeelingIndicator(0);
        MvcResult mvcResult = performPostRequest(mockMvc, "/feeling/record", feelingRequest, cookie);
        assertBadRequestErrorResponse(mvcResult, "feelingIndicator.should.be.between.values", "Feeling Indicator should be between 1-5");
    }

    @Test
    public void testAddFeelingWithFeelingAlreadyExists() throws Exception {
        when(feelingService.feelingExists(any())).thenReturn(true);
        when(feelingService.saveFeeling(any(), any())).thenReturn(5.0D);
        Cookie cookie = new Cookie("ipAddress", "127.0.0.1");
        MvcResult mvcResult = performPostRequest(mockMvc, "/feeling/record", feelingRequest, cookie);
        assertBadRequestErrorResponse(mvcResult, "feeling.already.exists", "Sorry, you have already submitted your response for today, try again tomorrow!");
    }

    @Test
    public void testAddFeelingPersistError() throws Exception {
        when(messageSource.getMessage("error.persisting.feeling", null, ENGLISH)).thenReturn("Error persisting feeling");
        when(feelingService.feelingExists(any())).thenReturn(false);
        when(feelingService.saveFeeling(any(), any())).thenThrow(new FeelingServiceException("error.persisting.feeling", "Error persisting feeling"));
        Cookie cookie = new Cookie("ipAddress", "127.0.0.1");
        MvcResult mvcResult = performPostRequest(mockMvc, "/feeling/record", feelingRequest, cookie);
        assertInternalServerErrorResponse(mvcResult, "error.persisting.feeling", "Error persisting feeling");
    }

    @Test
    public void testGetAverageForDay() throws Exception {
        when(feelingService.getAverageFeelingForDay()).thenReturn(5.0D);
        MvcResult mvcResult = performGetRequest(mockMvc, "/feeling/averageForDay");
        assertSuccessResponse(mvcResult, "Average feeling for day");
    }

    @Test
    public void testAddFeelingInternalServerError() throws Exception {
        when(messageSource.getMessage("error.getting.average.feeling.for.day", null, ENGLISH)).thenReturn("Error getting average feeling for day");
        when(feelingService.getAverageFeelingForDay()).thenThrow(new FeelingServiceException("error.getting.average.feeling.for.day", "Error getting average feeling for day"));
        Cookie cookie = new Cookie("ipAddress", "127.0.0.1");
        MvcResult mvcResult = performGetRequest(mockMvc, "/feeling/averageForDay");
        assertInternalServerErrorResponse(mvcResult, "error.getting.average.feeling.for.day", "Error getting average feeling for day");
    }


}