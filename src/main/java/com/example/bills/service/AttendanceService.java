package com.example.bills.service;

import com.example.bills.model.Attendance;
import com.example.bills.model.AttendanceId;
import com.example.bills.repository.AttendanceRepository;
import com.example.bills.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    FlatmateService flatmateService;

    public List<Attendance> getAttendanceByFlatmateAndMonth(Integer flatmateId, Integer monthInt) {
        if (monthInt < 1 || monthInt > 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid month");
        }
        Month month = Month.of(monthInt);
        flatmateService.getFlatmate(flatmateId);

        AttendanceId attendanceId = new AttendanceId(flatmateId, month);
        return attendanceRepository.findAllByAttendanceId(attendanceId);
    }

    public List<Attendance> getAttendanceByFlatAndMonth(Integer flatId, Integer monthInt) {
        return List.of();
    }

    public Attendance addAttendance(Attendance attendance) {
        return attendance;
    }
}
