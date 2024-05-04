package com.example.bills.repositories.bill;

import com.example.bills.models.Flat;
import com.example.bills.models.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByFlat(Flat flat);
}
