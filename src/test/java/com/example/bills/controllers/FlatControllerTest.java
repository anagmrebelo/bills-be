package com.example.bills.controllers;

import com.example.bills.dtos.FlatDto;
import com.example.bills.models.Flat;
import com.example.bills.repositories.FlatRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FlatControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Flat flatOne;
    private Flat flatTwo;
    private User user;

    @BeforeEach
    void setUp() {
        user = userService.saveUser(new User(null, "Mike", "mike", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_ADMIN");

        userService.saveUser(new User(null, "Marc", "marc", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_USER");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        flatOne = new Flat("Gran Via");
        flatTwo = new Flat("Sagrada");

        flatRepository.saveAll(List.of(flatOne, flatTwo));
        user.setFlat(flatOne);
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
        //attendanceRepository.deleteAll();
        //attendanceRepository.flush();
        //flatmateRepository.deleteAll();
        //flatmateRepository.flush();
        flatRepository.deleteAll();
        flatRepository.flush();
    }

    @Test
    @WithMockUser(username = "mike")
    void getAllFlats() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/flats"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Flat> flats = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Flat>>() {}
        );

        assertEquals(2, flats.size());
        assertTrue(flats.contains(flatOne));
        assertTrue(flats.contains(flatTwo));
    }

    @Test
    @WithMockUser(username = "marc")
    void getAllFlatsForbiddenUser() throws Exception {
        mockMvc.perform(get("/flats"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getExistentFlat() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/flats/" + flatOne.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Flat flat = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                Flat.class
        );

        assertEquals(flatOne, flat);
    }

    @Test
    @WithMockUser(username = "marc")
    void getExistentFlatNotAllowedUser() throws Exception {
        mockMvc.perform(get("/flats/" + flatOne.getId()))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getNonExistentFlat() throws Exception {
        mockMvc.perform(get("/flats/" + 100))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "marc")
    void addFlatEmptyUser() throws Exception {
        FlatDto flatDto = new FlatDto("Born");
        String body = objectMapper.writeValueAsString(flatDto);

        MvcResult mvcResult = mockMvc.perform(post("/flats")
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Flat createdFlat = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Flat.class);

        assertEquals(createdFlat.getName(), flatDto.getName());
    }

    @Test
    @WithMockUser(username = "mike")
    void addFlatFullUser() throws Exception {
        FlatDto flatDto = new FlatDto("Born");
        String body = objectMapper.writeValueAsString(flatDto);

        mockMvc.perform(post("/flats")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}