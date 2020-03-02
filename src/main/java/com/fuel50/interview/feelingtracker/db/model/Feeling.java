package com.fuel50.interview.feelingtracker.db.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Table(name = "feeling", schema = "feeling_indicator")
@Entity
public class Feeling {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feeling_indicator.feeling_seq")
    private Long id;

    private Integer feelingValue;
    private String ipAddress;
    private LocalDate feelingDate;
    private String message;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date modified;

    public Feeling() {
    }

    public Feeling(Integer feelingValue, String ipAddress, LocalDate feelingDate, String message) {
        this.feelingValue = feelingValue;
        this.ipAddress = ipAddress;
        this.feelingDate = feelingDate;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Integer getFeelingValue() {
        return feelingValue;
    }

    public void setFeelingValue(Integer feelingValue) {
        this.feelingValue = feelingValue;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDate getFeelingDate() {
        return feelingDate;
    }

    public void setFeelingDate(LocalDate feelingDate) {
        this.feelingDate = feelingDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }
}
