package com.example.bills.service;

import com.example.bills.model.Attendance;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    FlatmateService flatmateService;

    @Autowired
    FlatService flatService;

    public List<Attendance> getAttendanceByFlatmateAndMonth(Integer flatmateId, Integer monthInt) {
        if (monthInt < 1 || monthInt > 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid month");
        }
        Month month = Month.of(monthInt);
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);

        Optional<Attendance> attendance = attendanceRepository.findByMonthAndFlatmate(month, flatmate);
        return attendance.map(List::of).orElseGet(ArrayList::new);
    }

    public List<Attendance> getAttendanceByFlatAndMonth(Integer flatId, Integer monthInt) {
        Flat flat = flatService.getFlat(flatId);
        List<Flatmate> flatmates = flat.getFlatmateList();

        if (flatmates == null) {
            return List.of();
        }

        List<Attendance> attendances = new ArrayList<>();
        for (Flatmate flatmate : flatmates) {
            List<Attendance> flatmateAttendance = getAttendanceByFlatmateAndMonth(flatmate.getId(), monthInt);
            if (flatmateAttendance.size() == 1) {
                attendances.add(flatmateAttendance.getFirst());
            }
        }

        return attendances;
    }

    public Attendance addAttendance(Attendance attendance) {
        flatmateService.getFlatmate(attendance.getFlatmate().getId());
        return attendanceRepository.save(attendance);
    }
}
