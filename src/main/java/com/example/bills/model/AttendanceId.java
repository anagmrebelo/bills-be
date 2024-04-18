package com.example.bills.model;

import jakarta.persistence.Embeddable;
import java.time.Month;

@Embeddable
public class AttendanceId {
    private int flatmateId;
    private Month month;
}
