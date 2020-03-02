package com.fuel50.interview.feelingtracker.integration;

import com.fuel50.interview.feelingtracker.db.FeelingRepository;
import com.fuel50.interview.feelingtracker.db.model.Feeling;
import com.fuel50.interview.feelingtracker.integration.configuration.IntegrationTestConfiguration;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@Import(IntegrationTestConfiguration.class)
public class AbstractIT {

    @Autowired
    protected FeelingRepository feelingRepository;

    @After
    public void tearDown() {
        feelingRepository.deleteAll();
    }

    protected void assertFeeling(Feeling actualFeeling, Feeling expectedFeeling) {
        assertThat(actualFeeling.getIpAddress()).isEqualTo(expectedFeeling.getIpAddress());
        assertThat(actualFeeling.getFeelingDate()).isEqualTo(expectedFeeling.getFeelingDate());
        assertThat(actualFeeling.getFeelingValue()).isEqualTo(expectedFeeling.getFeelingValue());
        assertThat(actualFeeling.getMessage()).isEqualTo(expectedFeeling.getMessage());
    }

    protected void assertFeelingAverage(Double expected, Double actual) {
        AssertionsForClassTypes.assertThat(expected).isEqualTo(actual);
    }

}
