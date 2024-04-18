package com.example.bills.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @EmbeddedId
    private AttendanceId attendanceId;
    @NonNull
    private Boolean wasPresent;
}
