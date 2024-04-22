package com.example.bills.repository.bill;

import com.example.bills.model.Flat;
import com.example.bills.model.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByFlat(Flat flat);
}
