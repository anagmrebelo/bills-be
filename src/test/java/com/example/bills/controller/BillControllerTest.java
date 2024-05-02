package com.example.bills.controller;

import com.example.bills.dto.BillDto;
import com.example.bills.model.Attendance;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.model.bill.Bill;
import com.example.bills.model.bill.ElectricityBill;
import com.example.bills.model.bill.WaterBill;
import com.example.bills.repository.AttendanceRepository;
import com.example.bills.repository.DebtRepository;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.FlatmateRepository;
import com.example.bills.repository.bill.BillRepository;
import com.example.bills.service.BillService;
import com.example.bills.service.FlatService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BillControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    BillRepository billRepository;
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    FlatService flatService;
    @Autowired
    FlatmateRepository flatmateRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    DebtRepository debtRepository;
    private Flat flatOne;
    private Flat flatTwo;
    private Bill billOne;
    private Bill billTwo;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
/*
        billRepository.deleteAll();
        billRepository.flush();
        flatmateRepository.deleteAll();
        flatmateRepository.flush();
        flatRepository.deleteAll();
        flatRepository.flush();*/
    }

    @Test
    void addBillToEmptyFlat() throws Exception {
        Month month =  Month.MARCH;
        BigDecimal bigDecimal = new BigDecimal("100.23");
        BillDto billDto = new BillDto(bigDecimal, flatTwo, month);

        String body = objectMapper.writeValueAsString(billDto);
        MvcResult mvcResult = mockMvc.perform(post("/bills/water")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void addBillToIncompleteAttendances() throws Exception {
        Month month =  Month.MARCH;

        Flatmate flatmateOne = new Flatmate("Ana", flatTwo);
        Flatmate flatmateTwo = new Flatmate("Pedro", flatTwo);
        flatmateRepository.saveAll(List.of(flatmateOne, flatmateTwo));
        flatService.addFlatmate(flatTwo, flatmateOne);
        flatService.addFlatmate(flatTwo, flatmateTwo);

        Attendance attendanceOne = new Attendance(flatmateOne, month, true);
        attendanceRepository.save(attendanceOne);

        BigDecimal bigDecimal = new BigDecimal("100.23");
        BillDto billDto = new BillDto(bigDecimal, flatTwo, month);

        String body = objectMapper.writeValueAsString(billDto);
        MvcResult mvcResult = mockMvc.perform(post("/bills/water")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }



    @Test
    void addBill() throws Exception {
        Month month =  Month.MARCH;

        Flatmate flatmateOne = new Flatmate("Ana", flatTwo);
        Flatmate flatmateTwo = new Flatmate("Pedro", flatTwo);
        Flatmate flatmateThree = new Flatmate("Rita", flatTwo);
        flatmateRepository.saveAll(List.of(flatmateOne, flatmateTwo, flatmateThree));
        flatService.addFlatmate(flatTwo, flatmateOne);
        flatService.addFlatmate(flatTwo, flatmateTwo);
        flatService.addFlatmate(flatTwo, flatmateThree);

        Attendance attendanceOne = new Attendance(flatmateOne, month, true);
        attendanceRepository.save(attendanceOne);
        Attendance attendanceTwo = new Attendance(flatmateTwo, month, true);
        attendanceRepository.save(attendanceTwo);
        Attendance attendanceThree = new Attendance(flatmateThree, month, false);
        attendanceRepository.save(attendanceThree);

        BigDecimal bigDecimal = new BigDecimal("100.23");
        BillDto billDto = new BillDto(bigDecimal, flatTwo, month);

        String body = objectMapper.writeValueAsString(billDto);
        MvcResult mvcResult = mockMvc.perform(post("/bills/water")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Bill bill = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Bill.class);

        assertEquals(bigDecimal, bill.getAmount());
        assertEquals(month, bill.getMonth());
        assertEquals(1, debtRepository.findAllByFlatmate(flatmateOne).size());
        assertEquals(1, debtRepository.findAllByFlatmate(flatmateTwo).size());
        assertEquals(0, debtRepository.findAllByFlatmate(flatmateThree).size());
    }

    @Test
    void deleteInvalidBill() throws Exception {
        int invalidBillId = 100;
        MvcResult mvcResult = mockMvc.perform(delete("/bills/" + invalidBillId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void deleteBill() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/bills/" + billOne.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(billRepository.findById(billOne.getId()).isPresent());
    }

    @Test
    void getBillsByInvalidFlat() throws Exception {
        int invalidFlatId = 100;
        MvcResult mvcResult = mockMvc.perform(get("/debts/" + invalidFlatId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getBillsByFlat() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/bills/"+ flatOne.getId()))
                .andExpect(status().isOk())
                .andReturn();
        List<Bill> bills = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Bill>>() {});

        assertEquals(2, bills.size());
        assertTrue(bills.contains(billOne));
        assertTrue(bills.contains(billTwo));
    }
}