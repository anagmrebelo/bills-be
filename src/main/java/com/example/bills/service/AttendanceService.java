package com.example.bills.service;

import com.example.bills.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;
}
