package com.example.bills.repositories;

import com.example.bills.models.bill.Bill;
import com.example.bills.models.Debt;
import com.example.bills.models.Flatmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Integer> {
    List<Debt> findByBillAndFlatmate(Bill bill, Flatmate flatmate);

    List<Debt> findAllByFlatmate(Flatmate flatmate);

    void deleteAllByBill(Bill bill);
}
