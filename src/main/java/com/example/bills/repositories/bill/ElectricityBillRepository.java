package com.example.bills.repositories.bill;

import com.example.bills.models.bill.ElectricityBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityBillRepository extends JpaRepository<ElectricityBill, Integer> {
}
