package com.example.bills.repository;

import com.example.bills.model.bill.Bill;
import com.example.bills.model.Debt;
import com.example.bills.model.Flatmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Integer> {
    List<Debt> findByBillAndFlatmate(Bill bill, Flatmate flatmate);

    List<Debt> findAllByFlatmate(Flatmate flatmate);
}
