package com.example.bills.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Month;

@Embeddable
@AllArgsConstructor
@Getter
public class AttendanceId {
    private int flatmateId;
    @Max(value = 12, message = "Month has to be a number from 1 to 12")
    @Min(value = 1, message = "Month has to be a number from 1 to 12")
    private Month month;
}
