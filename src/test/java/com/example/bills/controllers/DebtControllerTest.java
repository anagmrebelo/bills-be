package com.example.bills.controllers;

import com.example.bills.models.bill.Bill;
import com.example.bills.models.Debt;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
import com.example.bills.repositories.bill.BillRepository;
import com.example.bills.repositories.DebtRepository;
import com.example.bills.repositories.FlatRepository;
import com.example.bills.repositories.FlatmateRepository;
import com.example.bills.security.models.User;
import com.example.bills.security.repositories.UserRepository;
import com.example.bills.security.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DebtControllerTest {
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    private Debt debtOne;
    private Debt debtTwo;
    private Flatmate flatmate;
    private Bill billOne;
    private User user;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        user = userService.saveUser(new User(null, "Mike", "mike", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_ADMIN");

        userService.saveUser(new User(null, "Marc", "marc", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_USER");

        Flat flat = new Flat("Gran Via");
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

        user.setFlat(flat);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRepository.flush();
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
    @WithMockUser(username = "mike")
    void getDebtByInvalidBillAndFlatmate() throws Exception {
        int invalidBillId = 100;
        MvcResult mvcResult = mockMvc.perform(get("/debts")
                .param("bill", String.valueOf(invalidBillId))
                .param("flatmate", String.valueOf(flatmate.getId())))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getDebtByBillAndInvalidFlatmate() throws Exception {
        int invalidFlatmateId = 100;
        MvcResult mvcResult = mockMvc.perform(get("/debts")
                        .param("flatmate", String.valueOf(invalidFlatmateId))
                        .param("bill", String.valueOf(billOne.getId())))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    @WithMockUser(username = "mike")
    void getDebtByBillAndFlatmate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/debts")
                        .param("flatmate", String.valueOf(flatmate.getId()))
                        .param("bill", String.valueOf(billOne.getId())))
                .andExpect(status().isOk())
                .andReturn();
        List<Debt> debts = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Debt>>() {});

        assertEquals(1, debts.size());
        assertTrue(debts.contains(debtOne));
    }

    @Test
    @WithMockUser(username = "marc")
    void getDebtByBillAndFlatmateForbiddenUser() throws Exception {
        mockMvc.perform(get("/debts")
                        .param("flatmate", String.valueOf(flatmate.getId()))
                        .param("bill", String.valueOf(billOne.getId())))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getDebtByInvalidFlatmate() throws Exception {
        int invalidFlatmateId = 100;
        MvcResult mvcResult = mockMvc.perform(get("/debts")
                        .param("flatmate", String.valueOf(invalidFlatmateId)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getDebtByFlatmate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/debts")
                        .param("flatmate", String.valueOf(flatmate.getId())))
                .andExpect(status().isOk())
                .andReturn();
        List<Debt> debts = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Debt>>() {});

        assertEquals(2, debts.size());
        assertTrue(debts.contains(debtOne));
        assertTrue(debts.contains(debtTwo));
    }

    @Test
    @WithMockUser(username = "marc")
    void getDebtByFlatmateForbiddenUser() throws Exception {
        mockMvc.perform(get("/debts")
                        .param("flatmate", String.valueOf(flatmate.getId())))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}