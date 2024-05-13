package com.example.bills.controllers;

import com.example.bills.models.Attendance;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
import com.example.bills.repositories.AttendanceRepository;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttendanceControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    FlatmateRepository flatmateRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    private Flat flat;
    private Flatmate flatmateOne;
    private Attendance attendanceFlatmateOneJan;
    private Attendance attendanceFlatmateTwoJan;
    private User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        user = userService.saveUser(new User(null, "Mike", "mike", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_ADMIN");

        userService.saveUser(new User(null, "Marc", "marc", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_USER");

        flat = new Flat("Gran Via");
        flat = flatRepository.save(flat);

        flatmateOne = new Flatmate("Pedro", flat);
        flatmateOne = flatmateRepository.save(flatmateOne);
        flat = flatRepository.save(flat);

        Flatmate flatmateTwo = new Flatmate("Cris", flat);
        flatmateTwo = flatmateRepository.save(flatmateTwo);
        flat = flatRepository.save(flat);

        attendanceFlatmateOneJan = new Attendance(flatmateOne, Month.of(1),true);
        attendanceFlatmateOneJan = attendanceRepository.save(attendanceFlatmateOneJan);

        attendanceFlatmateTwoJan = new Attendance(flatmateTwo,Month.of(1),true);
        attendanceFlatmateTwoJan = attendanceRepository.save(attendanceFlatmateTwoJan);

        user.setFlat(flat);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRepository.flush();
        //debtRepository.deleteAll();
        //debtRepository.flush();
        //billRepository.deleteAll();
        //billRepository.flush();
        attendanceRepository.deleteAll();
        attendanceRepository.flush();
        flatmateRepository.deleteAll();
        flatmateRepository.flush();
        flatRepository.deleteAll();
        flatRepository.flush();
    }

    @Test
    @WithMockUser(username = "mike")
    void getAttendanceByFlatmateInvalidFlatmate() throws Exception {
        int invalidFlatmateId = 1000;
        mockMvc.perform(get("/attendance")
                .param("month", "1")
                .param("flatmate", String.valueOf(invalidFlatmateId)))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    @WithMockUser(username = "mike")
    void getAttendanceByFlatmateInvalidMonth() throws Exception {
        int invalidMonth = 13;
        MvcResult mvcResult = mockMvc.perform(get("/attendances")
                .param("month", String.valueOf(invalidMonth))
                .param("flatmate", String.valueOf(flatmateOne.getId())))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getAttendanceByFlatmate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/attendances")
                .param("month", "1")
                .param("flatmate", String.valueOf(flatmateOne.getId())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Attendance> attendanceList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Attendance>>() {});

        assertEquals(1, attendanceList.size());
        assertTrue(attendanceList.contains(attendanceFlatmateOneJan));
    }

    @Test
    @WithMockUser(username = "marc")
    void getAttendanceByFlatmateForbiddenUser() throws Exception {
        mockMvc.perform(get("/attendances")
                        .param("month", "1")
                        .param("flatmate", String.valueOf(flatmateOne.getId())))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getAttendanceByFlatInvalidFlat() throws Exception {
        int invalidFlatId = 100;
        MvcResult mvcResult = mockMvc.perform(get("/attendances")
                        .param("month", "1")
                        .param("flat", String.valueOf(invalidFlatId)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getAttendanceByFlatInvalidMonth() throws Exception {
        int invalidMonth = 13;
        MvcResult mvcResult = mockMvc.perform(get("/attendances")
                        .param("month", String.valueOf(invalidMonth))
                        .param("flat", String.valueOf(flat.getId())))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getAttendanceByFlat() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/attendances")
                        .param("month", "1")
                        .param("flat", String.valueOf(flat.getId())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Attendance> attendanceList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Attendance>>() {});

        assertEquals(2, attendanceList.size());
        assertTrue(attendanceList.contains(attendanceFlatmateOneJan));
        assertTrue(attendanceList.contains(attendanceFlatmateTwoJan));
    }

    @Test
    @WithMockUser(username = "marc")
    void getAttendanceByFlatForbiddenUser() throws Exception {
        mockMvc.perform(get("/attendances")
                        .param("month", "1")
                        .param("flat", String.valueOf(flat.getId())))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getAttendancePriority() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/attendances")
                        .param("month", "1")
                        .param("flat", String.valueOf(flat.getId()))
                        .param("flatmate", String.valueOf(flatmateOne.getId())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Attendance> attendanceList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Attendance>>() {});

        assertEquals(2, attendanceList.size());
        assertTrue(attendanceList.contains(attendanceFlatmateOneJan));
        assertTrue(attendanceList.contains(attendanceFlatmateTwoJan));
    }

    @Test
    @WithMockUser(username = "mike")
    void addInvalidFlatmateAttendance() throws Exception {
        Month month = Month.APRIL;
        Attendance attendance = new Attendance(new Flatmate("test", flat), month, true);
        String body = objectMapper.writeValueAsString(attendance);

        MvcResult mvcResult = mockMvc.perform(get("/attendances")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void addValidAttendance() throws Exception {
        Month month = Month.APRIL;
        Attendance attendance = new Attendance(flatmateOne, month, true);
        String body = objectMapper.writeValueAsString(attendance);

        MvcResult mvcResult = mockMvc.perform(post("/attendances")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Attendance createdAttendance = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Attendance.class);

        assertEquals(flatmateOne, createdAttendance.getFlatmate());
        assertEquals(month, createdAttendance.getMonth());
        assertTrue(createdAttendance.getWasPresent());
    }

    @Test
    @WithMockUser(username = "marc")
    void addValidAttendanceForbiddenUser() throws Exception {
        Month month = Month.APRIL;
        Attendance attendance = new Attendance(flatmateOne, month, true);
        String body = objectMapper.writeValueAsString(attendance);

       mockMvc.perform(post("/attendances")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}