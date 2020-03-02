package com.fuel50.interview.feelingtracker.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuel50.interview.feelingtracker.web.response.ErrorResponse;
import com.fuel50.interview.feelingtracker.web.response.FeelingResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AbstractTestController {

    protected final static ObjectMapper objectMapper = new ObjectMapper();

    MvcResult performPostRequest(MockMvc mvc, String path, Object request, Cookie cookie) throws Exception {
        return mvc.perform(post(path)
                .cookie(cookie)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    MvcResult performGetRequest(MockMvc mvc, String path) throws Exception {
        return mvc.perform(get(path)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    void assertSuccessResponse(MvcResult mvcResult, String message) throws IOException {
        assertThat(200).isEqualTo(mvcResult.getResponse().getStatus());
        FeelingResponse feelingResponse = (FeelingResponse) getObjectFromString(mvcResult.getResponse().getContentAsString(), FeelingResponse.class);
        assertThat(message).isEqualTo(feelingResponse.getMessage());
        assertThat(5.0D).isEqualTo(feelingResponse.getAverageForDay());
    }

    void assertInternalServerErrorResponse(MvcResult mvcResult, String code, String message) throws Exception {
        assertThat(500).isEqualTo(mvcResult.getResponse().getStatus());
        ErrorResponse errorResponse = (ErrorResponse) getObjectFromString(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(code).isEqualTo(errorResponse.getCode());
        assertThat(message).isEqualTo(errorResponse.getMessage());

    }

    void assertBadRequestErrorResponse(MvcResult mvcResult, String code, String message) throws IOException {
        assertThat(400).isEqualTo(mvcResult.getResponse().getStatus());
        ErrorResponse errorResponse = (ErrorResponse) getObjectFromString(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(code).isEqualTo(errorResponse.getCode());
        assertThat(message).isEqualTo(errorResponse.getMessage());
    }

    private Object getObjectFromString(String responseString, Class className) throws IOException {
        return objectMapper.readValue(responseString, className);
    }

    private String getJsonString(Object request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }
}
