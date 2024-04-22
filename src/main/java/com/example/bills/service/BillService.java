package com.example.bills.service;

import com.example.bills.model.bill.ElectricityBill;
import com.example.bills.model.bill.TelcoBill;
import com.example.bills.repository.bill.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    WaterBillRepository waterBillRepository;
    @Autowired
    GasBillRepository gasBillRepository;
    @Autowired
    ElectricityBillRepository electricityBillRepository;
    @Autowired
    TelcoBillRepository telcoBillRepository;

}
