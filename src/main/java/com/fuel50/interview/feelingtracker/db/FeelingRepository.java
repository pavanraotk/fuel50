package com.fuel50.interview.feelingtracker.db;

import com.fuel50.interview.feelingtracker.db.model.Feeling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FeelingRepository extends JpaRepository<Feeling, Long> {

    @Query(value = "SELECT avg(feeling_value) FROM feeling_indicator.feeling WHERE feeling_date = ?", nativeQuery = true)
    Double findAverageForDay(LocalDate localDate);
    Feeling findByFeelingDateAndIpAddress(LocalDate feelingDate, String ipAddress);
}
