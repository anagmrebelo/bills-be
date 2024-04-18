package com.example.bills.controller;

import com.example.bills.dto.FlatmateDto;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.FlatmateRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FlatmateControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    FlatmateRepository flatmateRepository;
    @Autowired
    FlatRepository flatRepository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Flat flat;
    private Flatmate flatmateOne;
    //private Flatmate flatmateTwo;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        flat = new Flat("Gran Via");
        flat = flatRepository.save(flat);

        flatmateOne = new Flatmate("Rita", flat);
        flatmateOne = flatmateRepository.save(flatmateOne);
    }

    @AfterEach
    void tearDown() {
        flatmateRepository.deleteAll();
        flatmateRepository.flush();

        flatRepository.deleteAll();
        flatRepository.flush();
    }

    @Test
    void getAllFlatmates() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/flatmates"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Flatmate> flatmates = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Flatmate>>() {}
        );

        assertEquals(1, flatmates.size());
        assertEquals(flatmateOne, flatmates.getFirst());
    }

    @Test
    void getValidFlatmate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/flatmates/" + flatmateOne.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Flatmate flatmateResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Flatmate.class);

        assertEquals(flatmateOne, flatmateResult);
    }

    @Test
    void getInvalidFlatmate() throws Exception {
        mockMvc.perform(get("/flatmates/" + 100))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void addFlatmate() throws Exception {
        String name = "Aitor";
        FlatmateDto flatmateDto = new FlatmateDto(name, flat);
        String body = objectMapper.writeValueAsString(flatmateDto);

        MvcResult mvcResult = mockMvc.perform(post("/flatmates")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Flatmate flatmateResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Flatmate.class);

        assertEquals(name, flatmateResult.getName());
        assertEquals(flat, flatmateResult.getFlat());
        //assertNotNull(flat.getFlatmateList());
        //assertTrue(flat.getFlatmateList().contains(flatmateResult));
    }

    @Test
    void patchFlatmate() {
    }
}