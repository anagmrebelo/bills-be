package com.example.bills.controllers;

import com.example.bills.models.Debt;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
import com.example.bills.models.bill.Bill;
import com.example.bills.repositories.DebtRepository;
import com.example.bills.repositories.FlatRepository;
import com.example.bills.repositories.FlatmateRepository;
import com.example.bills.repositories.bill.BillRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BalanceControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
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
    private Flatmate flatmateOne;
    private Flatmate flatmateTwo;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Flat flat = new Flat("Gran Via");
        flat = flatRepository.save(flat);
        flatmateOne = new Flatmate("Rita", flat);
        flatmateOne = flatmateRepository.save(flatmateOne);

        flatmateTwo = new Flatmate("Rita", flat);
        flatmateTwo = flatmateRepository.save(flatmateTwo);

        Bill billOne = new Bill(new BigDecimal("80.23"), flat, Month.of(1));
        billOne = billRepository.save(billOne);

        Bill billTwo = new Bill(new BigDecimal("100.50"), flat, Month.of(2));
        billTwo = billRepository.save(billTwo);

        debtOne = new Debt(flatmateOne, billOne, new BigDecimal("12.23"));
        debtOne = debtRepository.save(debtOne);

        debtTwo = new Debt(flatmateOne, billTwo, new BigDecimal("15.00"));
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
    void getBalance() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/balance/" + flatmateOne.getId()))
                .andExpect(status().isOk())
                .andReturn();

        BigDecimal balance = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BigDecimal.class);

        assertEquals(debtOne.getAmount().add(debtTwo.getAmount()), balance);
    }

    @Test
    void getEmptyBalance() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/balance/" + flatmateTwo.getId()))
                .andExpect(status().isOk())
                .andReturn();

        BigDecimal balance = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BigDecimal.class);

        assertEquals(new BigDecimal("0"), balance);
    }

    @Test
    void getBalanceInvalidFlatmate() throws Exception {
        int invalidFlatmateId = 100;
        MvcResult mvcResult = mockMvc.perform(get("/balance/" + invalidFlatmateId))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}