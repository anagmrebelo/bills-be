package com.example.bills.controllers;

import com.example.bills.models.Attendance;
import com.example.bills.services.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/attendance")
    @ResponseStatus(HttpStatus.OK)
    List<Attendance> getAttendance(@RequestParam(name = "month") Integer monthInt, @RequestParam(name = "flat") Optional<Integer> flatId, @RequestParam(name = "flatmate") Optional<Integer> flatmateId) {
        if (flatId.isPresent()) {
            return attendanceService.getAttendanceByFlatAndMonth(flatId.get(), monthInt);
        }
        if (flatmateId.isPresent()) {
            return attendanceService.getAttendanceByFlatmateAndMonth(flatmateId.get(), monthInt);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need request param flat or flatmate");
    }

    @PostMapping("/attendance")
    @ResponseStatus(HttpStatus.CREATED)
    Attendance addAttendance(@RequestBody @Valid Attendance attendance) {
        return attendanceService.addAttendance(attendance);
    }
}
