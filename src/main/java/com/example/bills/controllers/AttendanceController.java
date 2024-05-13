package com.example.bills.controllers;

import com.example.bills.models.Attendance;
import com.example.bills.security.models.User;
import com.example.bills.security.services.UserService;
import com.example.bills.services.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    UserService userService;

    @GetMapping("/attendances")
    @ResponseStatus(HttpStatus.OK)
    List<Attendance> getAttendance(@RequestParam(name = "month") Integer monthInt, @RequestParam(name = "flat") Optional<Integer> flatId, @RequestParam(name = "flatmate") Optional<Integer> flatmateId) {
        User user = fetchUser();

        if (flatId.isPresent()) {
            return attendanceService.getAttendanceByFlatAndMonth(flatId.get(), monthInt, user);
        }
        if (flatmateId.isPresent()) {
            return attendanceService.getAttendanceByFlatmateAndMonth(flatmateId.get(), monthInt, user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need request param flat or flatmate");
    }

    @PostMapping("/attendances")
    @ResponseStatus(HttpStatus.CREATED)
    Attendance addAttendance(@RequestBody @Valid Attendance attendance) {
        User user = fetchUser();
        return attendanceService.addAttendance(attendance, user);
    }

    User fetchUser() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            user = userService.getUser(userDetails.getUsername());
        } else {
            user = userService.getUser((String) principal);
        }
        return user;
    }
}
