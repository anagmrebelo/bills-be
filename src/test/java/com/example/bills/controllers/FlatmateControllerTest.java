package com.example.bills.controllers;

import com.example.bills.dtos.FlatmateNameDto;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FlatmateControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    FlatmateRepository flatmateRepository;
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Flat flat;
    private Flatmate flatmateOne;
    private User user;

    @BeforeEach
    void setUp() {
        user = userService.saveUser(new User(null, "Mike", "mike", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_ADMIN");

        userService.saveUser(new User(null, "Marc", "marc", "1234", new ArrayList<>(), null));
        userService.addRoleToUser("mike", "ROLE_USER");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        flat = new Flat("Gran Via");
        flat = flatRepository.save(flat);

        flatmateOne = new Flatmate("Rita", flat);
        flatmateOne = flatmateRepository.save(flatmateOne);

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
        //attendanceRepository.deleteAll();
        //attendanceRepository.flush();
        flatmateRepository.deleteAll();
        flatmateRepository.flush();
        flatRepository.deleteAll();
        flatRepository.flush();
    }

    @Test
    @WithMockUser(username = "mike")
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
    @WithMockUser(username = "marc")
    void getAllFlatmatesForbiddenUser() throws Exception{
        mockMvc.perform(get("/flatmates"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void getValidFlatmate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/flatmates/" + flatmateOne.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Flatmate flatmateResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Flatmate.class);

        assertEquals(flatmateOne, flatmateResult);
    }

    @Test
    @WithMockUser(username = "marc")
    void getValidFlatmateForbiddenUser() throws Exception {
        mockMvc.perform(get("/flatmates/" + flatmateOne.getId()))
                .andExpect(status().isForbidden())
                .andReturn();

    }

    @Test
    @WithMockUser(username = "mike")
    void getInvalidFlatmate() throws Exception {
        int invalidFlatmateId = 100;
        mockMvc.perform(get("/flatmates/" + invalidFlatmateId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void addFlatmate() throws Exception {
        String name = "Aitor";
        Flatmate flatmateDto = new Flatmate(name, flat);
        String body = objectMapper.writeValueAsString(flatmateDto);

        MvcResult mvcResult = mockMvc.perform(post("/flatmates")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Flatmate flatmateResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Flatmate.class);

        assertEquals(name, flatmateResult.getName());
        assertEquals(flat, flatmateResult.getFlat());

        Optional<Flat> dbFlat = flatRepository.findById(flat.getId());
        assertTrue(dbFlat.isPresent());
    }

    @Test
    @WithMockUser(username = "marc")
    void addFlatmateForbiddenUser() throws Exception {
        String name = "Aitor";
        Flatmate flatmateDto = new Flatmate(name, flat);
        String body = objectMapper.writeValueAsString(flatmateDto);

        mockMvc.perform(post("/flatmates")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "mike")
    void patchFlatmate() throws Exception {
        String patchedName = "Patched Name";
        FlatmateNameDto flatmateNameDto = new FlatmateNameDto(patchedName);

        String body = objectMapper.writeValueAsString(flatmateNameDto);
        MvcResult mvcResult = mockMvc.perform(patch("/flatmates/" + flatmateOne.getId())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Flatmate flatmateResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Flatmate.class);

        assertEquals(patchedName, flatmateResult.getName());
    }

    @Test
    @WithMockUser(username = "marc")
    void patchFlatmateForbiddenUser() throws Exception {
        String patchedName = "Patched Name";
        FlatmateNameDto flatmateNameDto = new FlatmateNameDto(patchedName);

        String body = objectMapper.writeValueAsString(flatmateNameDto);
        mockMvc.perform(patch("/flatmates/" + flatmateOne.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

}