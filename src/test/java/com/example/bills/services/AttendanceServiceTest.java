package com.example.bills.services;

import com.example.bills.dtos.FlatmateDto;
import com.example.bills.models.Attendance;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
import com.example.bills.repositories.AttendanceRepository;
import com.example.bills.repositories.FlatRepository;
import com.example.bills.repositories.FlatmateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AttendanceServiceTest {
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    FlatmateRepository flatmateRepository;
    private Flat flat;
    private Flatmate flatmateOne;
    private Attendance attendanceFlatmateOneJan;
    private Attendance attendanceFlatmateTwoJan;

    @BeforeEach
    void setUp() {
        flat = new Flat("Gran Via");
        flat = flatRepository.save(flat);

        flatmateOne = new Flatmate(new FlatmateDto("Pedro", flat));
        flatmateOne = flatmateRepository.save(flatmateOne);
        flat.addFlatmate(flatmateOne);
        flat = flatRepository.save(flat);

        Flatmate flatmateTwo = new Flatmate(new FlatmateDto("Cris", flat));
        flatmateTwo = flatmateRepository.save(flatmateTwo);
        flat.addFlatmate(flatmateTwo);
        flat = flatRepository.save(flat);

        attendanceFlatmateOneJan = new Attendance(flatmateOne,Month.of(1),true);
        attendanceFlatmateOneJan = attendanceRepository.save(attendanceFlatmateOneJan);

        Attendance attendanceFlatmateOneFeb = new Attendance(flatmateOne, Month.of(2), true);
        attendanceRepository.save(attendanceFlatmateOneFeb);

        attendanceFlatmateTwoJan = new Attendance(flatmateTwo,Month.of(1),true);
        attendanceFlatmateTwoJan = attendanceRepository.save(attendanceFlatmateTwoJan);
    }

    @AfterEach
    void tearDown() {
        //debtRepository.deleteAll();
        //debtRepository.flush();
        //billRepository.deleteAll();
        //billRepository.flush();
        attendanceRepository.deleteAll();
        attendanceRepository.flush();
        flatmateRepository.deleteAll();
        flatmateRepository.flush();
        flatRepository.deleteAll();
        flatRepository.flush();
    }

    @Test
    void getAttendanceByFlatmateAndMonthInvalidMonth() {
        int invalidMonth = 13;
        assertThrows(ResponseStatusException.class, () -> attendanceService.getAttendanceByFlatmateAndMonth(flatmateOne.getId(), invalidMonth));
    }

    @Test
    void getAttendanceByFlatmateAndMonthInvalidFlatmate() {
        int invalidFlatmateId = 100;
        assertThrows(ResponseStatusException.class, () -> attendanceService.getAttendanceByFlatmateAndMonth(invalidFlatmateId, 1));
    }

    @Test
    void getAttendanceByFlatmateAndMonth() {
        List<Attendance> attendances = attendanceService.getAttendanceByFlatmateAndMonth(flatmateOne.getId(), 1);
        assertEquals(1, attendances.size());
        assertTrue(attendances.contains(attendanceFlatmateOneJan));
    }
    @Test
    void getAttendanceByFlatAndMonthInvalidMonth() {
        int invalidMonth = 13;
        assertThrows(ResponseStatusException.class, () -> attendanceService.getAttendanceByFlatAndMonth(flat.getId(), invalidMonth));
    }
    @Test
    void getAttendanceByFlatAndMonthInvalidFlat() {
        int invalidFlatId = 100;
        assertThrows(ResponseStatusException.class, () -> attendanceService.getAttendanceByFlatAndMonth(invalidFlatId, 1));
    }
    @Test
    void getAttendanceByFlatAndMonth() {
        List<Attendance> attendances = attendanceService.getAttendanceByFlatAndMonth(flat.getId(), 1);
        assertEquals(2, attendances.size());
        assertTrue(attendances.contains(attendanceFlatmateOneJan));
        assertTrue(attendances.contains(attendanceFlatmateTwoJan));
    }
    @Test
    void addInvalidFlatmateAttendance() {
        Attendance attendance = new Attendance(new Flatmate("Pere", flat), Month.MARCH, true);
        assertThrows(ResponseStatusException.class, () -> attendanceService.addAttendance(attendance));
    }

    @Test
    void addValidAttendance() {
        Attendance attendance = new Attendance(flatmateOne, Month.MARCH, true);
        Attendance createdAttendance = attendanceService.addAttendance(attendance);

        assertEquals(attendance, createdAttendance);
    }
}