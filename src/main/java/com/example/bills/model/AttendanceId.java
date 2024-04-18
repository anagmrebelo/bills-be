package com.example.bills.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

import java.time.Month;

@Embeddable
@AllArgsConstructor
public class AttendanceId {
    private int flatmateId;
    private Month month;
}
