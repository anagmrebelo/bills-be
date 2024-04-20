package com.example.bills.service;

import com.example.bills.model.*;
import com.example.bills.repository.BillRepository;
import com.example.bills.repository.DebtRepository;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.FlatmateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

        billOne = new Bill();
        billOne = billRepository.save(billOne);

        Bill billTwo = new Bill();
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
    }

    @Test
    void getDebtByBillAndFlatmate() {
        List<Debt> debts = debtService.getDebtByBillAndFlatmate(billOne.getId(), flatmate.getId());

        assertEquals(1, debts.size());
        assertTrue(debts.contains(debtOne));
    }

    @Disabled
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

    @Disabled
    @Test
    void addInvalidBillDebt() {
        Debt debt = new Debt(flatmate, new Bill(), new BigDecimal("1.23"));
        assertThrows(ResponseStatusException.class, () -> debtService.addDebt(debt));
    }
}