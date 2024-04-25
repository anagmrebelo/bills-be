package com.example.bills.controller;

import com.example.bills.dto.BillDto;
import com.example.bills.model.Flat;
import com.example.bills.model.bill.Bill;
import com.example.bills.model.bill.ElectricityBill;
import com.example.bills.model.bill.WaterBill;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.bill.BillRepository;
import com.example.bills.service.BillService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BillControllerTest {
    @Autowired
    BillService billService;
    @Autowired
    BillRepository billRepository;
    @Autowired
    FlatRepository flatRepository;
    private Flat flatOne;
    private Flat flatTwo;
    private Bill billOne;
    private Bill billTwo;

    @BeforeEach
    void setUp() {
        flatOne = new Flat("Gran Via");
        flatTwo = new Flat("Sagrada");
        flatRepository.saveAll(List.of(flatOne, flatTwo));

        billOne = new WaterBill(new BillDto(new BigDecimal("10.23"), flatOne, Month.JANUARY));
        billTwo = new ElectricityBill(new BillDto(new BigDecimal("15.23"), flatOne, Month.JANUARY));
        Bill billThree = new WaterBill(new BillDto(new BigDecimal("50.23"), flatTwo, Month.FEBRUARY));
        billRepository.saveAll(List.of(billOne, billTwo, billThree));
    }

    @AfterEach
    void tearDown() {
        billRepository.deleteAll();
        billRepository.flush();
    }

    @Test
    void addBill() {
    }

    @Test
    void deleteBill() {
    }

    @Test
    void getBillsByFlat() {
    }
}