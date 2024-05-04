package com.example.bills.services;

import com.example.bills.models.*;
import com.example.bills.models.bill.Bill;
import com.example.bills.repositories.bill.BillRepository;
import com.example.bills.repositories.DebtRepository;
import com.example.bills.repositories.FlatRepository;
import com.example.bills.repositories.FlatmateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DebtServiceTest {
    @Autowired
    DebtService debtService;
    @Autowired
    DebtRepository debtRepository;
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    FlatmateRepository flatmateRepository;
    @Autowired
    BillRepository billRepository;
    private Debt debtOne;
    private Debt debtTwo;
    private Flatmate flatmate;
    private Bill billOne;
    private Flat flat;

    @BeforeEach
    void setUp() {
        flat = new Flat("Gran Via");
        flat = flatRepository.save(flat);
        flatmate = new Flatmate("Rita", flat);
        flatmate = flatmateRepository.save(flatmate);

        billOne = new Bill(new BigDecimal("80.23"), flat, Month.of(1));
        billOne = billRepository.save(billOne);

        Bill billTwo = new Bill(new BigDecimal("100.50"), flat, Month.of(2));
        billTwo = billRepository.save(billTwo);

        debtOne = new Debt(flatmate, billOne, new BigDecimal("12.23"));
        debtOne = debtRepository.save(debtOne);

        debtTwo = new Debt(flatmate, billTwo, new BigDecimal("15.00"));
        debtTwo = debtRepository.save(debtTwo);
    }

    @AfterEach
    void tearDown() {
        debtRepository.deleteAll();
        debtRepository.flush();
        billRepository.deleteAll();
        billRepository.flush();
        //attendanceRepository.deleteAll();
        //attendanceRepository.flush();
        flatmateRepository.deleteAll();
        flatmateRepository.flush();
        flatRepository.deleteAll();
        flatRepository.flush();
    }

    @Test
    void getDebtByBillAndFlatmate() {
        List<Debt> debts = debtService.getDebtByBillAndFlatmate(billOne.getId(), flatmate.getId());

        assertEquals(1, debts.size());
        assertTrue(debts.contains(debtOne));
    }

    @Test
    void getDebtByInvalidBillAndFlatmate() {
        int invalidBillId = 100;
        assertThrows(ResponseStatusException.class, () -> debtService.getDebtByBillAndFlatmate(invalidBillId, flatmate.getId()));
    }

    @Test
    void getDebtByBillAndInvalidFlatmate() {
        int invalidFlatmateId = 100;
        assertThrows(ResponseStatusException.class, () -> debtService.getDebtByBillAndFlatmate(billOne.getId(), invalidFlatmateId));
    }

    @Test
    void getDebtByInvalidFlatmate() {
        int invalidFlatmateId = 100;
        assertThrows(ResponseStatusException.class, () -> debtService.getDebtByFlatmate(invalidFlatmateId));
    }

    @Test
    void getDebtByFlatmate() {
        List<Debt> debts = debtService.getDebtByFlatmate(flatmate.getId());

        assertEquals(2, debts.size());
        assertTrue(debts.contains(debtOne));
        assertTrue(debts.contains(debtTwo));
    }

    @Test
    void addDebt() {
        Bill billThree = new Bill();
        billThree = billRepository.save(billThree);
        Debt debt = new Debt(flatmate, billThree, new BigDecimal("1.23"));
        Debt createdDebt = debtService.addDebt(debt);

        assertEquals(debt, createdDebt);
    }

    @Test
    void addInvalidFlatmateDebt() {
        Debt debt = new Debt(new Flatmate(13, "Pere", flat), billOne, new BigDecimal("1.23"));
        assertThrows(ResponseStatusException.class, () -> debtService.addDebt(debt));
    }
}