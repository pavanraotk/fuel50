package com.fuel50.interview.feelingtracker.integration.db;

import com.fuel50.interview.feelingtracker.builder.FeelingBuilder;
import com.fuel50.interview.feelingtracker.db.model.Feeling;
import com.fuel50.interview.feelingtracker.integration.AbstractIT;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class FeelingRepositoryIntegrationTest extends AbstractIT {

    @Test
    public void testFeelingRepository() {
        Feeling feeling = new FeelingBuilder().withData().build();
        feelingRepository.save(feeling);
        assertFeelingAverage(1.0D, feelingRepository.findAverageForDay(LocalDate.now()));
        LocalDate yesterday = LocalDate.now().minusDays(1);
        feeling = new FeelingBuilder().withData().setFeelingDate(yesterday).build();
        feelingRepository.save(feeling);
        assertFeelingAverage(1.0D, feelingRepository.findAverageForDay(LocalDate.now()));
        assertFeelingAverage(1.0D, feelingRepository.findAverageForDay(yesterday));
        feeling = new FeelingBuilder().withData().setFeelingValue(5).setIpAddress("127.0.0.2").build();
        feelingRepository.save(feeling);
        assertFeelingAverage(1.0D, feelingRepository.findAverageForDay(yesterday));
        assertFeelingAverage(3.0D, feelingRepository.findAverageForDay(LocalDate.now()));
        assertThat(feelingRepository.findByFeelingDateAndIpAddress(LocalDate.now(), feeling.getIpAddress())).isNotNull();
    }

}