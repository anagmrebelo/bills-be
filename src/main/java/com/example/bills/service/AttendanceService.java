package com.example.bills.service;

import com.example.bills.model.Attendance;
import com.example.bills.model.AttendanceId;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.AttendanceRepository;
import com.example.bills.repository.FlatRepository;
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
        flatmateService.getFlatmate(flatmateId);

        AttendanceId attendanceId = new AttendanceId(flatmateId, month);
        Optional<Attendance> attendance = attendanceRepository.findByAttendanceId(attendanceId);
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
            List<Attendance> flatmateAttendance = getAttendanceByFlatAndMonth(flatmate.getId(), monthInt);
            if (flatmateAttendance.size() == 1) {
                attendances.add(flatmateAttendance.getFirst());
            }
        }

        return attendances;
    }

    public Attendance addAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
}
