package com.fuel50.interview.feelingtracker.web;

import com.fuel50.interview.feelingtracker.service.FeelingService;
import com.fuel50.interview.feelingtracker.service.exception.FeelingServiceException;
import com.fuel50.interview.feelingtracker.web.request.FeelingRequest;
import com.fuel50.interview.feelingtracker.web.response.AbstractResponse;
import com.fuel50.interview.feelingtracker.web.response.ErrorResponse;
import com.fuel50.interview.feelingtracker.web.response.FeelingResponse;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/feeling")
public class FeelingController extends BaseController {

    private final FeelingService feelingService;

    @Autowired
    public FeelingController(MessageSource messageSource, FeelingService feelingService) {
        super(messageSource);
        this.feelingService = feelingService;
    }

    @RequestMapping(value = "/record", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<AbstractResponse> recordFeelings(
            @CookieValue(value = "ipAddress") String ipAddress,
            @Valid @RequestBody FeelingRequest feelingRequest) throws FeelingServiceException {
        if(!InetAddressValidator.getInstance().isValid(ipAddress)) {
            return new ResponseEntity<>(new ErrorResponse("ipAddress.format.is.wrong", "IP Address format is wrong"), BAD_REQUEST);
        }
        if (feelingService.feelingExists(ipAddress)) {
            return new ResponseEntity<>(new ErrorResponse("feeling.already.exists", "Sorry, you have already submitted your response for today, try again tomorrow!"), BAD_REQUEST);
        }
        Double averageForDay = feelingService.saveFeeling(ipAddress, feelingRequest);
        return new ResponseEntity<>(new FeelingResponse("Successfully saved your response", averageForDay), OK);
    }

    @RequestMapping(value = "/averageForDay", method = RequestMethod.GET)
    public ResponseEntity<AbstractResponse> getAverageForDay() throws FeelingServiceException {
        double averageForDay = feelingService.getAverageFeelingForDay();
        return new ResponseEntity<>(new FeelingResponse("Average feeling for day", averageForDay), OK);
    }

}
