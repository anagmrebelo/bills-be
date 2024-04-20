package com.example.bills.repository;

import com.example.bills.model.Attendance;
import com.example.bills.model.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
    Optional<Attendance> findByAttendanceId(AttendanceId attendanceId);
}
