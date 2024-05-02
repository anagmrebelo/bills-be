package com.example.bills.service;

import com.example.bills.dto.FlatmateDto;
import com.example.bills.dto.FlatmateNameDto;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.FlatmateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FlatmateServiceTest {
    @Autowired
    FlatmateService flatmateService;
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
    void getAllFlatmates() {
        List<Flatmate> flatmates = flatmateService.getAllFlatmates();
        assertEquals(flatmateRepository.findAll().size(), flatmates.size());
        assertTrue(flatmates.contains(flatmateOne));
        assertTrue(flatmates.contains(flatmateTwo));
    }

    @Test
    void getExistentFlatmate() {
        Flatmate flatmate = flatmateService.getFlatmate(flatmateOne.getId());
        assertEquals(flatmateOne, flatmate);
    }

    @Test
    void getNonExistentFlatmate() {
        assertThrows(ResponseStatusException.class, () -> flatmateService.getFlatmate(100));
    }

    @Test
    void addFlatmate() {
        FlatmateDto flatmateDto = new FlatmateDto("Johana", flat);
        Flatmate createdFlatmate = flatmateService.addFlatmate(flatmateDto);

        assertEquals("Johana", createdFlatmate.getName());
        assertEquals(flat, createdFlatmate.getFlat());
        assertNotNull(flat.getFlatmateList());
        assertTrue(flat.getFlatmateList().contains(createdFlatmate));
    }

    @Test
    void patchFlatmate() {
        String patchedName = "Patched Name";
        Flatmate patchedFlatmate = flatmateService.patchFlatmate(flatmateOne.getId(), new FlatmateNameDto(patchedName));

        assertEquals(patchedName, patchedFlatmate.getName());
    }
}