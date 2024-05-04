package com.example.bills.controllers;

import com.example.bills.dtos.FlatDto;
import com.example.bills.models.Flat;
import com.example.bills.repositories.FlatRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FlatControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    FlatRepository flatRepository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Flat flatOne;
    private Flat flatTwo;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        flatOne = new Flat("Gran Via");
        flatTwo = new Flat("Sagrada");

        flatRepository.saveAll(List.of(flatOne, flatTwo));
    }

    @AfterEach
    void tearDown() {
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
    void getNonExistentFlat() throws Exception {
        mockMvc.perform(get("/flats/" + 100))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void addFlat() throws Exception {
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
}