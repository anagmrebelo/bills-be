package com.example.bills.repository.bill;

import com.example.bills.model.bill.ElectricityBill;
import com.example.bills.model.bill.WaterBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricityBillRepository extends JpaRepository<ElectricityBill, Integer> {
}
