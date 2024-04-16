package com.example.bills.service;

import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.FlatmateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FlatmateServiceTest {
    @Autowired
    FlatmateRepository flatmateRepository;
    @Autowired
    FlatRepository flatRepository;
    Flatmate flatmateOne;
    Flatmate flatmateTwo;
    Flat flat;

    @BeforeEach
    void setUp() {
        flat = new Flat("Gran Via");
        flat = flatRepository.save(flat);

        flatmateOne = new Flatmate("Ben", flat);
        flatmateTwo = new Flatmate("Ana", flat);
        flatmateRepository.saveAll(List.of(flatmateOne, flatmateTwo));
    }

    @AfterEach
    void tearDown() {
        flatmateRepository.deleteAll();
        flatRepository.deleteAll();
        flatRepository.flush();
        flatmateRepository.flush();
    }

    @Test
    void getAllFlatmates() {
    }

    @Test
    void getFlatmate() {
    }

    @Test
    void addFlatmate() {
    }

    @Test
    void patchFlatmate() {
    }
}