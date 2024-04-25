package com.example.bills.service;

import com.example.bills.dto.BillDto;
import com.example.bills.model.Flat;
import com.example.bills.model.bill.Bill;
import com.example.bills.model.bill.ElectricityBill;
import com.example.bills.model.bill.WaterBill;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.bill.BillRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BillServiceTest {
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
    void getBillsByInvalidFlat() {
        int invalidFlatId = 100;
        assertThrows(ResponseStatusException.class, () -> billService.getBillsByFlat(invalidFlatId));
    }
    @Test
    void getBillsByValidFlat() {
        List<Bill> bills = billService.getBillsByFlat(flatOne.getId());
        assertEquals(2, bills.size());
        assertTrue(bills.contains(billOne));
        assertTrue(bills.contains(billTwo));
    }

    @Test
    void addInvalidTypeBill() {
        BillDto billDto = new BillDto(new BigDecimal("100.23"), flatTwo, Month.MARCH);
        assertThrows(ResponseStatusException.class, () -> billService.addBill("invalid", billDto));
    }
    @Test
    void addWaterBill() {
        BigDecimal bigDecimal = new BigDecimal("100.23");
        Month month =  Month.MARCH;
        BillDto billDto = new BillDto(bigDecimal, flatTwo, month);

        Bill waterBill = billService.addWaterBill(billDto);

        assertEquals("water", waterBill.getType());
        assertEquals(bigDecimal, waterBill.getAmount());
        assertEquals(month, waterBill.getMonth());
    }

    @Test
    void addElectricityBill() {
        BigDecimal bigDecimal = new BigDecimal("100.23");
        Month month =  Month.MARCH;
        BillDto billDto = new BillDto(bigDecimal, flatTwo, month);

        Bill electricityBill = billService.addElectricityBill(billDto);

        assertEquals("electricity", electricityBill.getType());
        assertEquals(bigDecimal, electricityBill.getAmount());
        assertEquals(month, electricityBill.getMonth());
    }

    @Test
    void addGasBill() {
        BigDecimal bigDecimal = new BigDecimal("100.23");
        Month month =  Month.MARCH;
        BillDto billDto = new BillDto(bigDecimal, flatTwo, month);

        Bill gasBill = billService.addGasBill(billDto);

        assertEquals("gas", gasBill.getType());
        assertEquals(bigDecimal, gasBill.getAmount());
        assertEquals(month, gasBill.getMonth());
    }

    @Test
    void addTelcoBill() {
        BigDecimal bigDecimal = new BigDecimal("100.23");
        Month month =  Month.MARCH;
        BillDto billDto = new BillDto(bigDecimal, flatTwo, month);

        Bill telcoBill = billService.addTelcoBill(billDto);

        assertEquals("telco", telcoBill.getType());
        assertEquals(bigDecimal, telcoBill.getAmount());
        assertEquals(month, telcoBill.getMonth());
    }

    @Test
    void deleteBill() {
        billService.deleteBill(billOne.getId());
        Optional<Bill> bill = billRepository.findById(billOne.getId());
        assertFalse(bill.isPresent());
    }
}