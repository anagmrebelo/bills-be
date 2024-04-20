package com.example.bills.repository;

import com.example.bills.model.Attendance;
import com.example.bills.model.Flatmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findByMonthAndFlatmate(Month month, Flatmate flatmate);
}
