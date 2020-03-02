package com.fuel50.interview.feelingtracker.service;

import com.fuel50.interview.feelingtracker.db.FeelingRepository;
import com.fuel50.interview.feelingtracker.db.model.Feeling;
import com.fuel50.interview.feelingtracker.service.exception.FeelingServiceException;
import com.fuel50.interview.feelingtracker.web.request.FeelingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FeelingService {

    private static final Logger logger = LoggerFactory.getLogger(FeelingService.class);

    private final FeelingRepository feelingRepository;

    @Autowired
    public FeelingService(FeelingRepository feelingRepository) {
        this.feelingRepository = feelingRepository;
    }

    public Double saveFeeling(String ipAddress, FeelingRequest feelingRequest) throws FeelingServiceException {
        Feeling feeling = generateFeeling(ipAddress, feelingRequest);
        try {
            feelingRepository.save(feeling);
            return getAverageFeelingForDay();
        } catch (Exception ex) {
            logger.error("Error persisting feeling {}", ex);
            throw new FeelingServiceException("error.persisting.feeling", "Error persisting feeling", ex);
        }
    }

    public Double getAverageFeelingForDay() throws FeelingServiceException {
        try {
            return feelingRepository.findAverageForDay(getCurrentDate());
        } catch (Exception ex) {
            logger.error("Error getting average feeling for day", ex);
            throw new FeelingServiceException("error.getting.average.feeling.for.day", "Error getting average feeling for day", ex);
        }
    }

    public boolean feelingExists(String ipAddress) throws FeelingServiceException {
        try {
            return feelingRepository.findByFeelingDateAndIpAddress(getCurrentDate(), ipAddress) != null;
        } catch (Exception ex) {
            logger.error("Error checking if feeling exists", ex);
            throw new FeelingServiceException("error.checking.feeling.exists", "Error checking if feeling exists", ex);
        }
    }

    private Feeling generateFeeling(String ipAddress, FeelingRequest feelingRequest) {
        Feeling feeling = new Feeling();
        feeling.setIpAddress(ipAddress);
        feeling.setFeelingDate(getCurrentDate());
        feeling.setFeelingValue(feelingRequest.getFeelingIndicator());
        feeling.setMessage(feelingRequest.getMessage());
        return feeling;
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
