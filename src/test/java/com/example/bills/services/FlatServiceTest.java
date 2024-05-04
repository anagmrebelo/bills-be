package com.example.bills.services;

import com.example.bills.dtos.FlatDto;
import com.example.bills.models.Flat;
import com.example.bills.repositories.FlatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlatServiceTest {
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    FlatService flatService;
    private Flat flatOne;
    private Flat flatTwo;

    @BeforeEach
    void setUp() {
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
    void getAllFlats() {
        List<Flat> flatsResult = flatService.getAllFlats();
        assertEquals(2, flatsResult.size());
        assertTrue(flatsResult.contains(flatOne));
        assertTrue(flatsResult.contains(flatTwo));
    }

    @Test
    void getValidFlat() {
        Flat flatResult = flatService.getFlat(flatOne.getId());
        assertEquals(flatOne, flatResult);
    }

    @Test
    void getInValidFlat() {
        assertThrows(ResponseStatusException.class, () -> flatService.getFlat(100));
    }

    @Test
    void addFlat() {
        String name = "Loreto";
        Flat flatResult = flatService.addFlat(new FlatDto(name));

        Optional<Flat> createdFlat = flatRepository.findById(flatResult.getId());
        assertTrue(createdFlat.isPresent());
        assertEquals(name, createdFlat.get().getName());
    }
}